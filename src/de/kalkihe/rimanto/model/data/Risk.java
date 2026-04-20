package de.kalkihe.rimanto.model.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Objects;

public class Risk implements IRisk, Serializable {
  String riskName;
  String riskDescription;
  int riskPriority;
  int riskImpact;
  String riskMitigation;
  String personInCharge;
  LocalDate dateOfNextRiskRevision;
  Map<Risk, String> impactOfRiskOnOtherProjects;

  public Risk(String riskName, String riskDescription, int riskPriority) {
    this.riskName = riskName;
    this.riskDescription = riskDescription;
    this.riskPriority = riskPriority;
  }

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
  public LocalDate getDateOfNextRiskRevision() {
    return dateOfNextRiskRevision;
  }

  @Override
  public Map<Risk, String> getImpactOfRiskOnOtherProjects() {
    return impactOfRiskOnOtherProjects;
  }

  @Override
  public String[] getGeneralRiskData() {
    String[] result = {this.riskName, this.riskDescription, String.valueOf(this.riskPriority)};
    return result;
  }

  @Override
  public String[] getGeneralDataNames() {
    // TODO: remove hard coding
    String[] result = {"name", "description", "priority"};
    return result;
  }

  @Override
  public String getRiskName() {
    return riskName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Risk risk = (Risk) o;
    return riskPriority == risk.riskPriority &&
      riskImpact == risk.riskImpact &&
      Objects.equals(riskName, risk.riskName) &&
      Objects.equals(riskDescription, risk.riskDescription) &&
      Objects.equals(riskMitigation, risk.riskMitigation) &&
      Objects.equals(personInCharge, risk.personInCharge) &&
      Objects.equals(dateOfNextRiskRevision, risk.dateOfNextRiskRevision) &&
      Objects.equals(impactOfRiskOnOtherProjects, risk.impactOfRiskOnOtherProjects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(riskName, riskDescription, riskPriority, riskImpact, riskMitigation, personInCharge, dateOfNextRiskRevision, impactOfRiskOnOtherProjects);
  }
}
