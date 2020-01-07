package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

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
    this.southPanel = new JPanel(new FlowLayout());

    // Create new Table
    this.projectTable = new JTable();
    // Add Sorter to table
    this.projectTable.setAutoCreateRowSorter(true);
    // Set Table Model for Table
    this.projectTable.setModel(new ProjectTableModel(this.rimantoView.requestProjects()));
    // Put Table into Scroll pane
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
          JTable target = (JTable) e.getSource();
          // Get model of table
          ProjectTableModel model = (ProjectTableModel) target.getModel();
          // Read selected Row
          int selectedRow = target.getSelectedRow();
          // Read the cell with the id of the selected project
          Object cellContent = target.getValueAt(selectedRow, model.getColumnWithId());
          // Convert content of the cell to integer
          String cellText = cellContent.toString();
          Integer projectId = Integer.valueOf(cellText);
          // Get selected project
          IProject project = model.getProjectWithId(projectId);
          //TODO;Replace with call to view for selected project
          JDialog dialog = new JDialog();
          dialog.setSize(400, 200);
          dialog.setTitle(project.getProjectName());
          dialog.setVisible(true);
        }
      }
    });
    // Add Scroll pane to center panel
    this.centerPanel.add(this.projectTableScrollPane);

    //TODO: Buttons
    JButton newProjectButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("new project"));
    JButton importProjectButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("import project"));
    this.southPanel.add(importProjectButton);
    this.southPanel.add(newProjectButton);
    //TODO: Labels
    JLabel overviewLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("current projects"));
    overviewLabel.setBorder(new EmptyBorder(10, 20, 10, 0));
    this.northPanel.add(overviewLabel);
    // Add panels to main Panels
    this.add(this.northPanel, BorderLayout.NORTH);
    this.add(this.centerPanel, BorderLayout.CENTER);
    this.add(this.southPanel, BorderLayout.SOUTH);
  }
}
