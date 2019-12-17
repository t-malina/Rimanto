package de.kalkihe.rimanto.view;

import javax.swing.*;

public class RimantoView implements IRimantoView{
  @Override
  public void initView() throws ClassNotFoundException {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      //throw new ClassNotFoundException("Fehler beim Laden des Look and Feels des Systems.", e);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void showErrorDialog(Exception exception, boolean shutdownApplication) {

  }
}
