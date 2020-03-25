package de.kalkihe.rimanto.view.panel.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.data.Risk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.panel.keyevent.TabKeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

/**
 * Panel for creation or view of risk
 */
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
  private JTextArea categeoryOfImpactOnOtherProjectsTextField;

  private JButton cancelOrBackButton;
  private JButton saveOrEditButton;

  private DefaultListModel<IProject> furtherProjectListModel;


  /**
   * Constructor for initialization for creating new risk
   * @param wordbook Wordbook for labels etc
   * @param eventProcessor Eventhandler to pass events to
   * @param rimantoView reference to view
   * @param project Project the risk is to be created for
   * @throws Exception
   */
  public RiskViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.buildPanel();
    this.initialize();
    this.setEditMode(true);
  }


  /**
   * Constructor for initialization for viewing and possibly editing risk
   * @param wordbook Wordbook for labels etc
   * @param eventProcessor Eventhandler to pass events to
   * @param rimantoView reference to view
   * @param project Project the risk belongs to
   * @param risk The risk that is to be viewed and possibly edited
   * @throws Exception
   */
  public RiskViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project, IRisk risk) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.risk = risk;
    this.buildPanel();
    this.initialize(risk);
    this.setEditMode(false);
  }

  /**
   * Fills a combobox with values from 1 to 5
   * @param comboBox
   */
  private void fillCombBox(JComboBox<Integer> comboBox)
  {
    for (int index = 1; index < 6; index++)
    {
      comboBox.addItem(new Integer(index));
    }
  }

  /**
   * Builds the elements of the panel
   * @throws Exception
   */
  @Override
  protected void buildPanel() throws Exception {
    this.centerPanel = new JPanel(new GridLayout(0, 2, 20, 20));
    this.southPanel = new JPanel(new FlowLayout());

    JLabel riskNameLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk_name"));
    this.centerPanel.add(riskNameLabel);
    this.riskNameTextArea = new JTextArea();
    JScrollPane riskNameScrollPane = super.configureAndInsertTextArea(this.riskNameTextArea);
    this.centerPanel.add(riskNameScrollPane);

    JLabel riskDescriptionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk_description"));
    this.centerPanel.add(riskDescriptionLabel);
    this.riskDescriptionTextArea = new JTextArea();
    JScrollPane riskDescriptionScrollPane = super.configureAndInsertTextArea(this.riskDescriptionTextArea);
    this.centerPanel.add(riskDescriptionScrollPane);

    JLabel riskPriorityLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk_priority"));
    this.centerPanel.add(riskPriorityLabel);
    this.riskPriorityComboBox = new JComboBox<Integer>();
    this.fillCombBox(this.riskPriorityComboBox);
    this.centerPanel.add(this.riskPriorityComboBox);

    JLabel riskImpactLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk_impact"));
    this.centerPanel.add(riskImpactLabel);
    this.riskImpactComboBox = new JComboBox<>();
    this.fillCombBox(this.riskImpactComboBox);
    this.centerPanel.add(this.riskImpactComboBox);

    JLabel riskMitigationLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk_mitigation"));
    this.centerPanel.add(riskMitigationLabel);
    this.riskMitigationTextArea = new JTextArea();
    JScrollPane riskMitigationScrollPane = super.configureAndInsertTextArea(this.riskMitigationTextArea);
    this.centerPanel.add(riskMitigationScrollPane);

    JLabel riskObserverLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("person_in_charge"));
    this.centerPanel.add(riskObserverLabel);
    this.riskObservatorTextField = new JTextField();
    this.centerPanel.add(riskObservatorTextField);

    DatePickerSettings revisionDatePickerSettings = new DatePickerSettings();
    revisionDatePickerSettings.setAllowKeyboardEditing(false);
    revisionDatePickerSettings.setFormatForDatesCommonEra(this.wordbook.getDateTimeFormatter());
    this.riskRevisionDatePicker = new DatePicker(revisionDatePickerSettings);
    JLabel riskRevisionDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("next_date_of_revision"));
    this.centerPanel.add(riskRevisionDateLabel);
    this.centerPanel.add(riskRevisionDatePicker);

    JLabel furtherProjectLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("further_projects"));
    this.centerPanel.add(furtherProjectLabel);
    this.furtherProjectListModel = new DefaultListModel<>();
    this.initializeWithAllProjectsExceptOwn(this.furtherProjectListModel);
    this.furtherProjectsList = new JList(this.furtherProjectListModel);
    JScrollPane furtherProjectScrollPane = new JScrollPane(this.furtherProjectsList);
    this.centerPanel.add(furtherProjectScrollPane);

    JLabel categoryOfImpactOnOtherProjectLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("category"));
    this.centerPanel.add(categoryOfImpactOnOtherProjectLabel);
    this.categeoryOfImpactOnOtherProjectsTextField = new JTextArea();
    JScrollPane categoryOfImpactOnOtherProjectsScrollPane = super.configureAndInsertTextArea(this.categeoryOfImpactOnOtherProjectsTextField);
    this.centerPanel.add(categoryOfImpactOnOtherProjectsScrollPane);

    this.add(this.centerPanel, BorderLayout.CENTER);

    this.riskNameTextArea.addKeyListener(new TabKeyAdapter(this.riskDescriptionTextArea));
    this.riskDescriptionTextArea.addKeyListener(new TabKeyAdapter(this.riskPriorityComboBox));
    this.riskPriorityComboBox.addKeyListener(new TabKeyAdapter(this.riskImpactComboBox));
    this.riskImpactComboBox.addKeyListener(new TabKeyAdapter(this.riskMitigationTextArea));
    this.riskMitigationTextArea.addKeyListener(new TabKeyAdapter(this.riskObservatorTextField));
    this.riskObservatorTextField.addKeyListener(new TabKeyAdapter(this.riskRevisionDatePicker));
    this.riskRevisionDatePicker.addKeyListener(new TabKeyAdapter(this.furtherProjectsList));
    this.furtherProjectsList.addKeyListener(new TabKeyAdapter(this.categeoryOfImpactOnOtherProjectsTextField));
    this.categeoryOfImpactOnOtherProjectsTextField.addKeyListener(new TabKeyAdapter(this.riskNameTextArea));

  }

  /**
   * Initializes the ui elements for creation of new risk
   */
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

  /**
   * Initializes the ui for viewing and editing existing risk
   * @param risk The risk to initialize the ui element with
   */
  private void initialize(IRisk risk)
  {
    // Fill Elements with Data
    this.riskNameTextArea.setText(risk.getRiskName());
    this.riskDescriptionTextArea.setText(risk.getRiskDescription());
    this.riskImpactComboBox.setSelectedItem(new Integer(risk.getRiskImpact()));
    this.riskPriorityComboBox.setSelectedItem(new Integer(risk.getRiskPriority()));
    this.riskMitigationTextArea.setText(risk.getRiskMitigation());
    this.riskObservatorTextField.setText(risk.getPersonInCharge());
    this.riskRevisionDatePicker.setDate(risk.getDateOfNextRiskReview());
    this.furtherProjectListModel.removeAllElements();
    this.furtherProjectListModel.addAll(risk.getImpactOfRiskOnOtherProjects());
    this.categeoryOfImpactOnOtherProjectsTextField.setText(risk.getCategoryOfImpactOnOtherProjects());

    this.cancelOrBackButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("back"));
    this.cancelOrBackButton.addActionListener(actionEvent -> this.eventProcessor.exitRiskDetailView(project, risk));
    this.southPanel.add(this.cancelOrBackButton);

    JButton exportRiskButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("export_risk"));
    exportRiskButton.addActionListener(actionEvent -> this.eventProcessor.exportRisk(this.project, risk));
    this.southPanel.add(exportRiskButton);

    JButton exportRiskAsInstructionButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("export_risk_instruction"));
    exportRiskAsInstructionButton.addActionListener(actionEvent -> this.eventProcessor.exportRiskAsInstruction(this.project, risk));
    this.southPanel.add(exportRiskAsInstructionButton);

    JButton deleteRiskButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("delete_risk"));
    deleteRiskButton.addActionListener(actionEvent -> this.eventProcessor.deleteRisk(this.project, risk));
    this.southPanel.add(deleteRiskButton);

    this.saveOrEditButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("edit_risk"));
    this.saveOrEditButton.addActionListener(actionEvent -> this.editButtonClick(risk));
    this.southPanel.add(this.saveOrEditButton);

    if (risk.isToReview())
    {
      JButton reviewButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("review_done"));
      reviewButton.setBackground(Color.GREEN);
      reviewButton.addActionListener(actionEvent -> this.eventProcessor.setRiskAsReviewed(this.project, risk));
      this.southPanel.add(reviewButton);
    }
    this.add(this.southPanel, BorderLayout.SOUTH);

  }

  /**
   * Sets if the contents of the ui elements can be edited.
   * Also changes the background color of the elements due to their editability
   * @param isEditable
   */
  private void setEditMode(boolean isEditable)
  {
    this.riskNameTextArea.setEditable(isEditable);
    this.riskDescriptionTextArea.setEditable(isEditable);
    this.riskPriorityComboBox.setEnabled(isEditable);
    this.riskImpactComboBox.setEnabled(isEditable);
    this.riskMitigationTextArea.setEditable(isEditable);
    this.riskObservatorTextField.setEditable(isEditable);
    this.riskRevisionDatePicker.setEnabled(isEditable);
    this.furtherProjectsList.setEnabled(isEditable);
    this.categeoryOfImpactOnOtherProjectsTextField.setEditable(isEditable);
    Color newBackgroundColor;
    if (isEditable)
    {
      newBackgroundColor = Color.WHITE;
    }
    else
    {
      newBackgroundColor = this.getBackground();
    }
    this.riskNameTextArea.setBackground(newBackgroundColor);
    this.riskDescriptionTextArea.setBackground(newBackgroundColor);
    this.riskMitigationTextArea.setBackground(newBackgroundColor);
    this.riskObservatorTextField.setBackground(newBackgroundColor);
    this.furtherProjectsList.setBackground(newBackgroundColor);
    this.categeoryOfImpactOnOtherProjectsTextField.setBackground(newBackgroundColor);
  }

  /**
   * Removes an action listener from a button
   * @param button
   */
  private void removeActionListenersFromButton(JButton button)
  {
    for(ActionListener listener: button.getActionListeners())
    {
      button.removeActionListener(listener);
    }
  }

  /**
   * Event for click on edit button. Initializes ui elements for edit mode
   * @param risk The risk that is to be edited
   */
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

  /**
   * Initializes a List Model with all projects except the one the shown risk belongs to
   * @param model
   */
  private void initializeWithAllProjectsExceptOwn(DefaultListModel<IProject> model)
  {
    List<IProject> projectList = this.rimantoView.requestProjects();
    projectList.remove(this.project);
    model.addAll(projectList);
  }

  /**
   * Eventhandler for click on cancel button. Re-intializes the panel for showing the risk which editiing was canceled
   * @param risk
   */
  private void cancelButtonClick(IRisk risk)
  {
    this.southPanel.removeAll();
    this.southPanel.revalidate();
    this.initialize(risk);
    this.setEditMode(false);
  }


  /**
   * Eventhandler for saving new created risk
   */
  private void saveButtonClick()
  {
    IRisk risk = this.generateRiskFromInput();
    if (risk != null)
    {
      this.eventProcessor.newRiskToCreate(this.project, risk);
    }
  }

  /**
   * Event handler for editing risk
   * @param risk
   */
  private void saveButtonClick(IRisk risk)
  {
    IRisk newRisk = this.generateRiskFromInput();
    if (newRisk != null)
    {
      this.eventProcessor.editRisk(this.project, risk, newRisk);
    }
  }

  /**
   * Checks the input and returns a risk, if applicable.
   * @return Risk from input data. Null, if necessary data missing
   */
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
      JOptionPane.showMessageDialog(this, this.wordbook.getWordFor("missing_risk_data"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
      return null;
    }
    LocalDate revisionDate = this.riskRevisionDatePicker.getDate();
    if (revisionDate != null)
    {
      boolean inBetween = revisionDate.isAfter(this.project.getDateOfProjectStart()) && revisionDate.isBefore(this.project.getDateOfProjectEnd());
      if (!inBetween)
      {
        JOptionPane.showMessageDialog(this, this.wordbook.getWordFor("wrong_revision_date"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
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
      JOptionPane.showMessageDialog(this, this.wordbook.getWordForWithCapitalLeadingLetter("no_category"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
      return null;
    }

    IRisk risk = new Risk(riskName, riskDescription, riskPriority, riskImpact, riskMitigation, riskObserver, revisionDate, furtherProjects, categoryForFurtherProjects);
    return risk;
  }

}
