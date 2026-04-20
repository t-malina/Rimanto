package de.kalkihe.rimanto.presenter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.utilities.RimantoModule;

public class RimantoPresenter implements IRimantoPresenter{
  private Injector injector;
  private IRimantoView rimantoView;
  private IRimantoModel rimantoModel;
  private RimantoIOCContainer rimantoIOCContainer;

  public RimantoPresenter() {
    super();
  }

  private void resolveDependencies() throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();

    this.rimantoView = (IRimantoView) this.rimantoIOCContainer.getInstanceFor(IRimantoView.class);

    this.rimantoView.setPresenter(this);

    this.rimantoModel = (IRimantoModel) this.rimantoIOCContainer.getInstanceFor(IRimantoModel.class);
  }
  private void initializeApplication()
  {
    try{
      // Resolve all needed dependencies
      this.resolveDependencies();
      // Initialize model
      this.rimantoModel.initializeModel(this.injector);
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

  @Override
  public String[] fetchColumnNamesForProjects() {
    return this.rimantoModel.getColumnNamesForProjects();
  }

  @Override
  public String[][] fetchDataOfProjects() {
    return this.rimantoModel.getDataOfProjects();
  }

  @Override
  public String[] fetchColumnNamesForRisks() {
    return this.rimantoModel.getColumnNamesForRisks();
  }

  @Override
  public String[][] fetchDataOfRisks() {
    return this.rimantoModel.getDataOfRisks();
  }
}
