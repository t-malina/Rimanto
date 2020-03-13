package de.kalkihe.rimanto.model.data;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface IRisk {
  String[] getGeneralRiskData();
  String[] getGeneralDataNames();

  String getRiskName();
  String getRiskDescription();
  int getRiskPriority();
  int getRiskImpact();
  String getRiskMitigation();
  String getPersonInCharge();
  LocalDate getDateOfNextRiskRevision();
  List<IProject> getImpactOfRiskOnOtherProjects();
  String getCategoryOfImpactOnOtherProjects();

  Object clone() throws CloneNotSupportedException;

  void annotateRiskSource(IProject project, String category);

  void makeAnnotatedRisk();

  void editRiskData(IRisk newRisk);

  boolean isToReview();

  void resetReview();
}
