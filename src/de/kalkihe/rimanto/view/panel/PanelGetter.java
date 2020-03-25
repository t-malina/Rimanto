package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.panel.panels.*;

import javax.swing.*;

/**
 * Implementation of panel getter
 */
public class PanelGetter implements IPanelGetter{
  private IWordbook wordbook;
  private IEventProcessor eventProcessor;
  private IRimantoView rimantoView;

  /**
   * Constructor. Initializes needed references
   * @param wordbook Wordbook for labels etc
   * @param eventProcessor Eventhandler to pass events to
   * @param rimantoView reference to view
   */
  public PanelGetter(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView)
  {
    this.wordbook = wordbook;
    this.eventProcessor = eventProcessor;
    this.rimantoView = rimantoView;
  }

  /**
   *
   * @return The Panel for start of application
   * @throws Exception
   */
  @Override
  public JPanel getPanelForOverview() throws Exception {
    return new OverviewPanel(this.wordbook, this.eventProcessor, this.rimantoView);
  }

  /**
   *
   * @param project The project to view
   * @return The project view panel for passed project
   * @throws Exception
   */
  @Override
  public JPanel getPanelForProjectView(IProject project) throws Exception {
    return new ProjectViewPanel(this.wordbook, this.eventProcessor, this.rimantoView, project);
  }

  /**
   *
   * @param project The project the risk belongs to
   * @param risk The risk to view
   * @return The risk view panel for the passed risk
   * @throws Exception
   */
  @Override
  public JPanel getPanelForRiskView(IProject project, IRisk risk) throws Exception {
    return new RiskViewPanel(this.wordbook, this.eventProcessor, this.rimantoView, project, risk);
  }

  /**
   *
   * @return The panel for creating a project
   */
  @Override
  public JPanel getPanelForRiskCreation(IProject project) throws Exception {
    return new RiskViewPanel(this.wordbook, this.eventProcessor, this.rimantoView, project);
  }

  /**
   *
   * @return The panel for creating a project
   */
  @Override
  public JPanel getPanelForProjectInput() {
    return new CreateProjectPanel(this.wordbook, this.eventProcessor, this.rimantoView);
  }

  /**
   *
   * @param project The project to edit
   * @return The panel to edit the passed project
   */
  @Override
  public JPanel getPanelForProjectEditing(IProject project) {
    return new CreateProjectPanel(this.wordbook, this.eventProcessor, this.rimantoView, project);
  }

  /**
   *
   * @param project The project which risk is to be exported
   * @param risk The risk to export
   * @return The panel fo export the passed risk as work instruction
   * @throws Exception
   */
  @Override
  public JPanel getPanelForExportRisk(IProject project, IRisk risk) throws Exception {
    return new InstructionGeneratorPanel(this.wordbook, this.eventProcessor, this.rimantoView, project, risk);
  }
}
