package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;
import de.kalkihe.rimanto.view.IRimantoView;

import java.io.IOException;
import java.util.List;

public class RimantoPresenter implements IRimantoPresenter{
  private IRimantoView rimantoView;
  private IRimantoModel rimantoModel;
  private RimantoIOCContainer rimantoIOCContainer;
  private IWordbook wordbook;

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

      try
      {
        IWordbook wordbook = this.rimantoModel.readWordbook();
        this.rimantoIOCContainer.setWordbook(wordbook);
      }
      catch (Exception exception)
      {
        this.rimantoIOCContainer.setWordbook();
      }

      // Initialize main application window and show it
      rimantoView.initializeApplicationWindow();
    }
    catch(Exception exception)
    {
      exception.printStackTrace();
      // Show error dialog and shutdown application
      rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_startup"), exception, true);
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
    try
    {
      return this.rimantoModel.getProjectList();
    }
    catch (IOException exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_projects_read"), exception, true);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, true);
    }
    return null;
  }

  /*
   * Method to request list of all risks of a given project
   */
  @Override
  public List<IRisk> fetchRisksOfProject(IProject project) {
    return this.rimantoModel.getRisksOfProject(project);
  }
}
