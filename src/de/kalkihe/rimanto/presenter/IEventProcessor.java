package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import java.util.IllegalFormatCodePointException;
import java.util.List;

public interface IEventProcessor {
  void newProjectButtonClick();
  void newProjectToCreate(IProject newProject);
  void projectImportRequested();
  void projectForDetailViewSelected(IProject project);
  void riskForDetailViewSelected(IProject project, IRisk risk);
  void riskImportRequested(IProject project);
  void newRiskButtonClick(IProject project);
  void newRiskCreationCanceled(IProject project);
  void newRiskToCreate(IProject project, IRisk newRisk, List<IProject> furtherProjects);
  void backToOverview();


  void projectEditingRequested(IProject project);
  void projectEditingCanceled(IProject project);
  void editProject(IProject oldProject, IProject newProject);
  void deleteProject(IProject project);
  void exportProject(IProject project);
}
