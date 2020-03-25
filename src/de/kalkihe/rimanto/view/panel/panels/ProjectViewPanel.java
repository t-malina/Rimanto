package de.kalkihe.rimanto.view.panel.panels;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.tablemodel.GeneralTableModel;
import de.kalkihe.rimanto.view.tablemodel.RimantoTableCellRenderer;
import de.kalkihe.rimanto.view.tablemodel.RiskTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class ProjectViewPanel extends GeneralRimantoPanel {
  private IProject project;

  private JPanel northPanel;
  private JPanel centerPanel;
  private JPanel southPanel;
  private JPanel descriptionPanel;
  private JPanel buttonPanel;

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
    this.northPanel = new JPanel(new GridLayout(0, 2));
    this.centerPanel = new JPanel(new BorderLayout());
    this.southPanel = new JPanel(new GridLayout(2, 0));
    this.descriptionPanel = new JPanel(new FlowLayout());
    this.buttonPanel = new JPanel(new FlowLayout());

    this.buildProjectPanel();

    this.buildRiskPanel();

    JLabel label = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("risk_to_review"));
    label.setOpaque(true);
    label.setBackground(Color.RED);
    this.descriptionPanel.add(label);

    // Buttons
    JButton backButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("back"));
    backButton.addActionListener(actionEvent -> this.eventProcessor.backToOverview());
    this.buttonPanel.add(backButton);
    JButton editProjectButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("edit_project"));
    editProjectButton.addActionListener(actionEvent -> this.eventProcessor.projectEditingRequested(this.project));
    this.buttonPanel.add(editProjectButton);
    JButton exportProjectButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("export_project"));
    exportProjectButton.addActionListener(actionEvent -> this.eventProcessor.exportProject(this.project));
    this.buttonPanel.add(exportProjectButton);
    JButton importRiskButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("import_risk"));
    importRiskButton.addActionListener(actionEvent -> this.eventProcessor.riskImportRequested(this.project));
    this.buttonPanel.add(importRiskButton);
    JButton newRiskButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("new_risk"));
    newRiskButton.addActionListener(actionEvent -> this.eventProcessor.newRiskButtonClick(this.project));
    this.buttonPanel.add(newRiskButton);

    if(this.project.isToReview())
    {
      JButton reviewButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("review_done"));
      reviewButton.setBackground(Color.GREEN);
      reviewButton.addActionListener(actionEvent -> this.eventProcessor.setProjectAsReviewed(this.project));
      this.buttonPanel.add(reviewButton);
    }

    this.southPanel.add(this.descriptionPanel);
    this.southPanel.add(this.buttonPanel);

    // Add panels to main Panels
    this.add(this.northPanel, BorderLayout.NORTH);
    this.add(this.centerPanel, BorderLayout.CENTER);
    this.add(this.southPanel, BorderLayout.SOUTH);
  }

  private void buildProjectPanel()
  {
    JLabel nameLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project_name"));
    JTextArea projectNameLabel = new JTextArea(this.project.getProjectName());
    JScrollPane projectNameScrollPane = super.getScrollPaneWithReadOnly(projectNameLabel);
    JLabel descriptionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project_description"));
    JTextArea projectDescriptionLabel = new JTextArea(this.project.getProjectDescription());
    JScrollPane projectDescriptionScrollPane = super.getScrollPaneWithReadOnly(projectDescriptionLabel);
    projectDescriptionScrollPane.setPreferredSize(new Dimension(0, 35));

    this.northPanel.add(nameLabel);
    this.northPanel.add(projectNameScrollPane);
    this.northPanel.add(descriptionLabel);
    this.northPanel.add(projectDescriptionScrollPane);

    JLabel startDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date_of_project_start"));
    JLabel projectStartDateLabel = new JLabel(this.project.getDateOfProjectStart().toString());
    JLabel endDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("date_of_project_end"));
    JLabel projectEndDateLabel = new JLabel(this.project.getDateOfProjectEnd().toString());

    this.northPanel.add(startDateLabel);
    this.northPanel.add(projectStartDateLabel);
    this.northPanel.add(endDateLabel);
    this.northPanel.add(projectEndDateLabel);

    JLabel furtherResourcesLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("further_resources"));

    JList furtherRessoucesList = new JList(this.project.getLinkedResources().toArray());
    furtherRessoucesList.setBackground(this.getBackground());
    furtherRessoucesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    furtherRessoucesList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if(e.getClickCount() == 2)
        {
          JList target = (JList) e.getSource();
          eventProcessor.ressourceForViewRequested(target.getSelectedValue().toString());
        }
      }
    });

    this.northPanel.add(furtherResourcesLabel);
    JScrollPane scrollPane = new JScrollPane(furtherRessoucesList);
    scrollPane.setPreferredSize(new Dimension(0, 20));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    this.northPanel.add(scrollPane);

    JLabel revisionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("next_date_of_revision"));
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
    GeneralTableModel riskTableModel = new RiskTableModel(this.rimantoView.requestRisksForProject(project));
    this.riskTable.setModel(riskTableModel);
    for (int index = 0; index < riskTableModel.getColumnCount(); index++)
    {
      this.riskTable.getColumnModel().getColumn(index).setCellRenderer(new RimantoTableCellRenderer());
    }
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

          eventProcessor.riskForDetailViewSelected(project, risk);
        }
      }
    });
    // Add Scroll pane to center panel
    this.centerPanel.add(this.riskTableScrollPane);
  }
}
