package de.kalkihe.rimanto.view.panel;

import javax.swing.*;

public interface IPanelGetter {
  /*
   * Gets the Panel for the start view of the application
   */
  JPanel getPanelForOverview() throws Exception;
  JPanel getPanelForProjectView();
  JPanel getPanelForRiskView();
  JPanel getPanelForProjectInput();
  JPanel getPanelForErrorView();
}
