package de.kalkihe.rimanto.model.storage;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RimantoFileStorage implements IRimantoFileStorage {
  private Path pathToStorageFolder;
  private String projectFileFormat = ".rmt";

  private List<IProject> currentProjects;

  public RimantoFileStorage() {
    this.pathToStorageFolder = Paths.get(System.getProperty("user.home"), "Rimanto");
    this.checkPathToStorageFolder();
  }

  private void checkPathToStorageFolder()
  {
    File folder = this.pathToStorageFolder.toFile();
    boolean exists = folder.exists();
    if (!exists)
    {
      folder.mkdir();
    }
  }

  private void writeProjectToDisk(IProject project, File file) throws IOException {
    FileOutputStream fileOutputStream = null;
    ObjectOutputStream objectOutputStream = null;
    fileOutputStream = new FileOutputStream(file);
    objectOutputStream = new ObjectOutputStream(fileOutputStream);
    objectOutputStream.writeObject(project);
    if (fileOutputStream != null)
    {
      fileOutputStream.close();
    }
    if (objectOutputStream != null) {
      objectOutputStream.close();
    }
  }

  private void writeProjectToDisk(IProject project, String fileName, Path folder) throws IOException {
    File file = new File(folder.toString(), fileName + this.projectFileFormat);
    this.writeProjectToDisk(project, file);
  }

  @Override
  public List<IProject> readProjects() throws IOException, ClassNotFoundException {
    List<IProject> resultList = new ArrayList<IProject>();
    File folder = this.pathToStorageFolder.toFile();
    for (File file : folder.listFiles())
    {
      if (file.toString().endsWith(this.projectFileFormat))
      {
        resultList.add(this.readProject(file));
      }
    }
    this.currentProjects = resultList;
    return resultList;
  }

  private IProject readProject(File fileToRead) throws IOException, ClassNotFoundException {
    FileInputStream fileInputStream = new FileInputStream(fileToRead);
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
    IProject project = (IProject) objectInputStream.readObject();
    objectInputStream.close();
    fileInputStream.close();
    return project;
  }

  @Override
  public void saveProject(IProject project) throws IOException {
    this.writeProjectToDisk(project, project.getUuid().toString(), this.pathToStorageFolder);
  }

  @Override
  public String getProjectFileFormat() {
    return this.projectFileFormat;
  }

  @Override
  public String getRiskFileFormat() {
    return null;
  }

  @Override
  public void importProject(File importFile) throws IOException, ClassNotFoundException {
    IProject readProject = this.readProject(importFile);
    File targetFile = new File(this.pathToStorageFolder.toString(), readProject.getUuid() + this.projectFileFormat);
    if (targetFile.exists())
    {
      throw new FileAlreadyExistsException(targetFile.getAbsolutePath());
    }
    else
    {
      for(IProject project : this.currentProjects)
      {
        if (project.equals(readProject))
        {
          throw new FileAlreadyExistsException(targetFile.getAbsolutePath());
        }
      }
      Files.copy(importFile.toPath(), targetFile.toPath());
    }

  }

  @Override
  public void deleteProject(IProject project) {
    File file = new File(this.pathToStorageFolder.toString(), project.getUuid() + this.projectFileFormat);
    file.delete();
  }

  @Override
  public void exportProject(IProject project, File exportFile) throws IOException {
    this.writeProjectToDisk(project, exportFile);
  }

  @Override
  public IRisk importRisk(File importFile) {
    return null;
  }
}
