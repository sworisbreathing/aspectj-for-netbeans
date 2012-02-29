package org.aspectj.tools.ajde.netbeans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class CopySourceFilesToClipboardAction extends CallableSystemAction {

    public void performAction() {
        ArrayList arraylist = new ArrayList();
        Project project = OpenProjects.getDefault().getMainProject();
        SourceGroup[] sourceGroups = ProjectUtils.getSources(project).getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
        //auch test src
        for (SourceGroup sourceGroup : sourceGroups) {
            arraylist.addAll(getSourceFilesInDir(FileUtil.toFile(sourceGroup.getRootFolder()).getAbsolutePath()));
        }
    }
    private List getSourceFilesInDir(String string) {
        //System.out.println("NbProjectProperties#getLstFilesInDir " + string);
        ArrayList arraylist = new ArrayList();
        File file = new File(string);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                arraylist.addAll(getSourceFilesInDir(files[i].getAbsolutePath()));
            } else if (files[i].getName().endsWith(".java") 
                    || files[i].getName().endsWith(".aj")) {
                arraylist.add(files[i].getAbsolutePath().replace('\\', '/'));
            }
        }
        return arraylist;
    }

    public String getName() {
        return NbBundle.getMessage(CopySourceFilesToClipboardAction.class, "CTL_CopySourceFilesToClipboardAction");
    }

    protected @Override
    void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    protected @Override
    boolean asynchronous() {
        return false;
    }
}
