package de.kalkihe.rimanto.model.data;

import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Risk implements IRisk, Serializable, Cloneable {
  /**
   * Attributes to save data
   */
  private String riskName;
  private String riskDescription;
  private int riskPriority;
  private int riskImpact;
  private String riskMitigation;
  private String personInCharge;
  private LocalDate dateOfNextRiskRevision;
  private List<IProject> impactOfRiskOnOtherProjects;
  private String categoryOfImpactOnOtherProjects;

  /**
   * Constructor. Initialzes the new risk with the given data
   * @param riskName Name of the new risk
   * @param riskDescription Description of the new risk
   * @param riskPriority Priority of the new risk
   * @param riskImpact Impact of the new risk
   * @param riskMitigation Mitigaiton of the new risk
   * @param personInCharge Person in charge for the new risk
   * @param dateOfNextRiskRevision Date of the next review of the new risk
   * @param impactOfRiskOnOtherProjects Projects the risk also has an impact an
   * @param categoryOfImpactOnOtherProjects Category of impact the risk has on other projects
   */
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


  /**
   *
   * @return The description of the risk
   */
  @Override
  public String getRiskDescription() {
    return riskDescription;
  }

  /**
   *
   * @return The priority of the risk
   */
  @Override
  public int getRiskPriority() {
    return riskPriority;
  }

  /**
   *
   * @return The impact of the risk
   */
  @Override
  public int getRiskImpact() {
    return riskImpact;
  }

  /**
   *
   * @return The mitigation of the risk
   */
  @Override
  public String getRiskMitigation() {
    return riskMitigation;
  }

  /**
   *
   * @return The person in charge of the risk
   */
  @Override
  public String getPersonInCharge() {
    return personInCharge;
  }

  /**
   *
   * @return The Date of the next review of the project
   */
  @Override
  public LocalDate getDateOfNextRiskReview() {
    return dateOfNextRiskRevision;
  }

  /**
   *
   * @return List of other projects this risk has an impact on
   */
  @Override
  public List<IProject> getImpactOfRiskOnOtherProjects() {
    return impactOfRiskOnOtherProjects;
  }

  /**
   *
   * @return The category of impact this risk has on other projects
   */
  @Override
  public String getCategoryOfImpactOnOtherProjects() {
    return this.categoryOfImpactOnOtherProjects;
  }

  /**
   * Returns the general data of the risk
   * @return The general data of the risk
   */
  @Override
  public String[] getGeneralRiskData() {
    String[] result = {this.riskName, this.riskDescription, String.valueOf(this.riskPriority)};
    return result;
  }

  /**
   *
   * @return Descriptions of the general data of the risk
   */
  @Override
  public String[] getGeneralDataNames() {
    String[] result = {"name", "description", "priority"};
    return result;
  }

  /**
   *
   * @return The name of the risk
   */
  @Override
  public String getRiskName() {
    return riskName;
  }

  /**
   * Returns true, if the passed object equals the risk this method is called for
   * @param o Object that is to compare to the called risk
   * @return True, if both objects are the same
   */
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

  /**
   *
   * @return Hash code of this risk
   */
  @Override
  public int hashCode() {
    return Objects.hash(riskName, riskDescription, riskPriority, riskImpact, riskMitigation, personInCharge, dateOfNextRiskRevision, impactOfRiskOnOtherProjects);
  }

  /**
   * Clones the risk
   * @return A new risk with the same data as this risk
   * @throws CloneNotSupportedException
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    IRisk risk = new Risk(this.riskName, this.riskDescription, this.riskPriority, this.riskImpact, this.riskMitigation, this.personInCharge, this.dateOfNextRiskRevision, this.impactOfRiskOnOtherProjects, this.categoryOfImpactOnOtherProjects);
    return risk;
  }

  /**
   * Annotates the given data. To use, if this risk originally comes from an other project
   * @param project The project this risk originally comes from
   * @param category The category of impact this risk has on the project it now belongs to
   * @throws Exception
   */
  @Override
  public void annotateRiskSource(IProject project, String category) throws Exception {
    IWordbook wordbook = (IWordbook) RimantoIOCContainer.getInstance().getInstanceFor(IWordbook.class);
    this.riskDescription += "\n\n " + wordbook.getWordForWithCapitalLeadingLetter("original_project") +  " \"" + project.getProjectName() +"\", " + wordbook.getWordFor("affects")
  + " \"" + category + "\" " + wordbook.getWordFor("this_project");
  }

  /**
   * Makes this risk an risks that was added from an different project than it belongs to
   */
  @Override
  public void makeAnnotatedRisk() {
    this.dateOfNextRiskRevision = null;
    this.categoryOfImpactOnOtherProjects = "";
    this.impactOfRiskOnOtherProjects = new ArrayList<>();
  }

  /**
   * Edits the data of this risk to the data of the given risk
   * @param newRisk Risk with data that the called risk has to adopt
   */
  @Override
  public void editRiskData(IRisk newRisk) {
    this.riskName = newRisk.getRiskName();
    this.riskDescription = newRisk.getRiskDescription();
    this.impactOfRiskOnOtherProjects = newRisk.getImpactOfRiskOnOtherProjects();
    this.categoryOfImpactOnOtherProjects = newRisk.getCategoryOfImpactOnOtherProjects();
    this.dateOfNextRiskRevision = newRisk.getDateOfNextRiskReview();
    this.riskPriority = newRisk.getRiskPriority();
    this.riskImpact = newRisk.getRiskImpact();
    this.riskMitigation = newRisk.getRiskMitigation();
    this.personInCharge = newRisk.getPersonInCharge();
  }

  /**
   *
   * @return True, if the called risk is to review
   */
  @Override
  public boolean isToReview() {
    if (dateOfNextRiskRevision == null)
    {
      return false;
    }
    return this.dateOfNextRiskRevision.isBefore(LocalDate.now()) || this.dateOfNextRiskRevision.isEqual((LocalDate.now()));
  }

  /**
   * To call, when the called risk has been reviewed
   */
  @Override
  public void resetReview() {
    this.dateOfNextRiskRevision = null;
  }
}
