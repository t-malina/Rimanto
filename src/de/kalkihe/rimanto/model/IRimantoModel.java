package de.kalkihe.rimanto.model;

import com.google.inject.Injector;

public interface IRimantoModel {
  String[] getColumnNamesForProjects();
  String[][] getDataOfProjects();
  String[] getColumnNamesForRisks();
  String[][] getDataOfRisks();

  void initializeModel(Injector injector) throws Exception;
}
