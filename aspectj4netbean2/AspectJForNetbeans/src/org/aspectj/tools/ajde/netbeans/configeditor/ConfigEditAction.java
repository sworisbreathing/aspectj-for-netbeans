/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aspectj.tools.ajde.netbeans.configeditor;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.aspectj.ajde.ui.InvalidResourceException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author Ramos
 */
public class ConfigEditAction extends NodeAction{

    @Override
    protected void performAction(Node[] arg0) {
        try {
            Set<FileObject> set = arg0[0].getLookup().lookup(DataObject.class).files();
            FileObject fo = set.iterator().next();
            FileUtil.toFile(fo).getAbsolutePath();
            JFrame f = new JFrame();
            NetBeansBuildConfigEditor bcfe = new NetBeansBuildConfigEditor();
            bcfe.openFile(FileUtil.toFile(fo).getAbsolutePath());
            NetBeansLstBuildConfigManager m = NetBeansLstBuildConfigManager.getDefault();
            f.add((JComponent) bcfe, BorderLayout.NORTH);
            AjcOptionsPanel p = new AjcOptionsPanel(m.getOptions());
            if (m.isSourceRoots()) ((JComponent) bcfe).setEnabled(false);
            f.add(p, BorderLayout.CENTER);
            f.pack();
            f.setVisible(true);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InvalidResourceException ex) {
            Exceptions.printStackTrace(ex);
        }
        
    }

    @Override
    protected boolean enable(Node[] arg0) {
        return true;
    }

    @Override
    public String getName() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return "Edit with Build Config Editor";
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }

}
