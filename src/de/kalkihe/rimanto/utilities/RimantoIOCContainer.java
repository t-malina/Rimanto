package de.kalkihe.rimanto.utilities;

import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.model.RimantoModel;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.Project;
import de.kalkihe.rimanto.model.storage.IRimantoFileStorage;
import de.kalkihe.rimanto.model.storage.RimantoFileStorage;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.model.wordbook.MultiLanguagueWordbook;
import de.kalkihe.rimanto.presenter.EventProcessor;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.presenter.IRimantoPresenter;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.RimantoView;
import de.kalkihe.rimanto.view.panel.IPanelGetter;
import de.kalkihe.rimanto.view.panel.PanelGetter;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

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
      if (this.rimantoModel == null)
      {
        this.rimantoModel = new RimantoModel();
      }
      return this.rimantoModel;
    }
    else if (wantedClass == IRimantoView.class) {
      if (this.rimantoView == null)
      {
        this.rimantoView = new RimantoView();
      }
      return this.rimantoView;
    }
    else if (wantedClass == IWordbook.class) {
      if (this.wordbook == null)
      {
        this.wordbook = new MultiLanguagueWordbook();
      }
      return this.wordbook;
    }
    else if (wantedClass == IEventProcessor.class) {
      if (this.eventProcessor == null)
      {
        this.eventProcessor = new EventProcessor((IRimantoView) this.getInstanceFor(IRimantoView.class), (IRimantoModel) this.getInstanceFor(IRimantoModel.class),
          (IWordbook) this.getInstanceFor(IWordbook.class));
      }
      return this.eventProcessor;
    }
    else if (wantedClass == IPanelGetter.class)
    {
      if (this.panelGetter == null)
      {
        this.panelGetter = new PanelGetter((IWordbook) this.getInstanceFor(IWordbook.class), (IEventProcessor) this.getInstanceFor(IEventProcessor.class),
          (IRimantoView) this.getInstanceFor(IRimantoView.class));
      }
      return this.panelGetter;
    }
    else if (wantedClass == IRimantoFileStorage.class)
    {
      if (this.rimantoFileStorage == null)
      {
        this.rimantoFileStorage = new RimantoFileStorage();
      }
      return this.rimantoFileStorage;
    }
    else
    {
      throw new Exception("Error while getting instance for " + wantedClass.toString() + ": No implementation found!");
    }
  }

  public void setWordbook()
  {
    this.wordbook = new MultiLanguagueWordbook();
  }

  public void setWordbook(IWordbook wordbook)
  {
    this.wordbook = wordbook;
  }

  public static IProject CreateProject(String projectName, String projectDescription, LocalDate startDate, LocalDate endDate, List<URI> furtherResources, LocalDate revisionDate)
  {
    return new Project(projectName, projectDescription, startDate, endDate, furtherResources, revisionDate);
  }
}
