package de.kalkihe.rimanto.view.filefilters;

import de.kalkihe.rimanto.model.wordbook.IWordbook;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Extension of class FileFilter to only select rimanto files
 */
public class RimantoFileFIlter extends FileFilter {
  private String allowedFileFormat;
  private IWordbook wordbook;

  /**
   * Constructor. Sets the allowd file format and the wordbook for labels
   * @param allowedFileFormat The allowed file format for selection
   * @param wordbook The wordbook
   */
  public RimantoFileFIlter(String allowedFileFormat, IWordbook wordbook)
  {
    this.allowedFileFormat = allowedFileFormat;
    this.wordbook = wordbook;
  }

  /**
   * Retruns true, if the passed file is accepted for selections
   * @param file The selected file
   * @return True, if the file is valid for selection
   */
  @Override
  public boolean accept(File file)
  {
    return file.isDirectory() || file.getName().toLowerCase().endsWith(this.allowedFileFormat);
  }

  /**
   *
   * @return The description of the allowed files
   */
  @Override
  public String getDescription()
  {
    return this.wordbook.getWordForWithCapitalLeadingLetter("rimanto_file");
  }
}
