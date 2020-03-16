package de.kalkihe.rimanto.utilities;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Wordbook implements IWordbook{
  // Map for identifying words in the app
  private Map<String, String> words;

  // Constructor
  // Initializes the map and puts the needed words in it
  public Wordbook()
  {
    super();
    words = new TreeMap<String, String>();
    words.put("project", "project");
    words.put("risk", "risk");
    words.put("name", "name");
    words.put("description", "description");
    words.put("date", "date");
    words.put("import", "import");
    words.put("export", "export");
    words.put("file", "file");
    words.put("help", "help");
    words.put("about", "about");
    words.put("info", "info");
    words.put("priority", "priority");
    words.put("mitigation", "mitigation");
    words.put("date of project start", "date of project start");
    words.put("date of project end", "date of project end");
    words.put("attached documents", "attached documents");
    words.put("next date of revision", "date of next review");
    words.put("further resources", "further resources (one per line)");
    words.put("impact", "impact");
    words.put("errorlookandfeel", "Error while reading the look and feel from the system!");
    words.put("new project", "new Project");
    words.put("import project", "import Project");
    words.put("current projects", "current Projects");
    words.put("back", "back");
    words.put("no projects", "No projects yet!");
    words.put("project description", "Description of the project");
    words.put("project name" , "Name of the project");
    words.put("save", "save");
    words.put("cancel", "cancel");
    words.put("missing project data", "At least a project name is required!");
    words.put("error", "error");
    words.put("wrong revision date", "The review date has to be between the start and the end date of the project!");
    words.put("rimanto project", "rimanto project");
    words.put("import project exists", "The project you wanted to import is already existing!");
    words.put("import project error", "Error while importing project");
    words.put("no risks", "No risks yet");
    words.put("new risk", "new risk");
    words.put("import risk", "import risk");
    words.put("back", "back");
    words.put("edit project", "edit project");
    words.put("export project", "export project");
    words.put("save project", "save project");
    words.put("risk name", "risk name");
    words.put("risk description", "risk description");
    words.put("risk priority", "priority (1: highest; 5: lowest)");
    words.put("risk impact", "level of impact (1: highest; 5: lowest)");
    words.put("risk mitigation", "mitigation");
    words.put("person in charge", "person in charge of tracking");
    words.put("further projects", "risk also has impact on following projects:");
    words.put("category", "category of impact in further projects");
    words.put("delete project", "delete project");
    words.put("missing risk data", "At least a risk name, description and mitigation is required!");
    words.put("rimanto file" , "rimanto file");
    words.put("export risk", "export risk");
    words.put("export risk instruction", "export risk as instruction");
    words.put("delete risk", "delete risk");
    words.put("edit risk", "edit risk");
    words.put("no category" , "if you select further projects this risk has an impact on, you have to give the category of the impact!");
    words.put("recipient", "recipient");
    words.put("due date" , "due date");
    words.put("instruction", "instruction");
    words.put("copy text", "copy text");
    words.put("generate text", "generate text");
    words.put("project to review" , "project is to review");
    words.put("risk to review", "risk is to review");
    words.put("project with risks to review", "project has risks that are to review");
    words.put("review done", "review done");
    words.put("risk already in project", "risk is already in poject!");
  }

  /*
   * Gets the word in the correct language for the given identifier
   */
  @Override
  public String getWordFor(String identifier) {
    return words.get(identifier);
  }

  /*
   * Gets the word in the correct language for the given identifier
   * The first letter of the word is capitalized in this case
   */
  @Override
  public String getWordForWithCapitalLeadingLetter(String identifier) {
    // Get word for identifier
    String result = this.getWordFor(identifier);
    // replace first sign with its upper case equivalent
    result = result.substring(0, 1).toUpperCase() + result.substring(1);
    // return the built string
    return result;
  }

  @Override
  public String getRiskInstruction(IProject project, IRisk risk, String recipient, String dueDate, String instruction) {
    String result = "Dear " + recipient + ",\n\n"
      + "please take care of this risk which is part of the project " + project.getProjectName();
    if (dueDate.trim().length() != 0)
    {
      result += " until " + dueDate;
    }
    if (instruction.trim().length() != 0)
    {
      result += " following this instructions: \n\n" + instruction + "\n";
    }
      result += "\nRisk name : " + risk.getRiskName() + "\n"
      + "Risk description : " + risk.getRiskDescription() + "\n"
      + "Risk priority : " + risk.getRiskPriority() + "\n"
      + "Risk impact : " + risk.getRiskImpact() + "\n"
      + "Risk mitigation : " + risk.getRiskMitigation() + "\n"
      + "Risk observer : " + risk.getPersonInCharge() + "\n"
      + "Next review date : ";
    LocalDate date = risk.getDateOfNextRiskRevision();
    if (date != null) {
      result += date.toString();
    }
    result += "\n"
      + "Category of impact on further projects : " + risk.getCategoryOfImpactOnOtherProjects() + "\n"
      + "Impact on further projects : \n ";
    for (IProject currentProject : risk.getImpactOfRiskOnOtherProjects())
    {
      result += " - " + currentProject.getProjectName() + "\n ";
    }
    return result;
  }

  @Override
  public String getDateTimeFormat() {
    return "dd.MM.yyyy";
  }

  @Override
  public DateTimeFormatter getDateTimeFormatter() {
    return DateTimeFormatter.ISO_LOCAL_DATE;
  }


}
