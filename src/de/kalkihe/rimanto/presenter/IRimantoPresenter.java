package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import java.io.IOException;
import java.util.List;

public interface IRimantoPresenter {
  /*
   * Method to request list of all projects in model
   */
  List<IProject> fetchProjects();

  /*
   * Method to request list of all risks for a given project
   */
  List<IRisk> fetchRisksOfProject(IProject project);


}
