package de.kalkihe.rimanto.model.data;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public interface IProject {
  /*
   * Method to get general data of a project, e.g. to display it in a overview table
   */
  String[] getGeneralProjectData();

  /*
   * Method to get the names of the general data of a project, e.g. for column headings in overview table
   */
  String[] getGeneralDataNames();

  /*
   * Method to get the name of the project
   */
  String getProjectName();

  /*
   * Method to get the description of the project
   */
  String getProjectDescription();

  /*
   * Method to get the start date of the project
   */
  LocalDate getDateOfProjectStart();

  /*
   * Method to get the end Date of the project
   */
  LocalDate getDateOfProjectEnd();

  /*
   * Method to get List of links to resources linked to the project
   */
  List<URI> getLinkedResources();

  /*
   * Method to get the Date of the next revision of the project
   */
  LocalDate getDateOfNextProjectRevision();

  /*
   * Method to get a list of all risks of that project
   */
  List<IRisk> getProjectRisks();

  /*
   * Returns true, if the project is to review
   */
  boolean isToReview();

  boolean hasRisksToReview();

  void resetReview();

  UUID getUuid();

  void addRisk(IRisk risk);

  void editProjectData(IProject projectToTakeDataFrom);

  void deleteRisk(IRisk risk);
}
