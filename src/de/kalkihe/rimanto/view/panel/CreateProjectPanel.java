package de.kalkihe.rimanto.view.panel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.Project;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.*;

public class CreateProjectPanel extends GeneralRimantoPanel {
  private JPanel centerPanel;

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

  private JButton saveButton;
  private JButton cancelButton;


  public CreateProjectPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) {
    super(wordbook, eventProcessor, rimantoView);
    this.buildPanel();
  }

  protected void buildPanel()
  {
    this.centerPanel = new JPanel(new GridLayout(0, 2, 20, 20));
    this.projectNameLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project name"));
    this.projectDescriptionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project description"));
    this.projectNameTextField = new JTextArea();
    this.projectNameScrollPane = new JScrollPane(this.projectNameTextField);
    this.projectDescriptionTextArea = new JTextArea();
    this.projectDescriptionScrollPane = new JScrollPane(this.projectDescriptionTextArea);
    this.centerPanel.add(this.projectNameLabel);
    this.centerPanel.add(this.projectNameScrollPane);
    this.centerPanel.add(this.projectDescriptionLabel);
    this.centerPanel.add(this.projectDescriptionScrollPane);

    this.projectStartDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date of project start"));
    this.projectEndDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date of project end"));

    DatePickerSettings datePickerSettings = new DatePickerSettings();
    datePickerSettings.setAllowKeyboardEditing(false);
    datePickerSettings.setAllowEmptyDates(false);
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
    this.furtherResourcesScrollPane = new JScrollPane(this.furtherResourcesTextArea);

    this.centerPanel.add(this.furtherResourcesLabel);
    this.centerPanel.add(this.furtherResourcesScrollPane);

    DatePickerSettings revisionDatePickerSettings = new DatePickerSettings();
    revisionDatePickerSettings.setAllowKeyboardEditing(false);
    this.projectRevisionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("next date of revision"));
    this.projectRevisionDatePicker = new DatePicker(revisionDatePickerSettings);

    this.centerPanel.add(this.projectRevisionLabel);
    this.centerPanel.add(this.projectRevisionDatePicker);


    this.cancelButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("cancel"));
    this.saveButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("save"));

    this.cancelButton.addActionListener(actionEvent ->
    {
      this.eventProcessor.newProjectCreationCanceled();
    });

    //TODO: Buttons in Southpanel
    this.saveButton.addActionListener(actionEvent -> this.saveButtonClick());
    this.centerPanel.add(this.cancelButton);
    this.centerPanel.add(this.saveButton);
    this.add(this.centerPanel);
  }

  private void saveButtonClick()
  {
    String projectName = this.projectNameTextField.getText();
    boolean noProjectName = projectName.trim().length() == 0;
    if (noProjectName)
    {
      JOptionPane.showMessageDialog(this, this.wordbook.getWordFor("missing project data"), this.wordbook.getWordForWithCapitalLeadingLetter("error"), 0);
      return;
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
        return;
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
        if (resource.length() == 0)
        {
          furtherResources.add(URI.create(resource));
        }
      }
    }
    IProject project = new Project(projectName, projectDescription, startDate, endDate, furtherResources, revisionDate);
    this.eventProcessor.newProjectToCreate(project);
  }
}
