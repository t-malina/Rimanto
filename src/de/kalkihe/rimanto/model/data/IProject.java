package de.kalkihe.rimanto.model.data;

import java.io.File;
import java.net.URI;
import java.util.Calendar;
import java.util.List;

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
  Calendar getDateOfProjectStart();

  /*
   * Method to get the end Date of the project
   */
  Calendar getDateOfProjectEnd();

  /*
   * Method to get a list of file pointers to files attached to this project
   */
  List<File> getAttachedFiles();

  /*
   * Method to get List of links to ressources linked to the project
   */
  List<URI> getLinkedResources();

  /*
   * Method to get the Date of the next revision of the project
   */
  Calendar getDateOfNextProjectRevision();

  /*
   * Method to get a list of all risks of that project
   */
  List<IRisk> getProjectRisks();

  /*
   * Returns true, if the project is to review
   */
  boolean isToReview();
}
