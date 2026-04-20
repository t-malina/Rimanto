package de.kalkihe.rimanto.model.data;

import java.io.File;
import java.net.URI;
import java.util.Calendar;
import java.util.List;

public interface IProject {
  String[] getGeneralProjectData();

  String[] getGeneralDataNames();

  String getProjectName();

  String getProjectDescription();

  Calendar getDateOfProjectStart();

  Calendar getDateOfProjectEnd();

  List<File> getAttachedFiles();

  List<URI> getLinkedResources();

  Calendar getDateOfNextProjectRevision();

  List<IRisk> getProjectRisks();
}
