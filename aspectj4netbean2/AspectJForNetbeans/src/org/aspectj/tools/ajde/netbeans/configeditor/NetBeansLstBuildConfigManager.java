/* *******************************************************************
 * Copyright (c) 1999-2001 Xerox Corporation, 
 *               2002 Palo Alto Research Center, Incorporated (PARC).
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 *  
 * Contributors: 
 *     Xerox/PARC     initial implementation 
 * ******************************************************************/
package org.aspectj.tools.ajde.netbeans.configeditor;

import java.io.*;
import java.util.*;

import org.aspectj.ajde.*;
import org.aspectj.ajde.internal.LstBuildConfigFileParser;
import org.aspectj.ajde.ui.*;
import org.aspectj.bridge.*;
import org.aspectj.tools.ajde.netbeans.configeditor.AjcOptions.AjcOption;
import org.aspectj.util.ConfigParser;
import org.aspectj.util.FileUtil;

/**
 * @author	Mik Kersten
 */
public class NetBeansLstBuildConfigManager implements BuildConfigManager {

//	private List configFiles = new ArrayList();	
    private List listeners = new ArrayList();
    private NetBeansLstBuildConfigFileUpdater fileUpdater = new NetBeansLstBuildConfigFileUpdater();
    protected String currConfigFilePath = null;
    private AjcOption[] options = null;
    
    private static NetBeansLstBuildConfigManager INSTANCE = new NetBeansLstBuildConfigManager();
    public static NetBeansLstBuildConfigManager getDefault(){
        return INSTANCE;
    }
    private NetBeansLstBuildConfigManager(){
        
    }

    public AjcOption[] getOptions() {
        return options;
    }
    private static final FilenameFilter SOURCE_FILE_FILTER = new FilenameFilter() {

        public boolean accept(File dir, String name) {
            return FileUtil.hasSourceSuffix(name) || name.endsWith(".lst");
        }
    };
    private static final FileFilter DIR_FILTER = new FileFilter() {

        public boolean accept(File file) {
            return file.isDirectory();
        }
    };

    public BuildConfigModel buildModel(String configFilePath) {
        File configFile = new File(configFilePath);
        String rootPath = configFile.getParent();
        String configFileName = configFile.getName();
        BuildConfigModel model = new BuildConfigModel(configFilePath);
        List configFiles = new ArrayList();
        List importedFiles = new ArrayList();
        List potentialOptions = null;
        try {
            LstBuildConfigFileParser configParser = new LstBuildConfigFileParser(configFilePath);
            configParser.parseConfigFile(new File(configFilePath));
            configFiles = configParser.getFiles();
            importedFiles = configParser.getImportedFiles();
            potentialOptions = configParser.getProblemEntries();
        } catch (ConfigParser.ParseException pe) {
//			String filePath = "<unknown>";
//			if (pe.getFile() != null) filePath = pe.getFile().getAbsolutePath();
            IMessage message = new Message(
                    pe.getMessage(),
                    IMessage.ERROR,
                    pe,
                    new SourceLocation(pe.getFile(), pe.getLine(), 1));
            Ajde.getDefault().getTaskListManager().addSourcelineTask(message);
        }

        List relativePaths = relativizeFilePaths(configFiles, rootPath);
        BuildConfigNode root = new BuildConfigNode(configFileName, BuildConfigNode.Kind.FILE_LST, rootPath);
        buildDirTree(root, rootPath, importedFiles, configFileName);
        model.setRoot(root);
        addFilesToDirTree(model, relativePaths, potentialOptions);

        pruneEmptyDirs(root);
        sortModel((BuildConfigNode) model.getRoot(), ALPHABETICAL_COMPARATOR);
        //addImportedFilesToDirTree(model, importedFiles);

        addPotentialOptions(root, potentialOptions);
        return model;
    }

    public void initWithConfigFile(String restoredConfigFile) {
        this.currConfigFilePath = restoredConfigFile;
    }

    //RAMOS: stattdessen options parsen und interpretieren fuer checkbox -> GUI
    private void addPotentialOptions(BuildConfigNode root, List potentialOptions) {
//        for (Iterator it = potentialOptions.iterator(); it.hasNext();) {
//            root.addChild(new BuildConfigNode(
//                    it.next().toString(),
//                    BuildConfigNode.Kind.ERROR, null));
//        }
        options = AjcOptions.getDefault().parseOptions(potentialOptions);
        //if (AjcOptions.getDefault().isIsSourceRoots()) buil;
    }
    public boolean isSourceRoots(){
        return AjcOptions.getDefault().isIsSourceRoots();
    }

    public void writeModel(BuildConfigModel model) {
//		final List paths = new ArrayList();
//		StructureWalker walker = new StructureWalker() {
//		    protected void postProcess(StructureNode node) { 
//		    	BuildConfigNode configNode = (BuildConfigNode)node;
//		    	if (configNode.isActive() && configNode.isValidResource()) {
//		    		paths.add(configNode.getResourcePath());
//		    	}
//		    }		
//		};
//		model.getRoot().walk(walker);	

        List activeSourceFiles = model.getActiveNodes(BuildConfigNode.Kind.FILE_ASPECTJ);
        activeSourceFiles.addAll(model.getActiveNodes(BuildConfigNode.Kind.FILE_JAVA));
        List activeImportedFiles = model.getActiveNodes(BuildConfigNode.Kind.FILE_LST);
        //RAMOS: noch options mitgeben (addProblemEntries)
        fileUpdater.writeConfigFile(model.getSourceFile(), activeSourceFiles, activeImportedFiles,options);
    }

    public void writePaths(String configFilePath, List files) {
        fileUpdater.writeConfigFile(configFilePath, files);
    }

    public void addFilesToConfig(String configFilePath, List paths) {

    }

    public void removeFilesFromConfig(String configFilePath, List files) {

    }

    private List relativizeFilePaths(List configFiles, String rootPath) {
        List relativePathsList = new ArrayList();
        for (Iterator it = configFiles.iterator(); it.hasNext();) {
            File file = (File) it.next();
            relativePathsList.add(fileUpdater.relativizePath(file.getPath(), rootPath));
        }
        return relativePathsList;
    }

//	private String relativizePath(String path, String rootPath) {
//		path = path.replace('\\', '/');
//		rootPath = rootPath.replace('\\', '/');
//		int pathIndex  = path.indexOf(rootPath);
//		if (pathIndex > -1) {
//			return path.substring(pathIndex + rootPath.length() + 1);
//		} else {
//			return path;	
//		}		
//	}  
    private void buildDirTree(BuildConfigNode node, String rootPath, List importedFiles, String configFileName) {
        File[] dirs = new File(node.getResourcePath()).listFiles(DIR_FILTER);
        if (dirs == null) {
            return;
        }
        for (int i = 0; i < dirs.length; i++) {
            BuildConfigNode dir = new BuildConfigNode(dirs[i].getName(), BuildConfigNode.Kind.DIRECTORY, dirs[i].getPath());
            File[] files = dirs[i].listFiles(SOURCE_FILE_FILTER);
            for (int j = 0; j < files.length; j++) {
                if (files[j] != null) {// && !files[j].getName().endsWith(".lst")) {  
                    String filePath = fileUpdater.relativizePath(files[j].getPath(), rootPath);
                    BuildConfigNode.Kind kind = BuildConfigNode.Kind.FILE_JAVA;
                    if (!files[j].getName().endsWith(".lst")) {
                        //kind = BuildConfigNode.Kind.FILE_LST;	
                        BuildConfigNode file = new BuildConfigNode(files[j].getName(), kind, filePath);
                        file.setActive(false);
                        dir.addChild(file);
                    }
                }
            }
            node.addChild(dir);
//			boolean foundMatch = false;
            for (Iterator it = importedFiles.iterator(); it.hasNext();) {
                File importedFile = (File) it.next();
                if (importedFile.getParentFile().getAbsolutePath().equals(dirs[i].getAbsolutePath())) {
//					foundMatch = true;
                    BuildConfigNode importedFileNode = new BuildConfigNode(
                            importedFile.getName(),
                            BuildConfigNode.Kind.FILE_LST,
                            fileUpdater.relativizePath(importedFile.getPath(), rootPath));
                    importedFileNode.setActive(true);
                    //dir.getChildren().clear();
                    boolean found = false;
                    for (Iterator it2 = dir.getChildren().iterator(); it2.hasNext();) {
                        if (((BuildConfigNode) it2.next()).getName().equals(importedFile.getName())) {
                            found = true;
                        }
                    }
                    if (!found) {
                        dir.addChild(importedFileNode);
                    }
                }

            }
            //if (!foundMatch) 
            buildDirTree(dir, rootPath, importedFiles, configFileName);
        }

        if (node.getName().endsWith(".lst")) {
            File[] files = new File(rootPath).listFiles(SOURCE_FILE_FILTER);
            if (files == null) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i] != null && !files[i].getName().equals(configFileName)) {// && !files[i].getName().endsWith(".lst")) {
                    BuildConfigNode.Kind kind = BuildConfigNode.Kind.FILE_JAVA;
                    if (files[i].getName().endsWith(".lst")) {
                        kind = BuildConfigNode.Kind.FILE_LST;
                    }
                    BuildConfigNode file = new BuildConfigNode(files[i].getName(), kind, files[i].getName());
                    file.setActive(false);
                    node.addChild(file);
                }
            }
        }
    }

    private void addFilesToDirTree(BuildConfigModel model, List configFiles, List badEntries) {
        for (Iterator it = configFiles.iterator(); it.hasNext();) {
            String path = (String) it.next();
            if (path.startsWith("..")) {
                File file = new File(path);
                BuildConfigNode node = new BuildConfigNode(file.getName(), BuildConfigNode.Kind.FILE_JAVA, path);
                BuildConfigNode upPath = model.getNodeForPath(file.getParentFile().getPath());
                if (upPath == model.getRoot()) {
                    upPath = new BuildConfigNode(file.getParentFile().getPath(), BuildConfigNode.Kind.DIRECTORY, file.getParentFile().getAbsolutePath());
                    model.getRoot().addChild(upPath);
                }
                node.setActive(true);
                upPath.addChild(node);
            } else if (!(new File(path).isAbsolute())) {
//				String name = new File(path).getName();
                BuildConfigNode existingNode = model.getNodeForPath(path);
                existingNode.setActive(true);
            } else {
                badEntries.add("Use relative paths only, omitting: " + path);
            }
        }
    }

    private boolean pruneEmptyDirs(BuildConfigNode node) {
        List nodesToRemove = new ArrayList();
        for (Iterator it = node.getChildren().iterator(); it.hasNext();) {
            BuildConfigNode currNode = (BuildConfigNode) it.next();
            boolean hasValidChildren = pruneEmptyDirs(currNode);
            if (!currNode.isValidResource() && !hasValidChildren) {
                nodesToRemove.add(currNode);
            }
        }

        for (Iterator it = nodesToRemove.iterator(); it.hasNext();) {
            BuildConfigNode currNode = (BuildConfigNode) it.next();
            node.removeChild(currNode);
        }
        return node.getChildren().size() > 0;
    }

    public String getActiveConfigFile() {
        if (currConfigFilePath == null) {
            return null;
        }
        if (currConfigFilePath.equals(DEFAULT_CONFIG_LABEL)) {
            return Ajde.getDefault().getProjectProperties().getDefaultBuildConfigFile();// getDefaultConfigFile();
        } else {
            return currConfigFilePath;
        }
    }

    public void setActiveConfigFile(String currConfigFilePath) {
        if (currConfigFilePath.equals(DEFAULT_CONFIG_LABEL)) {
            this.currConfigFilePath = Ajde.getDefault().getProjectProperties().getDefaultBuildConfigFile();//getDefaultConfigFile();
        } else {
            this.currConfigFilePath = currConfigFilePath;
        }
        notifyConfigChanged();
    }

    public void addListener(BuildConfigListener configurationListener) {
        listeners.add(configurationListener);
    }

    public void removeListener(BuildConfigListener configurationListener) {
        listeners.remove(configurationListener);
    }

    private void notifyConfigChanged() {
        for (Iterator it = listeners.iterator(); it.hasNext();) {
            ((BuildConfigListener) it.next()).currConfigChanged(currConfigFilePath);
        }
    }

//    private void notifyConfigsListUpdated() {
//        for (Iterator it = listeners.iterator(); it.hasNext(); ) {
//            ((BuildConfigListener)it.next()).configsListUpdated(configFiles);
//        }
//    }
//    
    private void sortModel(BuildConfigNode node, Comparator comparator) {
        if (node == null || node.getChildren() == null) {
            return;
        }
        Collections.sort(node.getChildren(), comparator);
        for (Iterator it = node.getChildren().iterator(); it.hasNext();) {
            BuildConfigNode nextNode = (BuildConfigNode) it.next();
            if (nextNode != null) {
                sortModel(nextNode, comparator);
            }
        }
    }
    private static final Comparator ALPHABETICAL_COMPARATOR = new Comparator() {

        public int compare(Object o1, Object o2) {
            BuildConfigNode n1 = (BuildConfigNode) o1;
            BuildConfigNode n2 = (BuildConfigNode) o2;
            return n1.getName().compareTo(n2.getName());
        }
    };
}


