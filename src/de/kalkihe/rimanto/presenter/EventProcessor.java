package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.view.IRimantoView;

public class EventProcessor implements IEventProcessor{
  private IRimantoView rimantoView;

  public EventProcessor(IRimantoView rimantoView) {
    this.rimantoView = rimantoView;
  }

  @Override
  public void newProjectButtonClick() {
    this.rimantoView.startCreationOfProject();
  }

  @Override
  public void newProjectCreationCanceled() {

  }

  @Override
  public void newProjectToCreate(IProject newProject) {

  }
}
