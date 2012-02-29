/* BuildConfigurationManager - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.aspectj.tools.ajde.netbeans;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.ajde.Ajde;
import org.aspectj.ajde.internal.LstBuildConfigManager;
//import org.eclipse.jdt.internal.core.JavaProject;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.ui.OpenProjects;
//import org.netbeans.core.ExLocalFileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;

public class BuildConfigurationManager extends LstBuildConfigManager
{
    public String getDefaultConfigFile() {
//        System.out.println("BuildConfigurationManager#getDefaultConfigFile");
	List list
	    = Ajde.getDefault().getProjectProperties().getProjectSourceFiles();
	String string
	    = Ajde.getDefault().getProjectProperties().getProjectName();
	String string_0_
	    = (Ajde.getDefault().getProjectProperties().getOutputPath() + '/'
	       + "default-config.lst");
	return string_0_;
    }
    
    public List getConfigFiles() {
//        System.out.println("BuildConfigurationManager#getConfigFiles");
	ArrayList arraylist = new ArrayList();
	//arraylist.add("<all project files>");
	Repository repository = null;
        Project project = OpenProjects.getDefault().getMainProject();
        if (project == null) {
                Ajde.getDefault().getErrorHandler().handleWarning("Please set a project as main project");
                return arraylist;
            }
        SourceGroup[] sourceGroups = ProjectUtils.getSources(project).getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
        for (SourceGroup sourceGroup : sourceGroups) {
            arraylist.addAll(getLstFilesInDir(FileUtil.toFile(sourceGroup.getRootFolder()).getAbsolutePath()));
        }
	if (arraylist.size() == 0)
	    Ajde.getDefault().getErrorHandler().handleWarning
		("Could not find any \".lst\" in the main project.");
	return arraylist;
    }
    
    private List getLstFilesInDir(String string) {
//        System.out.println("BuildConfigurationManager#getLstFilesInDir "+string);
	ArrayList arraylist = new ArrayList();
	File file = new File(string);
	File[] files = file.listFiles();
	for (int i = 0; i < files.length; i++) {
	    if (files[i].isDirectory())
		arraylist.addAll(getLstFilesInDir(files[i].getAbsolutePath()));
	    else if (files[i].getName().endsWith(".lst"))
		arraylist.add(files[i].getAbsolutePath().replace('\\', '/'));
	}
	return arraylist;
    }
    
    public String getCurrConfigFile() {
//        System.out.println("BuildConfigurationManager#getCurrConfigFile");
	if (currConfigFilePath == null)
	    return null;
	if (currConfigFilePath.equals("<all project files>"))
	    return getDefaultConfigFile();
	return currConfigFilePath;
    }
}
