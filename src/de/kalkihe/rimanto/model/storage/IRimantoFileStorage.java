package de.kalkihe.rimanto.model.storage;

import de.kalkihe.rimanto.model.data.IProject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IRimantoFileStorage {
  /*
   * Method to read Projects from saved data from hard drive
   * Returns list with all read projects
   */
  List<IProject> readProjects() throws IOException, ClassNotFoundException;

  void saveNewProject(IProject project) throws IOException;

  String getProjectFileFormat();

  void importProject(File importFile);

}
