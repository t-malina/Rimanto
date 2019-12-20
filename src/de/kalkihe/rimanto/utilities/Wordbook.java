package de.kalkihe.rimanto.view.wordbook;

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
    words.put("further resources", "further resources");
    words.put("impact", "impact");
    words.put("errorlookandfeel", "Error while reading the look and feel from the system!");
    words.put("new project", "new Project");
    words.put("import project", "import Project");
    words.put("current projects", "current Projects");
    words.put("back", "back");

  }

  @Override
  public String getWordFor(String identifier) {
    return words.get(identifier);
  }

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
