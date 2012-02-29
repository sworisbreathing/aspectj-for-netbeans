package org.aspectj.tools.ajde.netbeans;

//~--- non-JDK imports --------------------------------------------------------
import org.aspectj.ajde.Ajde;
import org.aspectj.asm.AsmManager;

import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;
import org.openide.util.actions.SystemAction;
import org.openide.windows.WindowManager;

//~--- JDK imports ------------------------------------------------------------

import java.util.prefs.BackingStoreException;

public class Installer extends ModuleInstall {

   @Override
   public void restored() {
      loadLastStructureModel();
      //startAspectJ();
   }

   @Override
   public void close() {
      super.close();
      persistCurrentStructureModel();
   }

   private void loadLastStructureModel() {
      String configFile = NbPreferences.forModule(Installer.class).get("config.file", "");
      if (!configFile.equals("")) {
         NbManager.restoredConfigFile = configFile;
         AsmManager.getDefault().readStructureModel(configFile);
      }
   }

   private void persistCurrentStructureModel() {
      try {
         String configFile = Ajde.getDefault().getConfigurationManager().getActiveConfigFile();
         NbPreferences.forModule(Installer.class).put("config.file", configFile);
         NbPreferences.forModule(Installer.class).flush();
         if ((configFile != null) && !configFile.equals("")) {
            AsmManager.getDefault().writeStructureModel(configFile);
         }
      } catch (BackingStoreException ex) {
         Exceptions.printStackTrace(ex);
      }
   }

   private void startAspectJ() {

      final AJStartBrowserAction START_BROWSER = (AJStartBrowserAction) SystemAction.get(AJStartBrowserAction.class);
      WindowManager.getDefault().invokeWhenUIReady( new Runnable() {

         public void run() {
            //START_BROWSER.performAction();
            START_BROWSER.setBooleanState(true);
            // AsmManager.getDefault().fireModelUpdated();
         }
      });
   }
   
}

