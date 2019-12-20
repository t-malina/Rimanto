package de.kalkihe.rimanto.model.data;

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
  Calendar getDateOfNextRiskRevision();
  Map<Risk, String> getImpactOfRiskOnOtherProjects();
}
