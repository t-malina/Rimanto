package de.kalkihe.rimanto.model.storage;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.wordbook.IWordbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

/**
 * Interface to save and read files from disk
 */
public interface IRimantoFileStorage {
  /**
   * Method to read Projects from saved data from hard drive
   * @return List with all read projects
   * @throws IOException
   * @throws ClassNotFoundException
   */
  List<IProject> readProjects() throws IOException, ClassNotFoundException;

  /**
   * Writes the passed project to disk
   * @param project The project that is to save
   * @throws IOException
   */
  void saveProject(IProject project) throws IOException;

  /**
   * @return The file format in which projects are saved
   */
  String getProjectFileFormat();

  /**
   *
   * @return The file format in which risks are saved
   */
  String getRiskFileFormat();

  /**
   * Method to import a project and add it to list of all projects
   * @param importFile File that contains a project that is to import
   * @throws IOException
   * @throws ClassNotFoundException
   */
  void importProject(File importFile) throws IOException, ClassNotFoundException;

  /**
   * Deletes the passed project
   * @param project The project that is to delete
   */
  void deleteProject(IProject project);

  /**
   * Export the passed project to the passed file
   * @param project The project that is to export
   * @param exportFile The file to that the project is to export to
   * @throws IOException
   */
  void exportProject(IProject project, File exportFile) throws IOException;

  /**
   * Imports a Risk and returns it
   * @param importFile The file from which the risk is to import
   * @return The imported risk
   * @throws IOException
   * @throws ClassNotFoundException
   */
  IRisk importRisk(File importFile) throws IOException, ClassNotFoundException;

  /**
   * Exports the passed risk to the passed file
   * @param risk The risk that is to export
   * @param exportFile The file to that the risk is to export to
   * @throws IOException
   */
  void exportRisk(IRisk risk, File exportFile) throws IOException;

  /**
   * Saves the passed wordbook
   * @param wordbook The wordbook that is to save
   * @throws IOException
   */
  void saveWordbook(IWordbook wordbook) throws IOException;

  /**
   * Reads the wordbook from the disk
   * @return The read wordbook from the disk
   * @throws IOException
   * @throws ClassNotFoundException
   */
  IWordbook readWordbook() throws IOException, ClassNotFoundException;

}
