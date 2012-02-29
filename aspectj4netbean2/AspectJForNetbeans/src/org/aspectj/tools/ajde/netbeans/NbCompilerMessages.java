package org.aspectj.tools.ajde.netbeans;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.aspectj.ajde.Ajde;
import org.aspectj.ajde.TaskListManager;
import org.aspectj.ajde.ui.swing.CompilerMessagesCellRenderer;
import org.aspectj.bridge.IMessage;
import org.aspectj.bridge.ISourceLocation;
import org.aspectj.bridge.Message;
//import org.openide.explorer.ExplorerPanel;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public class NbCompilerMessages implements TaskListManager {

   private CompilerMessageWindow compilerMessageWindow = new CompilerMessageWindow();
   //private OutputTab outputTab = null;
   private OutputComponent outputTab = null;
   private boolean hasWarning = false;
   private static final String NAME = "AspectJ Compiler Output";

   static class OutputComponent extends TopComponent {

      private final String PREFERRED_ID = "outputComponent";

      public OutputComponent(JPanel jpanel) {
         this.setName("AspectJ Compiler Output");
         this.setLayout(new BorderLayout());
         this.add(jpanel, "Center");
         this.add(jpanel);
      }

      @Override
      public Image getIcon() {
         return ((ImageIcon) NbIconRegistry.INSTANCE.getStartAjdeIcon()).getImage();
      }

//        public SystemAction[] getSystemActions() {
//            return new SystemAction[0];
//        }
      @Override
      public int getPersistenceType() {
         return TopComponent.PERSISTENCE_NEVER;
      }

      @Override
      protected String preferredID() {
         return PREFERRED_ID;
      }
   }

   static class CompilerMessageWindow extends JPanel {

      JScrollPane jScrollPane1 = new JScrollPane();
      JScrollPane jScrollPane2 = new JScrollPane();
      JList list = new JList();
      DefaultListModel listModel = new DefaultListModel();
      BorderLayout borderLayout1 = new BorderLayout();

      public CompilerMessageWindow() {
         try {
            jbInit();
         } catch (Exception exception) {
            exception.printStackTrace();
         }
         list.setModel(listModel);
         MouseAdapter mouseadapter = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent event) {
               if (event.getClickCount() == 2) {
                  int i = list.locationToIndex(event.getPoint());
                  if (i != -1) {
                     IMessage imessage = (IMessage) listModel.getElementAt(i);
                     ISourceLocation isourcelocation = imessage.getSourceLocation();
                     String filename = "<no file>";
                     int line = 0;
                     if (null != isourcelocation) {
                        File file = isourcelocation.getSourceFile();
                        if (null != file) {
                           filename = file.getAbsolutePath();
                        }
                        line = isourcelocation.getLine();
                     }
                     Ajde.getDefault().getEditorAdapter().showSourceLine(filename, line, true);
                  }
               }
            }
            };
         list.addMouseListener(mouseadapter);
         list.setCellRenderer(new CompilerMessagesCellRenderer());
      }

      public void addMessage(IMessage imessage) {
         listModel.addElement(imessage);
         if (imessage.isError() || imessage.isWarning()){
            //would add compiler gutter annotations here
            //add if openInEditor
            //listener editor.registry if new opened
         }
         //inspectMessage(imessage);
         
      }

      public void clearMessages() {
         listModel.clear();
         System.out.println("clearMessages");
         //would delete compiler gutter annotations here
      }

      private void emptyBorders() {
         this.setBorder(BorderFactory.createEmptyBorder());
         list.setBorder(BorderFactory.createEmptyBorder());
         jScrollPane2.setBorder(BorderFactory.createEmptyBorder());
         jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
      }

      private void inspectMessage(IMessage imessage) {
        System.out.println("getDetails: "+imessage.getDetails());
        System.out.println("getMessage: "+imessage.getMessage());
        System.out.println("getDeclared: "+imessage.getDeclared());
        System.out.println("getExtraSourceLocations: "+imessage.getExtraSourceLocations());
        System.out.println("getID: "+imessage.getID());
        System.out.println("getKind: "+imessage.getKind());
        System.out.println("getSourceEnd: "+imessage.getSourceEnd());
        System.out.println("getSourceStart: "+imessage.getSourceStart());
        System.out.println("getThrown: "+imessage.getThrown());
        System.out.println("isAbort: "+imessage.isAbort());
        System.out.println("isDebug: "+imessage.isDebug());
        System.out.println("isError: "+imessage.isError());
        System.out.println("isFailed: "+imessage.isFailed());
        System.out.println("isInfo: "+imessage.isInfo());
        System.out.println("isTaskTag: "+imessage.isTaskTag());
        System.out.println("isWarning: "+imessage.isWarning());
        inspectSourceLocation(imessage.getSourceLocation());
      }

      private void inspectSourceLocation(ISourceLocation sourceLocation) {
         System.out.println("getContext: "+sourceLocation.getContext());
         System.out.println("getSourceFileName: "+sourceLocation.getSourceFileName());
         System.out.println("getColumn: "+sourceLocation.getColumn());
         System.out.println("getEndLine: "+sourceLocation.getEndLine());
         System.out.println("getLine: "+sourceLocation.getLine());
         System.out.println("getOffset: "+sourceLocation.getOffset());
         System.out.println("getSourceFile: "+sourceLocation.getSourceFile());
         
      }

      private void jbInit() throws Exception {
         //emptyBorders();
         //jScrollPane1.getViewport().setBorder(BorderFactory.createEmptyBorder());
         this.setLayout(borderLayout1);
         this.add(jScrollPane1, "Center");
         jScrollPane1.getViewport().add(jScrollPane2);
         jScrollPane2.getViewport().add(list);
      }
   }

   public NbCompilerMessages() {
      installTab();
   }

   private void installTab() {
      if (outputTab == null) {
         outputTab = new OutputComponent(compilerMessageWindow);
      }

      Mode mode = findOutputMode();
      mode.dockInto(outputTab);
   }

   private Mode findOutputMode() {

      return WindowManager.getDefault().findMode("output");
   }

   public boolean hasWarning() {
      return hasWarning;
   }

   public void addSourcelineTask(String string, ISourceLocation isourcelocation, IMessage.Kind kind) {
      addSourcelineTask(new Message(string, kind, null, isourcelocation));
   }

   public void addSourcelineTask(IMessage imessage) {
      maintainHasWarning(null == imessage ? null : imessage.getKind());
      compilerMessageWindow.addMessage(imessage);

      if (SwingUtilities.isEventDispatchThread()) {
         outputTab.open();/*outputTab.requestActive();*/
      } else {
         SwingUtilities.invokeLater(new Runnable() {

            public void run() {
               outputTab.open();
            /*outputTab.requestActive();*/
            }
            });
      }
   }

   public void addProjectTask(String string, IMessage.Kind kind) {
      maintainHasWarning(kind);
      if (!outputTab.isDisplayable()) {
         installTab();
      }
      compilerMessageWindow.addMessage(new Message(string, kind, null, null));
      outputTab.open();
   /*outputTab.requestActive();*/
   }

   public void clearTasks() {
      compilerMessageWindow.clearMessages();
      hasWarning = false;
   }

   private void maintainHasWarning(IMessage.Kind kind) {
      if (null != kind && !hasWarning && IMessage.WARNING.isSameOrLessThan(kind)) {
         hasWarning = true;
      }
   }
}
