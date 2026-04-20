package de.kalkihe.rimanto.view.panel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.data.Risk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.tablemodel.ProjectTableModel;
import de.kalkihe.rimanto.view.tablemodel.RiskTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProjectViewPanel extends GeneralRimantoPanel {
  private IProject project;

  private JPanel northPanel;
  private JPanel centerPanel;
  private JPanel southPanel;

  private JTable riskTable;
  private JScrollPane riskTableScrollPane;

  private JLabel projectNameLabel;
  private JLabel projectDescriptionLabel;
  private JLabel projectStartDateLabel;
  private JLabel projectEndDateLabel;
  private JLabel nextRevisionLabel;

  private JScrollPane projectNameScrollPane;
  private JScrollPane projectDescriptionScrollPane;
  private JScrollPane furtherResourcesScrollPane;

  private JTextArea projectNameTextField;
  private JTextArea projectDescriptionTextArea;

  private DatePicker projectStartDatePicker;
  private DatePicker projectEndDatePicker;

  private JLabel furtherResourcesLabel;
  private JTextArea furtherResourcesTextArea;

  private JLabel projectRevisionLabel;
  private DatePicker projectRevisionDatePicker;

  public ProjectViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.buildPanel();
  }

  @Override
  protected void buildPanel() throws Exception {
    // Create Panels
    this.northPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    this.centerPanel = new JPanel(new BorderLayout());
    this.southPanel = new JPanel(new FlowLayout());

    this.buildProjectPanel();
    this.switchEditiingOfProjectData(false);

    this.buildRiskPanel();

    // Buttons

    // Add panels to main Panels
    this.add(this.northPanel, BorderLayout.NORTH);
    this.add(this.centerPanel, BorderLayout.CENTER);
    this.add(this.southPanel, BorderLayout.SOUTH);
  }

  private void buildProjectPanel()
  {
    this.projectNameLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project name"));
    this.projectDescriptionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project description"));
    this.projectNameTextField = new JTextArea(this.project.getProjectName());
    this.projectNameScrollPane = new JScrollPane(this.projectNameTextField);
    this.projectDescriptionTextArea = new JTextArea(this.project.getProjectDescription());
    this.projectDescriptionScrollPane = new JScrollPane(this.projectDescriptionTextArea);
    this.northPanel.add(this.projectNameLabel);
    this.northPanel.add(this.projectNameScrollPane);
    this.northPanel.add(this.projectDescriptionLabel);
    this.northPanel.add(this.projectDescriptionScrollPane);

    this.projectStartDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date of project start"));
    this.projectEndDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date of project end"));

    DatePickerSettings datePickerSettings = new DatePickerSettings();
    datePickerSettings.setAllowKeyboardEditing(false);
    datePickerSettings.setAllowEmptyDates(false);
    this.projectStartDatePicker = new DatePicker(datePickerSettings);
    this.projectStartDatePicker.setDate(this.project.getDateOfProjectStart());
    this.projectEndDatePicker = new DatePicker(datePickerSettings.copySettings());
    this.projectEndDatePicker.setDate(this.project.getDateOfProjectEnd());

    this.northPanel.add(this.projectStartDateLabel);
    this.northPanel.add(this.projectStartDatePicker);
    this.northPanel.add(this.projectEndDateLabel);
    this.northPanel.add(this.projectEndDatePicker);


    this.furtherResourcesLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("further resources"));
    // TODO: Darstellung Liste
    this.furtherResourcesTextArea = new JTextArea();
    this.furtherResourcesScrollPane = new JScrollPane(this.furtherResourcesTextArea);

    this.northPanel.add(this.furtherResourcesLabel);
    this.northPanel.add(this.furtherResourcesScrollPane);

    DatePickerSettings revisionDatePickerSettings = new DatePickerSettings();
    revisionDatePickerSettings.setAllowKeyboardEditing(false);
    this.projectRevisionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("next date of revision"));
    this.projectRevisionDatePicker = new DatePicker(revisionDatePickerSettings);
    this.projectRevisionDatePicker.setDate(this.project.getDateOfNextProjectRevision());

    this.northPanel.add(this.projectRevisionLabel);
    this.northPanel.add(this.projectRevisionDatePicker);
  }

  private void switchEditiingOfProjectData(boolean isEditable)
  {
    this.projectDescriptionTextArea.setEnabled(isEditable);
    this.projectNameTextField.setEnabled(isEditable);
    this.projectStartDatePicker.setEnabled(isEditable);
    this.projectEndDatePicker.setEnabled(isEditable);
    this.furtherResourcesTextArea.setEnabled(isEditable);
    this.projectRevisionDatePicker.setEnabled(isEditable);
  }

  private void buildRiskPanel() throws Exception {
    // Create new Table
    this.riskTable = new JTable();
    // Add Sorter to table
    this.riskTable.setAutoCreateRowSorter(true);
    // Set Table Model for Table
    this.riskTable.setModel(new RiskTableModel(this.rimantoView.requestRisksForProject(project)));
    // Put Table into Scroll pane
    this.riskTableScrollPane = new JScrollPane(this.riskTable);
    // Adjust scroll pane
    this.riskTableScrollPane.setPreferredSize(new Dimension(400,300));
    this.riskTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.riskTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // Add click listener for selecting project
    this.riskTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        // Has to a double click
        if (e.getClickCount() == 2) {
          // Get clicked table
          JTable target = (JTable) e.getSource();
          // Get model of table
          RiskTableModel model = (RiskTableModel) target.getModel();
          // Read selected Row
          int selectedRow = target.getSelectedRow();
          // Read the cell with the id of the selected risk
          Object cellContent = target.getValueAt(selectedRow, model.getColumnWithId());
          // Convert content of the cell to integer
          String cellText = cellContent.toString();
          Integer riskId = Integer.valueOf(cellText);
          // Get selected risk
          IRisk risk = model.getRiskWithId(riskId);

          eventProcessor.riskForDetailViewSelected(risk);
        }
      }
    });
    // Add Scroll pane to center panel
    this.centerPanel.add(this.riskTableScrollPane);
  }
}
