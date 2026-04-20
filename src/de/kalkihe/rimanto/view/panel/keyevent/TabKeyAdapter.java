package de.kalkihe.rimanto.view.panel.keyevent;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Extension of KeyAdapter for tabbing through input elements
 */
public class TabKeyAdapter extends KeyAdapter {
  private JComponent nextComponent;

  /**
   * Connstructor. Initalizes the next component
   * @param nextComponent
   */
  public TabKeyAdapter(JComponent nextComponent) {
    super();
    this.nextComponent = nextComponent;
  }

  /**
   * Event if key was pressed
   * @param e The key event
   */
  @Override
  public void keyPressed(KeyEvent e) {
    // If tab was pressed
    if(e.getKeyChar() == KeyEvent.VK_TAB)
    {
      // Grab the focus of the next component that was passed
      nextComponent.grabFocus();
    }
  }
}
