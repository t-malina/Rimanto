package de.kalkihe.rimanto.view.wordbook;

public interface IWordbook {
  String getWordFor(String identifier);
  String getWordForWithCapitalLeadingLetter(String identifier);
}
