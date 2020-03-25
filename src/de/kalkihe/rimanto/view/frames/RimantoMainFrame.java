package de.kalkihe.rimanto.view.frames;

import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.presenter.IEventProcessor;

import javax.swing.*;
import java.awt.*;

/**
 * The main application window
 */
public class RimantoMainFrame extends JFrame {
  /**
   * Other needed classes
   */
  private IWordbook wordbook;
  private IEventProcessor eventProcessor;

  /**
   * UI-elements
   */
  private JPanel shownPanel;
  private JMenuBar mainMenuBar;
  private ImageIcon icon;

  /**
   * Constructor. Initializes needed references and starts initializing the application window
   * @param wordbook The wordbook to use
   * @param eventProcessor The event processor to give events to
   */
  public RimantoMainFrame(IWordbook wordbook, IEventProcessor eventProcessor) {
    // Initialize main panel
    this.shownPanel = new JPanel(new BorderLayout());
    // Add main panel to frame
    this.add(shownPanel);
    // Set references
    this.wordbook = wordbook;
    this.eventProcessor = eventProcessor;
    // Start initialization of the window
    init();
  }

  /**
   * Initialize the main application window
   */
  private void init() {
    // Set Title of Main window
    this.setTitle("Rimanto");
    // Set icon of the main window
    this.icon = new ImageIcon(getClass().getResource("/danger.png"));
    this.setIconImage(this.icon.getImage());
    // Set initial size of the current window
    // this.setSize(800, 700);
    // Set minimum size of the window
    this.setMinimumSize(new Dimension(1100, 700));
    // Set default close operation
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Initialize menu bar
    this.initializeMenuBar();
  }

  /**
   * Creates all needed items for the menu bar of the main window
   */
  public void initializeMenuBar() {
    mainMenuBar = new JMenuBar();
    JMenu fileMenu = new JMenu(wordbook.getWordForWithCapitalLeadingLetter("file"));
    JMenuItem exitMenu = new JMenuItem(wordbook.getWordForWithCapitalLeadingLetter("exit"));
    exitMenu.addActionListener(actionEvent -> System.exit(0));
    JMenu optionMenu = new JMenu(wordbook.getWordForWithCapitalLeadingLetter("options"));
    JMenu languageMenu = new JMenu(wordbook.getWordForWithCapitalLeadingLetter("language"));
    JMenuItem germanMenu = new JMenuItem(wordbook.getWordForWithCapitalLeadingLetter("german"));
    germanMenu.addActionListener(actionEvent -> this.eventProcessor.languageChanged("de", "DE"));
    JMenuItem englishMenu = new JMenuItem(wordbook.getWordForWithCapitalLeadingLetter("english"));
    englishMenu.addActionListener(actionEvent -> this.eventProcessor.languageChanged("en", "US"));
    JMenu helpMenu = new JMenu(wordbook.getWordForWithCapitalLeadingLetter("help"));
    JMenuItem aboutItem = new JMenuItem(wordbook.getWordForWithCapitalLeadingLetter("about"));
    aboutItem.addActionListener(actionEvent -> this.showAbout());
    mainMenuBar.add(fileMenu);
    fileMenu.add(exitMenu);
    mainMenuBar.add(optionMenu);
    optionMenu.add(languageMenu);
    languageMenu.add(germanMenu);
    languageMenu.add(englishMenu);
    mainMenuBar.add(helpMenu);
    helpMenu.add(aboutItem);
    this.setJMenuBar(mainMenuBar);
  }

  /**
   * Sets the panel that is to show
   * @param panel The panel that is to show
   */
  public void setJPanel(JPanel panel)
  {
    this.shownPanel.removeAll();
    this.shownPanel.add(panel);
    this.shownPanel.revalidate();
    this.shownPanel.repaint();
  }

  /**
   * Shows the about message window
   */
  private void showAbout()
  {
    String message = this.wordbook.getWordForWithCapitalLeadingLetter("rimanto") + "\n\n" + this.wordbook.getWordForWithCapitalLeadingLetter("icon");
    JOptionPane.showMessageDialog(this, message, this.wordbook.getWordForWithCapitalLeadingLetter("about"), 0, this.icon);
  }
}
