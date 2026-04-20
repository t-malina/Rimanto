package de.kalkihe.rimanto.view.panel.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.Project;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.panel.keyevent.TabKeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.security.Key;
import java.time.LocalDate;
import java.util.List;
import java.util.*;

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

  private JLabel projectRevisionLabel;
  private DatePicker projectRevisionDatePicker;

  private JButton saveButton;
  private JButton cancelButton;
  private JButton deleteButton;

  private IProject project;

  public CreateProjectPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project)
  {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.buildPanel();
    this.initialize(project);
  }

  public CreateProjectPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) {
    super(wordbook, eventProcessor, rimantoView);
    this.buildPanel();
    this.initialize();
  }

  protected void buildPanel()
  {
    this.centerPanel = new JPanel(new GridLayout(0, 2, 20, 20));
    this.southPanel = new JPanel(new FlowLayout());
    this.projectNameLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project name"));
    this.projectDescriptionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project description"));
    this.projectNameTextArea = new JTextArea();
    this.projectNameScrollPane = super.configureAndInsertTextArea(this.projectNameTextArea);
    this.projectDescriptionTextArea = new JTextArea();
    this.projectDescriptionScrollPane = super.configureAndInsertTextArea(this.projectDescriptionTextArea);
    this.centerPanel.add(this.projectNameLabel);
    this.centerPanel.add(this.projectNameScrollPane);
    this.centerPanel.add(this.projectDescriptionLabel);
    this.centerPanel.add(this.projectDescriptionScrollPane);

    this.projectStartDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date of project start"));
    this.projectEndDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date of project end"));

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

    this.furtherResourcesLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("further resources"));
    this.furtherResourcesTextArea = new JTextArea();
    this.furtherResourcesScrollPane = super.configureAndInsertTextArea(this.furtherResourcesTextArea);

    this.centerPanel.add(this.furtherResourcesLabel);
    this.centerPanel.add(this.furtherResourcesScrollPane);

    DatePickerSettings revisionDatePickerSettings = new DatePickerSettings();
    revisionDatePickerSettings.setAllowKeyboardEditing(false);
    revisionDatePickerSettings.setFormatForDatesCommonEra(this.wordbook.getDateTimeFormatter());
    this.projectRevisionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("next date of revision"));
    this.projectRevisionDatePicker = new DatePicker(revisionDatePickerSettings);

    this.centerPanel.add(this.projectRevisionLabel);
    this.centerPanel.add(this.projectRevisionDatePicker);

    this.add(this.centerPanel, BorderLayout.CENTER);

    this.saveButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("save"));
    this.cancelButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("cancel"));
    this.deleteButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("delete project"));


    this.projectNameTextArea.addKeyListener(new TabKeyAdapter(this.projectDescriptionTextArea));
    this.projectDescriptionTextArea.addKeyListener(new TabKeyAdapter(this.projectStartDatePicker));
    this.projectStartDatePicker.addKeyListener(new TabKeyAdapter(this.projectEndDatePicker));
    this.projectEndDatePicker.addKeyListener(new TabKeyAdapter(this.furtherResourcesTextArea));
    this.furtherResourcesTextArea.addKeyListener(new TabKeyAdapter(this.projectRevisionDatePicker));
    this.projectRevisionDatePicker.addKeyListener(new TabKeyAdapter(this.projectNameTextArea));

  }


  private void initialize(IProject project)  {
    this.projectNameTextArea.setText(project.getProjectName());
    this.projectDescriptionTextArea.setText(project.getProjectDescription());
    this.projectStartDatePicker.setDate(project.getDateOfProjectStart());
    this.projectEndDatePicker.setDate(project.getDateOfProjectEnd());
    for(URI uri : project.getLinkedResources())
    {
      this.furtherResourcesTextArea.append(uri.toString() + "\n");
    }
    this.projectRevisionDatePicker.setDate(project.getDateOfNextProjectRevision());

    this.cancelButton.addActionListener(actionEvent -> this.eventProcessor.projectEditingCanceled(this.project));
    this.southPanel.add(this.cancelButton);
    this.deleteButton.addActionListener(actionEvent -> this.eventProcessor.deleteProject(project));
    this.southPanel.add(this.deleteButton);
    this.saveButton.addActionListener(actionEvent -> this.saveButtonClick(project));
    this.southPanel.add(this.saveButton);

    this.add(this.southPanel, BorderLayout.SOUTH);


  }

  private void initialize()
  {
    this.cancelButton.addActionListener(actionEvent -> this.eventProcessor.backToOverview());
    this.southPanel.add(this.cancelButton);
    this.saveButton.addActionListener(actionEvent -> this.saveButtonClick());
    this.southPanel.add(this.saveButton);


    this.add(this.southPanel, BorderLayout.SOUTH);
  }

  private void saveButtonClick()
  {
    IProject project = this.generateProjectFromInput();
    if (project != null)
    {
      this.eventProcessor.newProjectToCreate(project);
    }
  }


  private void saveButtonClick(IProject project)
  {
    IProject editedProject = this.generateProjectFromInput();
    if (editedProject != null)
    {
      this.eventProcessor.editProject(project, editedProject);
    }
  }

  private IProject generateProjectFromInput(){
    String projectName = this.projectNameTextArea.getText();
    boolean noProjectName = projectName.trim().length() == 0;
    if (noProjectName)
    {
      JOptionPane.showMessageDialog(this, this.wordbook.getWordFor("missing project data"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
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
        JOptionPane.showMessageDialog(this, this.wordbook.getWordFor("wrong revision date"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
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
          furtherResources.add(URI.create(resource));
        }
      }
    }
    IProject project = new Project(projectName, projectDescription, startDate, endDate, furtherResources, revisionDate);
    return project;
  }
}
