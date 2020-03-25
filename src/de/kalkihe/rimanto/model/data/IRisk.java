package de.kalkihe.rimanto.model.data;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface IRisk {
  /**
   * Returns the general data of the risk
   * @return The general data of the risk
   */
  String[] getGeneralRiskData();

  /**
   *
   * @return Descriptions of the general data of the risk
   */
  String[] getGeneralDataNames();

  /**
   *
   * @return The name of the risk
   */
  String getRiskName();

  /**
   *
   * @return The description of the risk
   */
  String getRiskDescription();

  /**
   *
   * @return The priority of the risk
   */
  int getRiskPriority();

  /**
   *
   * @return The impact of the risk
   */
  int getRiskImpact();

  /**
   *
   * @return The mitigation of the risk
   */
  String getRiskMitigation();

  /**
   *
   * @return The person in charge of the risk
   */
  String getPersonInCharge();

  /**
   *
   * @return The Date of the next review of the project
   */
  LocalDate getDateOfNextRiskReview();

  /**
   *
   * @return List of other projects this risk has an impact on
   */
  List<IProject> getImpactOfRiskOnOtherProjects();

  /**
   *
   * @return The category of impact this risk has on other projects
   */
  String getCategoryOfImpactOnOtherProjects();

  /**
   * Clones the risk
   * @return A new risk with the same data as this risk
   * @throws CloneNotSupportedException
   */
  Object clone() throws CloneNotSupportedException;

  /**
   * Annotates the given data. To use, if this risk originally comes from an other project
   * @param project The project this risk originally comes from
   * @param category The category of impact this risk has on the project it now belongs to
   * @throws Exception
   */
  void annotateRiskSource(IProject project, String category) throws Exception;

  /**
   * Makes this risk an risks that was added from an different project than it belongs to
   */
  void makeAnnotatedRisk();

  /**
   * Edits the data of this risk to the data of the given risk
   * @param newRisk Risk with data that the called risk has to adopt
   */
  void editRiskData(IRisk newRisk);

  /**
   *
   * @return True, if the called risk is to review
   */
  boolean isToReview();

  /**
   * To call, when the called risk has been reviewed
   */
  void resetReview();
}
