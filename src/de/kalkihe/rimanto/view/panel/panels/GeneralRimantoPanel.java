package de.kalkihe.rimanto.view.panel.panels;

import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import java.awt.*;

/**
 * Extension of JPanel to define all needed ressources for panels of this application
 */
public abstract class GeneralRimantoPanel extends JPanel {
  /**
   * Needed references
   */
  protected IWordbook wordbook;
  protected IEventProcessor eventProcessor;
  protected IRimantoView rimantoView;

  /**
   * Constructor. Initializes needed references
   * @param wordbook Wordbook for labels etc
   * @param eventProcessor Eventhandler to pass events to
   * @param rimantoView reference to view
   */
  public GeneralRimantoPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) {
    // Call super constructor with a new Border Layout as default
    super(new BorderLayout());
    this.wordbook = wordbook;
    this.eventProcessor = eventProcessor;
    this.rimantoView = rimantoView;
  }

  /**
   * Build the panel
   * @throws Exception
   */
  protected abstract void buildPanel() throws Exception;

  /**
   * Takes a text area, wraps it into a scroll pane and returns that scroll pane
   * @param textArea The text area to wrap into scroll pane
   * @return
   */
  protected JScrollPane configureAndInsertTextArea(JTextArea textArea)
  {
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(textArea);
    return scrollPane;
  }

  /**
   * Takes a text area, makes it read only and wraps it into a scroll pane
   * @param textArea The text area to wrap into scroll pane
   * @return
   */
  protected JScrollPane getScrollPaneWithReadOnly(JTextArea textArea)
  {
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    textArea.setOpaque(false);
    textArea.setEditable(false);
    textArea.setFocusable(false);
    textArea.setBackground(UIManager.getColor("Label.background"));
    textArea.setFont(UIManager.getFont("Label.font"));
    textArea.setBorder(UIManager.getBorder("Label.border"));
    return new JScrollPane(textArea);
  }
}
