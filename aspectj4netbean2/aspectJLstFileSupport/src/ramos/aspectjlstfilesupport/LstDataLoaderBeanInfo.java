package ramos.aspectjlstfilesupport;

import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.SimpleBeanInfo;
import org.openide.loaders.UniFileLoader;
import org.openide.util.Utilities;

public class LstDataLoaderBeanInfo extends SimpleBeanInfo {

    public @Override
    BeanInfo[] getAdditionalBeanInfo() {
        try {
            return new BeanInfo[]{Introspector.getBeanInfo(UniFileLoader.class)};
        } catch ( IntrospectionException e) {
            throw new AssertionError(e);
        }
    }

    public @Override
    Image getIcon( int type) {
        return super.getIcon(type); // TODO add a custom icon here: Utilities.loadImage(..., true)

    }
}
