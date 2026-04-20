package de.kalkihe.rimanto.view;

import com.google.inject.AbstractModule;
import de.kalkihe.rimanto.presenter.EventProcessor;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.view.error.ErrorDialog;
import de.kalkihe.rimanto.view.error.IErrorDialog;
import de.kalkihe.rimanto.view.panel.IPanelGetter;
import de.kalkihe.rimanto.view.panel.PanelGetter;
import de.kalkihe.rimanto.view.wordbook.IWordbook;
import de.kalkihe.rimanto.view.wordbook.Wordbook;

public class RimantoModule extends AbstractModule {
  @Override
  protected void configure()
  {
    bind(IRimantoView.class).to(RimantoView.class);
    bind(IWordbook.class).to(Wordbook.class);
    bind(IPanelGetter.class).to(PanelGetter.class);
    bind(IErrorDialog.class).to(ErrorDialog.class);
    bind(IEventProcessor.class).to(EventProcessor.class);
  }
}
