package de.kalkihe.rimanto.model.wordbook;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import java.time.format.DateTimeFormatter;

/**
 * Interface to get all labels in ui elements and all error messages
 */
public interface IWordbook {

  /**
   * Gets the word in the correct language for the given identifier
   * @param identifier Identifier for the requested word
   * @returnS The requested word or sentence in the current language
   */
  String getWordFor(String identifier);

  /**
   * Gets the word in the correct language for the given identifier
   * The first letter of the word is capitalized in this case
   * @param identifier Identifier for the requested word
   * @return The requested word or sentence in the current language with the first letter capitalized
   */
  String getWordForWithCapitalLeadingLetter(String identifier);

  /**
   * Build and returns a work instruction for the passed risk
   * @param project The project the passed risk belongs
   * @param risk The risk
   * @param recipient The recipient for the work order
   * @param dueDate The due date for the instruction
   * @param instruction The specific part of the instruction
   * @return
   */
  String getRiskInstruction(IProject project, IRisk risk, String recipient, String dueDate, String instruction);

  /**
   *
   * @return The DateTimeFormatter for the current language
   */
  DateTimeFormatter getDateTimeFormatter();

  /**
   * Sets the locale that is to use for the woordbock.
   * Currently supported: de-DE, en-US
   * @param language The language, de or en
   * @param country The country, DE or US
   */
  void setLocale(String language, String country);
}
