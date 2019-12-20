package de.kalkihe.rimanto.model;

import com.google.inject.Injector;
import de.kalkihe.rimanto.model.data.IProject;
import de.kalkihe.rimanto.model.storage.IRimantoFileStorage;
import de.kalkihe.rimanto.utilities.IWordbook;
import de.kalkihe.rimanto.utilities.RimantoIOCContainer;

import java.util.List;

public class RimantoModel implements IRimantoModel{
  private List<IProject> projectList;
  private RimantoIOCContainer rimantoIOCContainer;
  private IRimantoFileStorage rimantoFileStorage;
  private IWordbook wordbook;


  @Override
  public String[] getColumnNamesForProjects() {
    IProject project0 = projectList.get(0);
    if (project0 == null)
    {
      return null;
    }
    String[] result = project0.getGeneralDataNames();
    for (int index = 0; index < result.length; index++)
    {
      result[index] = this.wordbook.getWordForWithCapitalLeadingLetter(result[index]);
    }
    return result;
  }

  @Override
  public String[][] getDataOfProjects() {
    String[][] results = new String[projectList.size()][];
    for(int index = 0; index < this.projectList.size(); index++)
    {
      results[index] = this.projectList.get(index).getGeneralProjectData();
    }
    return results;
  }

  @Override
  public String[] getColumnNamesForRisks() {
    return new String[0];
  }

  @Override
  public String[][] getDataOfRisks() {
    return new String[0][];
  }

  @Override
  public void initializeModel(Injector injector) throws Exception {
    this.rimantoIOCContainer = RimantoIOCContainer.getInstance();
    this.rimantoFileStorage = (IRimantoFileStorage) this.rimantoIOCContainer.getInstanceFor(IRimantoFileStorage.class);
    this.projectList = this.rimantoFileStorage.readProjects();
    this.wordbook = (IWordbook) this.rimantoIOCContainer.getInstanceFor(IWordbook.class);
  }
}
