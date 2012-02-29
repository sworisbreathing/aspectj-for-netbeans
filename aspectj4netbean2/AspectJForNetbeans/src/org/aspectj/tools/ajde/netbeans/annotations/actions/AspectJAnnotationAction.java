/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aspectj.tools.ajde.netbeans.annotations.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.text.JTextComponent;
import org.aspectj.ajde.Ajde;
import org.aspectj.ajde.ui.swing.AjdeUIManager;
import org.aspectj.ajde.ui.swing.IconRegistry;
import org.aspectj.asm.AsmManager;
import org.aspectj.asm.IProgramElement;
import org.aspectj.asm.internal.Relationship;
import org.aspectj.tools.ajde.netbeans.annotation.Annotator;
import org.aspectj.tools.ajde.netbeans.annotation.AspectJAnnotation;
import org.aspectj.tools.ajde.netbeans.NbIconRegistry;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.cookies.LineCookie;
import org.openide.nodes.Node;
import org.openide.text.Line;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;

/**
 *
 * @author ramos
 * 
 * soll annotation abfragen
 */
public class AspectJAnnotationAction extends CookieAction {

   private AspectJAnnotation annotation = null;
   private JMenuItem x;
   private static HashMap<String, String> nameMap = new HashMap<String, String>();

   static {
      nameMap.put("annotates", "Annotates ");
      nameMap.put("annotated by", "Annotated By ");
      nameMap.put("softens", "Softens ");
      nameMap.put("softened by", "Softened By ");
      nameMap.put("declare parents", "Declare Parents ");
      nameMap.put("aspect declarations", "Aspect Declarations ");
      nameMap.put("declared on", "Declared On ");
      nameMap.put("declare warning", "Declare Warning ");
      nameMap.put("declare error", "Declare Error ");
      nameMap.put("advises", "Advises ");
      nameMap.put("advised by", "Advised By ");
      nameMap.put("matched by", "Matched By ");
      nameMap.put("matches declare", "Matches Declare ");
   }

   public AspectJAnnotationAction() {
      //System.out.println("AspectJAnnotationAction CREATED");
      x = new JMenuItem("<not enabled>");
      x.setEnabled(false);
   }

   @Override
   protected int mode() {
      return CookieAction.MODE_EXACTLY_ONE;
   }

   @Override
   protected Class<?>[] cookieClasses() {
      return new Class<?>[]{LineCookie.class};
   }

   @Override
   protected void performAction(Node[] arg0) {
      assert false;
   }

   @Override
   public String getName() {
      return "The AspectJ Annotation Action";
   }

   @Override
   public HelpCtx getHelpCtx() {
      return HelpCtx.DEFAULT_HELP;
   }

   @Override
   public boolean isEnabled() {
      JTextComponent c = EditorRegistry.focusedComponent();
      //int caretPos = c.getCaretPosition();
      if (c == null) {
//            c = EditorRegistry.lastFocusedComponent();
//            if (c == null) {
//                return false;
//            }
         return false;
      }
      //System.out.println("focused text comp: " + c);
      Line line = NbEditorUtilities.getLine(c, true);
      //System.out.println("Line: " + line);
      annotation = Annotator.getDefault().annotationForLine(line);
      //System.out.println("found annotation: " + annotation);
      //System.out.println("isEnabled " + (annotation != null));
      return annotation != null;

   }

   @Override
   public JMenuItem getPopupPresenter() {
      JTextComponent c = EditorRegistry.focusedComponent();
      Line line = NbEditorUtilities.getLine(c, true);
      //System.out.println("Line: " + line);
      annotation = Annotator.getDefault().annotationForLine(line);
      //erster item für relationship name -> advised by
      //untermenu für targets
      //items mit actions goto line
      JMenu rootMenu = null;
      if (annotation == null) {
         return x;
      }
      //IconRegistry iconRegistry = AjdeUIManager.getDefault().getIconRegistry();
      List<Relationship> relationships = annotation.getRelationships();
      if (relationships.size() == 1) {
         Relationship relationship = relationships.get(0);
         rootMenu = new JMenu(toLabel(relationship.getName()));
         addMenuItems(relationship, rootMenu);
      } else {
         rootMenu = new JMenu("Relationships");
         for (Relationship relationship : relationships) {
            JMenu relationMenu = new JMenu(toLabel(relationship.getName()));
            //System.out.println("relationMenu with name: " + relationship.getName());
            addMenuItems(relationship, relationMenu);
            rootMenu.add(relationMenu);
         }
      }
      return rootMenu;
   }

   @Override
   public JMenuItem getMenuPresenter() {
      return getPopupPresenter();
   }

   private void addMenuItems(Relationship relationship, JMenu relationMenu) {
      IconRegistry iconRegistry = AjdeUIManager.getDefault().getIconRegistry();
      for (String handle : (List<String>) relationship.getTargets()) {

         IProgramElement targetElement = AsmManager.getDefault().getHierarchy().findElementForHandle(handle);
         if (targetElement != null) {
            JMenuItem jmi = relationMenu.add(
                    //new JMenuItem(targetElement+" in "+targetElement.getParent()));
                    new JMenuItem(targetElement.toLinkLabelString(false)));
            //jmi.setForeground(Color.BLUE);
            //System.out.println("   JMenuItem with name: " + targetElement.getName());
            final IProgramElement p = targetElement;
            jmi.addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {
                  Ajde.getDefault().getEditorAdapter().showSourceLine(p.getSourceLocation(), true);
               }
            });
            NbIconRegistry icons = (NbIconRegistry) AjdeUIManager.getDefault().getIconRegistry();

            if (targetElement.getKind().equals(IProgramElement.Kind.ADVICE)) {

               IProgramElement.ExtraInformation xtra = targetElement.getExtraInfo();
               if (xtra != null) {
                  String adviceInfo = xtra.getExtraAdviceInformation();
                  jmi.setIcon(icons.getAdviceIcon(adviceInfo));
               } else {
                  //by name
                  jmi.setIcon(icons.getAdviceIcon(targetElement.getName()));
               }
            } else {
               jmi.setIcon(iconRegistry.getStructureSwingIcon(targetElement.getKind()));
            }
         }
      }
   }

   private static String toLabel(String name) {
      String ret = nameMap.get(name);
      if (ret == null) {
         ret = name;
      }
      return ret;
   }
}
