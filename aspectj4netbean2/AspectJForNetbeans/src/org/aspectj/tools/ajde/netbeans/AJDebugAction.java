/* AJDebugAction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.aspectj.tools.ajde.netbeans;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public class AJDebugAction extends CallableSystemAction
{
    /*synthetic*/ static Class class$org$aspectj$tools$ajde$netbeans$AJDebugAction;
    
    @Override
    public void performAction() {
        //NbManager.INSTANCE.build();
	NbManager.INSTANCE.debug();
    }
    
    public String getName() {
	return (NbBundle.getMessage
		((class$org$aspectj$tools$ajde$netbeans$AJDebugAction == null
		  ? (class$org$aspectj$tools$ajde$netbeans$AJDebugAction
		     = class$("org.aspectj.tools.ajde.netbeans.AJDebugAction"))
		  : class$org$aspectj$tools$ajde$netbeans$AJDebugAction),
		 "LBL_DebugAction"));
    }
    
    @Override
    protected String iconResource() {
	return "org/aspectj/tools/ajde/netbeans/resources/netbeans/debugProject.png";
    }
    
    public HelpCtx getHelpCtx() {
	return HelpCtx.DEFAULT_HELP;
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
