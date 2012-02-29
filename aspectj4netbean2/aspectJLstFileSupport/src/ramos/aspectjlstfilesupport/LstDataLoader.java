package ramos.aspectjlstfilesupport;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

public class LstDataLoader extends UniFileLoader {

    public static final String REQUIRED_MIME = "text/x-lst";
    private static final long serialVersionUID = 1L;

    public LstDataLoader() {
        super("ramos.aspectjlstfilesupport.LstDataObject");
    }

    protected @Override
    String defaultDisplayName() {
        return NbBundle.getMessage(LstDataLoader.class, "LBL_Lst_loader_name");
    }

    protected @Override
    void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }

    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new LstDataObject(primaryFile, this);
    }

    protected @Override
    String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
}
