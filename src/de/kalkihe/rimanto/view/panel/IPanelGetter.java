package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import javax.swing.*;

public interface IPanelGetter {
  /*
   * Gets the Panel for the start view of the application
   */
  JPanel getPanelForOverview() throws Exception;
  JPanel getPanelForProjectView(IProject project) throws Exception;
  JPanel getPanelForRiskView(IProject project, IRisk risk) throws Exception;
  JPanel getPanelForRiskCreation(IProject project) throws Exception;
  JPanel getPanelForProjectInput();
  JPanel getPanelForProjectEditing(IProject project);
  JPanel getPanelForErrorView();
}
