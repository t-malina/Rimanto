package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;

public class PanelGetter implements IPanelGetter{
  private IWordbook wordbook;
  private IEventProcessor eventProcessor;
  private IRimantoView rimantoView;

  public PanelGetter(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView)
  {
    this.wordbook = wordbook;
    this.eventProcessor = eventProcessor;
    this.rimantoView = rimantoView;
  }

  /*
   * Creates new Overview Panel and returns it
   */
  @Override
  public JPanel getPanelForOverview() throws Exception {
    return new OverviewPanel(this.wordbook, this.eventProcessor, this.rimantoView);
  }

  @Override
  public JPanel getPanelForProjectView(IProject project) throws Exception {
    return new ProjectViewPanel(this.wordbook, this.eventProcessor, this.rimantoView, project);
  }

  @Override
  public JPanel getPanelForRiskView(IRisk risk) throws Exception {
    return new RiskViewPanel(this.wordbook, this.eventProcessor, this.rimantoView, risk);
  }

  @Override
  public JPanel getPanelForRiskCreation() throws Exception {
    return new RiskViewPanel(this.wordbook, this.eventProcessor, this.rimantoView);
  }

  @Override
  public JPanel getPanelForProjectInput() {
    return new CreateProjectPanel(this.wordbook, this.eventProcessor, this.rimantoView);
  }

  @Override
  public JPanel getPanelForProjectEditing(IProject project) {
    return new CreateProjectPanel(this.wordbook, this.eventProcessor, this.rimantoView, project);
  }

  @Override
  public JPanel getPanelForErrorView() {
    return null;
  }
}
