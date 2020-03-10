package de.kalkihe.rimanto.model.data;

import java.time.LocalDate;
import java.util.Calendar;
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
  Map<Risk, String> getImpactOfRiskOnOtherProjects();
}
