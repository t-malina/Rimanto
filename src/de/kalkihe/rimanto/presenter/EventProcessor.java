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

/**
 * Implementation of Interface for event handling
 */
public class EventProcessor implements IEventProcessor{
  /**
   * Needed references
   */
  private IRimantoView rimantoView;
  private IRimantoModel rimantoModel;
  private IWordbook wordbook;

  /**
   * Constructor, Initializes passed references
   * @param rimantoView Reference to view
   * @param rimantoModel Reference to model
   * @param wordbook Reference to wordbook
   */
  public EventProcessor(IRimantoView rimantoView, IRimantoModel rimantoModel, IWordbook wordbook) {
    this.rimantoView = rimantoView;
    this.rimantoModel = rimantoModel;
    this.wordbook = wordbook;
  }

  /**
   * Event, when creation of new project is started
   */
  @Override
  public void newProjectButtonClick() {
    this.rimantoView.startCreationOfProject();
  }

  /**
   * Event, when new project is to create
   * @param newProject Project that is to create
   */
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

  /**
   * Event, when import of project is started
   */
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

  /**
   * Event, when project is selected for detailed view
   * @param project Project that is selected for detail view
   */
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

  /**
   * Event, wehen risk is selected for detail view
   * @param project project the risk belongs to
   * @param risk Risk that is selected for detail view
   */
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

  /**
   * Event when import of a risk is started
   * @param project The project the imported risk should belong to
   */
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

  /**
   * Event, when creation of risk is started
   * @param project The project the new risk should belong to
   */
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

  /**
   * Event, when creation of risk is canceled
   * @param project The project, that risk creation was canceled for
   */
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

  /**
   * Event, when new risk is to create
   * @param project The project the risk is created for
   * @param newRisk The risk that is to be created
   */
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

  /**
   * Event, when editing of a project is requested
   * @param project The project that should be edited
   */
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

  /**
   * Event, when editing of project is canceled
   * @param project The project whose editing was canceled
   */
  @Override
  public void projectEditingRequested(IProject project) {
    this.rimantoView.startEditingOfProject(project);
  }

  /**
   * Event, when editing of project is canceled
   * @param project The project whose editing was canceled
   */
  @Override
  public void projectEditingCanceled(IProject project) {
    this.projectForDetailViewSelected(project);
  }

  /**
   * Event, when a project is to edit
   * @param oldProject The project that is to be edited
   * @param newProject A new project with the data to adopt
   */
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

  /**
   * Event, when a project is to be deleted
   * @param project The project that is to delete
   */
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

  /**
   * Event, when the export of a project is started
   * @param project The project that is to be exported
   */
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

  /**
   * Event, the export of a risk is started
   * @param project The project the risk belongs to
   * @param risk The risk that is to be exported
   */
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

  /**
   * Event, when the export of a risk as work instruction is started
   * @param project The project the risk belongs to
   * @param risk The risk that is to be exported
   */
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

  /**
   * Event, when a risk of a project is to be deleted
   * @param project The project the risk belongs to
   * @param risk The risk that is to be deleted
   */
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

  /**
   * Event, when a risk is to be edited
   * @param project The project the risk belongs to
   * @param oldRisk The risk that is to be edited
   * @param newRisk A new risk with all data to be adopted
   */
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

  /**
   * Event, when the detail view of a risk is leaved
   * @param project The project the risk belongs to
   * @param risk The risk which detail view is leaved
   */
  @Override
  public void exitRiskDetailView(IProject project, IRisk risk) {
    this.projectForDetailViewSelected(project);
  }

  /**
   * Event, when the export of a risk as instruction is canceled
   * @param project The project the risk belongs to
   * @param risk The risk that was being exported
   */
  @Override
  public void abortRiskAsInstruction(IProject project, IRisk risk) {
    this.riskForDetailViewSelected(project, risk);
  }

  /**
   * Event, when a project has ben reviewed
   * @param project The project that has been reviewed
   */
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

  /**
   * Event, when a risk has been reviewed
   * @param project The project the reviewed risk belongs to
   * @param risk The reviewed risk
   */
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

  /**
   * Event, when a attached resource has been requested for view
   * @param ressource Path to the resource
   */
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


  /**
   * Event when the language has been changed
   * @param language language string
   * @param country country string
   */
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

  /**
   * Opens an URL
   * @param desktop Current desktop
   * @param ressource The path to the url
   * @return
   */
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

  /**
   * Opens an file
   * @param desktop Current desktop
   * @param ressource The path to the file
   * @return
   */
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
