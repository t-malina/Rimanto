package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OverviewPanel extends JPanel {
  private IWordbook wordbook;
  private IEventProcessor eventProcessor;
  private IRimantoView rimantoView;

  private JPanel northPanel;
  private JPanel centerPanel;
  private JPanel southPanel;
  private JTable projectTable;
  private JScrollPane projectTableScrollPane;

  public OverviewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) {
    super(new BorderLayout());
    this.eventProcessor = eventProcessor;
    this.rimantoView = rimantoView;
    this.wordbook = wordbook;
    this.buildPanel();
  }

  private void buildPanel()
  {
    this.northPanel = new JPanel(new BorderLayout());
    this.centerPanel = new JPanel(new BorderLayout());
    this.southPanel = new JPanel(new FlowLayout());

    String data[][]= this.rimantoView.requestDataForProjectOverview();
    String column[]= this.rimantoView.requestColumnNamesForProjectOverview();

    this.projectTable = new JTable(data, column);
    this.projectTable.setModel(new RimantoTableModel(data, column));
    this.projectTableScrollPane = new JScrollPane(this.projectTable);
    this.projectTableScrollPane.setPreferredSize(new Dimension(400,300));
    this.projectTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.projectTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.projectTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getClickCount() == 2) {
          JTable target = (JTable) e.getSource();
          int row = target.getSelectedRow();
          int column = target.getSelectedColumn();
          // do some action if appropriate column
          JDialog dialog = new JDialog();
          dialog.setTitle("row: " + row + ", column: " + column);
          dialog.setVisible(true);
        }
      }
    });
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
