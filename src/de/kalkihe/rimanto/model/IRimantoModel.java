package de.kalkihe.rimanto.model;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.wordbook.IWordbook;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Interface for the model
 */
public interface IRimantoModel {

  /**
   * Method to initialize model and data
   * @throws Exception
   */
  void initializeModel() throws Exception;

  /**
   *
   * @return List of current projects
   * @throws IOException
   * @throws ClassNotFoundException
   */
  List<IProject> getProjectList() throws IOException, ClassNotFoundException;

  /**
   * @param project Project to read risks of
   * @return List of risks of the passed project
   */
  List<IRisk> getRisksOfProject(IProject project);

  /**
   * Method to create a new project
   * @param project Project to create
   * @throws IOException
   */
  void createNewProject(IProject project) throws IOException;

  /**
   *
   * @return The file format projects are saved in
   */
  String getProjectFileFormat();

  /**
   *
   * @return The file format risks are saved in
   */
  String getRiskFileFormat();

  /**
   * Imports a project from the passed file
   * @param importFile The file the project is to import from
   * @throws IOException
   * @throws ClassNotFoundException
   */
  void importProject(File importFile) throws IOException, ClassNotFoundException;

  /**
   * Adds the passed risk to the passed project
   * @param project The project to that the risk is to be added
   * @param risk The risk that is to be added to the project
   * @throws Exception
   */
  void addRiskToProject(IProject project, IRisk risk) throws Exception;

  /**
   * Adopts the data from newProject to oldProject
   * @param oldProject The project which data is to be changed
   * @param newProject The project which data the oldProject should adopt
   * @throws IOException
   */
  void editProject(IProject oldProject, IProject newProject) throws IOException;

  /**
   * Deletes the passed project
   * @param project The project that is to delete
   */
  void deleteProject(IProject project);

  /**
   * Exports the passed project to the passed file
   * @param project The project that is to export
   * @param exportFile The file the project is to export to
   * @throws IOException
   */
  void exportProject(IProject project, File exportFile) throws IOException;

  /**
   * Imports a risk from the passed file and adds it to the passed project
   * @param importFile The file the risk is to import from
   * @param project The project the risk is to add to after import
   * @throws IOException
   * @throws ClassNotFoundException
   */
  void importRisk(File importFile, IProject project) throws IOException, ClassNotFoundException;

  /**
   * Edits the risk of a project
   * @param project The project the risks belongs to
   * @param oldRisk The risk that is to edit
   * @param newRisk A risk with all the data the oldRisk should adopt
   * @throws Exception
   */
  void editRisk(IProject project, IRisk oldRisk, IRisk newRisk) throws Exception;

  /**
   * Deletes the passed risk from the passed project
   * @param project The project the risk is to delete from
   * @param risk The risk to delete
   * @throws IOException
   */
  void deleteRisk(IProject project, IRisk risk) throws IOException;

  /**
   * Export a risk to the passed file
   * @param risk The risk that is to export
   * @param exportFile The file the risk is to export to
   * @throws IOException
   */
  void exportRisk(IRisk risk, File exportFile) throws IOException;

  /**
   * Sets the passed project as reviewed
   * @param project The project that is to be set as reviewed
   * @throws IOException
   */
  void setProjectAsReviewed(IProject project) throws IOException;

  /**
   * Sets the passed Risk as reviewed
   * @param project The project the risk belongs to
   * @param risk The risk that is to be set as reviewed
   * @throws IOException
   */
  void setRiskAsReviewed(IProject project, IRisk risk) throws IOException;

  /**
   * Saves the wordbook to tisk
   * @param wordbook The wordbook that is to save
   * @throws IOException
   */
  void saveWordbook(IWordbook wordbook) throws IOException;

  /**
   * Reads and returns the wordbook from disk
   * @return The read wordbook
   * @throws IOException
   * @throws ClassNotFoundException
   */
  IWordbook readWordbook() throws IOException, ClassNotFoundException;
}
