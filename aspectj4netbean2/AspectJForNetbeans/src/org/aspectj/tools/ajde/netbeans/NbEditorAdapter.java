package org.aspectj.tools.ajde.netbeans;

import org.aspectj.tools.ajde.netbeans.annotation.Annotator;
import org.aspectj.ajde.Ajde;
import org.aspectj.ajde.EditorAdapter;
import org.aspectj.bridge.ISourceLocation;

import org.openide.cookies.EditorCookie;
import org.openide.cookies.LineCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.Line;

import java.io.File;
import java.io.IOException;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.text.Document;

import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.modules.editor.NbEditorUtilities;


/**
 * @author $author$
 * @version $Revision: 1.1.1.1 $
 */
public class NbEditorAdapter implements EditorAdapter {
    private static NbEditorAdapter INSTANCE = new NbEditorAdapter();

    public static NbEditorAdapter getDefault() {
        return INSTANCE;
    }
    private JTree treeRef;

    public JTree getTreeRef() {
        return treeRef;
    }

    public void setTreeRef(JTree treeRef) {
        this.treeRef = treeRef;
    }

    NbEditorAdapter() {
        
    }

    
    public String getCurrFile() {
        //System.out.println("NbEditorAdapter#getCurrFile");
        String currfile = "<no file>";
        try {
            Document selectedDoc = EditorRegistry.lastFocusedComponent().getDocument();
            DataObject dataObject = NbEditorUtilities.getDataObject(selectedDoc);
            FileObject fo = dataObject.getPrimaryFile();
            File file = FileUtil.toFile(fo);
            currfile = file.getAbsolutePath();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
//        System.out.println("getCurrFile: "+currfile);
        return currfile;
    }

    public void showSourceLine(String filename, int line, boolean bool) {
        //System.out.println("NbEditorAdapter#showSourceLine " + filename + " " + line + " " + bool);
        if (NbManager.INSTANCE.isIgnoreGotoLines()) return ;
        for (int j = 0; j < treeRef.getRowCount(); j++) {
                    treeRef.expandRow(j);
        }
        if (line <= 1 || filename.equals("")) {
            return;
        }
        try {
            filename = filename.replace('\\', '/');//Zuweisung an parameter?
            FileObject fileobject = FileUtil.toFileObject(new File(filename));
            DataObject dataobject = DataObject.find(fileobject);
            EditorCookie editorCookie = dataobject.getCookie(EditorCookie.class);
            editorCookie.open();
            Annotator.getDefault().annotateFile(filename);
            int lineNumber = line - 1;
            LineCookie lc = dataobject.getCookie(LineCookie.class);
            Line l = lc.getLineSet().getOriginal(lineNumber);
            l.show(Line.SHOW_GOTO);
        } catch (Exception exception) {
            Ajde.getDefault().getErrorHandler().handleError("Could not seek to line in file: " + filename +
               ".", exception);
        }
    }

    public void pasteToCaretPos(String string) {
        //System.out.println("NbEditorAdapter#pasteToCaretPos " + string);
    }

    public void saveContents() throws IOException {
        //System.out.println("NbEditorAdapter#saveContents");
    }

    public void showSourceLine(int i, boolean bool) {
        //System.out.println("showSourceLine: " + i + " " + bool);
        showSourceLine(getCurrFile(), i, true);
    }

    public void showSourceLine(ISourceLocation isourcelocation, boolean bool) {
        //System.out.println("showSourceLine: " + isourcelocation + " " + bool);
        showSourceLine(isourcelocation.getSourceFile().getAbsolutePath(),
           isourcelocation.getLine(), bool);
    }

    public void showSourcelineAnnotation(String string, int i, List list) {
        //System.out.println("NbEditorAdapter#showSourcelineAnnotation " + string + " " + list);
    }

    //            JFrame f = new JFrame();
            //            f.add(AjdeUIManager.getDefault().getFileStructurePanel());
            //            f.pack();
            //            f.setVisible(true);
            //            JFrame f2 = new JFrame();
            //            f2.add( AjdeUIManager.getDefault().getBuildConfigEditor());
            //            f2.pack();
            //            f2.setVisible(true);

    public void showSourcelineAnnotation(String string, int i,
       JPopupMenu jpopupmenu) {
        //System.out.println("NbEditorAdapter#showSourcelineAnnotation " + string);
    }

    public String getCurrSourceFilePath() {
        //System.out.println("NbEditorAdapter#getCurrSourceFilePath");
        return "";
    }

    public JPanel getPanel() {
        //System.out.println("NbEditorAdapter#getPanel");
        return null;
    }
}
