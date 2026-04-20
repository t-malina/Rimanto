package de.kalkihe.rimanto.view.panel.panels;

import de.kalkihe.rimanto.presenter.IEventProcessor;
import de.kalkihe.rimanto.model.wordbook.IWordbook;
import de.kalkihe.rimanto.view.IRimantoView;

import javax.swing.*;
import java.awt.*;

public abstract class GeneralRimantoPanel extends JPanel {
  /*
   * Needed references
   */
  protected IWordbook wordbook;
  protected IEventProcessor eventProcessor;
  protected IRimantoView rimantoView;

  public GeneralRimantoPanel(IWordbook wordbook, IEventProcessor eventProcessor, IRimantoView rimantoView) {
    super(new BorderLayout());
    this.wordbook = wordbook;
    this.eventProcessor = eventProcessor;
    this.rimantoView = rimantoView;
  }

  protected abstract void buildPanel() throws Exception;

  protected JScrollPane configureAndInsertTextArea(JTextArea textArea)
  {
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(textArea);
    return scrollPane;

  }

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
