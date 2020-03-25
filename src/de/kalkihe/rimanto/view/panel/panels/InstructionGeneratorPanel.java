package de.kalkihe.rimanto.view.panel.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.IRisk;
import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;
import de.kalkihe.rimanto.view.panel.keyevent.TabKeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Panel for creation of a instruction out of a risk
 */
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

  /**
   * Constructor. Initializes needed references
   * @param wordbook Wordbook for labels etc
   * @param eventProcessor Eventhandler to pass events to
   * @param rimantoView reference to view
   * @param project The project the exported risk is part of
   * @param risk The risk that is to export
   * @throws Exception
   */
  public InstructionGeneratorPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView, IProject project, IRisk risk) throws Exception {
    super(wordbook, eventProcessor, rimantoView);
    this.project = project;
    this.risk = risk;
    this.buildPanel();
  }

  /**
   * Builds the content of the panel
   * @throws Exception
   */
  @Override
  protected void buildPanel() throws Exception {
    this.northPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    this.centerPanel = new JPanel(new BorderLayout());
    this.southPanel = new JPanel(new FlowLayout());

    JLabel recipientLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("recipient"));
    this.northPanel.add(recipientLabel);
    this.recipientTextField = new JTextField();
    this.northPanel.add(recipientTextField);

    JLabel dueDateLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("due_date"));
    this.northPanel.add(dueDateLabel);
    DatePickerSettings datePickerSettings = new DatePickerSettings();
    datePickerSettings.setAllowKeyboardEditing(false);
    datePickerSettings.setFormatForDatesCommonEra(this.wordbook.getDateTimeFormatter());
    this.dueDatePicker = new DatePicker(datePickerSettings);
    this.northPanel.add(this.dueDatePicker);

    JLabel instuctionLabel = new JLabel(this.wordbook.getWordForWithCapitalLeadingLetter("instruction"));
    this.northPanel.add(instuctionLabel);
    this.instructionTextArea = new JTextArea();
    this.instructionTextArea.setWrapStyleWord(true);
    this.instructionTextArea.setLineWrap(true);
    JScrollPane scrollPane = new JScrollPane(this.instructionTextArea);
    scrollPane.setPreferredSize(new Dimension(0, 50));
    this.northPanel.add(scrollPane);

    this.outputTextArea = new JTextArea();
    this.outputTextArea.setLineWrap(true);
    this.outputTextArea.setWrapStyleWord(true);
    JScrollPane outputScrollPane = new JScrollPane(this.outputTextArea);

    this.centerPanel.add(outputScrollPane);

    JButton backButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("back"));
    backButton.addActionListener(actionEvent -> this.eventProcessor.abortRiskAsInstruction(this.project, this.risk));
    this.southPanel.add(backButton);

    JButton copyTextButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("copy_text"));
    copyTextButton.addActionListener(actionEvent -> this.copyOutput());
    this.southPanel.add(copyTextButton);

    JButton generateTextButton = new JButton(this.wordbook.getWordForWithCapitalLeadingLetter("generate_text"));
    generateTextButton.addActionListener(actionEvent -> this.generateOutput());
    this.southPanel.add(generateTextButton);

    this.add(this.northPanel, BorderLayout.NORTH);
    this.add(this.centerPanel, BorderLayout.CENTER);
    this.add(this.southPanel, BorderLayout.SOUTH);

    this.recipientTextField.addKeyListener(new TabKeyAdapter(this.dueDatePicker));
    this.dueDatePicker.addKeyListener(new TabKeyAdapter(this.instructionTextArea));
    this.instructionTextArea.addKeyListener(new TabKeyAdapter(this.outputTextArea));
  }

  /**
   * Copies the generated output to the clipboard
   */
  private void copyOutput()
  {
    StringSelection selection = new StringSelection(this.outputTextArea.getText());
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(selection, null);

  }

  /**
   * Generates the output and displays in the text area
   */
  private void generateOutput()
  {
    String outputText = this.wordbook.getRiskInstruction(this.project, this.risk, this.recipientTextField.getText(), this.dueDatePicker.toString(), this.instructionTextArea.getText());
    this.outputTextArea.setText(outputText);

  }
}
