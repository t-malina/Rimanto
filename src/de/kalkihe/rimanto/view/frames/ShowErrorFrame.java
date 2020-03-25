package de.kalkihe.rimanto.view.frames;

import de.kalkihe.rimanto.model.wordbook.IWordbook;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Frame for showing errors
 */
public class ShowErrorFrame extends JDialog {
  /**
   * Needed reference
   */
  private IWordbook wordbook;
  /**
   * The error message
   */
  private String errorMessage;
  /**
   * The causing exception
   */
  private Exception exception;
  /**
   * True, if the application is to shut down after showing this window
   */
  private boolean shutdownApplication;

  /**
   * The text area with the error message
   */
  private JTextArea errorTextArea;

  /**
   * Constructor. Sets the needed references, attributes
   * @param wordbook The wordbook for labels and messages
   * @param errorMessage The error message to display
   * @param exception The causing exception
   * @param shutdownApplication True, if the application is to shut down after closing of this window
   * @throws HeadlessException
   */
  public ShowErrorFrame(IWordbook wordbook, String errorMessage, Exception exception, boolean shutdownApplication) throws HeadlessException {
    super();
    this.wordbook = wordbook;
    this.errorMessage = errorMessage;
    this.exception = exception;
    this.shutdownApplication = shutdownApplication;
    this.init();
  }

  /**
   * Initializes the error frame
   */
  private void init() {
    // Set Title of Main window
    this.setTitle(this.wordbook.getWordForWithCapitalLeadingLetter("error"));
    // Set icon of the main window
    ImageIcon icon = new ImageIcon(getClass().getResource("/danger.png"));
    this.setIconImage(icon.getImage());
    // Set the minimum size of this window
    this.setMinimumSize(new Dimension(300, 200));
    // Set the Layout
    this.setLayout(new BorderLayout());
    // Create and "style" the error text area
    errorTextArea = new JTextArea();
    errorTextArea.setWrapStyleWord(true);
    errorTextArea.setLineWrap(true);
    errorTextArea.setOpaque(false);
    errorTextArea.setEditable(false);
    errorTextArea.setFocusable(false);
    errorTextArea.setBackground(UIManager.getColor("Label.background"));
    errorTextArea.setFont(UIManager.getFont("Label.font"));
    errorTextArea.setBorder(UIManager.getBorder("Label.border"));
    JScrollPane scrollPane =  new JScrollPane(errorTextArea);

    // Set the text of the error text area
    errorTextArea.setText("\n" + errorMessage + "\n\n\n" + this.getStackTrace(exception));
    errorTextArea.setCaretPosition(0);
    // Add the text area to the window
    this.add(scrollPane, BorderLayout.CENTER);

    // Create new panel for buttons
    JPanel southPanel = new JPanel(new FlowLayout());
    // Create ok button and add event to it
    JButton okButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("ok"));
    okButton.addActionListener(actionEvent -> this.closeFrame());
    southPanel.add(okButton);
    // Create copy message button and add event to id
    JButton copyMessageButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("copy_text"));
    copyMessageButton.addActionListener(actionEvent -> this.copyErrorMessage());
    southPanel.add(copyMessageButton);
    // add button panel to window
    this.add(southPanel, BorderLayout.SOUTH);
  }

  /**
   * Closes this window
   */
  private void closeFrame()
  {
    if (this.shutdownApplication)
    {
      System.exit(0);
    }
    else
    {
      this.dispose();
    }
  }

  /**
   * Copies the error message to clipboard
   */
  private void copyErrorMessage()
  {
    StringSelection selection = new StringSelection(this.errorTextArea.getText());
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(selection, null);
  }

  /**
   * Returns the stack trace of the passed exception
   * @param exception Exception to read stack trace from
   * @return The stack trace of the exception
   */
  private String getStackTrace(Exception exception)
  {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    exception.printStackTrace(printWriter);
    return stringWriter.toString();
  }
}
