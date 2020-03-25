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

public class RimantoView implements IRimantoView{

  private RimantoIOCContainer rimantoIOCContainer;
  private IRimantoPresenter presenter;
  private IWordbook wordbook;
  private IPanelGetter panelGetter;
  private IEventProcessor eventProcessor;

  private RimantoMainFrame rimantoMainFrame;

  /*
   * Resolves all needed depencies with help from ioc container
   */
  private void resolveDependencies() throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();
    this.wordbook = (IWordbook) this.rimantoIOCContainer.getInstanceFor(IWordbook.class);
    this.panelGetter = (IPanelGetter) this.rimantoIOCContainer.getInstanceFor(IPanelGetter.class);
    this.eventProcessor = (IEventProcessor) this.rimantoIOCContainer.getInstanceFor(IEventProcessor.class);
  }

  /*
   * Initializes the displaying of an error message
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

  /*
   * Set reference to presenter
   */
  @Override
  public void setPresenter(IRimantoPresenter rimantoPresenter) {
    this.presenter = rimantoPresenter;
  }

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


  /*
   * Requests list of all projects from presenter
   */
  @Override
  public List<IProject> requestProjects() {
    return this.presenter.fetchProjects();
  }

  /*
   * Requests list of all risks of the given project from presenter
   */
  @Override
  public List<IRisk> requestRisksForProject(IProject project) {
    return this.presenter.fetchRisksOfProject(project);
  }

  @Override
  public void startCreationOfProject() {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForProjectInput());

  }

  @Override
  public void showOverview() throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForOverview());
  }

  @Override
  public void projectCreated() throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForOverview());
  }

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

  @Override
  public void showProject(IProject project) throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForProjectView(project));
  }

  @Override
  public void showRisk(IProject project, IRisk risk) throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForRiskView(project, risk));
  }

  @Override
  public void startCreationOfRisk(IProject project) throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForRiskCreation(project));
  }

  @Override
  public void startEditingOfProject(IProject project) {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForProjectEditing(project));
  }

  @Override
  public void projectEdited(IProject project) throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForProjectView(project));

  }

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

  @Override
  public void exportRiskAsInstruction(IProject project, IRisk risk) throws Exception {
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForExportRisk(project, risk));
  }

  @Override
  public void languageChanged() {
    this.rimantoMainFrame.initializeMenuBar();
  }


}
