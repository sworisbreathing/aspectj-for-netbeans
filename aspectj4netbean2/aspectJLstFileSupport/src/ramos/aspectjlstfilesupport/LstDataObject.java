package ramos.aspectjlstfilesupport;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.text.DataEditorSupport;

public class LstDataObject extends MultiDataObject {

    public LstDataObject(FileObject pf, LstDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        System.out.println("LstDataObject &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        CookieSet cookies = getCookieSet();
        cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
    }

    protected @Override
    Node createNodeDelegate() {
        return new LstDataNode(this, getLookup());
    }

    public @Override
    Lookup getLookup() {
        return getCookieSet().getLookup();
    }
}
