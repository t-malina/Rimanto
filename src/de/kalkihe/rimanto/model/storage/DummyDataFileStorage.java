package de.kalkihe.rimanto.model.storage;

import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.data.Project;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/*
 * Implementation of IRimantoFileStorage interface to provide dummy data for testing the application
 */
public class DummyDataFileStorage implements IRimantoFileStorage {
  /*
   * Returns list with some dummy projects
   */
  @Override
  public List<IProject> readProjects() {
    List<IProject> resultList = new ArrayList<IProject>();
    IProject project1 = new Project("Testprojekt1", "Beschreibung für Testprojekt 1", new GregorianCalendar(2022, 12,17),
      new GregorianCalendar(2022, 12, 20), null, null, null);
    IProject project2 = new Project("Testprojekt2", "Beschreibung für Testprojekt 2",
      new GregorianCalendar(2032, 12,17), new GregorianCalendar(2042, 12, 20),
      null, null, null);
    resultList.add(project1);
    resultList.add(project2);
    return resultList;
  }
}
