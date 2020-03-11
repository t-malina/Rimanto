package de.kalkihe.rimanto.view.panel.panels;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.tablemodel.RiskTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;

public class ProjectViewPanel extends GeneralRimantoPanel {
  private IProject project;

  private JPanel northPanel;
  private JPanel centerPanel;
  private JPanel southPanel;

  private JTable riskTable;
  private JScrollPane riskTableScrollPane;

  public ProjectViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.buildPanel();
  }

  @Override
  protected void buildPanel() throws Exception {
    // Create Panels
    this.northPanel = new JPanel(new GridLayout(0, 2, 0, 0));
    this.centerPanel = new JPanel(new BorderLayout());
    this.southPanel = new JPanel(new FlowLayout());

    this.buildProjectPanel();

    this.buildRiskPanel();

    // Buttons
    JButton backButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("back"));
    backButton.addActionListener(actionEvent -> this.eventProcessor.backToOverview());
    JButton editProjectButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("edit project"));
    editProjectButton.addActionListener(actionEvent -> this.eventProcessor.projectEditingRequested(this.project));
    JButton exportProjectButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("export project"));
    exportProjectButton.addActionListener(actionEvent -> this.eventProcessor.exportProject(this.project));
    JButton importRiskButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("import risk"));
    importRiskButton.addActionListener(actionEvent -> this.eventProcessor.riskImportRequested(this.project));
    JButton newRiskButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("new risk"));
    newRiskButton.addActionListener(actionEvent -> this.eventProcessor.newRiskButtonClick(this.project));

    this.southPanel.add(backButton);
    this.southPanel.add(editProjectButton);
    this.southPanel.add(exportProjectButton);
    this.southPanel.add(importRiskButton);
    this.southPanel.add(newRiskButton);

    // Add panels to main Panels
    this.add(this.northPanel, BorderLayout.NORTH);
    this.add(this.centerPanel, BorderLayout.CENTER);
    this.add(this.southPanel, BorderLayout.SOUTH);
  }

  private void buildProjectPanel()
  {
    JLabel nameLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project name"));
    JLabel projectNameLabel = new JLabel(this.project.getProjectName());
    JScrollPane projectNameScrollPane = new JScrollPane(projectNameLabel);
    JLabel descriptionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project description"));
    JLabel projectDescriptionLabel = new JLabel(this.project.getProjectDescription());
    JScrollPane projectDescriptionScrollPane = new JScrollPane(projectDescriptionLabel);
    projectDescriptionScrollPane.setPreferredSize(new Dimension(0, 35));

    this.northPanel.add(nameLabel);
    this.northPanel.add(projectNameScrollPane);
    this.northPanel.add(descriptionLabel);
    this.northPanel.add(projectDescriptionScrollPane);

    JLabel startDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date of project start"));
    JLabel projectStartDateLabel = new JLabel(this.project.getDateOfProjectStart().toString());
    JLabel endDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date of project end"));
    JLabel projectEndDateLabel = new JLabel(this.project.getDateOfProjectEnd().toString());

    this.northPanel.add(startDateLabel);
    this.northPanel.add(projectStartDateLabel);
    this.northPanel.add(endDateLabel);
    this.northPanel.add(projectEndDateLabel);

    JLabel furtherResourcesLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("further resources"));
    JPanel resourcesPanel = new JPanel(new GridLayout(0, 1));
    for(URI uri : this.project.getLinkedResources())
    {
      JButton button = new JButton(uri.toString());
      button.addActionListener(actionEvent ->
      {
        //TODO: Auch für Dateien auf System oder Netzwerk möglich machen
        Desktop desktop = java.awt.Desktop.getDesktop();
        try {
          desktop.browse(uri);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      resourcesPanel.add(button);
    }

    this.northPanel.add(furtherResourcesLabel);
    this.northPanel.add(resourcesPanel);

    JLabel revisionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("next date of revision"));
    LocalDate date = this.project.getDateOfNextProjectRevision();
    JLabel projectRevisionLabel;
    if (date == null)
    {
      projectRevisionLabel = new JLabel("None");
    }
    else
    {
      projectRevisionLabel = new JLabel(this.project.getDateOfNextProjectRevision().toString());
    }

    this.northPanel.add(revisionLabel);
    this.northPanel.add(projectRevisionLabel);
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
