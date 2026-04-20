package de.kalkihe.rimanto.view.panel.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.Project;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.panel.keyevent.TabKeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.*;

/**
 * Panel to create projects
 */
public class CreateProjectPanel extends GeneralRimantoPanel {
  private JPanel centerPanel;
  private JPanel southPanel;

  private JLabel projectNameLabel;
  private JLabel projectDescriptionLabel;
  private JLabel projectStartDateLabel;
  private JLabel projectEndDateLabel;
  private JLabel nextRevisionLabel;

  private JScrollPane projectNameScrollPane;
  private JScrollPane projectDescriptionScrollPane;
  private JScrollPane furtherResourcesScrollPane;

  private JTextArea projectNameTextArea;
  private JTextArea projectDescriptionTextArea;

  private DatePicker projectStartDatePicker;
  private DatePicker projectEndDatePicker;

  private JLabel furtherResourcesLabel;
  private JTextArea furtherResourcesTextArea;
  private JButton furtherResourcesButton;

  private JLabel projectRevisionLabel;
  private DatePicker projectRevisionDatePicker;

  private JButton saveButton;
  private JButton cancelButton;
  private JButton deleteButton;

  private IProject project;

  /**
   * Constructor for panel with existing project
   * @param wordbook Wordbook for labels etc
   * @param eventProcessor Eventhandler to pass events to
   * @param rimantoView reference to view
   * @param project Project to display on ui elements
   */
  public CreateProjectPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project)
  {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.buildPanel();
    this.initialize(project);
  }

  /**
   * Constructor for panel with new project
   * @param wordbook Wordbook for labels etc
   * @param eventProcessor Eventhandler to pass events to
   * @param rimantoView reference to view
   */
  public CreateProjectPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) {
    super(wordbook, eventProcessor, rimantoView);
    this.buildPanel();
    this.initialize();
  }

  /**
   * Builds the panel
   */
  protected void buildPanel()
  {
    this.centerPanel = new JPanel(new GridLayout(0, 2, 20, 20));
    this.southPanel = new JPanel(new FlowLayout());
    this.projectNameLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project_name"));
    this.projectDescriptionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project_description"));
    this.projectNameTextArea = new JTextArea();
    this.projectNameScrollPane = super.configureAndInsertTextArea(this.projectNameTextArea);
    this.projectDescriptionTextArea = new JTextArea();
    this.projectDescriptionScrollPane = super.configureAndInsertTextArea(this.projectDescriptionTextArea);
    this.centerPanel.add(this.projectNameLabel);
    this.centerPanel.add(this.projectNameScrollPane);
    this.centerPanel.add(this.projectDescriptionLabel);
    this.centerPanel.add(this.projectDescriptionScrollPane);

    this.projectStartDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date_of_project_start"));
    this.projectEndDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date_of_project_end"));

    DatePickerSettings datePickerSettings = new DatePickerSettings();
    datePickerSettings.setAllowKeyboardEditing(false);
    datePickerSettings.setAllowEmptyDates(false);
    datePickerSettings.setFormatForDatesCommonEra(this.wordbook.getDateTimeFormatter());
    this.projectStartDatePicker = new DatePicker(datePickerSettings);
    this.projectEndDatePicker = new DatePicker(datePickerSettings.copySettings());
    this.projectStartDatePicker.setDateToToday();
    this.projectEndDatePicker.setDate(LocalDate.now().plusMonths(1));

    this.centerPanel.add(this.projectStartDateLabel);
    this.centerPanel.add(this.projectStartDatePicker);
    this.centerPanel.add(this.projectEndDateLabel);
    this.centerPanel.add(this.projectEndDatePicker);

    this.furtherResourcesLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("further_resources"));
    JPanel furtherResourcesPanel = new JPanel(new BorderLayout());
    this.furtherResourcesButton = new JButton("+");
    this.furtherResourcesButton.addActionListener(actionEvent -> this.addFileToProject());
    this.furtherResourcesTextArea = new JTextArea();
    this.furtherResourcesScrollPane = new JScrollPane(this.furtherResourcesTextArea);
    furtherResourcesPanel.add(this.furtherResourcesScrollPane, BorderLayout.CENTER);
    furtherResourcesPanel.add(this.furtherResourcesButton, BorderLayout.EAST);

    this.centerPanel.add(this.furtherResourcesLabel);
    this.centerPanel.add(furtherResourcesPanel);

    DatePickerSettings revisionDatePickerSettings = new DatePickerSettings();
    revisionDatePickerSettings.setAllowKeyboardEditing(false);
    revisionDatePickerSettings.setFormatForDatesCommonEra(this.wordbook.getDateTimeFormatter());
    this.projectRevisionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("next_date_of_revision"));
    this.projectRevisionDatePicker = new DatePicker(revisionDatePickerSettings);

    this.centerPanel.add(this.projectRevisionLabel);
    this.centerPanel.add(this.projectRevisionDatePicker);

    this.add(this.centerPanel, BorderLayout.CENTER);

    this.saveButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("save"));
    this.cancelButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("cancel"));
    this.deleteButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("delete_project"));


    this.projectNameTextArea.addKeyListener(new TabKeyAdapter(this.projectDescriptionTextArea));
    this.projectDescriptionTextArea.addKeyListener(new TabKeyAdapter(this.projectStartDatePicker));
    this.projectStartDatePicker.addKeyListener(new TabKeyAdapter(this.projectEndDatePicker));
    this.projectEndDatePicker.addKeyListener(new TabKeyAdapter(this.furtherResourcesTextArea));
    this.furtherResourcesTextArea.addKeyListener(new TabKeyAdapter(this.projectRevisionDatePicker));
    this.projectRevisionDatePicker.addKeyListener(new TabKeyAdapter(this.projectNameTextArea));
  }

  /**
   * Initializes ui elements with data of passed project and for offering edition of project
   * @param project The project with data to initialize ui elements
   */
  private void initialize(IProject project)  {
    this.projectNameTextArea.setText(project.getProjectName());
    this.projectDescriptionTextArea.setText(project.getProjectDescription());
    this.projectStartDatePicker.setDate(project.getDateOfProjectStart());
    this.projectEndDatePicker.setDate(project.getDateOfProjectEnd());
    for(URI uri : project.getLinkedResources())
    {
      this.furtherResourcesTextArea.append(uri.toString() + "\n");
    }
    this.projectRevisionDatePicker.setDate(project.getDateOfNextProjectReview());

    this.cancelButton.addActionListener(actionEvent -> this.eventProcessor.projectEditingCanceled(this.project));
    this.southPanel.add(this.cancelButton);
    this.deleteButton.addActionListener(actionEvent -> this.eventProcessor.deleteProject(project));
    this.southPanel.add(this.deleteButton);
    this.saveButton.addActionListener(actionEvent -> this.saveButtonClick(project));
    this.southPanel.add(this.saveButton);

    this.add(this.southPanel, BorderLayout.SOUTH);
  }

  /**
   * Initialize ui elements for creation of new project
   */
  private void initialize()
  {
    this.cancelButton.addActionListener(actionEvent -> this.eventProcessor.backToOverview());
    this.southPanel.add(this.cancelButton);
    this.saveButton.addActionListener(actionEvent -> this.saveButtonClick());
    this.southPanel.add(this.saveButton);

    this.add(this.southPanel, BorderLayout.SOUTH);
  }

  /**
   * Called when save button was clicked for new creation of project
   */
  private void saveButtonClick()
  {
    IProject project = this.generateProjectFromInput();
    if (project != null)
    {
      this.eventProcessor.newProjectToCreate(project);
    }
  }


  /**
   * Called when save button was clicked for edition of existing project
   * @param project The project to edit
   */
  private void saveButtonClick(IProject project)
  {
    IProject editedProject = this.generateProjectFromInput();
    if (editedProject != null)
    {
      this.eventProcessor.editProject(project, editedProject);
    }
  }

  /**
   * Checks the data in the input and creates a project
   * @return A project, when data is valid. Null, when data is not valid or data is missing
   */
  private IProject generateProjectFromInput(){
    String projectName = this.projectNameTextArea.getText();
    boolean noProjectName = projectName.trim().length() == 0;
    if (noProjectName)
    {
      JOptionPane.showMessageDialog(this, this.wordbook.getWordFor("missing_project_data"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
      return null;
    }
    LocalDate startDate = this.projectStartDatePicker.getDate();
    LocalDate endDate = this.projectEndDatePicker.getDate();
    LocalDate revisionDate = this.projectRevisionDatePicker.getDate();
    if (revisionDate != null)
    {
      boolean inBetween = revisionDate.isAfter(startDate) && revisionDate.isBefore(endDate);
      if (!inBetween)
      {
        JOptionPane.showMessageDialog(this, this.wordbook.getWordFor("wrong_revision_date"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
        return null;
      }
    }
    String projectDescription = this.projectDescriptionTextArea.getText();
    String resourcesTextArea = this.furtherResourcesTextArea.getText();
    List<URI> furtherResources = null;
    boolean noFurtherResources = resourcesTextArea.trim().length() == 0;
    if (!noFurtherResources)
    {
      String[] attachedResources = this.furtherResourcesTextArea.getText().split("\\r?\\n|\\r");
      furtherResources = new ArrayList<URI>();
      for(String resource : attachedResources)
      {
        resource = resource.trim();
        if (resource.length() != 0)
        {
          resource = resource.replace('\\', '/');
          furtherResources.add(URI.create(resource));
        }
      }
    }
    IProject project = new Project(projectName, projectDescription, startDate, endDate, furtherResources, revisionDate);
    return project;
  }

  /**
   * Allows user to add files to the project. Shows file chooser and adds the URI to this file to the corresponding text area
   */
  void addFileToProject()
  {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(true);
    int state = fileChooser.showOpenDialog(this);
    if (state == JFileChooser.APPROVE_OPTION)
    {
      String resources = this.furtherResourcesTextArea.getText();
      String file = fileChooser.getSelectedFile().toURI().toString();
      this.furtherResourcesTextArea.setText(resources + "\n" + file);
    }
  }
}
