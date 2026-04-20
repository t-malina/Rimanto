package de.kalkihe.rimanto.model.wordbook;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Implementation of the wordbook to get labels and error messages
 */
public class MultiLanguagueWordbook implements IWordbook, Serializable {
  /**
   * The currently used locale
   */
  private Locale locale;

  /**
   * Variable to save the colon.
   */
  private String colon = ": ";

  /**
   * Constructor. Sets the default locale
   */
  public MultiLanguagueWordbook()
  {
    this.locale = new Locale("en", "US");
  }

  /**
   * Gets the word in the correct language for the given identifier
   * @param identifier Identifier for the requested word
   * @returnS The requested word or sentence in the current language
   */
  @Override
  public String getWordFor(String identifier) {
    return ResourceBundle.getBundle("MessageBundle", this.locale).getString(identifier);
  }

  /**
   * Gets the word in the correct language for the given identifier
   * The first letter of the word is capitalized in this case
   * @param identifier Identifier for the requested word
   * @return The requested word or sentence in the current language with the first letter capitalized
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

  /**
   * Build and returns a work instruction for the passed risk
   * @param project The project the passed risk belongs
   * @param risk The risk
   * @param recipient The recipient for the work order
   * @param dueDate The due date for the instruction
   * @param instruction The specific part of the instruction
   * @return
   */
  @Override
  public String getRiskInstruction(IProject project, IRisk risk, String recipient, String dueDate, String instruction) {
    String result = this.getWordForWithCapitalLeadingLetter("dear") + " " + recipient + ",\n\n"
      + this.getWordFor("take_care") + " \"" +  project.getProjectName() + "\"";
    if (dueDate.trim().length() != 0)
    {
      result += " " + this.getWordFor("until") +  " "  + dueDate + " ";
    }
    if (instruction.trim().length() != 0)
    {
      result += this.getWordFor("follow_instructions") + this.colon + "\n\n" + instruction + "\n";
    }
    result += "\n" +
      this.getWordForWithCapitalLeadingLetter("risk_name") + this.colon + risk.getRiskName() + "\n"
      + this.getWordForWithCapitalLeadingLetter("risk_description") + this.colon + risk.getRiskDescription() + "\n"
      + this.getWordForWithCapitalLeadingLetter("risk_priority") + this.colon + risk.getRiskPriority() + "\n"
      + this.getWordForWithCapitalLeadingLetter("risk_impact") + this.colon + risk.getRiskImpact() + "\n"
      + this.getWordForWithCapitalLeadingLetter("risk_mitigation") + this.colon + risk.getRiskMitigation() + "\n"
      + this.getWordForWithCapitalLeadingLetter("person_in_charge") + this.colon + risk.getPersonInCharge() + "\n"
      + this.getWordForWithCapitalLeadingLetter("next_date_of_revision") + this.colon;
    LocalDate date = risk.getDateOfNextRiskReview();
    if (date != null) {
      result += date.toString();
    }
    result += "\n"
      + this.getWordForWithCapitalLeadingLetter("category") + this.colon + risk.getCategoryOfImpactOnOtherProjects() + "\n"
      + this.getWordForWithCapitalLeadingLetter("further_projects") + this.colon + "\n ";
    for (IProject currentProject : risk.getImpactOfRiskOnOtherProjects())
    {
      result += " - " + currentProject.getProjectName() + "\n ";
    }
    return result;
  }

  /**
   *
   * @return The DateTimeFormatter for the current language
   */
  @Override
  public DateTimeFormatter getDateTimeFormatter() {
    return DateTimeFormatter.ISO_LOCAL_DATE;
  }

  /**
   * Sets the locale that is to use for the woordbock.
   * Currently supported: de-DE, en-US
   * @param language The language, de or en
   * @param country The country, DE or US
   */
  @Override
  public void setLocale(String language, String country) {
    this.locale = new Locale(language, country);
  }

  /**
   * Compares the passed object with the called instance
   * @param o The object to compare with
   * @return True, if both objects are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MultiLanguagueWordbook that = (MultiLanguagueWordbook) o;
    return Objects.equals(locale, that.locale) &&
      Objects.equals(colon, that.colon);
  }

  /**
   * Returns the hash code of this object
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(locale, colon);
  }
}
