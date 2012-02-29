package org.aspectj.tools.ajde.netbeans;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public class AJBuildAction extends CallableSystemAction
{
    public static boolean PROP_ENABLED_BOOLEAN = false;
    
    
    public void performAction() {
        //System.out.println("AJBuildAction#performAction");
	NbManager.INSTANCE.build();
    }
    
    public String getName() {
	return NbBundle.getMessage(AJBuildAction.class,"LBL_BuildAction");
    }
    
    @Override
    protected String iconResource() {
	return "org/aspectj/tools/ajde/netbeans/resources/actions/build.gif";
    }
    
    public HelpCtx getHelpCtx() {
	return HelpCtx.DEFAULT_HELP;
    }
    
    @Override
    public void setEnabled(boolean bool) {
	PROP_ENABLED_BOOLEAN = bool;
	super.setEnabled(bool);
    }
    
    @Override
    public boolean isEnabled() {
	return PROP_ENABLED_BOOLEAN;
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
    
}
