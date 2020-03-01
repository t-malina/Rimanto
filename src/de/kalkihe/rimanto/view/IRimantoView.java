package de.kalkihe.rimanto.view;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IRimantoPresenter;

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
}

