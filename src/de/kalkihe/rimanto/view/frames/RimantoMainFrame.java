package de.kalkihe.rimanto.view;

import javax.swing.*;

public class RimantoMainFrame extends JFrame{
  private JPanel mainPanel;

  private JMenuBar menuBar;

  public RimantoMainFrame()
  {
    init();
  }

  public RimantoMainFrame(String title)
  {
    super(title);
    init();
  }
  private void init()
  {
    menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("Datei");
    JMenu helpMenu = new JMenu("Hilfe");
    menuBar.add(fileMenu);
    menuBar.add(helpMenu);
    this.setJMenuBar(menuBar);


    // Icon
    ImageIcon icon = new ImageIcon("/windows/Nextcloud/Studium/Module/T3_3101 Studienarbeit/Rimanto/images/danger.png");
    this.setIconImage(icon.getImage());
  }

//  public void setVisible(boolean visible)
/*  {
    JFrame jFrame = new JFrame("Rimanto");
    jFrame.add(this.mainPanel);
    jFrame.setSize(300, 300);
    jFrame.setVisible(visible);
  } */
}
