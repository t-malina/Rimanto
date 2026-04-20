package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import java.io.IOException;
import java.util.List;

/**
 * Interface for the presenter
 */
public interface IRimantoPresenter {
  /*
   * Method to request list of all projects in model
   */

  /**
   * Method to request list of all projects in model
   * @return List of all current projects
   */
  List<IProject> fetchProjects();

  /**
   * Method to request list of all risks for a given project
   * @param project Project to fetch risks for
   * @return List of all risks of the passed project
   */
  List<IRisk> fetchRisksOfProject(IProject project);


}
