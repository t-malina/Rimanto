package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;
import de.kalkihe.rimanto.view.IRimantoView;

import java.util.List;

public class RimantoPresenter implements IRimantoPresenter{
  private IRimantoView rimantoView;
  private IRimantoModel rimantoModel;
  private RimantoIOCContainer rimantoIOCContainer;

  public RimantoPresenter() {
    super();
  }

  /*
   * Gets all needed dependencies from ioc-container
   */
  private void resolveDependencies() throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();

    this.rimantoView = (IRimantoView) this.rimantoIOCContainer.getInstanceFor(IRimantoView.class);

    this.rimantoView.setPresenter(this);

    this.rimantoModel = (IRimantoModel) this.rimantoIOCContainer.getInstanceFor(IRimantoModel.class);
  }

  /*
   * Initializes the application for the user to work with
   */
  private void initializeApplication()
  {
    try{
      // Resolve all needed dependencies
      this.resolveDependencies();
      // Initialize the model
      this.rimantoModel.initializeModel();
      // Initialize main application window and show it
      rimantoView.initializeApplicationWindow();
    }
    catch(Exception exception)
    {
      exception.printStackTrace();
      // Show error dialog and shutdown application
      rimantoView.showErrorDialog(exception, true);
    }
  }

  public static void main(String[] args) {
    // create presenter with dependency to view
    RimantoPresenter rimantoPresenter = new RimantoPresenter();
    // give presenter object further handling of initialization of the application
    try {
      rimantoPresenter.initializeApplication();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  /*
   * Method to request list of all projects at model
   */
  @Override
  public List<IProject> fetchProjects() {
    return this.rimantoModel.getProjectList();
  }

  /*
   * Method to request list of all risks of a given project
   */
  @Override
  public List<IRisk> fetchRisksOfProject(IProject project) {
    return this.rimantoModel.getRisksOfProject(project);
  }
}
