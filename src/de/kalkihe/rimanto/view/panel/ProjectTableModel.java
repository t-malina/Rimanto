package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectTableModel extends AbstractTableModel {
  // List with the names of the columns of the table
  List<String> columnNames;
  // List of Lists of Strings with the data for every project
  List<List<String>> projectData;
  // List of projects
  List<IProject> projects;
  // Reference to the wordbook
  IWordbook wordbook;
  // Reference to the ioc container
  RimantoIOCContainer rimantoIOCContainer;

  /*
   * Constructor
   * Takes a list of projects to display in the table
   * Initializes needed variables and initiates reading of data for displaying in table
   */
  public ProjectTableModel(List<IProject> projects) throws Exception {
    this.projects = projects;
    this.columnNames = new ArrayList<String>();
    this.projectData = new ArrayList<List<String>>();
    this.resolveDependencies();
    this.readColumnNames();
    this.readData();
  }

  /*
   * Resolves all needed dependencies using the ioc container
   */
  private void resolveDependencies() throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();
    this.wordbook = (IWordbook) rimantoIOCContainer.getInstanceFor(IWordbook.class);
  }

  /*
   * Reads the names of the column in the corresponding objects
   */
  private void readColumnNames()
  {
    // Check if there is at least one project existing
    if (this.projects.size() > 0)
    {
      // Add field for the project id (index position in project list) to columns
      this.columnNames.add("Id");
      // Read the column names from ONE project
      String[] columnNames = this.projects.get(0).getGeneralDataNames();
      // Iterate over all entries in column names
      for (int index = 0; index < columnNames.length; index++)
      {
        // Add column name to list of column names
        this.columnNames.add(this.wordbook.getWordForWithCapitalLeadingLetter(columnNames[index]));
      }
    }
    // if there is no project existing
    else
    {
      // Display a message that there are no projects in the table header
      this.columnNames.add(this.wordbook.getWordFor("no projects"));
    }
  }

  /*
   * Reads the data from the projects into the corresponding objects
   */
  private void readData()
  {
    // Iterate over every project in list
    for (int index = 0; index < this.projects.size(); index++)
    {
      // Create a new list in list of lists for the current project
      this.projectData.add(index, new ArrayList<String>());
      // Add the id (position in list) of the current project to the data
      this.projectData.get(index).add(Integer.toString(index));
      // Read the rest of the general project data
      String[] currentProjectData = this.projects.get(index).getGeneralProjectData();
      // Iterate over every entry in currentProjectData and put it into data list
      for (int innerIndex = 0; innerIndex < currentProjectData.length; innerIndex++)
      {
        this.projectData.get(index).add(currentProjectData[innerIndex]);
      }
    }
  }

  /*
   * Returns the number of rows
   */
  @Override
  public int getRowCount() {
    return this.projectData.size();
  }

  /*
   * Returns the number of columns
   */
  @Override
  public int getColumnCount() {
    return this.columnNames.size();
  }

  /*
   * Returns the Value at the given column and row
   */
  @Override
  public Object getValueAt(int row, int column) {
    return this.projectData.get(row).get(column);
  }

  /*
   * Returns the heading of a given column
   */
  @Override
  public String getColumnName(int column) {
    return this.columnNames.get(column);
  }

  /*
   * Makes all cells not editable
   */
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  /*
   * Returns the project to the given id
   */
  public IProject getProjectWithId(int id)
  {
    return this.projects.get(id);
  }

  /*
   * Returns the number of the column where the project id is in
   */
  public int getColumnWithId()
  {
    return 0;
  }

}
