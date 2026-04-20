package de.kalkihe.rimanto.view.error;

public interface IErrorDialog {
  void showErrorDialog(Exception exception, boolean shutdownApplication);
}
