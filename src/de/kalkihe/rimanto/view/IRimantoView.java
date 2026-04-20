package de.kalkihe.rimanto.view;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IRimantoPresenter;

import java.io.File;
import java.util.List;

/**
 * Interface of the view
 */
public interface IRimantoView {
  /**
   * Method to show an error dialog
   * @param errorMessage Error message
   * @param exception The causing exception
   * @param shutdownApplication Parameter to declare, if the application is to stop after error is shown and closed
   */
  void showErrorDialog(String errorMessage, Exception exception, boolean shutdownApplication);

  /**
   * Sets reference to IRimantoPresenter in implementations
   * @param rimantoPresenter
   */
  void setPresenter(IRimantoPresenter rimantoPresenter);

  /**
   * Initializes the Application window
   */
  void initializeApplicationWindow() throws Exception;

  /**
   * Request a list of all projects from presenter
   * @return List of projects
   */
  List<IProject> requestProjects();

  /**
   * Requests a list of risks for the passed project from presenter
   * @param project Project to request risks for
   * @return List of risks of the passed project.
   */
  List<IRisk> requestRisksForProject(IProject project);

  /**
   * Starts the creation of a project
   */
  void startCreationOfProject();

  /**
   * Shows the overiew
   * @throws Exception
   */
  void showOverview() throws Exception;

  /**
   * To call when project was created
   * @throws Exception
   */
  void projectCreated() throws Exception;

  /**
   * Shows import file dialog
   * @param allowedFileFormat Allowed file format
   * @return Selected file
   */
  File showImportFileDialog(String allowedFileFormat);

  /**
   * Shows the view for the passed project
   * @param project Project to show
   * @throws Exception
   */
  void showProject(IProject project) throws Exception;

  /**
   * Shows the view for the passed risk
   * @param project Project the passed risk belongs to
   * @param risk Risk to show
   * @throws Exception
   */
  void showRisk(IProject project, IRisk risk) throws Exception;

  /**
   * Shows the view to create a risk
   * @param project Project the risk is to create for
   * @throws Exception
   */
  void startCreationOfRisk(IProject project) throws Exception;

  /**
   * Starts the edition of a project
   * @param project Project that is to edit
   */
  void startEditingOfProject(IProject project);

  /**
   * Shows an export file dialog
   * @param allowedFileFormat Allowed file format
   * @return The selected file to export something to
   */
  File showExportFileDialog(String allowedFileFormat);

  /**
   * Shows the view to export a risk as work instruction
   * @param project Project the risk belongs to
   * @param risk Risk that is to export
   * @throws Exception
   */
  void exportRiskAsInstruction(IProject project, IRisk risk) throws Exception;

  /**
   * Called when the language has changed.
   */
  void languageChanged();

}

