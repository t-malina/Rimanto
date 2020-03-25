package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import javax.swing.*;

/**
 * Interface for getting Panels
 */
public interface IPanelGetter {
  /**
   *
   * @return The Panel for start of application
   * @throws Exception
   */
  JPanel getPanelForOverview() throws Exception;

  /**
   *
   * @param project The project to view
   * @return The project view panel for passed project
   * @throws Exception
   */
  JPanel getPanelForProjectView(IProject project) throws Exception;

  /**
   *
   * @param project The project the risk belongs to
   * @param risk The risk to view
   * @return The risk view panel for the passed risk
   * @throws Exception
   */
  JPanel getPanelForRiskView(IProject project, IRisk risk) throws Exception;

  /**
   *
   * @param project The project the risk is to create for
   * @return The panel for the cration of a risk
   * @throws Exception
   */
  JPanel getPanelForRiskCreation(IProject project) throws Exception;

  /**
   *
   * @return The panel for creating a project
   */
  JPanel getPanelForProjectInput();

  /**
   *
   * @param project The project to edit
   * @return The panel to edit the passed project
   */
  JPanel getPanelForProjectEditing(IProject project);

  /**
   *
   * @param project The project which risk is to be exported
   * @param risk The risk to export
   * @return The panel fo export the passed risk as work instruction
   * @throws Exception
   */
  JPanel getPanelForExportRisk(IProject project, IRisk risk) throws Exception;
}
