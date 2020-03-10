package de.kalkihe.rimanto.utilities;

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
    words.put("next date of revision", "next date of revision");
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
    words.put("wrong revision date", "The revision date has to be between the start and the end date");
    words.put("rimanto project", "rimanto project");
    words.put("import project exists", "The project you wanted to import is already existing!");
    words.put("import project error", "Error while importing project");
    words.put("no risks", "No risks yet");
    words.put("new risk", "new risk");
    words.put("import risk", "import risk");
    words.put("back", "back");
    words.put("edit project", "edit project");
    words.put("save project", "save project");
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
}
