/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aspectj.tools.ajde.netbeans;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import org.aspectj.ajde.Ajde;
import org.aspectj.ajde.ui.StructureView;
import org.aspectj.ajde.ui.StructureViewProperties;
import org.aspectj.asm.AsmManager;
import org.aspectj.asm.IHierarchy;
import org.aspectj.asm.IHierarchyListener;
import org.aspectj.asm.IProgramElement;
import org.aspectj.asm.IProgramElement.Modifiers;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Ramos
 */
public class StructureFilterPanel extends JToolBar {

   NbIconRegistry icons;
   JToggleButton showAdvices;
   JToggleButton showPointcuts;
   JToggleButton showFields;
   JToggleButton showMethods;
   JToggleButton showStatic;
   JToggleButton showNonPublic;
   JToggleButton showAlpha;
   JToggleButton showBySource;
   private static final String CMD_SORT_BY_SOURCE = "sort by source";
   private static final String CMD_SORT_ALPHABETICAL = "sort alphabetically";
   MemberFilterListener memberFilterListener = new MemberFilterListener();
   ButtonGroup buttonGroup = new ButtonGroup();
   IProgramElement.Accessibility[] nonPublics = new IProgramElement.Accessibility[]{IProgramElement.Accessibility.PACKAGE,
      IProgramElement.Accessibility.PRIVATE,
      IProgramElement.Accessibility.PROTECTED
   };
   private StructureView currentView;

   StructureFilterPanel(StructureView currentView) {
      super(JToolBar.HORIZONTAL);

      AsmManager.getDefault().addListener(MODEL_LISTENER);
      setFloatable(false);
      setRollover(true);
      setBorderPainted(false);
      //setBorder(BorderFactory.createEmptyBorder());
      //setBorder(null);
      setBorder(new EmptyBorder(0, 2, 5, 5));
      setOpaque(false);
      setFocusable(false);
      icons = NbIconRegistry.INSTANCE;
      //jeweils icon und selected true
      this.currentView = currentView;
      //this.setLayout(new FlowLayout());
      this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

      showMethods = makeButton(icons.getShowMethodsIcon(), null, "Show methods", null);
      showFields = makeButton(icons.getShowFieldsIcon(), null, "Show fields", null);
      showStatic = makeButton(icons.getShowStaticIcon(), null, "Show static members", null);
      showNonPublic = makeButton(icons.getShowNonPublicIcon(), null, "Show non-public members", null);
      showAdvices = makeButton(icons.getShowAdviceIcon(), null, "Show advices", null);
      showPointcuts = makeButton(icons.getShowPointcutIcon(), null, "Show pointcuts", null);

      showAlpha = makeButton(icons.getOrderIcon(), CMD_SORT_ALPHABETICAL, "Sort by name", buttonGroup);
      showBySource = makeButton(icons.getOrderBySourceIcon(), CMD_SORT_BY_SOURCE, "Sort by source", buttonGroup);
      showBySource.setSelected(true);
   }

   void addNavButtons(AbstractButton left, AbstractButton right) {
      customizeButton(left);
      customizeButton(right);
      this.add(Box.createHorizontalGlue());
      this.add(left);
      this.add(right);
   }

   private void customizeButton(AbstractButton button) {
      Icon icon2 = button.getIcon();
      Dimension size = new Dimension(icon2.getIconWidth() + 12, icon2.getIconHeight() + 4);
      button.setPreferredSize(size);
      button.setMargin(new Insets(2, 3, 2, 3));
      button.setText(null);
      button.setFocusable(false);
   }

   private JToggleButton makeButton(Icon icon, String actionCommand, String tooltipText, ButtonGroup group) {
      Dimension space = new Dimension(3, 0);
      JToggleButton button = new JToggleButton(icon, true);
      customizeButton(button);
      button.setSelected(true);
      this.addSeparator(space);
      this.add(button);
      button.addActionListener(memberFilterListener);
      if (actionCommand != null) {
         button.setActionCommand(actionCommand);
      }
      button.setToolTipText(tooltipText);
      if (group != null) {
         group.add(button);
      }
      return button;
   }

   @Override
   protected void paintComponent(Graphics g) {
   }

   class MemberFilterListener implements ActionListener {

      public void actionPerformed(ActionEvent e) {

         JToggleButton toggle = (JToggleButton) e.getSource();
//            System.out.println("toggle: " + toggle);
         boolean selected = toggle.isSelected();
//            System.out.println("sel: " + selected);
         IProgramElement.Kind[] filters = new IProgramElement.Kind[0];
         if (toggle.equals(showAdvices)) {
            filters = new IProgramElement.Kind[]{IProgramElement.Kind.ADVICE};
         } else if (toggle.equals(showPointcuts)) {
            filters = new IProgramElement.Kind[]{IProgramElement.Kind.POINTCUT};
         } else if (toggle.equals(showFields)) {
            filters = new IProgramElement.Kind[]{IProgramElement.Kind.FIELD, IProgramElement.Kind.INTER_TYPE_FIELD};
         } else if (toggle.equals(showMethods)) {
            filters = new IProgramElement.Kind[]{
                       IProgramElement.Kind.METHOD,
                       IProgramElement.Kind.INTER_TYPE_METHOD
                    };
         } else if (toggle.equals(showStatic)) {
            if (selected) {
               currentView.getViewProperties().removeFilteredMemberModifiers(Modifiers.STATIC);
            } else {
               currentView.getViewProperties().addFilteredMemberModifiers(Modifiers.STATIC);
            }
         } else if (toggle.equals(showNonPublic)) {
            for (int i = 0; i < nonPublics.length; i++) {
               if (selected) {
                  currentView.getViewProperties().removeFilteredMemberAccessibility(nonPublics[i]);
               } else {
                  currentView.getViewProperties().addFilteredMemberAccessibility(nonPublics[i]);
               }
            }
         } else if (toggle.getActionCommand().equals(CMD_SORT_BY_SOURCE)) {
            currentView.getViewProperties().setSorting(StructureViewProperties.Sorting.DECLARATIONAL);
         } else if (toggle.getActionCommand().equals(CMD_SORT_ALPHABETICAL)) {
            currentView.getViewProperties().setSorting(StructureViewProperties.Sorting.ALPHABETICAL);
         }

         for (IProgramElement.Kind kind : filters) {
            if (selected) {
               currentView.getViewProperties().removeFilteredMemberKind(kind);
            } else {
               currentView.getViewProperties().addFilteredMemberKind(kind);
            }
         }

         Ajde.getDefault().getStructureViewManager().refreshView(currentView);
      }
   }
   public final IHierarchyListener MODEL_LISTENER = new IHierarchyListener() {

      public void elementsUpdated(IHierarchy model) {
         //System.out.println(" ########## ELEMENTS UPDATED #############");
         String path = Ajde.getDefault().getConfigurationManager().getActiveConfigFile();
         String fileName = "<no active config>";
         if (path != null) {
            fileName = new File(path).getName();
         }
         final String finalname = fileName;
         if (!SwingUtilities.isEventDispatchThread()) {
            try {
               SwingUtilities.invokeAndWait(new Runnable() {

                  public void run() {
                     WindowManager wm = WindowManager.getDefault();
                     //Due to reported NPE
                     if (wm != null) {
                        TopComponent tc = WindowManager.getDefault().findTopComponent("structureViewComponent");
                        //Due to reported NPE
                        if (tc != null) {
                           tc.setName("AspectJ CrossRefs (" + finalname + ")");
                        }
                     }
                  }
               });
            } catch (Exception ex) {
               ex.printStackTrace();
            }

         } else {
            WindowManager wm = WindowManager.getDefault();
            //Due to reported NPE
            if (wm != null) {
               TopComponent tc = wm.findTopComponent("structureViewComponent");
               //Due to reported NPE
               if (tc != null) {
                  tc.setName("AspectJ CrossRefs  (" + fileName + ")");
               }
            }
         }
      }
   };
}
