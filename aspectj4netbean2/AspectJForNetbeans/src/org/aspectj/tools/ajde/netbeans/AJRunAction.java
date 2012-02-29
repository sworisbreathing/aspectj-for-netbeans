/* AJRunAction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.aspectj.tools.ajde.netbeans;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public class AJRunAction extends CallableSystemAction
{
    public static boolean PROP_ENABLED = false;
    /*synthetic*/ static Class class$org$aspectj$tools$ajde$netbeans$AJRunAction;
    
    public void performAction() {
        //NbManager.INSTANCE.build();
	NbManager.INSTANCE.run();
    }
    
    public String getName() {
	return (NbBundle.getMessage
		((class$org$aspectj$tools$ajde$netbeans$AJRunAction == null
		  ? (class$org$aspectj$tools$ajde$netbeans$AJRunAction
		     = class$("org.aspectj.tools.ajde.netbeans.AJRunAction"))
		  : class$org$aspectj$tools$ajde$netbeans$AJRunAction),
		 "LBL_RunAction"));
    }
    
    @Override
    protected String iconResource() {
	return "org/aspectj/tools/ajde/netbeans/resources/netbeans/runProject.png";
    }
    
    public HelpCtx getHelpCtx() {
	return HelpCtx.DEFAULT_HELP;
    }
    
    @Override
    public boolean isEnabled() {
	return PROP_ENABLED;
    }
    
    @Override
    public void setEnabled(boolean bool) {
	PROP_ENABLED = bool;
	super.setEnabled(bool);
    }
    
    @Override
    protected void initialize() {
        super.initialize();
	super.setEnabled(false);
    }
    @Override
     protected boolean asynchronous() {
       return false;
    }
    /*synthetic*/ static Class class$(String string) {
	Class var_class;
	try {
	    var_class = Class.forName(string);
	} catch (ClassNotFoundException classnotfoundexception) {
	    throw new NoClassDefFoundError(classnotfoundexception
					       .getMessage());
	}
	return var_class;
    }
}
