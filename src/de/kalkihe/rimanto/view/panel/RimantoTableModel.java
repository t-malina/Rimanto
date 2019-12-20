package de.kalkihe.rimanto.view.panel;

import javax.swing.table.AbstractTableModel;

public class RimantoTableModel extends AbstractTableModel {
  String[][] data;
  String[] columns;

  public RimantoTableModel(String[][] data, String[] columns) {
    this.data = data;
    this.columns = columns;
  }

  @Override
  public int getRowCount() {
    return this.data.length;
  }

  @Override
  public int getColumnCount() {
    return this.columns.length;
  }

  @Override
  public Object getValueAt(int i, int i1) {
    return this.data[i][i1];
  }

  @Override
  public String getColumnName(int column) {
    return this.columns[column];
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }
}
