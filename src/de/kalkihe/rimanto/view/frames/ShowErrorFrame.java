package de.kalkihe.rimanto.view.frames;

import de.kalkihe.rimanto.model.wordbook.IWordbook;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ShowErrorFrame extends JDialog {
  private IWordbook wordbook;
  private String errorMessage;
  private Exception exception;
  private boolean shutdownApplication;

  private JTextArea errorTextArea;

  public ShowErrorFrame(IWordbook wordbook, String errorMessage, Exception exception, boolean shutdownApplication) throws HeadlessException {
    super();
    this.wordbook = wordbook;
    this.errorMessage = errorMessage;
    this.exception = exception;
    this.shutdownApplication = shutdownApplication;
    this.init();
  }

  private void init() {
    // Set Title of Main window
    this.setTitle(this.wordbook.getWordForWithCapitalLeadingLetter("error"));
    // Set icon of the main window
    ImageIcon icon = new ImageIcon("./images/danger.png");
    this.setIconImage(icon.getImage());
    this.setMinimumSize(new Dimension(300, 200));

    this.setLayout(new BorderLayout());

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

    errorTextArea.setText("\n" + errorMessage + "\n\n\n" + this.getStackTrace(exception));
    errorTextArea.setCaretPosition(0);
    this.add(scrollPane, BorderLayout.CENTER);

    JPanel southPanel = new JPanel(new FlowLayout());
    JButton okButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("ok"));
    okButton.addActionListener(actionEvent -> this.closeFrame());
    southPanel.add(okButton);
    JButton copyMessageButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("copy_text"));
    copyMessageButton.addActionListener(actionEvent -> this.copyErrorMessage());
    southPanel.add(copyMessageButton);
    this.add(southPanel, BorderLayout.SOUTH);
  }

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

  private void copyErrorMessage()
  {
    StringSelection selection = new StringSelection(this.errorTextArea.getText());
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(selection, null);
  }

  private String getStackTrace(Exception exception)
  {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    exception.printStackTrace(printWriter);
    return stringWriter.toString();
  }
}
