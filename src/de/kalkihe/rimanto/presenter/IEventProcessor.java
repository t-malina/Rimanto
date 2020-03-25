package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

/**
 * Interface for processing events that happens
 */
public interface IEventProcessor {
  /**
   * Event, when creation of new project is started
   */
  void newProjectButtonClick();

  /**
   * Event, when new project is to create
   * @param newProject Project that is to create
   */
  void newProjectToCreate(IProject newProject);

  /**
   * Event, when import of project is started
   */
  void projectImportRequested();

  /**
   * Event, when project is selected for detailed view
   * @param project Project that is selected for detail view
   */
  void projectForDetailViewSelected(IProject project);

  /**
   * Event, wehen risk is selected for detail view
   * @param project project the risk belongs to
   * @param risk Risk that is selected for detail view
   */
  void riskForDetailViewSelected(IProject project, IRisk risk);

  /**
   * Event when import of a risk is started
   * @param project The project the imported risk should belong to
   */
  void riskImportRequested(IProject project);

  /**
   * Event, when creation of risk is started
   * @param project The project the new risk should belong to
   */
  void newRiskButtonClick(IProject project);

  /**
   * Event, when creation of risk is canceled
   * @param project The project, that risk creation was canceled for
   */
  void newRiskCreationCanceled(IProject project);

  /**
   * Event, when new risk is to create
   * @param project The project the risk is created for
   * @param newRisk The risk that is to be created
   */
  void newRiskToCreate(IProject project, IRisk newRisk);

  /**
   * Event, when overview of projects is requested
   */
  void backToOverview();

  /**
   * Event, when editing of a project is requested
   * @param project The project that should be edited
   */
  void projectEditingRequested(IProject project);

  /**
   * Event, when editing of project is canceled
   * @param project The project whose editing was canceled
   */
  void projectEditingCanceled(IProject project);

  /**
   * Event, when a project is to edit
   * @param oldProject The project that is to be edited
   * @param newProject A new project with the data to adopt
   */
  void editProject(IProject oldProject, IProject newProject);

  /**
   * Event, when a project is to be deleted
   * @param project The project that is to delete
   */
  void deleteProject(IProject project);

  /**
   * Event, when the export of a project is started
   * @param project The project that is to be exported
   */
  void exportProject(IProject project);

  /**
   * Event, the export of a risk is started
   * @param project The project the risk belongs to
   * @param risk The risk that is to be exported
   */
  void exportRisk(IProject project, IRisk risk);

  /**
   * Event, when the export of a risk as work instruction is started
   * @param project The project the risk belongs to
   * @param risk The risk that is to be exported
   */
  void exportRiskAsInstruction(IProject project, IRisk risk);

  /**
   * Event, when a risk of a project is to be deleted
   * @param project The project the risk belongs to
   * @param risk The risk that is to be deleted
   */
  void deleteRisk(IProject project, IRisk risk);

  /**
   * Event, when a risk is to be edited
   * @param project The project the risk belongs to
   * @param oldRisk The risk that is to be edited
   * @param newRisk A new risk with all data to be adopted
   */
  void editRisk(IProject project, IRisk oldRisk, IRisk newRisk);

  /**
   * Event, when the detail view of a risk is leaved
   * @param project The project the risk belongs to
   * @param risk The risk which detail view is leaved
   */
  void exitRiskDetailView(IProject project, IRisk risk);

  /**
   * Event, when the export of a risk as instruction is canceled
   * @param project The project the risk belongs to
   * @param risk The risk that was being exported
   */
  void abortRiskAsInstruction(IProject project, IRisk risk);

  /**
   * Event, when a project has ben reviewed
   * @param project The project that has been reviewed
   */
  void setProjectAsReviewed(IProject project);

  /**
   * Event, when a risk has been reviewed
   * @param project The project the reviewed risk belongs to
   * @param risk The reviewed risk
   */
  void setRiskAsReviewed(IProject project, IRisk risk);

  /**
   * Event, when a attached resource has been requested for view
   * @param ressource Path to the resource
   */
  void ressourceForViewRequested(String ressource);

  /**
   * Event when the language has been changed
   * @param language language string
   * @param country country string
   */
  void languageChanged(String language, String country);

}
