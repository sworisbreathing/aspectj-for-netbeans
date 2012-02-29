/* NbProjectProperties - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.aspectj.tools.ajde.netbeans;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import org.aspectj.ajde.Ajde;
import org.aspectj.ajde.OutputLocationManager;
import org.aspectj.ajde.ProjectPropertiesAdapter;
import org.aspectj.ajde.ui.UserPreferencesAdapter;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.java.queries.BinaryForSourceQuery;
import org.netbeans.api.java.queries.UnitTestForSourceQuery;
import org.netbeans.api.project.*;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/** TODO: handle code duplication */
public class NbProjectProperties implements ProjectPropertiesAdapter {

    private HashMap<Project, String> outputPathMap = new HashMap<Project, String>();
    private UserPreferencesAdapter preferencesAdapter = null;
    private static final String PATH_SEPARATOR = System.getProperty("path.separator");
    //private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private Project project;
    public NbProjectProperties(UserPreferencesAdapter userpreferencesadapter) {
        this.preferencesAdapter = userpreferencesadapter;

    }

    public String getDefaultBuildConfigFile() {
        //System.out.println("NbProjectProperties#getDefaultBuildConfigFile");
       //Logger.getLogger(this.getClass().getName()).info(PATH_SEPARATOR);
        List list = Ajde.getDefault().getProjectProperties().getProjectSourceFiles();
        String string = Ajde.getDefault().getProjectProperties().getProjectName();
        String string_0_ = (Ajde.getDefault().getProjectProperties().getOutputPath() + '/' + "default-config.lst");
        return string_0_;
    }

    public List getBuildConfigFiles() {
        //System.out.println("NbProjectProperties#getBuildConfigFiles");
        //arraylist.add("<all project files>");
        Project project = getProject();
        List arraylist = new ArrayList();
        if (project == null) {
            //Ajde.getDefault().getErrorHandler().handleWarning("Please set a project as main project");
            return arraylist;
        }
        
        arraylist = getBuildConfigFiles(project);
//        if (arraylist == null || arraylist.size() == 0) {
//            Ajde.getDefault().getErrorHandler().handleWarning("Could not find any \".lst\" in the main project.");
//        }
        return arraylist;
    }
    public List getBuildConfigFiles(Project project) {
        ArrayList arraylist = new ArrayList();
       SourceGroup[] sourceGroups = ProjectUtils.getSources(project).getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
        for (SourceGroup sourceGroup : sourceGroups) {
            arraylist.addAll(getLstFilesInDir(toFile(sourceGroup.getRootFolder()).getAbsolutePath()));
        }
        return arraylist;
    }
    

    public String getLastActiveBuildConfigFile() {
//        System.out.println("NbProjectProperties#getLastActiveBuildConfigFile");
        return null;
    }

    private List getLstFilesInDir(String string) {
        //System.out.println("NbProjectProperties#getLstFilesInDir " + string);
        ArrayList arraylist = new ArrayList();
        File file = new File(string);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                arraylist.addAll(getLstFilesInDir(files[i].getAbsolutePath()));
            } else if (files[i].getName().endsWith(".lst")) {
                arraylist.add(files[i].getAbsolutePath().replace('\\', '/'));
            }
        }
        return arraylist;
    }

    public boolean isGlobalMode() {
        //System.out.println("NbProjectProperties#isGlobalMode");
        return true;
    }

    public String getAjcWorkingDir() {
        //System.out.println("NbProjectProperties#getAjcWorkingDir");
        return getRootProjectDir();
    }

    public String getOutputPath() {
//        System.out.println("NbProjectProperties#getOutputPath");
        String outputPath = null;
        try {
            Project project = getProject();
            if (project == null) {
                Ajde.getDefault().getErrorHandler().handleWarning("Please set a project as main project");
                return outputPath;
            }
            if (outputPathMap.containsKey(project)) {
                return outputPathMap.get(project);
            }
            Sources sources = project.getLookup().lookup(Sources.class);
            //System.out.println("sources " + sources);
            if (sources == null) {
                Ajde.getDefault().getErrorHandler().handleWarning("Project contains no sources");
                return null;
            }
            SourceGroup[] sgroups = sources.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
            //System.out.println("Project roots:");
            //inspectArray(sgroups);
            if (sgroups.length == 0) {
                Ajde.getDefault().getErrorHandler().handleWarning("No project roots???");
                return null;
            }
//            else if (sgroups.length > 2) {
//                Ajde.getDefault().getErrorHandler().handleWarning("More than one project root");
//                return null;
//            }
            //TODO: look at this carefully
            for (SourceGroup sourceGroup : sgroups) {
                //URL url = sourceGroup.getRootFolder().getURL();
                FileObject sourceGroupRootFolder = sourceGroup.getRootFolder();
                URL[] units = UnitTestForSourceQuery.findUnitTests(sourceGroupRootFolder);
                if (units.length != 0) {//unit tests haben keine unit tests
                    //dann echter source root -> falsch

                    URL sourceGroupRootFolderURL = sourceGroupRootFolder.getURL();
//                    System.out.println("no unit tests for " + sourceGroupRootFolderURL);
                    URL[] outputsForSourceGroup = BinaryForSourceQuery.findBinaryRoots(sourceGroupRootFolderURL).getRoots();
//                    System.out.println("outputs: ");
                    //inspectArray(outputsForSourceGroup);
                    if (outputsForSourceGroup.length == 0) {
                        Ajde.getDefault().getErrorHandler().handleWarning("No output path???");
                        return null;
                    } else if (outputsForSourceGroup.length > 1) {
                        Ajde.getDefault().getErrorHandler().handleWarning("More than one output path");
                        return null;
                    }
                    //outputPath = outputsForSourceGroup[0].getPath();
                    outputPath = new File(outputsForSourceGroup[0].toURI()).getAbsolutePath();
                    outputPathMap.put(project, outputPath);
                //no break? or return?
                }
            }

        //String outputPath = this.getRootProjectDir() + FILE_SEPARATOR + "build" + FILE_SEPARATOR + "classes";

        //if (!System.getProperty("os.name").equalsIgnoreCase("linux")) outputPath = calculateOutputPath();
//      System.out.println("returning "+outputPath);
        //String outputPath = calculateOutputPath();

        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        } catch (FileStateInvalidException ex) {
            Exceptions.printStackTrace(ex);
        }
//        System.out.println("will return outputpath: " + outputPath);
        return outputPath;
    }

//    public String getOutputPath2() {
//        String outputPath = null;
////        System.out.println("NbProjectProperties#getOutputPath");
//        try {
//
//            Project project = OpenProjects.getDefault().getMainProject();
//            if (project == null) {
//                Ajde.getDefault().getErrorHandler().handleWarning("Please set a project as main project");
//                return outputPath;
//            }
//            FileObject sourceGroupRootFolder = this.getSourceFolder(project);
//            URL sourceGroupRootFolderURL = sourceGroupRootFolder.getURL();
////            System.out.println("no unit tests for " + sourceGroupRootFolderURL);
//            URL[] outputsForSourceGroup = BinaryForSourceQuery.findBinaryRoots(sourceGroupRootFolderURL).getRoots();
////            System.out.println("outputs: ");
//
//            if (outputsForSourceGroup.length == 0) {
//                Ajde.getDefault().getErrorHandler().handleWarning("No output path???");
//                return null;
//            } else if (outputsForSourceGroup.length > 1) {
//                Ajde.getDefault().getErrorHandler().handleWarning("More than one output path");
//                return null;
//            }
//
//            outputPath = new File(outputsForSourceGroup[0].toURI()).getAbsolutePath();
//        } catch (FileStateInvalidException ex) {
//            Exceptions.printStackTrace(ex);
//        } catch (URISyntaxException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//        return outputPath;
//    }
    public String getClassToExecute() {
        //System.out.println("NbProjectProperties#getClassToExecute");
        return null;
    }

    public String getExecutionArgs() {
        //System.out.println("NbProjectProperties#getExecutionArgs");
        return null;
    }

    public String getVmArgs() {
        //System.out.println("NbProjectProperties#getVmArgs");
        return null;
    }

    public String getProjectName() {
        //System.out.println("NbProjectProperties#getProjectName");
        Project project = getProject();
        if (project == null) {
            Ajde.getDefault().getErrorHandler().handleWarning("Please set a project as main project");
            return "";
        }
        ProjectInformation pi = ProjectUtils.getInformation(project);
        return pi.getDisplayName();
    }

    public String getRootProjectDir() {
        //System.out.println("NbProjectProperties#getRootProjectDir");
        Project _project = getProject();
        if (_project == null) {
            Ajde.getDefault().getErrorHandler().handleWarning("Please set a project as main project");
            return "";
        }
        return toFile(_project.getProjectDirectory()).getAbsolutePath();

    }

    //unused?
    public String getProjectSourcePath() {
        //System.out.println("NbProjectProperties#getProjectSourcePath");
        Project project = getProject();
        if (project == null) {
            Ajde.getDefault().getErrorHandler().handleWarning("Please set a project as main project");
            return "";
        }
        //         ClassPathProvider cpp = project.getLookup().lookup(ClassPathProvider.class);
        //         ClassPath cp = cpp.findClassPath(project.getProjectDirectory(),ClassPath.COMPILE);
        //
        FileObject classFO = project.getProjectDirectory();
        ClassPath cp = ClassPath.getClassPath(classFO, ClassPath.SOURCE);

        FileObject[] fos = cp.getRoots();

        StringBuilder cpBuffer = new StringBuilder();
        for (FileObject fo : fos) {
            File f = toFile(fo);
            if (f != null) {
                cpBuffer.append(f.getAbsolutePath());
                cpBuffer.append(PATH_SEPARATOR);
            }
        }
        String classpath = cpBuffer.toString();
        if (classpath.equals("")) {//jar?
            //List entries = (List) cp.entries();
            ClassPath.Entry entry = cp.entries().get(0);
            FileObject jarFO = FileUtil.getArchiveFile(entry.getRoot());
            File jar = toFile(jarFO);
            classpath = jar.getAbsolutePath();
        }
        //System.out.println("returning " + classpath);
        return classpath;
    }

    public List getProjectSourceFiles() {
        //System.out.println("NbProjectProperties#getProjectSourceFiles");

        ArrayList arraylist = new ArrayList();
        //      arraylist.add("/home/ramos/NetBeansProjects/TestAJ/src/testaj/Main.java");
//      arraylist.add("/home/ramos/NetBeansProjects/TestAJ/src/testaj/Test.aj");
        //	Enumeration enumeration
        //	    = Repository.getDefault().getFileSystems();
        //	while (enumeration.hasMoreElements()) {
        //	    FileSystem filesystem = (FileSystem) enumeration.nextElement();
        //	    if (filesystem instanceof ExLocalFileSystem) {
        //		ExLocalFileSystem exlocalfilesystem
        //		    = (ExLocalFileSystem) filesystem;
        //		File file
        //		    = (/*TYPE_ERROR*/ exlocalfilesystem).getRootDirectory();
        //		Vector vector = new Vector();
        //		Vector vector_1_ = new Vector();
        //		vector.add(file);
        //		Vector vector_2_ = new Vector();
        //		while (vector.size() > 0) {
        //		    File file_3_ = (File) vector.remove(0);
        //		    File[] files = file_3_.listFiles(new FileFilter() {
        //			public boolean accept(File file_5_) {
        //			    return (file_5_.isDirectory()
        //				    || file_5_.getName().endsWith(".java"));
        //			}
        //		    });
        //		    if (files != null) {
        //			for (int i = 0; i < files.length; i++) {
        //			    File file_6_ = files[i];
        //			    if (file_6_.isDirectory())
        //				vector.add(file_6_);
        //			    else
        //				arraylist.add(file_6_.getAbsolutePath());
        //			}
        //		    }
        //		}
        //	    }
        //	}
        return arraylist;
    }

    //TODO: classpath caching and prop listeners 
    public String getClasspath() {
        
        List<SourceGroup> sgroups = sourcesNoTests();
        //List<ClassPath> classpathElementList = new ArrayList<ClassPath>();
        Set<ClassPath> classpathElementList = new HashSet<ClassPath>();
        for (SourceGroup elem : sgroups) {
            ClassPath cp = ClassPath.getClassPath(elem.getRootFolder(), ClassPath.EXECUTE);
            if (cp != null) {
                classpathElementList.add(cp);
            }
        }
        StringBuilder cpBuffer = new StringBuilder();
        for (ClassPath cp : classpathElementList) {
           
            List<ClassPath.Entry> entries = cp.entries();
            if (entries != null) {
                for (ClassPath.Entry entry : entries) {
                    if (entry == null || entry.getRoot() == null) {
                        continue;
                    }
                    File file = toFile(entry.getRoot());
                    if (file == null) {
                        FileObject jarFO = FileUtil.getArchiveFile(entry.getRoot());
                        File jar = toFile(jarFO);
                        cpBuffer.append(jar.getAbsolutePath());
                        cpBuffer.append(PATH_SEPARATOR);
                    } else {
                        cpBuffer.append(file.getAbsolutePath());
                        cpBuffer.append(PATH_SEPARATOR);
                    }
                }
            }
        }
        
        String classpath = cpBuffer.toString() + getBootClasspath();
        //System.out.println("returning " + classpath);
        Logger.getLogger(this.getClass().getName()).info("classpath ("+getProject()+"): "+classpath);
        return classpath;
    }

    public String getBootClasspath() {
        //System.out.println("NbProjectProperties#getBootClasspath");
        return System.getProperty("sun.boot.class.path");
    }

    public Set getAspectPath() {
        //System.out.println("NbProjectProperties#getAspectPath");
        return null;
    }

    public Set getInJars() {
        //System.out.println("NbProjectProperties#getInJars");
        return null;
    }

    public String getOutJar() {
        //System.out.println("NbProjectProperties#getOutJar");
        //return this.getRootProjectDir()+FILE_SEPARATOR+"dist"+FILE_SEPARATOR+"test.jar";
        return null;
    }

    public Set getSourceRoots() {
        //System.out.println("NbProjectProperties#getSourceRoots");
//      
//      Set a = new TreeSet();
//      a.add("/home/ramos/NetBeansProjects/TestAJ/src");
//      
//      return a;
        return null;
    }

    public Map getSourcePathResources() {
//        System.out.println("NbProjectProperties#getSourcePathResources");
        Map map = new HashMap();
        try {
            List<FileObject> folders = getSourceFolders2();
            File[] srcBase = toFileArray(folders);
            // fo.getURL().toURI()

            /* Allow the user to override the testProjectPath by using sourceRoots */
//            File[] srcBase = new File[]{new File(folders.getURL().toURI())};
//		if (sourceRoots == null || sourceRoots.isEmpty()) {
//			srcBase = new File[] { new File(getProjectSourcePath()) };
//		}
//		else {
//			srcBase = new File[sourceRoots.size()];
//			sourceRoots.toArray(srcBase);
//		}
            for (int j = 0; j < srcBase.length; j++) {
                File[] fromResources = org.aspectj.util.FileUtil.listFiles(srcBase[j], new FileFilter() {

                    public boolean accept(File pathname) {
                        String name = pathname.getName().toLowerCase();
                        return !name.endsWith(".ajsym") && !name.endsWith("~") && !name.endsWith(".lst") && !name.endsWith(".class") && !name.endsWith(".java") && !name.endsWith(".aj");
                    }
                });
                for (int i = 0; i < fromResources.length; i++) {
                    String normPath = org.aspectj.util.FileUtil.normalizedPath(fromResources[i], srcBase[j]);
                    map.put(normPath, fromResources[i]);
                }
            }

        } catch (FileStateInvalidException ex) {
            Exceptions.printStackTrace(ex);
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
//        System.out.println("resource map: "+map);
        return map;
    }

    public Set getInpath() {
        //System.out.println("NbProjectProperties#getInpath");
        return null;
    }
    //   private List<String> getClassPathList(ClassPath cp){
////      Project project = OpenProjects.getDefault().getMainProject();
////      FileObject classFO = project.getProjectDirectory();
//      FileObject[] fos = cp.getRoots();
//      
//      List list = new ArrayList();
//      for (FileObject fo : fos) {
//         File f = toFile(fo);
//         if (f != null) {
//            list.add(f.getAbsolutePath());
//         }
//      }
//      return list;
//   }
    private Set<URL> getEntries(SourceGroup[] sgroups, String type) {
        //System.out.println("sgroups " + sgroups);
        List<ClassPath> classpaths = new ArrayList<ClassPath>();
        for (SourceGroup elem : sgroups) {
            ClassPath cp = ClassPath.getClassPath(elem.getRootFolder(), type);
            //ClassPath cpEx = ClassPath.getClassPath(elem.getRootFolder(), type);
            //System.out.println("cp " + cp);
            ////System.out.println("cp EXECUTE"+cpEx);
            if (cp != null) {
                classpaths.add(cp);
            }
        }

        Set<URL> set = new HashSet<URL>();
        for (ClassPath cp : classpaths) {
            List<ClassPath.Entry> entries = cp.entries();
            if (entries != null) {
                for (ClassPath.Entry entry : entries) {
                    if (entry == null || entry.getURL() == null) {
                        continue;
                    }
                    set.add(entry.getURL());

                }
            }
        }
        return set;
    }

    public OutputLocationManager getOutputLocationManager() {
        //System.out.println("NbProjectProperties#getOutputLocationManager");
        return null;
    }

    private List<FileObject> getSourceFolders(Project project)
            throws FileStateInvalidException, URISyntaxException {
        List<FileObject> folders = new ArrayList<FileObject>();
        FileObject sourceFolder = null;
        Sources sources = project.getLookup().lookup(Sources.class);
        //System.out.println("sources " + sources);
        if (sources == null) {
            Ajde.getDefault().getErrorHandler().handleWarning("Project contains no sources");
            return null;
        }
        SourceGroup[] sgroups = sources.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
        //System.out.println("Project roots:");
        //inspectArray(sgroups);
        if (sgroups.length == 0) {
            Ajde.getDefault().getErrorHandler().handleWarning("No project roots???");
            return null;
        }
//        else if (sgroups.length > 2) {
//            Ajde.getDefault().getErrorHandler().handleWarning("More than one project root");
//            return null;
//        }
        for (SourceGroup sourceGroup : sgroups) {
            //URL url = sourceGroup.getRootFolder().getURL();
            FileObject sourceGroupRootFolder = sourceGroup.getRootFolder();
            URL[] units = UnitTestForSourceQuery.findUnitTests(sourceGroupRootFolder);
            if (units.length != 0) {//unit tests haben keine unit tests
                //dann echter source root -> falsch
                sourceFolder = sourceGroupRootFolder;
                folders.add(sourceFolder);
//                    URL sourceGroupRootFolderURL = sourceGroupRootFolder.getURL();
//                    sourceFolderPath = new File(sourceGroupRootFolderURL.toURI()).getAbsolutePath();
            //no break? or return?
            }
        }
        //return sourceFolder;
        return folders;
    }
    private Map<FileObject, File> toFileMap = new HashMap<FileObject, File>();

    private File toFile(FileObject fileObject) {
        File file = toFileMap.get(fileObject);
        if (file == null) {
            file = FileUtil.toFile(fileObject);
            if (file != null) {
                toFileMap.put(fileObject, file);
            }
        }
        return file;
    }

    private File[] toFileArray(List<FileObject> folders) {
        File[] srcBase = new File[folders.size()];
        int i = 0;
        for (FileObject fileObject : folders) {
            //srcBase[i] = new File(fileObject.getURL().toURI());
            srcBase[i] = toFile(fileObject);
            i++;
        }
        return srcBase;
    }

    //could get also only test sources alone
    private List<SourceGroup> sourcesNoTests() {
        List<SourceGroup> ret = new ArrayList<SourceGroup>();
        Project _project = getProject();
        if (_project == null) {
            Ajde.getDefault().getErrorHandler().handleWarning("Please set a project as main project");
            return null;
        }
        Sources sources = _project.getLookup().lookup(Sources.class);
        SourceGroup[] sgroups = sources.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);

        if (sgroups.length == 0) {
            Ajde.getDefault().getErrorHandler().handleWarning("No project roots???");
            return null;
        }

        for (SourceGroup sourceGroup : sgroups) {
            //URL url = sourceGroup.getRootFolder().getURL();
            FileObject sourceGroupRootFolder = sourceGroup.getRootFolder();
            URL[] units = UnitTestForSourceQuery.findUnitTests(sourceGroupRootFolder);
            if (units.length != 0) {//unit tests haben keine unit tests
                //dann echter source root -> falsch
                ret.add(sourceGroup);
            }
        }
        return ret;
    }

    private List<FileObject> getSourceFolders2()
            throws FileStateInvalidException, URISyntaxException {
        List<SourceGroup> sgroups = sourcesNoTests();
        List<FileObject> folders = new ArrayList<FileObject>();
        for (SourceGroup sourceGroup : sgroups) {
           
            folders.add(sourceGroup.getRootFolder());
        }

        return folders;
    }

   public Project getProject() {
      if (project == null){
         return OpenProjects.getDefault().getMainProject();
      }
      else{
         return project;
      }
   }

   public void setProject(Project project) {
      this.project = project;
   }
}
