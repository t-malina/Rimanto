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

  /**
   * Base constructor. Initalizes needed lists and references
   * @throws Exception
   */
  protected GeneralTableModel() throws Exception {
    this.columnNames = new ArrayList<String>();
    this.tableData = new ArrayList<List<String>>();
    this.resolveDependencies();
  }

  /**
   * Resolves all needed dependencies using the ioc container
   */
  private void resolveDependencies() throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();
    this.wordbook = (IWordbook) rimantoIOCContainer.getInstanceFor(IWordbook.class);
  }

  /**
   *
   * @return Nummer of rows
   */
  @Override
  public int getRowCount() {
    return this.tableData.size();
  }

  /**
   *
   * @return Number of columns
   */
  @Override
  public int getColumnCount() {
    return this.columnNames.size();
  }


  /**
   *
   * @param row
   * @param column
   * @return Value at given row and column
   */
  @Override
  public Object getValueAt(int row, int column) {
    return this.tableData.get(row).get(column);
  }

  /**
   *
   * @param column
   * @return Heading of the passed column
   */
  @Override
  public String getColumnName(int column) {
    return this.columnNames.get(column);
  }

  /**
   * Makes all cells not editable
   * @param rowIndex
   * @param columnIndex
   * @return True, if cell in given row and column is editable
   */
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  /**
   *
   * @return Returns the number of the column in which the referencing id for the displayed object is
   */
  public int getColumnWithId()
  {
    return 0;
  }

  /**
   * Returns the background color of the passed table in the passed row
   * @param table
   * @param row
   * @return Color of the table in the row
   */
  abstract public Color getRowColor(JTable table, int row);


  /**
   * Returns the id at the passed row
   * @param table
   * @param row
   * @return Id in the passed table in the passed row1
   */
  protected int getIdAtRow(JTable table, int row)
  {
    Object cellContent = table.getValueAt(row, this.getColumnWithId());
    // Convert content of the cell to integer
    String cellText = cellContent.toString();
    Integer id = Integer.valueOf(cellText);
    return id;
  }
}
