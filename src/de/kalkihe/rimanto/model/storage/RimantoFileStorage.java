package de.kalkihe.rimanto.model.storage;

import de.kalkihe.rimanto.model.data.IProject;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RimantoFileStorage implements IRimantoFileStorage {
  private Path pathToStorageFolder;
  private String fileFormat = ".rmt";

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

  private void writeProjectToDisk(IProject project, String fileName, Path folder) throws IOException {
    FileOutputStream fileOutputStream = null;
    ObjectOutputStream objectOutputStream = null;
    File file = new File(folder.toString(), fileName + this.fileFormat);
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

  @Override
  public List<IProject> readProjects() throws IOException, ClassNotFoundException {
    List<IProject> resultList = new ArrayList<IProject>();
    File folder = this.pathToStorageFolder.toFile();
    for (File file : folder.listFiles())
    {
      if (file.toString().endsWith(this.fileFormat))
      {
        resultList.add(this.readProject(file));
      }
    }
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
  public void saveNewProject(IProject project) throws IOException {
    this.writeProjectToDisk(project, project.getUuid().toString(), this.pathToStorageFolder);
  }

  @Override
  public String getProjectFileFormat() {
    return this.fileFormat;
  }

  @Override
  public void importProject(File importFile) {
    String fileName = importFile.getName();
    File targetFile = new File(this.pathToStorageFolder.toString(), fileName);
    if (targetFile.exists())
    {
      
    }
    else
    {

    }

  }
}
