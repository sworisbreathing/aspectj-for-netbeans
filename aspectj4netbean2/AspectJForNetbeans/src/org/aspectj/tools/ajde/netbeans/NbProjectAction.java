package org.aspectj.tools.ajde.netbeans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.aspectj.ajde.Ajde;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.openide.awt.DynamicMenuContent;
import org.openide.awt.Mnemonics;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;

public class NbProjectAction extends AbstractAction implements ContextAwareAction {

   public void actionPerformed(ActionEvent e) {
      assert false;
   }

   public Action createContextAwareInstance(Lookup context) {
      return new ContextAction(context);
   }

   private boolean enable(Project p) {
      return p != null && NbManager.INSTANCE != null;//ajde initialized
      //test if project is java project????
      
      // TODO for which projects action should be enabled
//        char c = ProjectUtils.getInformation(p).getDisplayName().charAt(0);
//        return c >= 'A' && c <= 'M';
   }

   private String labelFor(Project p) {
      assert p != null;
      // TODO menu item label with optional mnemonics
      return "&AspectJ Build for " + ProjectUtils.getInformation(p).getDisplayName();
   }

   //not called now with submenus
   private void perform(Project project) {
//      System.out.println("performing for " + project);
//      assert project != null;
//      List list = NbManager.INSTANCE.getProjectProperties().getBuildConfigFiles(project);
//      if (list.size() == 1) {
//         Ajde.getDefault().getConfigurationManager().setActiveConfigFile((String) list.get(0));
//         NbManager.INSTANCE.build();
//      }
      throw new UnsupportedOperationException();

   }

   private final class ContextAction extends AbstractAction implements Presenter.Popup {

      private final Project p;

      public ContextAction(Lookup context) {
         Project _p = (Project) context.lookup(Project.class);
         p = (_p != null && enable(_p)) ? _p : null;
      }

      public void actionPerformed(ActionEvent e) {
         perform(p);
      }

      // return NbBundle.getMessage(AJBuildAction.class,"LBL_BuildAction");
      private JMenuItem nothingItem = new JMenuItem(NbBundle.getMessage(NbProjectAction.class, "No_Lst_Files_in_Project"));

      public JMenuItem getPopupPresenter() {
         class Presenter extends JMenu implements DynamicMenuContent {

            private boolean loaded = false;

            public Presenter() {
               super(ContextAction.this);
               addActionListener(new ActionListener() {

                  public void actionPerformed(ActionEvent e) {
                     System.out.println("ACTION!!!!!!!!!!");
                  }
               });
               addItemListener(new ItemListener() {

                  public void itemStateChanged(ItemEvent e) {
                     if (loaded) {
                        return;
                     }
                     if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("NbProjectAction WORKING ...");
                        loadItems();
                        loaded = true;
                     }
                  }

                  private void loadItems() {
                     List list = NbManager.INSTANCE.getProjectProperties().getBuildConfigFiles(p);
                     //if (list.size() > 1) {
                     for (final Object object : list) {
                        Presenter.this.add(new AbstractAction(object.toString()) {

                           public void actionPerformed(ActionEvent e) {
                              // Logger.g
                              System.out.println("PROJECT: " + NbManager.INSTANCE.getProjectProperties().getProject());
                              System.out.println("CLASSPATH: " + NbManager.INSTANCE.getProjectProperties().getClasspath());
                              Ajde.getDefault().getConfigurationManager().setActiveConfigFile((String) object);
                              NbManager.INSTANCE.build();
                              //NbManager.INSTANCE.getProjectProperties().setProject(null);
                           }
                        });
                     }
                     if (list.size() == 0) {
                        nothingItem.setEnabled(false);
                        Presenter.this.add(nothingItem);
                     }
                  }
               });
            }

            public JComponent[] getMenuPresenters() {
               if (p != null) {
                  Mnemonics.setLocalizedText(this, labelFor(p));
                  NbManager.INSTANCE.getProjectProperties().setProject(p);
                  return new JComponent[]{this};
               } else {
                  return new JComponent[0];
               }
            }

            public JComponent[] synchMenuPresenters(JComponent[] items) {
               return getMenuPresenters();
            }
         }
         return new Presenter();
      }
   }
}
