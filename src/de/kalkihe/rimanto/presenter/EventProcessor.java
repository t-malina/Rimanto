package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.view.IRimantoView;

import java.io.File;
import java.io.IOException;

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
    catch(Exception exception)
    {
      this.rimantoView.showErrorDialog(exception, false);
    }
  }
}
