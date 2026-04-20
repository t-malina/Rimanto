package de.kalkihe.rimanto.model.data;

import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Project implements IProject{
  private String projectName;
  private String projectDescription;
  private LocalDate dateOfProjectStart;
  private LocalDate dateOfProjectEnd;
  private List<URI> linkedResources;
  private LocalDate dateOfNextProjectRevision;
  private List<IRisk> projectRisks;


  /*
   * Constructor
   * Initializes project data with passed data
   */
  public Project(String projectName, String projectDescription, LocalDate dateOfProjectStart, LocalDate dateOfProjectEnd, List<URI> linkedResources, LocalDate dateOfNextProjectRevision) {
    this.projectName = projectName;
    this.projectDescription = projectDescription;
    this.dateOfProjectStart = dateOfProjectStart;
    this.dateOfProjectEnd = dateOfProjectEnd;
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

  public LocalDate getDateOfProjectStart() {
    return dateOfProjectStart;
  }

  public LocalDate getDateOfProjectEnd() {
    return dateOfProjectEnd;
  }

  public List<URI> getLinkedResources() {
    return linkedResources;
  }

  public LocalDate getDateOfNextProjectRevision() {
    return dateOfNextProjectRevision;
  }

  public List<IRisk> getProjectRisks() {
    return projectRisks;
  }

  /*
   * Returns true, if the project has to be reviewed
   * This is determined by checking if dateofNextProjectRevision was before current date
   */
  @Override
  public boolean isToReview() {
    LocalDate currentDate = LocalDate.now();
    return this.dateOfNextProjectRevision.isBefore(currentDate);

  }
}
