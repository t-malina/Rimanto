package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.view.IRimantoView;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public class EventProcessor implements IEventProcessor{
  private IRimantoView rimantoView;
  private IRimantoModel rimantoModel;

  public EventProcessor(IRimantoView rimantoView, IRimantoModel rimantoModel) {
    this.rimantoView = rimantoView;
    this.rimantoModel = rimantoModel;
  }

  @Override
  public void newProjectButtonClick() {
    this.rimantoView.startCreationOfProject();
  }

  @Override
  public void newProjectToCreate(IProject newProject) {
    try
    {
      this.rimantoModel.createNewProject(newProject);
      this.projectForDetailViewSelected(newProject);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(e, false);
    }
  }

  @Override
  public void projectImportRequested() {
    try
    {
      File importFile = this.rimantoView.showImportFileDialog(this.rimantoModel.getProjectFileFormat());
      if (importFile != null)
      {
        this.rimantoModel.importProject(importFile);
        this.rimantoView.projectCreated();
      }
    }
    //TODO: Add reference to wordbook --> use error messages
    catch(FileAlreadyExistsException exception)
    {
      this.rimantoView.showErrorDialog(exception, false);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, false);
    }
  }

  @Override
  public void projectForDetailViewSelected(IProject project) {
    try
    {
      this.rimantoView.showProject(project);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, true);
    }
  }

  @Override
  public void riskForDetailViewSelected(IProject project, IRisk risk) {
    try
    {
      this.rimantoView.showRisk(project, risk);
    }
    catch(Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, true);
    }

  }

  @Override
  public void riskImportRequested(IProject project) {
    try
    {
      File file = this.rimantoView.showImportFileDialog(this.rimantoModel.getRiskFileFormat());
      if (file != null)
      {
        this.rimantoModel.importRisk(file, project);
        this.rimantoView.showProject(project);
      }
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, false);
    }
  }

  @Override
  public void newRiskButtonClick(IProject project) {
    try
    {
      this.rimantoView.startCreationOfRisk(project);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, false);
    }
  }

  @Override
  public void newRiskCreationCanceled(IProject project) {
    try
    {
      this.rimantoView.showProject(project);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, true);
    }
  }

  @Override
  public void newRiskToCreate(IProject project, IRisk newRisk) {
    try
    {
      this.rimantoModel.addRiskToProject(project, newRisk);
      this.rimantoView.showProject(project);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, false);
    }
  }

  @Override
  public void backToOverview() {
    try
    {
      this.rimantoView.showOverview();
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, true);
    }
  }

  @Override
  public void projectEditingRequested(IProject project) {
    this.rimantoView.startEditingOfProject(project);
  }

  @Override
  public void projectEditingCanceled(IProject project) {
    this.projectForDetailViewSelected(project);
  }

  @Override
  public void editProject(IProject oldProject, IProject newProject) {
    try
    {
      this.rimantoModel.editProject(oldProject, newProject);
      this.rimantoView.showProject(oldProject);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(e, false);
    }
  }

  @Override
  public void deleteProject(IProject project) {
    this.rimantoModel.deleteProject(project);
    try
    {
      this.rimantoView.showOverview();
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, true);
    }
  }

  @Override
  public void exportProject(IProject project) {
    File exportFile = this.rimantoView.showExportFileDialog(this.rimantoModel.getProjectFileFormat());
    if (exportFile != null)
    {
      try
      {
        this.rimantoModel.exportProject(project, exportFile);
      }
      catch(Exception exception)
      {
        this.rimantoView.showErrorDialog(exception, false);
      }
    }
  }

  @Override
  public void exportRisk(IProject project, IRisk risk) {

    File exportFile = this.rimantoView.showExportFileDialog(this.rimantoModel.getRiskFileFormat());
    if (exportFile != null)
    {
      try
      {
        this.rimantoModel.exportRisk(risk, exportFile);
      }
      catch (Exception exception)
      {
        this.rimantoView.showErrorDialog(exception, false);
      }
    }
  }

  @Override
  public void exportRiskAsInstruction(IProject project, IRisk risk) {
    try
    {
      this.rimantoView.exportRiskAsInstruction(project, risk);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, false);
    }
  }

  @Override
  public void deleteRisk(IProject project, IRisk risk) {
    try
    {
      this.rimantoModel.deleteRisk(project, risk);
      this.rimantoView.showProject(project);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, false);
    }
  }

  @Override
  public void editRisk(IProject project, IRisk oldRisk, IRisk newRisk) {
    try
    {
      this.rimantoModel.editRisk(project, oldRisk, newRisk);
      this.rimantoView.showRisk(project, oldRisk);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, false);
    }
  }

  @Override
  public void exitRiskDetailView(IProject project, IRisk risk) {
    this.projectForDetailViewSelected(project);
  }

  @Override
  public void abortRiskAsInstruction(IProject project, IRisk risk) {
    this.riskForDetailViewSelected(project, risk);
  }
}
