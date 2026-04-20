package de.kalkihe.rimanto.utilities;

import com.google.inject.AbstractModule;
import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.model.RimantoModel;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.data.Project;
import de.kalkihe.rimanto.model.data.Risk;
import de.kalkihe.rimanto.model.storage.DummyDataFileStorage;
import de.kalkihe.rimanto.model.storage.IRimantoFileStorage;
import de.kalkihe.rimanto.model.storage.RimantoFileStorage;
import de.kalkihe.rimanto.presenter.EventProcessor;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.RimantoView;
import de.kalkihe.rimanto.view.error.ErrorDialog;
import de.kalkihe.rimanto.view.error.IErrorDialog;
import de.kalkihe.rimanto.view.panel.IPanelGetter;
import de.kalkihe.rimanto.view.panel.PanelGetter;

public class RimantoModule extends AbstractModule {
  @Override
  protected void configure()
  {
    /*
     * Utilities
     */
    bind(IWordbook.class).to(Wordbook.class);

    /*
     * View
     */
    bind(IRimantoView.class).to(RimantoView.class);
    bind(IPanelGetter.class).to(PanelGetter.class);
    bind(IErrorDialog.class).to(ErrorDialog.class);
    bind(IEventProcessor.class).to(EventProcessor.class);

    /*
     * Model
     */
    bind(IProject.class).to(Project.class);
    bind(IRisk.class).to(Risk.class);
    //TODO:Use real file storage
    bind(IRimantoFileStorage.class).to(DummyDataFileStorage.class);
    bind(IRimantoModel.class).to(RimantoModel.class);
  }
}
