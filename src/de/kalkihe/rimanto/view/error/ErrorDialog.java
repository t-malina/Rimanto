package de.kalkihe.rimanto.view.error;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorDialog implements IErrorDialog{
  @Override
  public void showErrorDialog(Exception exception, boolean shutdownApplication) {

    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    exception.printStackTrace(printWriter);

    JTextArea errorMessage = new JTextArea(stringWriter.toString());
    errorMessage.setLineWrap(true);
    errorMessage.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(errorMessage);

    JOptionPane.showMessageDialog(null, scrollPane, "Error", JOptionPane.ERROR_MESSAGE);
    exception.printStackTrace();
    if (shutdownApplication)
    {
      System.exit(0);
    }
  }
}
