package de.kalkihe.rimanto.view;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.presenter.IRimantoPresenter;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;
import de.kalkihe.rimanto.view.filefilters.RimantoFileFIlter;
import de.kalkihe.rimanto.view.frames.RimantoMainFrame;
import de.kalkihe.rimanto.view.frames.ShowErrorFrame;
import de.kalkihe.rimanto.view.panel.IPanelGetter;
import de.kalkihe.rimanto.model.wordbook.IWordbook;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * Implementation of the view
 */
public class RimantoView implements IRimantoView{
  /**
   * Needed references
   */
  private RimantoIOCContainer rimantoIOCContainer;
  private IRimantoPresenter presenter;
  private IWordbook wordbook;
  private IPanelGetter panelGetter;
  private IEventProcessor eventProcessor;

  /**
   * Main application window
   */
  private RimantoMainFrame rimantoMainFrame;

  /**
   * Resolves all needed depencies with help from ioc container
   */
  private void resolveDependencies() throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();
    this.wordbook = (IWordbook) this.rimantoIOCContainer.getInstanceFor(IWordbook.class);
    this.panelGetter = (IPanelGetter) this.rimantoIOCContainer.getInstanceFor(IPanelGetter.class);
    this.eventProcessor = (IEventProcessor) this.rimantoIOCContainer.getInstanceFor(IEventProcessor.class);
  }

  /**
   * Method to show an error dialog
   * @param errorMessage Error message
   * @param exception The causing exception
   * @param shutdownApplication Parameter to declare, if the application is to stop after error is shown and closed
   */
  @Override
  public void showErrorDialog(String errorMessage, Exception exception, boolean shutdownApplication) {
    try {
      ShowErrorFrame errorFrame = new ShowErrorFrame(this.wordbook, errorMessage, exception, shutdownApplication);
      errorFrame.setModal(true);
      errorFrame.setLocationRelativeTo(this.rimantoMainFrame);
      errorFrame.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets reference to IRimantoPresenter in implementations
   * @param rimantoPresenter
   */
  @Override
  public void setPresenter(IRimantoPresenter rimantoPresenter) {
    this.presenter = rimantoPresenter;
  }

  /**
   * Initializes the Application window
   */
  @Override
  public void initializeApplicationWindow() throws Exception {
    // Set look and feel to native system style
    try {
      this.resolveDependencies();
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception exception)
    {
      throw new Exception(wordbook.getWordFor("errorlookandfell"), exception);
    }
    // Create new main frame and pass reference to workbook
    this.rimantoMainFrame = new RimantoMainFrame(this.wordbook, this.eventProcessor);
    // set the panel to display
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForOverview());
    // Set the main frame to be visible
    this.rimantoMainFrame.setVisible(true);
  }


  /**
   * Request a list of all projects from presenter
   * @return List of projects
   */
  @Override
  public List<IProject> requestProjects() {
    return this.presenter.fetchProjects();
  }

  /**
   * Request a list of all projects from presenter
   * @return List of projects
   */
  @Override
  public List<IRisk> requestRisksForProject(IProject project) {
    return this.presenter.fetchRisksOfProject(project);
  }

  /**
   * Starts the creation of a project
   */
  @Override
  public void startCreationOfProject() {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForProjectInput());

  }

  /**
   * Shows the overiew
   * @throws Exception
   */
  @Override
  public void showOverview() throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForOverview());
  }

  /**
   * To call when project was created
   * @throws Exception
   */
  @Override
  public void projectCreated() throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForOverview());
  }

  /**
   * Shows import file dialog
   * @param allowedFileFormat Allowed file format
   * @return Selected file
   */
  @Override
  public File showImportFileDialog(String allowedFileFormat) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setFileFilter(new RimantoFileFIlter(allowedFileFormat, this.wordbook));
    int state = fileChooser.showOpenDialog(this.rimantoMainFrame);
    if (state == JFileChooser.APPROVE_OPTION)
    {
      return fileChooser.getSelectedFile();
    }
    return null;
  }

  /**
   * Shows the view for the passed project
   * @param project Project to show
   * @throws Exception
   */
  @Override
  public void showProject(IProject project) throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForProjectView(project));
  }

  /**
   * Shows the view for the passed risk
   * @param project Project the passed risk belongs to
   * @param risk Risk to show
   * @throws Exception
   */
  @Override
  public void showRisk(IProject project, IRisk risk) throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForRiskView(project, risk));
  }

  /**
   * Shows the view to create a risk
   * @param project Project the risk is to create for
   * @throws Exception
   */
  @Override
  public void startCreationOfRisk(IProject project) throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForRiskCreation(project));
  }

  /**
   * Starts the edition of a project
   * @param project Project that is to edit
   */
  @Override
  public void startEditingOfProject(IProject project) {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForProjectEditing(project));
  }

  /**
   * Shows an export file dialog
   * @param allowedFileFormat Allowed file format
   * @return The selected file to export something to
   */
  @Override
  public File showExportFileDialog(String allowedFileFormat) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setFileFilter(new RimantoFileFIlter(allowedFileFormat, this.wordbook));
    int state = fileChooser.showSaveDialog(this.rimantoMainFrame);
    if (state == JFileChooser.APPROVE_OPTION)
    {
      return new File(fileChooser.getSelectedFile() + allowedFileFormat);
    }
    return null;
  }

  /**
   * Shows the view to export a risk as work instruction
   * @param project Project the risk belongs to
   * @param risk Risk that is to export
   * @throws Exception
   */
  @Override
  public void exportRiskAsInstruction(IProject project, IRisk risk) throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForExportRisk(project, risk));
  }

  /**
   * Called when the language has changed.
   */
  @Override
  public void languageChanged() {
    this.rimantoMainFrame.initializeMenuBar();
  }
}
