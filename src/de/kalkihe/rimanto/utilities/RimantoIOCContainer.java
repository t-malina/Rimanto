package de.kalkihe.rimanto.utilities;

import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.model.RimantoModel;
import de.kalkihe.rimanto.model.storage.DummyDataFileStorage;
import de.kalkihe.rimanto.model.storage.IRimantoFileStorage;
import de.kalkihe.rimanto.presenter.EventProcessor;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.presenter.IRimantoPresenter;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.RimantoView;
import de.kalkihe.rimanto.view.error.ErrorDialog;
import de.kalkihe.rimanto.view.error.IErrorDialog;
import de.kalkihe.rimanto.view.panel.IPanelGetter;
import de.kalkihe.rimanto.view.panel.PanelGetter;

// Singleton
public class RimantoIOCContainer {
  private static RimantoIOCContainer iocContainer = new RimantoIOCContainer();

  /*
   * Singletons
   */
  IRimantoModel rimantoModel;
  IRimantoView rimantoView;
  IRimantoPresenter rimantoPresenter;
  IWordbook wordbook;
  IEventProcessor eventProcessor;
  IPanelGetter panelGetter;
  IRimantoFileStorage rimantoFileStorage;

  /*
   * Private Constructor, so that only one instance of this class can be created
   */
  private RimantoIOCContainer()
  {
    this.createSingletonInstances();
  }

  private void createSingletonInstances()
  {
    this.rimantoModel = new RimantoModel();
    this.rimantoView = new RimantoView();
    this.wordbook = new Wordbook();
    this.eventProcessor = new EventProcessor();
    this.panelGetter = new PanelGetter(this.wordbook, this.eventProcessor, this.rimantoView);
    this.rimantoFileStorage = new DummyDataFileStorage();
  }

  /*
   * Method to get a reference to the instance of this class
   */
  public static RimantoIOCContainer getInstance()
  {
    return iocContainer;
  }

  /*
   * Returns a instance of the passed class.
   * Uses created singleton if applicable
   */
  public Object getInstanceFor(Class wantedClass) throws Exception {
    if (wantedClass == IRimantoModel.class) {
      return this.rimantoModel;
    }
    else if (wantedClass == IRimantoView.class) {
      return this.rimantoView;
    }
    else if (wantedClass == IWordbook.class) {
      return this.wordbook;
    }
    else if (wantedClass == IEventProcessor.class) {
      return this.eventProcessor;
    }
    else if (wantedClass == IPanelGetter.class)
    {
      return this.panelGetter;
    }
    else if (wantedClass == IRimantoFileStorage.class)
    {
      return this.rimantoFileStorage;
    }
    else if (wantedClass == IErrorDialog.class)
    {
      return new ErrorDialog();
    }
    else
    {
      throw new Exception("Error while getting instance for " + wantedClass.toString() + ": No implementation found!");
    }
  }
}
