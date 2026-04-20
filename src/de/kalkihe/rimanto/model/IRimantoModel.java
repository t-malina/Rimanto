package de.kalkihe.rimanto.model;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

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

  void importProject(File importFile) throws IOException;
}
