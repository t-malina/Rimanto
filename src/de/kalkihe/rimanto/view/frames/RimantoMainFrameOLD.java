package de.kalkihe.rimanto.view.frames;

import de.kalkihe.rimanto.utilities.IWordbook;

import javax.swing.*;

public class RimantoMainFrameOLD extends JFrame {
  /*
   * Other needed classes
   */
  private IWordbook wordbook;

  /*
   * UI-elements
   */
  private JPanel mainPanel;
  private JButton button2;
  private JButton button5;
  private JTable table1;
  private JButton testButton;
  private JScrollPane jScrollPane;
  private JMenuBar menuBar;

  public RimantoMainFrameOLD(IWordbook wordbook) {
    this.wordbook = wordbook;
    init();
  }

  private void init() {
    // Set Title of Main window
    this.setTitle("Rimanto");
    // Set icon of the main window
    ImageIcon icon = new ImageIcon("/windows/Nextcloud/Studium/Module/T3_3101 Studienarbeit/Rimanto/images/danger.png");
    this.setIconImage(icon.getImage());
    // Set initial size of the current window
    this.setSize(600, 500);
    // Set default close operation
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Initialize menu bar
    this.initializeMenuBar();

    /*String data[][]={ {"101","Amit","670000"},
      {"102","Jai","780000"},
      {"101","Sachin","700000"}};
    String column[]={"ID","NAME","SALARY"};

    table1 = new JTable(data, column);
    jScrollPane = new JScrollPane(table1);
    mainPanel.add(jScrollPane, BorderLayout.CENTER);
    /*

    mainPanel.add(table1);
*/
    this.setJPanel(this.mainPanel);
  }

  private void initializeMenuBar() {
    menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu(wordbook.getWordForWithCapitalLeadingLetter("file"));
    JMenu helpMenu = new JMenu(wordbook.getWordForWithCapitalLeadingLetter("help"));
    menuBar.add(fileMenu);
    menuBar.add(helpMenu);
    this.setJMenuBar(menuBar);
  }

  public JPanel getPanel()
  {
    return mainPanel;
  }

  public void setJPanel(JPanel panel)
  {
    this.add(panel);
  }
}

