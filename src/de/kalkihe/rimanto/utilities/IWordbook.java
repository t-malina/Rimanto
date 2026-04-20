package de.kalkihe.rimanto.utilities;

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
}
