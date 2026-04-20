package de.kalkihe.rimanto.view.panel.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.data.Risk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RiskViewPanel extends GeneralRimantoPanel {
  private IProject project;
  private IRisk risk;

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
  private JTextField riskObservatorTextField;
  private DatePicker riskRevisionDatePicker;
  private JList<IProject> furtherProjectsList;
  private JTextField categeoryOfImpactOnOtherProjectsTextField;

  private JButton cancelOrBackButton;
  private JButton saveOrEditButton;

  private DefaultListModel<IProject> furtherProjectListModel;


  public RiskViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.buildPanel();
    this.initialize();
    this.setEditMode(true);
  }


  public RiskViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project, IRisk risk) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.risk = risk;
    this.buildPanel();
    this.initialize(risk);
    this.setEditMode(false);
  }

  private void fillCombBox(JComboBox<Integer> comboBox)
  {
    for (int index = 1; index < 6; index++)
    {
      comboBox.addItem(new Integer(index));
    }
  }

  @Override
  protected void buildPanel() throws Exception {
    this.centerPanel = new JPanel(new GridLayout(0, 2, 20, 20));
    this.southPanel = new JPanel(new FlowLayout());

    JLabel riskNameLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk name"));
    this.centerPanel.add(riskNameLabel);
    this.riskNameTextArea = new JTextArea();
    JScrollPane riskNameScrollPane = new JScrollPane(this.riskNameTextArea);
    this.centerPanel.add(riskNameScrollPane);

    JLabel riskDescriptionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk description"));
    this.centerPanel.add(riskDescriptionLabel);
    this.riskDescriptionTextArea = new JTextArea();
    JScrollPane riskDescriptionScrollPane = new JScrollPane(this.riskDescriptionTextArea);
    this.centerPanel.add(riskDescriptionScrollPane);

    JLabel riskPriorityLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk priority"));
    this.centerPanel.add(riskPriorityLabel);
    this.riskPriorityComboBox = new JComboBox<Integer>();
    this.fillCombBox(this.riskPriorityComboBox);
    this.centerPanel.add(this.riskPriorityComboBox);

    JLabel riskImpactLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk impact"));
    this.centerPanel.add(riskImpactLabel);
    this.riskImpactComboBox = new JComboBox<>();
    this.fillCombBox(this.riskImpactComboBox);
    this.centerPanel.add(this.riskImpactComboBox);

    JLabel riskMitigationLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk mitigation"));
    this.centerPanel.add(riskMitigationLabel);
    this.riskMitigationTextArea = new JTextArea();
    JScrollPane riskMitigationScrollPane = new JScrollPane(this.riskMitigationTextArea);
    this.centerPanel.add(riskMitigationScrollPane);

    JLabel riskObserverLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("person in charge"));
    this.centerPanel.add(riskObserverLabel);
    this.riskObservatorTextField = new JTextField();
    this.centerPanel.add(riskObservatorTextField);

    DatePickerSettings revisionDatePickerSettings = new DatePickerSettings();
    revisionDatePickerSettings.setAllowKeyboardEditing(false);
    this.riskRevisionDatePicker = new DatePicker(revisionDatePickerSettings);
    JLabel riskRevisionDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("next date of revision"));
    this.centerPanel.add(riskRevisionDateLabel);
    this.centerPanel.add(riskRevisionDatePicker);

    JLabel furtherProjectLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("further projects"));
    this.centerPanel.add(furtherProjectLabel);
    this.furtherProjectListModel = new DefaultListModel<>();
    this.initializeWithAllProjectsExceptOwn(this.furtherProjectListModel);
    this.furtherProjectsList = new JList(this.furtherProjectListModel);
    this.centerPanel.add(this.furtherProjectsList);

    JLabel categoryOfImpactOnOtherProjectLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("category"));
    this.centerPanel.add(categoryOfImpactOnOtherProjectLabel);
    this.categeoryOfImpactOnOtherProjectsTextField = new JTextField();
    this.centerPanel.add(this.categeoryOfImpactOnOtherProjectsTextField);

    this.add(this.centerPanel, BorderLayout.CENTER);

  }

  private void initialize()
  {
    this.cancelOrBackButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("cancel"));
    this.cancelOrBackButton.addActionListener(actionEvent -> this.eventProcessor.newRiskCreationCanceled(this.project));
    this.saveOrEditButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("save"));
    this.saveOrEditButton.addActionListener(actionEvent -> this.saveButtonClick());
    this.southPanel.add(this.cancelOrBackButton);
    this.southPanel.add(this.saveOrEditButton);

    this.add(this.southPanel, BorderLayout.SOUTH);
  }

  private void initialize(IRisk risk)
  {
    // Fill Elements with Data
    this.riskNameTextArea.setText(risk.getRiskName());
    this.riskDescriptionTextArea.setText(risk.getRiskDescription());
    this.riskImpactComboBox.setSelectedItem(new Integer(risk.getRiskImpact()));
    this.riskPriorityComboBox.setSelectedItem(new Integer(risk.getRiskPriority()));
    this.riskMitigationTextArea.setText(risk.getRiskMitigation());
    this.riskObservatorTextField.setText(risk.getPersonInCharge());
    this.riskRevisionDatePicker.setDate(risk.getDateOfNextRiskRevision());
    this.furtherProjectListModel.removeAllElements();
    this.furtherProjectListModel.addAll(risk.getImpactOfRiskOnOtherProjects());
    this.categeoryOfImpactOnOtherProjectsTextField.setText(risk.getCategoryOfImpactOnOtherProjects());

    this.cancelOrBackButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("back"));
    this.cancelOrBackButton.addActionListener(actionEvent -> this.eventProcessor.exitRiskDetailView(project, risk));
    this.southPanel.add(this.cancelOrBackButton);

    JButton exportRiskButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("export risk"));
    exportRiskButton.addActionListener(actionEvent -> this.eventProcessor.exportRisk(this.project, risk));
    this.southPanel.add(exportRiskButton);

    JButton exportRiskAsInstructionButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("export risk instruction"));
    exportRiskAsInstructionButton.addActionListener(actionEvent -> this.eventProcessor.exportRiskAsInstruction(this.project, risk));
    this.southPanel.add(exportRiskAsInstructionButton);

    JButton deleteRiskButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("delete risk"));
    deleteRiskButton.addActionListener(actionEvent -> this.eventProcessor.deleteRisk(this.project, risk));
    this.southPanel.add(deleteRiskButton);

    this.saveOrEditButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("edit risk"));
    this.saveOrEditButton.addActionListener(actionEvent -> this.editButtonClick(risk));
    this.southPanel.add(this.saveOrEditButton);


    this.add(this.southPanel, BorderLayout.SOUTH);

  }

  private void setEditMode(boolean isEditable)
  {
    for(Component component : this.centerPanel.getComponents())
    {
      component.setEnabled(isEditable);
    }
    this.riskNameTextArea.setEnabled(isEditable);
    this.riskDescriptionTextArea.setEnabled(isEditable);
    this.riskMitigationTextArea.setEnabled((isEditable));

  }

  private void removeActionListenersFromButton(JButton button)
  {
    for(ActionListener listener: button.getActionListeners())
    {
      button.removeActionListener(listener);
    }
  }

  private void editButtonClick(IRisk risk)
  {
    this.removeActionListenersFromButton(this.saveOrEditButton);
    this.saveOrEditButton.setText(this.wordbook.getWordForWithCapitalLeadingLetter("save"));
    this.saveOrEditButton.addActionListener(actionEvent -> this.saveButtonClick(risk));

    this.removeActionListenersFromButton(this.cancelOrBackButton);
    this.cancelOrBackButton.setText(this.wordbook.getWordForWithCapitalLeadingLetter("cancel"));
    this.cancelOrBackButton.addActionListener(actionEvent -> this.cancelButtonClick(risk));

    this.furtherProjectListModel.removeAllElements();
    this.initializeWithAllProjectsExceptOwn(this.furtherProjectListModel);
    int[] indices = new int[risk.getImpactOfRiskOnOtherProjects().size()];
    int index = 0;
    for(IProject project : risk.getImpactOfRiskOnOtherProjects())
    {
      this.furtherProjectsList.setSelectedValue(project, false);
      indices[index] = (this.furtherProjectsList.getSelectedIndex());
      index++;
    }
    this.furtherProjectsList.setSelectedIndices(indices);

    this.setEditMode(true);
  }

  private void initializeWithAllProjectsExceptOwn(DefaultListModel<IProject> model)
  {
    List<IProject> projectList = this.rimantoView.requestProjects();
    projectList.remove(this.project);
    model.addAll(projectList);
  }

  private void cancelButtonClick(IRisk risk)
  {
    this.southPanel.removeAll();
    this.southPanel.revalidate();
    this.initialize(risk);
    this.setEditMode(false);
  }


  private void saveButtonClick()
  {
    IRisk risk = this.generateRiskFromInput();
    if (risk != null)
    {
      this.eventProcessor.newRiskToCreate(this.project, risk);
    }
  }

  private void saveButtonClick(IRisk risk)
  {
    IRisk newRisk = this.generateRiskFromInput();
    if (newRisk != null)
    {
      this.eventProcessor.editRisk(this.project, risk, newRisk);
    }
  }

  private IRisk generateRiskFromInput()
  {
    String riskName = this.riskNameTextArea.getText();
    boolean noRiskName = riskName.trim().length() == 0;
    String riskDescription = this.riskDescriptionTextArea.getText();
    boolean noRiskDescription = riskDescription.trim().length() == 0;
    String riskMitigation = this.riskMitigationTextArea.getText();
    boolean noRiskMitigation = riskMitigation.trim().length() == 0;
    if (noRiskName || noRiskDescription || noRiskMitigation)
    {
      JOptionPane.showMessageDialog(this, this.wordbook.getWordFor("missing risk data"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
      return null;
    }
    LocalDate revisionDate = this.riskRevisionDatePicker.getDate();
    if (revisionDate != null)
    {
      boolean inBetween = revisionDate.isAfter(this.project.getDateOfProjectStart()) && revisionDate.isBefore(this.project.getDateOfProjectEnd());
      if (!inBetween)
      {
        JOptionPane.showMessageDialog(this, this.wordbook.getWordFor("wrong revision date"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
        return null;
      }
    }
    int riskPriority = (int) this.riskPriorityComboBox.getSelectedItem();
    int riskImpact = (int) this.riskImpactComboBox.getSelectedItem();
    String riskObserver = this.riskObservatorTextField.getText();
    List<IProject> furtherProjects = this.furtherProjectsList.getSelectedValuesList();
    boolean furtherProjectsSelected = furtherProjects.size() != 0;
    String categoryForFurtherProjects = this.categeoryOfImpactOnOtherProjectsTextField.getText();
    boolean noCategoryGiven = categoryForFurtherProjects.trim().length() == 0;
    if (furtherProjectsSelected && noCategoryGiven)
    {
      JOptionPane.showMessageDialog(this, this.wordbook.getWordForWithCapitalLeadingLetter("no category"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
      return null;
    }

    IRisk risk = new Risk(riskName, riskDescription, riskPriority, riskImpact, riskMitigation, riskObserver, revisionDate, furtherProjects, categoryForFurtherProjects);
    return risk;
  }

}
