package de.kalkihe.rimanto.view;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IRimantoPresenter;

import java.io.File;
import java.util.List;

public interface IRimantoView {
  /*
   * Method to show an error dialog
   * Parameter to declare, if the application is to stop after error is shown and closed
   */
  void showErrorDialog(Exception exception, boolean shutdownApplication);

  /*
   * Sets reference to IRimantoPresenter in implementations
   */
  void setPresenter(IRimantoPresenter rimantoPresenter);

  /*
   * Initializes the Application window
   */
  void initializeApplicationWindow() throws Exception;

  /*
   * Request a list of all projects from presenter
   */
  List<IProject> requestProjects();

  /*
   * Requests a list of risks for the passed project from presenter
   */
  List<IRisk> requestRisksForProject(IProject project);

  void startCreationOfProject();

  void showOverview() throws Exception;

  void projectCreated() throws Exception;

  File showImportFileDialog(String allowedFileFormat);

  void showProject(IProject project) throws Exception;

  void showRisk(IProject project, IRisk risk) throws Exception;

  void startCreationOfRisk(IProject project) throws Exception;

  void startEditingOfProject(IProject project);

  void projectEdited(IProject project) throws Exception;

  File showExportFileDialog(String allowedFileFormat);

  void exportRiskAsInstruction(IProject project, IRisk risk) throws Exception;


}

