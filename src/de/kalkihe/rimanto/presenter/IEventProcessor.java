package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.data.IProject;

public interface IEventProcessor {
  void newProjectButtonClick();
  void newProjectCreationCanceled();
  void newProjectToCreate(IProject newProject);
}
