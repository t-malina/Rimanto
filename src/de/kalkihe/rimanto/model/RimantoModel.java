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
    this.rimantoFileStorage.saveNewProject(project);
  }

  @Override
  public String getProjectFileFormat() {
    return this.rimantoFileStorage.getProjectFileFormat();
  }

  @Override
  public void importProject(File importFile) throws IOException {
    this.rimantoFileStorage.importProject(importFile);
  }
}
