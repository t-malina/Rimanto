package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProjectViewPanel extends GeneralRimantoPanel {
  private IProject project;

  private JPanel northPanel;
  private JPanel centerPanel;
  private JPanel southPanel;

  public ProjectViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.buildPanel();
  }

  @Override
  protected void buildPanel() throws Exception {
    // Create Panels
    this.northPanel = new JPanel(new BorderLayout());
    this.centerPanel = new JPanel(new BorderLayout());
    this.southPanel = new JPanel(new FlowLayout());

    JLabel overviewLabel = new JLabel(this.project.getProjectName());
    overviewLabel.setBorder(new EmptyBorder(10, 20, 10, 0));
    this.northPanel.add(overviewLabel);

    // Add panels to main Panels
    this.add(this.northPanel, BorderLayout.NORTH);
    this.add(this.centerPanel, BorderLayout.CENTER);
    this.add(this.southPanel, BorderLayout.SOUTH);
  }
}
