package de.kalkihe.rimanto.view.filefilters;

import de.kalkihe.rimanto.utilities.IWordbook;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class RimantoFileFIlter extends FileFilter {
  private String allowedFileFormat;
  private IWordbook wordbook;

  public RimantoFileFIlter(String allowedFileFormat, IWordbook wordbook)
  {
    this.allowedFileFormat = allowedFileFormat;
    this.wordbook = wordbook;
  }

  @Override
  public boolean accept(File file)
  {
    return file.isDirectory() || file.getName().toLowerCase().endsWith(this.allowedFileFormat);
  }

  @Override
  public String getDescription()
  {
    return this.wordbook.getWordForWithCapitalLeadingLetter("rimanto project");
  }


}
