package de.kalkihe.rimanto.model.data;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;

public class Project implements IProject, Serializable {
  private String projectName;
  private String projectDescription;
  private LocalDate dateOfProjectStart;
  private LocalDate dateOfProjectEnd;
  private List<URI> linkedResources;
  private LocalDate dateOfNextProjectRevision;
  private List<IRisk> projectRisks;

  private UUID uuid;

  public UUID getUuid() {
    return uuid;
  }

  @Override
  public void addRisk(IRisk risk) {
    this.projectRisks.add(risk);
  }

  @Override
  public void editProjectData(IProject projectToTakeDataFrom) {
    this.projectName = projectToTakeDataFrom.getProjectName();
    this.projectDescription = projectToTakeDataFrom.getProjectDescription();
    this.dateOfProjectStart = projectToTakeDataFrom.getDateOfProjectStart();
    this.dateOfProjectEnd = projectToTakeDataFrom.getDateOfProjectEnd();
    this.linkedResources = projectToTakeDataFrom.getLinkedResources();
    this.dateOfNextProjectRevision = projectToTakeDataFrom.getDateOfNextProjectRevision();
  }

  @Override
  public void deleteRisk(IRisk risk) {
    this.projectRisks.remove(risk);
  }


  /*
   * Constructor
   * Initializes project data with passed data
   */
  public Project(String projectName, String projectDescription, LocalDate dateOfProjectStart, LocalDate dateOfProjectEnd, List<URI> linkedResources, LocalDate dateOfNextProjectRevision) {
    this.projectName = projectName;
    this.projectDescription = projectDescription;
    this.dateOfProjectStart = dateOfProjectStart;
    this.dateOfProjectEnd = dateOfProjectEnd;
    if (linkedResources == null)
    {
      this.linkedResources = new ArrayList<URI>();
    }
    else
    {
      this.linkedResources = linkedResources;
    }
    this.dateOfNextProjectRevision = dateOfNextProjectRevision;
    this.uuid = UUID.randomUUID();
    this.projectRisks = new ArrayList<IRisk>();
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
    return this.projectRisks;
  }

  /*
   * Returns true, if the project has to be reviewed
   * This is determined by checking if dateofNextProjectRevision was before current date
   */
  @Override
  public boolean isToReview() {
    if (this.dateOfNextProjectRevision == null)
    {
      return false;
    }
    LocalDate currentDate = LocalDate.now();
    return this.dateOfNextProjectRevision.isBefore(currentDate) || this.dateOfNextProjectRevision.isEqual(currentDate);

  }

  @Override
  public boolean hasRisksToReview() {
    for(IRisk risk : this.projectRisks)
    {
      if (risk.isToReview())
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public void resetReview() {
    this.dateOfNextProjectRevision = null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Project project = (Project) o;
    return Objects.equals(uuid, project.uuid);
  }

  @Override
  public int hashCode() {
    return this.uuid.hashCode();
  }

  @Override
  public String toString() {
    return this.projectName;
  }
}
