package de.kalkihe.rimanto.view.tablemodel;

import de.kalkihe.rimanto.model.data.IRisk;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model for risks
 */
public class RiskTableModel extends GeneralTableModel {
  private List<IRisk> risks;

  /**
   * Constructor. Initializes table.
   * @param risks List of risks to display
   * @throws Exception
   */
  public RiskTableModel(List<IRisk> risks) throws Exception {
    super();
    this.risks = risks;
    this.readColumnNames();
    this.readData();
  }

  /**
   *
   * @param id
   * @return Risk to the given id
   */
  public IRisk getRiskWithId(int id)
  {
    return this.risks.get(id);
  }

  /**
   * Reads the names of the column in the corresponding objects
   */
  private void readColumnNames() {
    // Check if there is at least one project existing
    if (this.risks.size() > 0) {
      // Add field for the project id (index position in project list) to columns
      this.columnNames.add("Id");
      // Read the column names from ONE project
      String[] columnNames = this.risks.get(0).getGeneralDataNames();
      // Iterate over all entries in column names
      for (int index = 0; index < columnNames.length; index++) {
        // Add column name to list of column names
        this.columnNames.add(this.wordbook.getWordForWithCapitalLeadingLetter(columnNames[index]));
      }
    }
    // if there is no project existing
    else {
      // Display a message that there are no projects in the table header
      this.columnNames.add(this.wordbook.getWordForWithCapitalLeadingLetter("no_risks"));
    }
  }

  /**
   * Reads the data from the risks into the corresponding objects
   */
  private void readData()
  {
    // Iterate over every project in list
    for (int index = 0; index < this.risks.size(); index++)
    {
      // Create a new list in list of lists for the current project
      this.tableData.add(index, new ArrayList<String>());
      // Add the id (position in list) of the current project to the data
      this.tableData.get(index).add(Integer.toString(index));
      // Read the rest of the general project data
      String[] currentRiskData = this.risks.get(index).getGeneralRiskData();
      // Iterate over every entry in currentRiskData and put it into data list
      for (int innerIndex = 0; innerIndex < currentRiskData.length; innerIndex++)
      {
        this.tableData.get(index).add(currentRiskData[innerIndex]);
      }
    }
  }

  /**
   * Returns the background color of the passed table in the passed row
   * @param table
   * @param row
   * @return Color of the table in the row
   */
  @Override
  public Color getRowColor(JTable table, int row) {
    int riskId = super.getIdAtRow(table, row);
    IRisk risk = this.getRiskWithId(riskId);
    if (risk.isToReview())
    {
      return Color.RED;
    }
    boolean isSelectedRow = row == table.getSelectedRow();
    if (isSelectedRow)
    {
      return Color.LIGHT_GRAY;
    }
    return Color.WHITE;

  }
}
