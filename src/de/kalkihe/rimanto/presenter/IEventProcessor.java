package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.view.IRimantoView;

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
  void newRiskToCreate(IProject project, IRisk newRisk);
  void backToOverview();


  void projectEditingRequested(IProject project);
  void projectEditingCanceled(IProject project);
  void editProject(IProject oldProject, IProject newProject);
  void deleteProject(IProject project);
  void exportProject(IProject project);


  void exportRisk(IProject project, IRisk risk);
  void exportRiskAsInstruction(IProject project, IRisk risk);
  void deleteRisk(IProject project, IRisk risk);
  void editRisk(IProject project, IRisk oldRisk, IRisk newRisk);
  void exitRiskDetailView(IProject project, IRisk risk);

  void abortRiskAsInstruction(IProject project, IRisk risk);

}
