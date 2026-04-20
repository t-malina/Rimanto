package de.kalkihe.rimanto.presenter;

public interface IRimantoPresenter {
  String[] fetchColumnNamesForProjects();
  String[][] fetchDataOfProjects();
  String[] fetchColumnNamesForRisks();
  String[][] fetchDataOfRisks();

}
