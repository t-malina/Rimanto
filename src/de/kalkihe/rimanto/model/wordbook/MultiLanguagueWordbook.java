package de.kalkihe.rimanto.model.wordbook;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MultiLanguagueWordbook implements IWordbook, Serializable {
  private Locale locale;

  private String colon = ": ";

  public MultiLanguagueWordbook()
  {
    this.locale = new Locale("en", "US");
  }

  @Override
  public String getWordFor(String identifier) {
    return ResourceBundle.getBundle("MessageBundle", this.locale).getString(identifier);
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

  @Override
  public DateTimeFormatter getDateTimeFormatter() {
    return DateTimeFormatter.ISO_LOCAL_DATE;
  }

  @Override
  public void setLocale(String language, String country) {
    this.locale = new Locale(language, country);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MultiLanguagueWordbook that = (MultiLanguagueWordbook) o;
    return Objects.equals(locale, that.locale) &&
      Objects.equals(colon, that.colon);
  }

  @Override
  public int hashCode() {
    return Objects.hash(locale, colon);
  }
}
