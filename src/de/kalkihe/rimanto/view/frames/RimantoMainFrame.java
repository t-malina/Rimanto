package de.kalkihe.rimanto.view.frames;

import de.kalkihe.rimanto.utilities.IWordbook;

import javax.swing.*;
import java.awt.*;

public class RimantoMainFrame extends JFrame {
  /*
   * Other needed classes
   */
  private IWordbook wordbook;

  /*
   * UI-elements
   */
  private JPanel shownPanel;
  private JMenuBar mainMenuBar;

  /*
   * Constructor
   * Initializes the wordbook.
   * Starts initializing the Application
   */
  public RimantoMainFrame(IWordbook wordbook) {
    this.shownPanel = new JPanel(new BorderLayout());
    this.add(shownPanel);
    this.wordbook = wordbook;
    init();
  }

  private void init() {
    // Set Title of Main window
    this.setTitle("Rimanto");
    // Set icon of the main window
    ImageIcon icon = new ImageIcon("./images/danger.png");
    this.setIconImage(icon.getImage());
    // Set initial size of the current window
    // this.setSize(800, 700);
    // Set minimum size of the window
    this.setMinimumSize(new Dimension(1000, 800));
    // Set default close operation
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Initialize menu bar
    this.initializeMenuBar();
  }
  /*
   * Creates all needed items for the menu bar of the main window
   */
  private void initializeMenuBar() {
    mainMenuBar = new JMenuBar();
    JMenu fileMenu = new JMenu(wordbook.getWordForWithCapitalLeadingLetter("file"));
    JMenu helpMenu = new JMenu(wordbook.getWordForWithCapitalLeadingLetter("help"));
    mainMenuBar.add(fileMenu);
    mainMenuBar.add(helpMenu);
    this.setJMenuBar(mainMenuBar);
  }

  /*
   * Sets the Panel that is to use
   */
  public void setJPanel(JPanel panel)
  {
    this.shownPanel.removeAll();
    this.shownPanel.add(panel);
    this.shownPanel.revalidate();
    this.shownPanel.repaint();
  }
}
