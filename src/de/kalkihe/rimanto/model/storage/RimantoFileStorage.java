package de.kalkihe.rimanto.model.storage;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.wordbook.IWordbook;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the interface to save and read files from the disk
 */
public class RimantoFileStorage implements IRimantoFileStorage {
  /**
   * Path to the folder that all files are saved in
   */
  private Path pathToStorageFolder;

  /**
   * File format for projects
   */
  private String projectFileFormat = ".rmtp";

  /**
   * File format for risks
   */
  private String riskFileFormat = ".rmtr";

  /**
   * Complete filename of the wordbook
   */
  private String wordbookFilename = "settings.rmt";

  /**
   * List of all projects that are currently stored
   */
  private List<IProject> currentProjects;

  /**
   * Constructor.
   * Initializes the path to the folder all files are saved in
   */
  public RimantoFileStorage() {
    this.pathToStorageFolder = Paths.get(System.getProperty("user.home"), "Rimanto");
    this.checkPathToStorageFolder();
  }

  /**
   * Checks if the storage folder exists.
   * Creates the folder if it does not exist.
   */
  private void checkPathToStorageFolder()
  {
    File folder = this.pathToStorageFolder.toFile();
    boolean exists = folder.exists();
    if (!exists)
    {
      folder.mkdir();
    }
  }

  /**
   * Writes any serializable object to any file
   * @param object Object that is to write to disk
   * @param file File the object is to be written to
   * @throws IOException
   */
  private void writeObjectToDisk(Object object, File file) throws IOException {
    // Create output streams
    FileOutputStream fileOutputStream = null;
    ObjectOutputStream objectOutputStream = null;
    fileOutputStream = new FileOutputStream(file);
    objectOutputStream = new ObjectOutputStream(fileOutputStream);
    // Write the object to the file
    objectOutputStream.writeObject(object);
    // If opening of the streams was successfull, close them
    if (fileOutputStream != null)
    {
      fileOutputStream.close();
    }
    if (objectOutputStream != null) {
      objectOutputStream.close();
    }
  }

  /**
   * Writes a project to any file
   * @param project The project that is to write to disk
   * @param file The file the project is to write to
   * @throws IOException
   */
  private void writeProjectToDisk(IProject project, File file) throws IOException {
    this.writeObjectToDisk(project, file);
  }


  /**
   * Writes the passed project to a file with the passed filename under the passed folder
   * @param project The project that is to be written to disk
   * @param fileName The name of the file that the project is to be written to
   * @param folder The folder under that the project is to be saved
   * @throws IOException
   */
  private void writeProjectToDisk(IProject project, String fileName, Path folder) throws IOException {
    File file = new File(folder.toString(), fileName + this.projectFileFormat);
    this.writeProjectToDisk(project, file);
  }

  /**
   * Method to read Projects from saved data from hard drive
   * @return List with all read projects
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public List<IProject> readProjects() throws IOException, ClassNotFoundException {
    // Create new List of projects
    List<IProject> resultList = new ArrayList<IProject>();
    // Get the folder with rimanto files in it
    File folder = this.pathToStorageFolder.toFile();
    // Iterate over every file in this folder
    for (File file : folder.listFiles())
    {
      // Check if the current file is a project
      if (file.toString().endsWith(this.projectFileFormat))
      {
        // Read the project and add it to the list of projects
        resultList.add(this.readProject(file));
      }
    }
    // Save the project list
    this.currentProjects = resultList;
    // Return the project list
    return resultList;
  }

  /**
   * Reads the passed file and returns it contained objects
   * @param fileToRead The files that is to read
   * @return The object that the file contains
   * @throws IOException
   * @throws ClassNotFoundException
   */
  private Object readFile(File fileToRead) throws IOException, ClassNotFoundException {
    // Open streams
    FileInputStream fileInputStream = new FileInputStream(fileToRead);
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
    // Read the Object
    Object object =   objectInputStream.readObject();
    // Close the streams
    fileInputStream.close();
    objectInputStream.close();
    // Return the object
    return object;
  }

  /**
   * Read a project from the passed file
   * @param fileToRead The file that is to read
   * @return The project that the file contained
   * @throws IOException
   * @throws ClassNotFoundException
   */
  private IProject readProject(File fileToRead) throws IOException, ClassNotFoundException {
    IProject project = (IProject) this.readFile(fileToRead);
    return project;
  }

  /**
   * Read a risk from the passed file
   * @param fileToRead The file that is to read
   * @return The risk that the file contained
   * @throws IOException
   * @throws ClassNotFoundException
   */
  private IRisk readRisk(File fileToRead) throws IOException, ClassNotFoundException {
    IRisk risk = (IRisk) this.readFile(fileToRead);
    return risk;
  }

  /**
   * Saves a project in the default folder
   * @param project The project that is to save
   * @throws IOException
   */
  @Override
  public void saveProject(IProject project) throws IOException {
    this.writeProjectToDisk(project, project.getUuid().toString(), this.pathToStorageFolder);
  }

  /**
   * @return The file format in which projects are saved
   */
  @Override
  public String getProjectFileFormat() {
    return this.projectFileFormat;
  }

  /**
   *
   * @return The file format in which risks are saved
   */
  @Override
  public String getRiskFileFormat() {
    return this.riskFileFormat;
  }

  /**
   * Method to import a project and add it to list of all projects
   * @param importFile File that contains a project that is to import
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public void importProject(File importFile) throws IOException, ClassNotFoundException {
    // Read the project
    IProject readProject = this.readProject(importFile);
    // Generate a file object with that the project should be saved in the default folder
    File targetFile = new File(this.pathToStorageFolder.toString(), readProject.getUuid() + this.projectFileFormat);
    // If the project file already exists
    if (targetFile.exists())
    {
      // Throw an exception
      throw new FileAlreadyExistsException(targetFile.getAbsolutePath());
    }
    // If the project file does not already exist
    else
    {
      // Iterate over all projects
      for(IProject project : this.currentProjects)
      {
        // Check if the project is already there
        if (project.equals(readProject))
        {
          throw new FileAlreadyExistsException(targetFile.getAbsolutePath());
        }
      }
      // If the project is not already there, copy the files to the default folder
      Files.copy(importFile.toPath(), targetFile.toPath());
    }
  }

  /**
   * Deletes the passed project
   * @param project The project that is to delete
   */
  @Override
  public void deleteProject(IProject project) {
    File file = new File(this.pathToStorageFolder.toString(), project.getUuid() + this.projectFileFormat);
    file.delete();
  }

  /**
   * Export the passed project to the passed file
   * @param project The project that is to export
   * @param exportFile The file to that the project is to export to
   * @throws IOException
   */
  @Override
  public void exportProject(IProject project, File exportFile) throws IOException {
    this.writeProjectToDisk(project, exportFile);
  }

  /**
   * Imports a Risk and returns it
   * @param importFile The file from which the risk is to import
   * @return The imported risk
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public IRisk importRisk(File importFile) throws IOException, ClassNotFoundException {
    return this.readRisk(importFile);
  }

  /**
   * Exports the passed risk to the passed file
   * @param risk The risk that is to export
   * @param exportFile The file to that the risk is to export to
   * @throws IOException
   */
  @Override
  public void exportRisk(IRisk risk, File exportFile) throws IOException {
   this.writeObjectToDisk(risk, exportFile);
  }

  /**
   * Saves the passed wordbook
   * @param wordbook The wordbook that is to save
   * @throws IOException
   */
  @Override
  public void saveWordbook(IWordbook wordbook) throws IOException {
    File writeFile = new File(this.pathToStorageFolder.toString(), this.wordbookFilename);
    this.writeObjectToDisk(wordbook, writeFile);
  }

  /**
   * Reads the wordbook from the disk
   * @return The read wordbook from the disk
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public IWordbook readWordbook() throws IOException, ClassNotFoundException {
    File readFile = new File(this.pathToStorageFolder.toString(), this.wordbookFilename);
    return (IWordbook) this.readFile(readFile);
  }
}
