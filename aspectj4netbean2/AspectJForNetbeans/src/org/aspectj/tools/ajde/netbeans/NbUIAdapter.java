/* NbUIAdapter - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.aspectj.tools.ajde.netbeans;
import org.aspectj.ajde.ui.IdeUIAdapter;
//import org.openide.TopManager;
import org.openide.awt.StatusDisplayer;

public class NbUIAdapter implements IdeUIAdapter
{
    public void displayStatusInformation(String string) {
	StatusDisplayer.getDefault().setStatusText(string);
    }
    
    public void resetGUI() {
	/* empty */
    }
}
