package de.kalkihe.rimanto.model.data;

import java.util.GregorianCalendar;
import java.util.Map;

public class Risk implements IRisk{
  String riskName;
  String riskDescription;
  int riskPriority;
  int riskImpact;
  String riskMitigation;
  String personInCharge;
  GregorianCalendar dateOfNextRiskRevision;
  Map<Risk, String> impactOfRiskOnOtherProjects;

  @Override
  public String getRiskDescription() {
    return riskDescription;
  }

  @Override
  public int getRiskPriority() {
    return riskPriority;
  }

  @Override
  public int getRiskImpact() {
    return riskImpact;
  }

  @Override
  public String getRiskMitigation() {
    return riskMitigation;
  }

  @Override
  public String getPersonInCharge() {
    return personInCharge;
  }

  @Override
  public GregorianCalendar getDateOfNextRiskRevision() {
    return dateOfNextRiskRevision;
  }

  @Override
  public Map<Risk, String> getImpactOfRiskOnOtherProjects() {
    return impactOfRiskOnOtherProjects;
  }

  @Override
  public String[] getGeneralRiskData() {
    return new String[0];
  }

  @Override
  public String[] getGeneralDataNames() {
    return new String[0];
  }

  @Override
  public String getRiskName() {
    return riskName;
  }
}
