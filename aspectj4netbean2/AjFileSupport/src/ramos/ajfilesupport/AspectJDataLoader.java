package ramos.ajfilesupport;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

public class AspectJDataLoader extends UniFileLoader {

    public static final String REQUIRED_MIME = "text/x-aspectj";
    private static final long serialVersionUID = 1L;

    public AspectJDataLoader() {
        super("ramos.ajfilesupport.AspectJDataObject");
    }

    protected @Override
    String defaultDisplayName() {
        return NbBundle.getMessage(AspectJDataLoader.class, "LBL_AspectJ_loader_name");
    }

    protected @Override
    void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }

    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new AspectJDataObject(primaryFile, this);
    }

    protected @Override
    String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
}
