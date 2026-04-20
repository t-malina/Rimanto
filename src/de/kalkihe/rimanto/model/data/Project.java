package de.kalkihe.rimanto.model.data;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;

public class Project implements IProject, Serializable {
  /**
   * Attributes to save the data
   */
  private String projectName;
  private String projectDescription;
  private LocalDate dateOfProjectStart;
  private LocalDate dateOfProjectEnd;
  private List<URI> linkedResources;
  private LocalDate dateOfNextProjectRevision;
  private List<IRisk> projectRisks;
  private UUID uuid;


  /**
   * Adds a risk to the risks of this project
   * @param risk The risk that is to add to the project
   */
  public UUID getUuid() {
    return uuid;
  }

  /**
   * Adds a risk to the project
   * @param risk The risk that is to add to the project
   */
  @Override
  public void addRisk(IRisk risk) {
    this.projectRisks.add(risk);
  }

  /**
   * Reads the data from the given project and puts it into the data of this project
   * @param projectToTakeDataFrom Project with data, that the current project has to adopt
   */
  @Override
  public void editProjectData(IProject projectToTakeDataFrom) {
    this.projectName = projectToTakeDataFrom.getProjectName();
    this.projectDescription = projectToTakeDataFrom.getProjectDescription();
    this.dateOfProjectStart = projectToTakeDataFrom.getDateOfProjectStart();
    this.dateOfProjectEnd = projectToTakeDataFrom.getDateOfProjectEnd();
    this.linkedResources = projectToTakeDataFrom.getLinkedResources();
    this.dateOfNextProjectRevision = projectToTakeDataFrom.getDateOfNextProjectReview();
  }

  /**
   * Deletes the given risk from the list of risks
   * @param risk The risk, that is to delete from the project
   */
  @Override
  public void deleteRisk(IRisk risk) {
    this.projectRisks.remove(risk);
  }

  /**
   * Constructor. Initializes the project with the passed data
   * @param projectName Name of the new project
   * @param projectDescription Description of the new project
   * @param dateOfProjectStart Date of the start of the new project
   * @param dateOfProjectEnd Date of the end of the new project
   * @param linkedResources List of resources linked to the new project
   * @param dateOfNextProjectRevision Date of next review of the new project
   */
  public Project(String projectName, String projectDescription, LocalDate dateOfProjectStart, LocalDate dateOfProjectEnd, List<URI> linkedResources, LocalDate dateOfNextProjectRevision) {
    this.projectName = projectName;
    this.projectDescription = projectDescription;
    this.dateOfProjectStart = dateOfProjectStart;
    this.dateOfProjectEnd = dateOfProjectEnd;
    // If no resources passed, initialize an empty list
    if (linkedResources == null)
    {
      this.linkedResources = new ArrayList<URI>();
    }
    else
    {
      this.linkedResources = linkedResources;
    }
    this.dateOfNextProjectRevision = dateOfNextProjectRevision;
    // Generate a new random uuid for identification ot this project
    this.uuid = UUID.randomUUID();
    // Create a new List of risks
    this.projectRisks = new ArrayList<IRisk>();
  }

  /**
   * Method to get general data of a project, e.g. to display it in a overview table
   * @return The general data of the project
   */
  @Override
  public String[] getGeneralProjectData() {
    String[] result = {this.projectName, this.projectDescription};
    return result;
  }

  /**
   * Method to get the names of the general data of a project, e.g. for column headings in overview table
   * @return The names of the general data of the project
   */
  @Override
  public String[] getGeneralDataNames() {
    String[] result = {"name", "description"};
    return result;
  }

  /**
   * Method to get the name of the project
   * @return The name of the project
   */
  public String getProjectName() {
    return projectName;
  }

  /**
   * Method to get the description of the project
   * @return The Description of the project
   */
  public String getProjectDescription() {
    return projectDescription;
  }

  /**
   * Method to get the start date of the project
   * @return The Start Date of the project
   */
  public LocalDate getDateOfProjectStart() {
    return dateOfProjectStart;
  }

  /**
   * Method to get the end Date of the project
   * @return The end date of the project
   */
  public LocalDate getDateOfProjectEnd() {
    return dateOfProjectEnd;
  }

  /**
   * Method to get List of links to resources linked to the project
   * @return A list of resurces linked to the project.
   */
  public List<URI> getLinkedResources() {
    return linkedResources;
  }

  /**
   * Method to get the Date of the next review of the project
   * @return The date of the nex review of the project
   */
  public LocalDate getDateOfNextProjectReview() {
    return dateOfNextProjectRevision;
  }

  /**
   * Method to get a list of all risks of that project
   * @return The risks of the project
   */
  public List<IRisk> getProjectRisks() {
    return this.projectRisks;
  }

  /**
   * Returns true, if the project is to review
   * @return True, if the project is to review
   */
  @Override
  public boolean isToReview() {
    if (this.dateOfNextProjectRevision == null)
    {
      return false;
    }
    // Get the current date
    LocalDate currentDate = LocalDate.now();
    // Return True, if review Date is today or was before today
    return this.dateOfNextProjectRevision.isBefore(currentDate) || this.dateOfNextProjectRevision.isEqual(currentDate);

  }

  /**
   *
   * @return True, if the project has risks that are to review
   */
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

  /**
   * Deletes the next review date
   */
  @Override
  public void resetReview() {
    this.dateOfNextProjectRevision = null;
  }

  /**
   * Returns true, if the given object equals the object the method is called on
   * @param o Object to compare called instance with
   * @return True, if both objects are the same
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Project project = (Project) o;
    return Objects.equals(uuid, project.uuid);
  }

  /**
   *
   * @return Hashcode of this project
   */
  @Override
  public int hashCode() {
    return this.uuid.hashCode();
  }

  /**
   * String representation of this project
   * @return Name of the project
   */
  @Override
  public String toString() {
    return this.projectName;
  }
}
