/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ramos.relationshiptable;

import java.awt.event.MouseEvent;
import org.aspectj.tools.ajde.netbeans.*;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.aspectj.ajde.Ajde;
import org.aspectj.asm.AsmManager;
import org.aspectj.asm.IHierarchy;
import org.aspectj.asm.IHierarchyListener;
import org.aspectj.asm.IProgramElement;
import org.aspectj.asm.IRelationshipMap;
import org.aspectj.asm.internal.Relationship;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Ramos
 */
public class RelationshipTable extends MouseAdapter implements IHierarchyListener {
   //private IRelationshipMap relationshipMap = AsmManager.getDefault().getRelationshipMap();
   private JXTable table = new JXTable();
   private JComponent component;
   private Vector<Vector> rows = new Vector<Vector>();
   private List<String> filterList = new ArrayList<String>();
   //advised by, advises, declared on, aspect declarations, matched by, matches declare,
   //annotates, annotated by, softens, softened by

   public RelationshipTable() {
      initializeFilterList();
      AsmManager.getDefault().addListener(this);
      setUpTable();
      JScrollPane scroll = new JScrollPane(table);
      component = scroll;
   }

   public Component getComponent() {
      return component;
   }

   private void setUpTable() {
//        try {
//            table.setAutoCreateRowSorter(true);
//        } catch (Throwable throwable) {
//
//        }
      table.addMouseListener(this);
      //table.setIntercellSpacing(new Dimension(-1, 1));
      table.setRowHeight(20);
      TableModel m = createTableModel();
      table.setModel(m);
      table.setDefaultRenderer(Object.class, new MyRenderer());
      //table.setShowGrid(false);
      table.setShowVerticalLines(false);
      table.setShowHorizontalLines(false);
      table.setColumnControlVisible(true);
   }

   class MyRenderer extends DefaultTableCellRenderer {

      @Override
      public Component getTableCellRendererComponent(
              JTable table,
              Object value,
              boolean isSelected,
              boolean hasFocus,
              int row,
              int column) {
         super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

         NbIconRegistry icons = NbIconRegistry.INSTANCE;
         if (value instanceof Relationship) {
            Relationship rel = (Relationship) value;
            setIcon(NbIconRegistry.INSTANCE.getAssociationSwingIcon(rel.getKind()));

         } else if (value instanceof IProgramElement) {
            IProgramElement pe = (IProgramElement) value;
//                pe.getKind();
//                setIcon(NbIconRegistry.INSTANCE.getStructureSwingIcon(pe.getKind()));
            setText("  " + pe.toLinkLabelString(false));
            if (pe.getKind().equals(IProgramElement.Kind.ADVICE)) {
               setText("    " + pe.toLinkLabelString(false));
               IProgramElement.ExtraInformation xtra = pe.getExtraInfo();
               if (xtra != null) {
                  String adviceInfo = xtra.getExtraAdviceInformation();
                  setIcon(icons.getAdviceIcon(adviceInfo));
               } else {
                  //by name
                  setIcon(icons.getAdviceIcon(pe.getName()));
               }
            } else {
               setIcon(icons.getStructureSwingIcon(pe.getKind(),
                       pe.getAccessibility(),
                       pe.getModifiers()));
            }
            if (pe.getKind().equals(IProgramElement.Kind.CODE)) {
               setText(pe.toLinkLabelString(false));
            }
         //if (!(pe instanceof Relationship)){

         //}

         }
         return this;

      }
   }

   private TableModel createTableModel() {
      rows.clear();
      Vector<String> colnames = new Vector<String>();

      colnames.add("Source");
      colnames.add("Relationship");
      colnames.add("Target");
      //System.out.println("Relationship            Source                     Target");
      IRelationshipMap relationshipMap = AsmManager.getDefault().getRelationshipMap();

      Set<String> set = AsmManager.getDefault().getRelationshipMap().getEntries();
      for (String handle : set) {
         IProgramElement programmElement = AsmManager.getDefault().getHierarchy().findElementForHandle(handle);
         List<Relationship> relationships = relationshipMap.get(programmElement);
         if (relationships == null) {
            continue;
         }
         for (Relationship relationship : relationships) {
            if (!passesFilter(relationship)) {
               continue;
            }
            IProgramElement sourceElement = AsmManager.getDefault().getHierarchy().findElementForHandle(relationship.getSourceHandle());
            if (sourceElement.getKind().isSourceFile()) {
               continue;
            }
            List<String> targets = relationship.getTargets();
            for (String ref : targets) {
               IProgramElement targetElement = AsmManager.getDefault().getHierarchy().findElementForHandle(ref);
               //System.out.println(relationship.getName() + " " + sourceElement + " " + targetElement);
               Vector<Object> row = new Vector<Object>();
               row.add(sourceElement);
               row.add(relationship);
               row.add(targetElement);
               rows.add(row);
            }

         }
      }
      //System.out.println("entries of rel map: " + relationshipMap.getEntries());


      DefaultTableModel dtm = new DefaultTableModel(rows, colnames) {

         @Override
         public boolean isCellEditable(int row, int column) {
            return false;
         }
      };
      return dtm;
   }

   public void elementsUpdated(IHierarchy arg0) {
     
      DefaultTableModel model = (DefaultTableModel) createTableModel();
      table.setModel(model);


   }

   @Override
   public void mouseClicked(MouseEvent e) {
     
      if (e.getButton() != MouseEvent.BUTTON1) {
         return;
      }
      int rowAtPoint = table.rowAtPoint(e.getPoint());
      if (rowAtPoint == -1) {
         return;
      }
      int modelRow = table.convertRowIndexToModel(rowAtPoint);
      int columnAtPoint = table.columnAtPoint(e.getPoint());
      int modelColumn = table.convertColumnIndexToModel(columnAtPoint);
      Vector row = rows.get(modelRow);
      if (row != null) {
         //get col
         Object clicked = row.get(modelColumn);
         if (clicked instanceof Relationship) {
            return;
         } else {
            IProgramElement pe = (IProgramElement) clicked;

            Ajde.getDefault().getEditorAdapter().showSourceLine(pe.getSourceLocation(), true);

         }
      }
   
   }

   private void addToFilterList(String relationshipName) {
      filterList.add(relationshipName);
   }

   private void removeFromFilterList(String relationshipName) {
      filterList.remove(relationshipName);
   }

   private boolean passesFilter(Relationship relationship) {

      if (!filterList.contains(relationship.getName())) {
         return true;
      }
      return false;


   }

   private void initializeFilterList() {
      filterList.add("advised by");
      filterList.add("aspect declarations");
      filterList.add("matches declare");
      filterList.add("annotated by");
      filterList.add("softened by");
   }
}
