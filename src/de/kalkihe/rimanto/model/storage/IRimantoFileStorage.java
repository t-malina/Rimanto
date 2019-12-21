package de.kalkihe.rimanto.model.storage;

import de.kalkihe.rimanto.model.data.IProject;

import java.util.List;

public interface IRimantoFileStorage {
  /*
   * Method to read Projects from saved data from hard drive
   * Returns list with all read projects
   */
  List<IProject> readProjects();
}
