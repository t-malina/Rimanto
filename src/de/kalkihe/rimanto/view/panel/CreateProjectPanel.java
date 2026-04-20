package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import java.awt.*;

public class CreateProjectPanel extends GeneralRimantoPanel {
  private JPanel centerPanel;

  private JLabel projectNameLabel;
  private JLabel projectDescriptionLabel;
  private JLabel projectStartDateLabel;
  private JLabel projectEndDateLabel;
  private JLabel nextRevisionLabel;

  private JTextField projectNameTextField;
  private JTextArea projectDescriptionTextArea;

  public CreateProjectPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) {
    super(wordbook, eventProcessor, rimantoView);
    this.buildPanel();
  }

  protected void buildPanel()
  {
    this.centerPanel = new JPanel(new GridLayout(5, 2));
    this.projectNameLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project name"));
    this.projectDescriptionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project description"));
    this.projectNameTextField = new JTextField();
    this.projectDescriptionTextArea = new JTextArea();
    this.centerPanel.add(this.projectNameLabel);
    this.centerPanel.add(this.projectNameTextField);
    this.centerPanel.add(this.projectDescriptionLabel);
    this.centerPanel.add(this.projectDescriptionTextArea);
    this.add(this.centerPanel);

  }
}
