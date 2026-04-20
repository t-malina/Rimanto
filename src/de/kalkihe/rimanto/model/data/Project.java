package de.kalkihe.rimanto.model.data;

import java.io.File;
import java.net.URI;
import java.util.GregorianCalendar;
import java.util.List;

public class Project implements IProject{
  private String projectName;
  private String projectDescription;
  private GregorianCalendar dateOfProjectStart;
  private GregorianCalendar dateOfProjectEnd;
  private List<File> attachedFiles;
  private List<URI> linkedResources;
  private GregorianCalendar dateOfNextProjectRevision;
  private List<IRisk> projectRisks;


  public Project(String projectName, String projectDescription, GregorianCalendar dateOfProjectStart, GregorianCalendar dateOfProjectEnd, List<File> attachedFiles, List<URI> linkedResources, GregorianCalendar dateOfNextProjectRevision) {
    this.projectName = projectName;
    this.projectDescription = projectDescription;
    this.dateOfProjectStart = dateOfProjectStart;
    this.dateOfProjectEnd = dateOfProjectEnd;
    this.attachedFiles = attachedFiles;
    this.linkedResources = linkedResources;
    this.dateOfNextProjectRevision = dateOfNextProjectRevision;
  }

  @Override
  public String[] getGeneralProjectData() {
    String[] result = {this.projectName, this.projectDescription};
    return result;
  }

  @Override
  public String[] getGeneralDataNames() {
    // TODO: Remove hard coding of text
    String[] result = {"name", "description"};
    return result;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getProjectDescription() {
    return projectDescription;
  }

  public GregorianCalendar getDateOfProjectStart() {
    return dateOfProjectStart;
  }

  public GregorianCalendar getDateOfProjectEnd() {
    return dateOfProjectEnd;
  }

  public List<File> getAttachedFiles() {
    return attachedFiles;
  }

  public List<URI> getLinkedResources() {
    return linkedResources;
  }

  public GregorianCalendar getDateOfNextProjectRevision() {
    return dateOfNextProjectRevision;
  }

  public List<IRisk> getProjectRisks() {
    return projectRisks;
  }
}
