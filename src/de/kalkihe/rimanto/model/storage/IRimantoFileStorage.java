package de.kalkihe.rimanto.model.storage;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.wordbook.IWordbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public interface IRimantoFileStorage {
  /*
   * Method to read Projects from saved data from hard drive
   * Returns list with all read projects
   */
  List<IProject> readProjects() throws IOException, ClassNotFoundException;

  void saveProject(IProject project) throws IOException;

  String getProjectFileFormat();

  String getRiskFileFormat();

  void importProject(File importFile) throws IOException, ClassNotFoundException;

  void deleteProject(IProject project);

  void exportProject(IProject project, File exportFile) throws IOException;

  IRisk importRisk(File importFile) throws IOException, ClassNotFoundException;

  void exportRisk(IRisk risk, File exportFile) throws IOException;

  void saveWordbook(IWordbook wordbook) throws IOException;

  IWordbook readWordbook() throws IOException, ClassNotFoundException;

}
