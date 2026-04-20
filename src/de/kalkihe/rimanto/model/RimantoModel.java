package de.kalkihe.rimanto.model;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.storage.IRimantoFileStorage;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public class RimantoModel implements de.kalkihe.rimanto.model.IRimantoModel {
  /**
    * List with all projects existing
    */
  private List<IProject> projectList;
  /**
    * Needed references to objects
    */
  private RimantoIOCContainer rimantoIOCContainer;
  private IRimantoFileStorage rimantoFileStorage;
  private IWordbook wordbook;

  /**
   * Method to initialize model and data
   * @throws Exception
   */
  @Override
  public void initializeModel() throws Exception {
    // Get instance of ioc-container
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();
    // Get instance for file storage
    this.rimantoFileStorage = (IRimantoFileStorage) this.rimantoIOCContainer.getInstanceFor(IRimantoFileStorage.class);
    // Read projects and save them in list
    this.projectList = this.rimantoFileStorage.readProjects();
    // Get reference to wordbook
    this.wordbook = (IWordbook) this.rimantoIOCContainer.getInstanceFor(IWordbook.class);
  }

  /**
   *
   * @return List of current projects
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public List<IProject> getProjectList() throws IOException, ClassNotFoundException {
    return this.rimantoFileStorage.readProjects();
  }

  /**
   * @param project Project to read risks of
   * @return List of risks of the passed project
   */
  @Override
  public List<IRisk> getRisksOfProject(IProject project) {
    return project.getProjectRisks();
  }

  /**
   * Method to create a new project
   * @param project Project to create
   * @throws IOException
   */
  @Override
  public void createNewProject(IProject project) throws IOException {
    this.rimantoFileStorage.saveProject(project);
  }

  /**
   *
   * @return The file format projects are saved in
   */
  @Override
  public String getProjectFileFormat() {
    return this.rimantoFileStorage.getProjectFileFormat();
  }

  /**
   *
   * @return The file format risks are saved in
   */
  @Override
  public String getRiskFileFormat() {
    return this.rimantoFileStorage.getRiskFileFormat();
  }

  /**
   * Imports a project from the passed file
   * @param importFile The file the project is to import from
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public void importProject(File importFile) throws IOException, ClassNotFoundException {
    this.rimantoFileStorage.importProject(importFile);
  }

  /**
   * Adds the passed risk to the passed project
   * @param project The project to that the risk is to be added
   * @param risk The risk that is to be added to the project
   * @throws Exception
   */
  @Override
  public void addRiskToProject(IProject project, IRisk risk) throws Exception {
    // Add risk to project
    project.addRisk(risk);
    // Iterate over all other project, the risk has impact on
    for(int index = 0; index < risk.getImpactOfRiskOnOtherProjects().size(); index++)
    {
      // Read current project
      IProject currentProject = risk.getImpactOfRiskOnOtherProjects().get(index);
      // Add clone of risk to the project
      IRisk newRisk = (IRisk) risk.clone();
      // Make that risk an risk added from other project
      newRisk.makeAnnotatedRisk();
      newRisk.annotateRiskSource(project, risk.getCategoryOfImpactOnOtherProjects());
      // Add that risk to the current project
      currentProject.addRisk(newRisk);
      // Write the current project to disk
      this.rimantoFileStorage.saveProject(currentProject);
    }
    // Write the project to disk
    this.rimantoFileStorage.saveProject(project);
  }

  /**
   * Adopts the data from newProject to oldProject
   * @param oldProject The project which data is to be changed
   * @param newProject The project which data the oldProject should adopt
   * @throws IOException
   */
  @Override
  public void editProject(IProject oldProject, IProject newProject) throws IOException {
    // Edit project
    oldProject.editProjectData(newProject);
    // Write project to disk
    this.rimantoFileStorage.saveProject(oldProject);
  }

  /**
   * Deletes the passed project
   * @param project The project that is to delete
   */
  @Override
  public void deleteProject(IProject project) {
    this.rimantoFileStorage.deleteProject(project);
  }

  /**
   * Exports the passed project to the passed file
   * @param project The project that is to export
   * @param exportFile The file the project is to export to
   * @throws IOException
   */
  @Override
  public void exportProject(IProject project, File exportFile) throws IOException {
    this.rimantoFileStorage.exportProject(project, exportFile);
  }

  /**
   * Imports a risk from the passed file and adds it to the passed project
   * @param importFile The file the risk is to import from
   * @param project The project the risk is to add to after import
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public void importRisk(File importFile, IProject project) throws IOException, ClassNotFoundException {
    // Read risk
    IRisk risk = this.rimantoFileStorage.importRisk(importFile);
    // If projects already contains that risk, throw exception
    if(project.getProjectRisks().contains(risk))
    {
      throw new FileAlreadyExistsException(this.wordbook.getWordForWithCapitalLeadingLetter("risk_already_in_project"));
    }
    // Add risk to project
    project.addRisk(risk);
    // Write project to disk
    this.rimantoFileStorage.saveProject(project);
  }

  /**
   * Deletes the passed risk from the passed project
   * @param project The project the risk is to delete from
   * @param risk The risk to delete
   * @throws IOException
   */
  @Override
  public void editRisk(IProject project, IRisk oldRisk, IRisk newRisk) throws Exception {
    // Iterate over all other project the new risk has impact on
    for (int index = 0; index < newRisk.getImpactOfRiskOnOtherProjects().size(); index++)
    {
      // read current project
      IProject currentProject = newRisk.getImpactOfRiskOnOtherProjects().get(index);
      // Check if the risk to edit already is part of the current project
      boolean isNewRiskToAppend = ! oldRisk.getImpactOfRiskOnOtherProjects().contains(currentProject);
      // If the risk to edit did not already appear in the current project
      if (isNewRiskToAppend)
      {
        // Clone the risk
        IRisk riskToAppend = (IRisk) newRisk.clone();
        // Mark the risk as risk that is comming from different project
        riskToAppend.makeAnnotatedRisk();
        riskToAppend.annotateRiskSource(project, newRisk.getCategoryOfImpactOnOtherProjects());
        // add the risk to the current project
        currentProject.addRisk(riskToAppend);
        // write current project to disk
        this.rimantoFileStorage.saveProject(currentProject);
      }
    }
    // Edit the risk
    oldRisk.editRiskData(newRisk);
    // Write project to disk
    this.rimantoFileStorage.saveProject(project);
  }

  /**
   * Deletes the passed risk from the passed project
   * @param project The project the risk is to delete from
   * @param risk The risk to delete
   * @throws IOException
   */
  @Override
  public void deleteRisk(IProject project, IRisk risk) throws IOException {
    project.deleteRisk(risk);
    this.rimantoFileStorage.saveProject(project);
  }

  /**
   * Export a risk to the passed file
   * @param risk The risk that is to export
   * @param exportFile The file the risk is to export to
   * @throws IOException
   */
  @Override
  public void exportRisk(IRisk risk, File exportFile) throws IOException {
    this.rimantoFileStorage.exportRisk(risk, exportFile);
  }

  /**
   * Sets the passed project as reviewed
   * @param project The project that is to be set as reviewed
   * @throws IOException
   */
  @Override
  public void setProjectAsReviewed(IProject project) throws IOException {
    project.resetReview();
    this.rimantoFileStorage.saveProject(project);
  }

  /**
   * Sets the passed Risk as reviewed
   * @param project The project the risk belongs to
   * @param risk The risk that is to be set as reviewed
   * @throws IOException
   */
  @Override
  public void setRiskAsReviewed(IProject project, IRisk risk) throws IOException {
    risk.resetReview();
    this.rimantoFileStorage.saveProject(project);
  }

  /**
   * Saves the wordbook to tisk
   * @param wordbook The wordbook that is to save
   * @throws IOException
   */
  @Override
  public void saveWordbook(IWordbook wordbook) throws IOException {
    this.rimantoFileStorage.saveWordbook(wordbook);
  }

  /**
   * Reads and returns the wordbook from disk
   * @return The read wordbook
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public IWordbook readWordbook() throws IOException, ClassNotFoundException {
    return this.rimantoFileStorage.readWordbook();
  }
}
