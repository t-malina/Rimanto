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
  // List with all projects existing
  private List<IProject> projectList;
  // Needed references to objects
  private RimantoIOCContainer rimantoIOCContainer;
  private IRimantoFileStorage rimantoFileStorage;
  private IWordbook wordbook;

  /*
   * Initializes the model and data
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

  /*
   * Return the list with all projects
   */
  @Override
  public List<IProject> getProjectList() throws IOException, ClassNotFoundException {
    return this.rimantoFileStorage.readProjects();
  }

  /*
   * Return a list of all risks for the given project
   */
  @Override
  public List<IRisk> getRisksOfProject(IProject project) {
    return project.getProjectRisks();
  }

  @Override
  public void createNewProject(IProject project) throws IOException {
    this.rimantoFileStorage.saveProject(project);
  }

  @Override
  public String getProjectFileFormat() {
    return this.rimantoFileStorage.getProjectFileFormat();
  }

  @Override
  public String getRiskFileFormat() {
    return this.rimantoFileStorage.getRiskFileFormat();
  }

  @Override
  public void importProject(File importFile) throws IOException, ClassNotFoundException {
    this.rimantoFileStorage.importProject(importFile);
  }

  @Override
  public void addRiskToProject(IProject project, IRisk risk) throws Exception {
    project.addRisk(risk);
    for(int index = 0; index < risk.getImpactOfRiskOnOtherProjects().size(); index++)
    {
      IProject currentProject = risk.getImpactOfRiskOnOtherProjects().get(index);
      IRisk newRisk = (IRisk) risk.clone();
      newRisk.makeAnnotatedRisk();
      newRisk.annotateRiskSource(project, risk.getCategoryOfImpactOnOtherProjects());
      currentProject.addRisk(newRisk);
      this.rimantoFileStorage.saveProject(currentProject);
    }
    this.rimantoFileStorage.saveProject(project);
  }

  @Override
  public void editProject(IProject oldProject, IProject newProject) throws IOException {
    oldProject.editProjectData(newProject);
    this.rimantoFileStorage.saveProject(oldProject);
  }

  @Override
  public void deleteProject(IProject project) {
    this.rimantoFileStorage.deleteProject(project);
  }

  @Override
  public void exportProject(IProject project, File exportFile) throws IOException {
    this.rimantoFileStorage.exportProject(project, exportFile);
  }

  @Override
  public void importRisk(File importFile, IProject project) throws IOException, ClassNotFoundException {
    IRisk risk = this.rimantoFileStorage.importRisk(importFile);
    if(project.getProjectRisks().contains(risk))
    {
      throw new FileAlreadyExistsException(this.wordbook.getWordForWithCapitalLeadingLetter("risk_already_in_project"));
    }
    project.addRisk(risk);
    this.rimantoFileStorage.saveProject(project);
  }

  @Override
  public void editRisk(IProject project, IRisk oldRisk, IRisk newRisk) throws Exception {
    for (int index = 0; index < newRisk.getImpactOfRiskOnOtherProjects().size(); index++)
    {
      IProject currentProject = newRisk.getImpactOfRiskOnOtherProjects().get(index);
      boolean isNewRiskToAppend = ! oldRisk.getImpactOfRiskOnOtherProjects().contains(currentProject);
      if (isNewRiskToAppend)
      {
        IRisk riskToAppend = (IRisk) newRisk.clone();
        riskToAppend.makeAnnotatedRisk();
        riskToAppend.annotateRiskSource(project, newRisk.getCategoryOfImpactOnOtherProjects());
        currentProject.addRisk(riskToAppend);
        this.rimantoFileStorage.saveProject(currentProject);
      }
    }
    oldRisk.editRiskData(newRisk);
    this.rimantoFileStorage.saveProject(project);
  }

  @Override
  public void deleteRisk(IProject project, IRisk risk) throws IOException {
    project.deleteRisk(risk);
    this.rimantoFileStorage.saveProject(project);
  }

  @Override
  public void exportRisk(IRisk risk, File exportFile) throws IOException {
    this.rimantoFileStorage.exportRisk(risk, exportFile);
  }

  @Override
  public void setProjectAsReviewed(IProject project) throws IOException {
    project.resetReview();
    this.rimantoFileStorage.saveProject(project);
  }

  @Override
  public void setRiskAsReviewed(IProject project, IRisk risk) throws IOException {
    risk.resetReview();
    this.rimantoFileStorage.saveProject(project);
  }

  @Override
  public void saveWordbook(IWordbook wordbook) throws IOException {
    this.rimantoFileStorage.saveWordbook(wordbook);
  }

  @Override
  public IWordbook readWordbook() throws IOException, ClassNotFoundException {
    return this.rimantoFileStorage.readWordbook();
  }
}
