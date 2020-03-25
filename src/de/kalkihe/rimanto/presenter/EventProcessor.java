package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.model.IRimantoModel;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;

public class EventProcessor implements IEventProcessor{
  private IRimantoView rimantoView;
  private IRimantoModel rimantoModel;
  private IWordbook wordbook;

  public EventProcessor(IRimantoView rimantoView, IRimantoModel rimantoModel, IWordbook wordbook) {
    this.rimantoView = rimantoView;
    this.rimantoModel = rimantoModel;
    this.wordbook = wordbook;
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
    catch (IOException e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_project_write"), e, false);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, false);
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
    catch (FileAlreadyExistsException exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("import_project_exists"), exception, false);
    }
    catch (IOException exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_project_read"), exception, false);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, false);
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
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, true);
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
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, true);
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
    catch (FileAlreadyExistsException exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("risk_already_in_project"), exception, false);
    }
    catch (IOException exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_risk_read"), exception, false);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, false);
    }
  }

  @Override
  public void newRiskButtonClick(IProject project) {
    try
    {
      this.rimantoView.startCreationOfRisk(project);
    }
    catch (IOException exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_projects_read"), exception, true);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, true);
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
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, true);
    }
  }

  @Override
  public void newRiskToCreate(IProject project, IRisk newRisk) {
    try
    {
      this.rimantoModel.addRiskToProject(project, newRisk);
      this.rimantoView.showProject(project);
    }
    catch (IOException exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_risk_write"), exception, false);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, false);
    }
  }

  @Override
  public void backToOverview() {
    try
    {
      this.rimantoView.showOverview();
    }
    catch (IOException exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_projects_read"), exception, true);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, false);
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
    catch (IOException e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_project_write"), e, true);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, true);
    }
  }

  @Override
  public void deleteProject(IProject project) {
    this.rimantoModel.deleteProject(project);
    try
    {
      this.rimantoView.showOverview();
    }
    catch (IOException e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_project_write"), e, true);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, true);
    }
  }

  @Override
  public void exportProject(IProject project) {
    File exportFile = this.rimantoView.showExportFileDialog(this.rimantoModel.getProjectFileFormat());
    if (exportFile != null) {
      try
      {
        this.rimantoModel.exportProject(project, exportFile);
      }
      catch (IOException e)
      {
        this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_project_write"), e, false);
      }
      catch (Exception e)
      {
        this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, false);
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
      catch (IOException e)
      {
        this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_risk_write"), e, false);
      }
      catch (Exception e)
      {
        this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, false);
      }
    }
  }

  @Override
  public void exportRiskAsInstruction(IProject project, IRisk risk) {
    try
    {
      this.rimantoView.exportRiskAsInstruction(project, risk);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, false);
    }
  }

  @Override
  public void deleteRisk(IProject project, IRisk risk) {
    try
    {
      this.rimantoModel.deleteRisk(project, risk);
      this.rimantoView.showProject(project);
    }
    catch (IOException e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_risk_write"), e, false);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, false);
    }
  }

  @Override
  public void editRisk(IProject project, IRisk oldRisk, IRisk newRisk) {
    try
    {
      this.rimantoModel.editRisk(project, oldRisk, newRisk);
      this.rimantoView.showRisk(project, oldRisk);
    }
    catch (IOException e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_risk_write"), e, false);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, false);
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

  @Override
  public void setProjectAsReviewed(IProject project) {
    try
    {
      this.rimantoModel.setProjectAsReviewed(project);
      this.rimantoView.showProject(project);
    }
    catch (IOException e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_project_write"), e, false);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, false);
    }
  }

  @Override
  public void setRiskAsReviewed(IProject project, IRisk risk) {
    try
    {
      this.rimantoModel.setRiskAsReviewed(project, risk);
      this.rimantoView.showRisk(project, risk);
    }
    catch (IOException e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_risk_write"), e, false);
    }
    catch (Exception e)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), e, false);
    }
  }

  @Override
  public void ressourceForViewRequested(String ressource) {
    Desktop desktop = java.awt.Desktop.getDesktop();
    try
    {
      boolean fileOpeningSuccessfull = this.openFile(desktop, ressource);
      if (! fileOpeningSuccessfull)
      {
        boolean urlOpeningSUccessfull = this.openURL(desktop, ressource);
        if (! urlOpeningSUccessfull)
        {
          throw new Exception();
        }
      }
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_resource_opening"), exception, false);
    }
  }

  @Override
  public void languageChanged(String language, String country) {
    this.wordbook.setLocale(language, country);
    this.rimantoView.languageChanged();
    this.backToOverview();
    try
    {
      this.rimantoModel.saveWordbook(this.wordbook);
    }
    catch(IOException exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_settings_write"), exception, true);
    }
    catch (Exception exception)
    {
      this.rimantoView.showErrorDialog(this.wordbook.getWordForWithCapitalLeadingLetter("error_general"), exception, true);
    }
  }

  private boolean openURL(Desktop desktop, String ressource)
  {
    try
    {
      URI url = new URL(ressource).toURI();
      desktop.browse(url);
      return true;
    }
    catch (Exception exception)
    {
      return false;
    }
  }

  private boolean openFile(Desktop desktop, String ressource)
  {
    try
    {
      File file = new File(ressource);
      desktop.open(file);
      return true;
    }
    catch (Exception exception)
    {
      return false;
    }
  }
}
