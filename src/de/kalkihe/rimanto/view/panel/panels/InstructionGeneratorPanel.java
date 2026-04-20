package de.kalkihe.rimanto.view.panel.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class InstructionGeneratorPanel extends GeneralRimantoPanel {
  private IProject project;
  private IRisk risk;

  private JPanel northPanel;
  private JPanel centerPanel;
  private JPanel southPanel;

  private JTextField recipientTextField;
  private DatePicker dueDatePicker;
  private JTextArea instructionTextArea;

  private JTextArea outputTextArea;

  public InstructionGeneratorPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project, IRisk risk) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.risk = risk;
    this.buildPanel();
  }

  @Override
  protected void buildPanel() throws Exception {
    this.northPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    this.centerPanel = new JPanel(new BorderLayout());
    this.southPanel = new JPanel(new FlowLayout());

    JLabel recipientLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("recipient"));
    this.northPanel.add(recipientLabel);
    this.recipientTextField = new JTextField();
    this.northPanel.add(recipientTextField);

    JLabel dueDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("due date"));
    this.northPanel.add(dueDateLabel);
    DatePickerSettings datePickerSettings = new DatePickerSettings();
    datePickerSettings.setAllowKeyboardEditing(false);
    datePickerSettings.setFormatForDatesCommonEra(this.wordbook.getDateTimeFormatter());
    this.dueDatePicker = new DatePicker(datePickerSettings);
    this.northPanel.add(this.dueDatePicker);

    JLabel instuctionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("instruction"));
    this.northPanel.add(instuctionLabel);
    this.instructionTextArea = new JTextArea();
    //TODO: Überall wie hier machen
    this.instructionTextArea.setWrapStyleWord(true);
    this.instructionTextArea.setLineWrap(true);
    JScrollPane scrollPane = new JScrollPane(this.instructionTextArea);
    scrollPane.setPreferredSize(new Dimension(0, 50));
    this.northPanel.add(scrollPane);

    this.outputTextArea = new JTextArea();
    JScrollPane outputScrollPane = new JScrollPane(this.outputTextArea);

    this.centerPanel.add(outputScrollPane);

    JButton backButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("back"));
    backButton.addActionListener(actionEvent -> this.eventProcessor.abortRiskAsInstruction(this.project, this.risk));
    this.southPanel.add(backButton);

    JButton copyTextButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("copy text"));
    copyTextButton.addActionListener(actionEvent -> this.copyOutput());
    this.southPanel.add(copyTextButton);

    JButton generateTextButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("generate text"));
    generateTextButton.addActionListener(actionEvent -> this.generateOutput());
    this.southPanel.add(generateTextButton);

    this.add(this.northPanel, BorderLayout.NORTH);
    this.add(this.centerPanel, BorderLayout.CENTER);
    this.add(this.southPanel, BorderLayout.SOUTH);

  }

  private void copyOutput()
  {
    StringSelection selection = new StringSelection(this.outputTextArea.getText());
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(selection, null);

  }

  private void generateOutput()
  {
    String outputText = this.wordbook.getRiskInstruction(this.project, this.risk, this.recipientTextField.getText(), this.dueDatePicker.toString(), this.instructionTextArea.getText());
    this.outputTextArea.setText(outputText);

  }
}
