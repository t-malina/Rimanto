package de.kalkihe.rimanto.view;

import com.google.inject.Injector;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IRimantoPresenter;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;
import de.kalkihe.rimanto.view.frames.RimantoMainFrame;
import de.kalkihe.rimanto.view.panel.IPanelGetter;
import de.kalkihe.rimanto.utilities.IWordbook;

import javax.swing.*;
import java.util.List;

public class RimantoView implements IRimantoView{

  private RimantoIOCContainer rimantoIOCContainer;
  private IRimantoPresenter presenter;
  private IWordbook wordbook;
  private IPanelGetter panelGetter;

  private RimantoMainFrame rimantoMainFrame;

  /*
   * Resolves all needed depencies with help from ioc container
   */
  private void resolveDependencies() throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();
    this.wordbook = (IWordbook) this.rimantoIOCContainer.getInstanceFor(IWordbook.class);
    this.panelGetter = (IPanelGetter) this.rimantoIOCContainer.getInstanceFor(IPanelGetter.class);
  }

  /*
   * Initializes the displaying of an error message
   */
  @Override
  public void showErrorDialog(Exception exception, boolean shutdownApplication) {
    //TODO: Implement Error Dialog showing
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
    this.rimantoMainFrame = new RimantoMainFrame(this.wordbook);
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


}
