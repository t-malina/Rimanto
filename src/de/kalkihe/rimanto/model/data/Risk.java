package de.kalkihe.rimanto.model.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Risk implements IRisk, Serializable, Cloneable {
  String riskName;
  String riskDescription;
  int riskPriority;
  int riskImpact;
  String riskMitigation;
  String personInCharge;
  LocalDate dateOfNextRiskRevision;
  List<IProject> impactOfRiskOnOtherProjects;
  String categoryOfImpactOnOtherProjects;

  public Risk(String riskName, String riskDescription, int riskPriority, int riskImpact, String riskMitigation, String personInCharge, LocalDate dateOfNextRiskRevision, List<IProject> impactOfRiskOnOtherProjects, String categoryOfImpactOnOtherProjects) {
    this.riskName = riskName;
    this.riskDescription = riskDescription;
    this.riskPriority = riskPriority;
    this.riskImpact = riskImpact;
    this.riskMitigation = riskMitigation;
    this.personInCharge = personInCharge;
    this.dateOfNextRiskRevision = dateOfNextRiskRevision;
    this.impactOfRiskOnOtherProjects = impactOfRiskOnOtherProjects;
    this.categoryOfImpactOnOtherProjects = categoryOfImpactOnOtherProjects;
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
  public List<IProject> getImpactOfRiskOnOtherProjects() {
    return impactOfRiskOnOtherProjects;
  }

  @Override
  public String getCategoryOfImpactOnOtherProjects() {
    return this.categoryOfImpactOnOtherProjects;
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

  @Override
  public Object clone() throws CloneNotSupportedException {
    IRisk risk = new Risk(this.riskName, this.riskDescription, this.riskPriority, this.riskImpact, this.riskMitigation, this.personInCharge, this.dateOfNextRiskRevision, this.impactOfRiskOnOtherProjects, this.categoryOfImpactOnOtherProjects);
    return risk;
  }

  @Override
  public void annotateRiskSource(IProject project, String category) {
    // TODO: Auslagern
    this.riskDescription += "\n\n Originally from project \"" + project.getProjectName() +"\", affects \"" + category + "\" of this project";
  }

  @Override
  public void makeAnnotatedRisk() {
    this.dateOfNextRiskRevision = null;
    this.categoryOfImpactOnOtherProjects = "";
    this.impactOfRiskOnOtherProjects = new ArrayList<>();
  }

  @Override
  public void editRiskData(IRisk newRisk) {
    this.riskName = newRisk.getRiskName();
    this.riskDescription = newRisk.getRiskDescription();
    this.impactOfRiskOnOtherProjects = newRisk.getImpactOfRiskOnOtherProjects();
    this.categoryOfImpactOnOtherProjects = newRisk.getCategoryOfImpactOnOtherProjects();
    this.dateOfNextRiskRevision = newRisk.getDateOfNextRiskRevision();
    this.riskPriority = newRisk.getRiskPriority();
    this.riskImpact = newRisk.getRiskImpact();
    this.riskMitigation = newRisk.getRiskMitigation();
    this.personInCharge = newRisk.getPersonInCharge();
  }
}
