package de.kalkihe.rimanto.model.storage;

import de.kalkihe.rimanto.model.data.IProject;

import java.util.List;

public interface IRimantoFileStorage {
  List<IProject> readProjects();
}
