package de.kalkihe.rimanto.view;

public interface IRimantoView {
  public void initView() throws ClassNotFoundException;
  public void showErrorDialog(Exception exception, boolean shutdownApplication);
}
