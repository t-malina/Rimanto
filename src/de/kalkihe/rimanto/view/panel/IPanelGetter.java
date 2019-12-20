package de.kalkihe.rimanto.view.panel;

import javax.swing.*;

public interface IPanelGetter {
  JPanel getPanelForOverview();
  JPanel getPanelForProjectView();
  JPanel getPanelForRiskView();
  JPanel getPanelForProjectInput();
  JPanel getPanelForErrorView();
}
