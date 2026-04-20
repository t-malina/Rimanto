package de.kalkihe.rimanto.utilities;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import java.time.format.DateTimeFormatter;

public interface IWordbook {
  /*
   * Gets the word in the correct language for the given identifier
   */
  String getWordFor(String identifier);

  /*
   * Gets the word in the correct language for the given identifier
   * The first letter of the word is capitalized in this case
   */
  String getWordForWithCapitalLeadingLetter(String identifier);

  String getRiskInstruction(IProject project, IRisk risk, String recipient, String dueDate, String instruction);

  String getDateTimeFormat();

  DateTimeFormatter getDateTimeFormatter();
}
