/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ramos.relationshiptable;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows RelationshipTable component.
 */
public class RelationshipTableAction extends AbstractAction {

    public RelationshipTableAction() {
        super(NbBundle.getMessage(RelationshipTableAction.class, "CTL_RelationshipTableAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(RelationshipTableTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = RelationshipTableTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
