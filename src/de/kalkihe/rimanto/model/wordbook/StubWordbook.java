package de.kalkihe.rimanto.model.wordbook;

/**
 * Stub implementation of the wordbook to get labels and error messages
 */
public class StubWordbook implements IWordbook {

    @Override
    public String getWordFor(String identifier) {
        return identifier;
    }

    @Override
    public String getWordForWithCapitalLeadingLetter(String identifier) {
        return identifier;
    }

    @Override
    public String getRiskInstruction(de.kalkihe.rimanto.model.data.IProject project,
            de.kalkihe.rimanto.model.data.IRisk risk, String recipient, String dueDate, String instruction) {
        return "Risk instruction";
    }

    @Override
    public java.time.format.DateTimeFormatter getDateTimeFormatter() {
        return java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    @Override
    public void setLocale(String language, String country) {

    }

}
