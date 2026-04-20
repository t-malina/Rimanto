package de.kalkihe.rimanto.view.panel.keyevent;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TabKeyAdapter extends KeyAdapter {
  private JComponent nextComponent;

  public TabKeyAdapter(JComponent nextComponent) {
    super();
    this.nextComponent = nextComponent;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyChar() == KeyEvent.VK_TAB)
    {
      nextComponent.grabFocus();
    }
  }
}
