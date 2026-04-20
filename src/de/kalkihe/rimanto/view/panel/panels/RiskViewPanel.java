package de.kalkihe.rimanto.view.panel.panels;

import com.github.lgooddatepicker.components.DatePicker;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import java.awt.*;

public class RiskViewPanel extends GeneralRimantoPanel {
  IRisk risk;

  /*
   * UI-Elements
   */
  private JPanel northPanel;
  private JPanel centerPanel;
  private JPanel southPanel;

  private JTextArea riskNameTextArea;
  private JTextArea riskDescriptionTextArea;
  private JComboBox<Integer> riskPriorityComboBox;
  private JComboBox<Integer> riskImpactComboBox;
  private JTextArea riskMitigationTextArea;
  private JTextArea riskObservatorTextArea;
  private DatePicker riskRevisionDatePicker;
  private JList<IProject> furtherProjectsList;
  private JTextArea categeoryOfImpactOnOtherProjectsTextArea;





  public RiskViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.buildPanel();
    this.setEditMode(true);
  }


  public RiskViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IRisk risk) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.setEditMode(false);
  }

  @Override
  protected void buildPanel() throws Exception {
    this.centerPanel = new JPanel(new GridLayout(0, 2, 20, 20));

  }

  private void initialize()
  {
    JButton cancelButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("cancel"));

  }

  private void initialize(IRisk risk)
  {

  }

  private void setEditMode(boolean isEditable)
  {

  }

}
