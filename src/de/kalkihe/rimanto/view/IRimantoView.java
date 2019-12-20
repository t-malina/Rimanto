package de.kalkihe.rimanto.view;

import com.google.inject.Injector;
import de.kalkihe.rimanto.presenter.IRimantoPresenter;

public interface IRimantoView {
  void showErrorDialog(Exception exception, boolean shutdownApplication);
  void setPresenter(IRimantoPresenter rimantoPresenter);
  void initializeApplicationWindow() throws Exception;


  String[] requestColumnNamesForProjectOverview();
  String[][] requestDataForProjectOverview();
  String[] requestColumnNamesForRiskOverview();
  String[][] requestDataForRiskOverview();
}

