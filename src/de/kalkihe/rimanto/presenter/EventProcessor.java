package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.view.IRimantoView;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

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
  public void newProjectCreationCanceled() {
    try
    {
      this.rimantoView.cancelCreationOfProject();
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, true);
    }
  }

  @Override
  public void newProjectToCreate(IProject newProject) {
    try
    {
      this.rimantoModel.createNewProject(newProject);
      this.rimantoView.projectCreated();
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
  public void riskForDetailViewSelected(IRisk risk) {
    try
    {
      this.rimantoView.showRisk(risk);
    }
    catch(Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, true);
    }

  }
}
