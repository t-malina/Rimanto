package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

public interface IEventProcessor {
  void newProjectButtonClick();
  void newProjectCreationCanceled();
  void newProjectToCreate(IProject newProject);
  void projectImportRequested();
  void projectForDetailViewSelected(IProject project);
  void riskForDetailViewSelected(IRisk risk);
}
