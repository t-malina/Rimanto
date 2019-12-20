package de.kalkihe.rimanto.view;

import com.google.inject.Injector;
import de.kalkihe.rimanto.presenter.IRimantoPresenter;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;
import de.kalkihe.rimanto.view.frames.RimantoMainFrame;
import de.kalkihe.rimanto.view.panel.IPanelGetter;
import de.kalkihe.rimanto.utilities.IWordbook;

import javax.swing.*;

public class RimantoView implements IRimantoView{

  private RimantoIOCContainer rimantoIOCContainer;
  private IRimantoPresenter presenter;
  private IWordbook wordbook;
  private IPanelGetter panelGetter;

  private RimantoMainFrame rimantoMainFrame;


  private void resolveDependencies() throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();
    this.wordbook = (IWordbook) this.rimantoIOCContainer.getInstanceFor(IWordbook.class);
    this.panelGetter = (IPanelGetter) this.rimantoIOCContainer.getInstanceFor(IPanelGetter.class);
  }

  @Override
  public void showErrorDialog(Exception exception, boolean shutdownApplication) {
    //TODO: Implement Error Dialog showing
  }

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
    this.rimantoMainFrame = new RimantoMainFrame(this.wordbook);
    this.rimantoMainFrame.setJPanel(this.panelGetter.getPanelForOverview());
    this.rimantoMainFrame.setVisible(true);
  }

  @Override
  public String[] requestColumnNamesForProjectOverview() {
    return this.presenter.fetchColumnNamesForProjects();
  }

  @Override
  public String[][] requestDataForProjectOverview() {
    return this.presenter.fetchDataOfProjects();
  }

  @Override
  public String[] requestColumnNamesForRiskOverview() {
    return this.presenter.fetchColumnNamesForRisks();
  }

  @Override
  public String[][] requestDataForRiskOverview() {
    return this.presenter.fetchDataOfRisks();
  }


}
