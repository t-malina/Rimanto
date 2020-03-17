package de.kalkihe.rimanto.view.tablemodel;

import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GeneralTableModel extends AbstractTableModel {
  // List with the names of the columns of the table
  java.util.List<String> columnNames;
  // List of Lists of Strings with the data for the table
  List<List<String>> tableData;
  // Reference to the wordbook
  IWordbook wordbook;
  // Reference to the ioc container
  RimantoIOCContainer rimantoIOCContainer;

  protected GeneralTableModel() throws Exception {
    this.columnNames = new ArrayList<String>();
    this.tableData = new ArrayList<List<String>>();
    this.resolveDependencies();
  }

  /*
   * Resolves all needed dependencies using the ioc container
   */
  private void resolveDependencies() throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();
    this.wordbook = (IWordbook) rimantoIOCContainer.getInstanceFor(IWordbook.class);
  }

  /*
   * Returns the number of rows
   */
  @Override
  public int getRowCount() {
    return this.tableData.size();
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
    return this.tableData.get(row).get(column);
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
   * Returns the number of the column where the project id is in
   */
  public int getColumnWithId()
  {
    return 0;
  }

  abstract public Color getRowColor(JTable table, int row);


  protected int getIdAtRow(JTable table, int row)
  {
    Object cellContent = table.getValueAt(row, this.getColumnWithId());
    // Convert content of the cell to integer
    String cellText = cellContent.toString();
    Integer id = Integer.valueOf(cellText);
    return id;
  }
}
