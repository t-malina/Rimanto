package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import java.awt.*;

public class CreateProjectPanel extends GeneralRimantoPanel {
  /*
   * Needed references
   */
  private IWordbook wordbook;
  private IEventProcessor eventProcessor;
  private IRimantoView rimantoView;



  public CreateProjectPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) {
    super(wordbook, eventProcessor, rimantoView);
    this.buildPanel();
  }

  protected void buildPanel()
  {

  }
}
