package de.kalkihe.rimanto.model;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.wordbook.IWordbook;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IRimantoModel {
  /*
   * Method to initialize model and data
   */
  void initializeModel() throws Exception;

  /*
   * Returns a list with all projects
   */
  List<IProject> getProjectList() throws IOException, ClassNotFoundException;

  /*
   * Returns a list with all risks of a ǵiven project
   */
  List<IRisk> getRisksOfProject(IProject project);

  void createNewProject(IProject project) throws IOException;

  String getProjectFileFormat();

  String getRiskFileFormat();

  void importProject(File importFile) throws IOException, ClassNotFoundException;

  void addRiskToProject(IProject project, IRisk risk) throws Exception;

  void editProject(IProject oldProject, IProject newProject) throws IOException;

  void deleteProject(IProject project);

  void exportProject(IProject project, File exportFile) throws IOException;

  void importRisk(File importFile, IProject project) throws IOException, ClassNotFoundException;

  void editRisk(IProject project, IRisk oldRisk, IRisk newRisk) throws Exception;

  void deleteRisk(IProject project, IRisk risk) throws IOException;

  void exportRisk(IRisk risk, File exportFile) throws IOException;

  void setProjectAsReviewed(IProject project) throws IOException;

  void setRiskAsReviewed(IProject project, IRisk risk) throws IOException;

  void saveWordbook(IWordbook wordbook) throws IOException;

  IWordbook readWordbook() throws IOException, ClassNotFoundException;
}
