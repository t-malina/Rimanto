package de.kalkihe.rimanto.presenter;

import de.kalkihe.rimanto.view.frames.RimantoMainFrame;

public class RimantoPresenter {
  public static void main(String[] args) {
    RimantoMainFrame mainFrame = new RimantoMainFrame("Rimanto");
    mainFrame.setSize(200,200);
    mainFrame.setVisible(true);
  }
}
