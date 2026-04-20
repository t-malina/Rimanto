package de.kalkihe.rimanto.view.tablemodel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class RimantoTableCellRenderer extends DefaultTableCellRenderer {
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    GeneralTableModel tableModel = (GeneralTableModel) table.getModel();
    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    component.setBackground(tableModel.getRowColor(table, row));
    return component;
  }
}
