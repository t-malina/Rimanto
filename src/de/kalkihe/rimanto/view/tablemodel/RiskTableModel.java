package de.kalkihe.rimanto.view.tablemodel;

import de.kalkihe.rimanto.model.data.IRisk;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RiskTableModel extends GeneralTableModel {
  private List<IRisk> risks;

  public RiskTableModel(List<IRisk> risks) throws Exception {
    super();
    this.risks = risks;
    this.readColumnNames();
    this.readData();
  }

  public IRisk getRiskWithId(int id)
  {
    return this.risks.get(id);
  }

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
      this.columnNames.add(this.wordbook.getWordFor("no risks"));
    }
  }

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

  public Color getRowColor(int row)
  {
    //TODO: Implement returning of right color
    return null;
  }
}
