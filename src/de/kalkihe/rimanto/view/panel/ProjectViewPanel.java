package de.kalkihe.rimanto.view.panel;

import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;

public class ProjectViewPanel extends GeneralRimantoPanel {

  public ProjectViewPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.buildPanel();
  }

  @Override
  protected void buildPanel() throws Exception {

  }
}
