package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import java.awt.*;

public abstract class GeneralRimantoPanel extends JPanel {
  /*
   * Needed references
   */
  protected IWordbook wordbook;
  protected IEventProcessor eventProcessor;
  protected IRimantoView rimantoView;

  public GeneralRimantoPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) {
    super(new BorderLayout());
    this.wordbook = wordbook;
    this.eventProcessor = eventProcessor;
    this.rimantoView = rimantoView;
  }

  protected abstract void buildPanel() throws Exception;
}
