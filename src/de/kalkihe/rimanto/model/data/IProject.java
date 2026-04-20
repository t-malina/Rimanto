package de.kalkihe.rimanto.model.data;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Interface for Data about one project
 */
public interface IProject {

  /**
   * Method to get general data of a project, e.g. to display it in a overview table
   * @return The general data of the project
   */
  String[] getGeneralProjectData();

  /**
   * Method to get the names of the general data of a project, e.g. for column headings in overview table
   * @return The names of the general data of the project
   */
  String[] getGeneralDataNames();

  /**
   * Method to get the name of the project
   * @return The name of the project
   */
  String getProjectName();

  /**
   * Method to get the description of the project
   * @return The Description of the project
   */
  String getProjectDescription();

  /**
   * Method to get the start date of the project
    * @return The Start Date of the project
   */
  LocalDate getDateOfProjectStart();

  /**
   * Method to get the end Date of the project
   * @return The end date of the project
   */
  LocalDate getDateOfProjectEnd();

  /**
   * Method to get List of links to resources linked to the project
   * @return A list of resurces linked to the project.
   */
  List<URI> getLinkedResources();

  /**
   * Method to get the Date of the next review of the project
   * @return The date of the nex review of the project
   */
  LocalDate getDateOfNextProjectReview();

  /**
   * Method to get a list of all risks of that project
   * @return The risks of the project
   */
  List<IRisk> getProjectRisks();

  /**
   * Returns true, if the project is to review
   * @return True, if the project is to review
   */
  boolean isToReview();

  /**
   *
   * @return True, if the project has risks that are to review
   */
  boolean hasRisksToReview();

  /**
   * Is to call, when the project was reviewed. Resets the fact, that it is to review
   */
  void resetReview();

  /**
   *
   * @return The unique identifier for this project
   */
  UUID getUuid();

  /**
   * Adds a risk to the risks of this project
   * @param risk The risk that is to add to the project
   */
  void addRisk(IRisk risk);

  /**
   * Adopts the data of the given project
   * @param projectToTakeDataFrom Project with data, that the current project has to adopt
   */
  void editProjectData(IProject projectToTakeDataFrom);

  /**
   * Deletes a risk from the lists of risks.
   * @param risk The risk, that is to delete from the project
   */
  void deleteRisk(IRisk risk);
}
