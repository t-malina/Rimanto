package de.kalkihe.rimanto.view.panel.panels;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.tablemodel.GeneralTableModel;
import de.kalkihe.rimanto.view.tablemodel.ProjectTableModel;
import de.kalkihe.rimanto.view.tablemodel.RimantoTableCellRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OverviewPanel extends GeneralRimantoPanel {
  /*
   * UI-elements
   */
  private JPanel northPanel;
  private JPanel centerPanel;
  private JPanel southPanel;
  private JPanel buttonPanel;
  private JPanel descriptionPanel;
  private JTable projectTable;
  private JScrollPane projectTableScrollPane;

  /*
   * Constructors
   * Initializes needed references
   * Initiates building of the panel
   */
  public OverviewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.buildPanel();
  }

  protected void buildPanel() throws Exception {
    // Create Panels
    this.northPanel = new JPanel(new BorderLayout());
    this.centerPanel = new JPanel(new BorderLayout());
    this.southPanel = new JPanel(new GridLayout(2, 0));
    this.buttonPanel = new JPanel(new FlowLayout());
    this.descriptionPanel = new JPanel(new FlowLayout());

    // Create new Table
    this.projectTable = new JTable();
    // Add Sorter to table
    this.projectTable.setAutoCreateRowSorter(true);
    // Set Table Model for Table
    GeneralTableModel projectTableModel = new ProjectTableModel(this.rimantoView.requestProjects());
    this.projectTable.setModel(projectTableModel);
    for(int index = 0; index < projectTableModel.getColumnCount(); index++)
    {
      this.projectTable.getColumnModel().getColumn(index).setCellRenderer(new RimantoTableCellRenderer());
    }
    // Put Table into Scroll pan
    this.projectTableScrollPane = new JScrollPane(this.projectTable);
    // Adjust scroll pane
    this.projectTableScrollPane.setPreferredSize(new Dimension(400,300));
    this.projectTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.projectTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // Add click listener for selecting project
    this.projectTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        // Has to a double click
        if (e.getClickCount() == 2) {
          // Get clicked table
          JTable table = (JTable) e.getSource();
          // Get model of table
          ProjectTableModel model = (ProjectTableModel) table.getModel();
          // Read selected Row
          int selectedRow = table.getSelectedRow();
          // Read the cell with the id of the selected project
          Object cellContent = table.getValueAt(selectedRow, model.getColumnWithId());
          // Convert content of the cell to integer
          String cellText = cellContent.toString();
          Integer projectId = Integer.valueOf(cellText);
          // Get selected project
          IProject project = model.getProjectWithId(projectId);
          //TODO;Replace with call to view for selected project
          eventProcessor.projectForDetailViewSelected(project);
        }
      }
    });
    // Add Scroll pane to center panel
    this.centerPanel.add(this.projectTableScrollPane);

    JButton newProjectButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("new project"));
    newProjectButton.addActionListener(actionEvent -> {
      this.eventProcessor.newProjectButtonClick();
    });
    JButton importProjectButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("import project"));
    importProjectButton.addActionListener(actionEvent -> {
      this.eventProcessor.projectImportRequested();
    });
    this.buttonPanel.add(importProjectButton);
    this.buttonPanel.add(newProjectButton);
    //TODO: Labels
    JLabel overviewLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("current projects"));
    overviewLabel.setBorder(new EmptyBorder(10, 20, 10, 0));
    this.northPanel.add(overviewLabel);

    JLabel projectReviewLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project to review"));
    projectReviewLabel.setOpaque(true);
    projectReviewLabel.setBackground(Color.RED);
    this.descriptionPanel.add(projectReviewLabel);

    JLabel projectRiskReviewLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("project with risks to review"));
    projectRiskReviewLabel.setOpaque(true);
    projectRiskReviewLabel.setBackground(Color.YELLOW);
    this.descriptionPanel.add(projectRiskReviewLabel);

    this.southPanel.add(this.descriptionPanel);
    this.southPanel.add(this.buttonPanel);


    // Add panels to main Panels
    this.add(this.northPanel, BorderLayout.NORTH);
    this.add(this.centerPanel, BorderLayout.CENTER);
    this.add(this.southPanel, BorderLayout.SOUTH);

    //TODO: Legende
  }
}
