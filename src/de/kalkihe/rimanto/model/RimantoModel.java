package de.kalkihe.rimanto.model;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.storage.IRimantoFileStorage;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;

import java.io.File;
import java.io.IOException;
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
  public void importProject(File importFile) throws IOException, ClassNotFoundException {
    this.rimantoFileStorage.importProject(importFile);
  }

  @Override
  public void addRiskToProject(IProject project, IRisk risk, List<IProject> furtherProjects) throws CloneNotSupportedException, IOException {
    project.addRisk(risk);
    for(IProject currentProject : furtherProjects)
    {
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
}
