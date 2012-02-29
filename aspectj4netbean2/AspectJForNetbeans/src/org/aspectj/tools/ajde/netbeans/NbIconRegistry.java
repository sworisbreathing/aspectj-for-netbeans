/* NbIconRegistry - Decompiled by JODE
 * Visit http://jode.sourceforge.net
 */
package org.aspectj.tools.ajde.netbeans;

import org.aspectj.ajde.ui.AbstractIcon;
import org.aspectj.ajde.ui.swing.IconRegistry;
import org.aspectj.asm.IProgramElement;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.aspectj.asm.IProgramElement.Accessibility;
import org.aspectj.asm.IRelationship;


//import org.openide.TopManager;
/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1.1.1 $
 */
public class NbIconRegistry extends IconRegistry {

    public static final NbIconRegistry INSTANCE = new NbIconRegistry();
    //public static final IconRegistry INSTANCE = new IconRegistry();
    protected static final String RESOURCE_PATH = "/org/aspectj/tools/ajde/netbeans/resources/";
    protected final Icon ADVICE = makeIcon("structure/ajdt/advice.gif");
    protected final Icon ASPECT = makeIcon("structure/ajdt/aspect.gif");
    protected final Icon ASPECT_PAC = makeIcon("structure/ajdt/aspectPackage.png");
    protected final Icon ASPECT_PRI = makeIcon("structure/ajdt/aspectPrivate.png");
    protected final Icon ASPECT_PRO = makeIcon("structure/ajdt/aspectProtected.png");
    protected final Icon CLASS = makeIcon("netbeans/class.png");
    protected final Icon CODE = makeIcon("structure/ajdt/code.gif");
    //protected final Icon RELATION_REFERENCE_FORWARD = makeIcon("structure/referenceForward.gif");
    protected final Icon INITIALIZER = makeIcon("netbeans/initializer.png");
    protected final Icon CONSTRUCTOR = makeIcon("netbeans/constructorPublic.png");
    protected final Icon CONSTRUCTOR_PUBLIC = makeIcon("netbeans/constructorPublic.png");
    protected final Icon INTRO_CONSTRUCTOR_PUBLIC = makeIcon("netbeans/introConstructorPublic.png");
    protected final Icon CONSTRUCTOR_PRIVATE = makeIcon("netbeans/constructorPrivate.png");
    protected final Icon INTRO_CONSTRUCTOR_PRIVATE = makeIcon("netbeans/introConstructorPrivate.png");
    protected final Icon CONSTRUCTOR_PROTECTED = makeIcon("netbeans/constructorProtected.png");
    protected final Icon INTRO_CONSTRUCTOR_PROTECTED = makeIcon("netbeans/introConstructorProtected.png");
    protected final Icon CONSTRUCTOR_PACKAGE = makeIcon("netbeans/constructorPackage.png");
    protected final Icon INTRO_CONSTRUCTOR_PACKAGE = makeIcon("netbeans/introConstructorPackage.png");
    protected final Icon DECLARE_ERROR = makeIcon("structure/ajdt/dec_error.gif");
    protected final Icon IMPORTS = makeIcon("structure/ajdt/imports.png");
    protected final Icon DECLARE_PARENTS = makeIcon(
            "structure/ajdt/dec_parents.gif");
    protected final Icon DECLARE_SOFT = makeIcon("structure/ajdt/dec_soft.gif");
    protected final Icon DECLARE_WARNING = makeIcon(
            "structure/ajdt/dec_warning.gif");
    protected final Icon NO_JAVA = makeIcon(
            "actions/no-java2.png");
    protected final Icon FIELD = makeIcon("netbeans/fieldPublic.png");
    protected final Icon FIELD_PACKAGE = makeIcon("netbeans/fieldPackage.png");
    protected final Icon INTRO_FIELD_PACKAGE = makeIcon("netbeans/introFieldPackage.png");
    protected final Icon FIELD_PRIVATE = makeIcon("netbeans/fieldPrivate.png");
    protected final Icon INTRO_FIELD_PRIVATE = makeIcon("netbeans/introFieldPrivate.png");
    protected final Icon FIELD_PROTECTED = makeIcon("netbeans/fieldProtected.png");
    protected final Icon INTRO_FIELD_PROTECTED = makeIcon("netbeans/introFieldProtected.png");
    protected final Icon FIELD_PUBLIC = makeIcon("netbeans/fieldPublic.png");
    protected final Icon INTRO_FIELD_PUBLIC = makeIcon("netbeans/introFieldPublic.png");
    protected final Icon FIELD_ST_PACKAGE = makeIcon("netbeans/fieldStaticPackage.png");
    protected final Icon INTRO_FIELD_ST_PACKAGE = makeIcon("netbeans/introFieldStaticPackage.png");
    protected final Icon FIELD_ST_PRIVATE = makeIcon("netbeans/fieldStaticPrivate.png");
    protected final Icon INTRO_FIELD_ST_PRIVATE = makeIcon("netbeans/introFieldStaticPrivate.png");
    protected final Icon FIELD_ST_PROTECTED = makeIcon("netbeans/fieldStaticProtected.png");
    protected final Icon INTRO_FIELD_ST_PROTECTED = makeIcon("netbeans/introFieldStaticProtected.png");
    protected final Icon FIELD_ST_PUBLIC = makeIcon("netbeans/fieldStaticPublic.png");
    protected final Icon INTRO_FIELD_ST_PUBLIC = makeIcon("netbeans/introFieldStaticPublic.png");
    protected final Icon FILE = makeIcon("structure/file.gif");
    protected final Icon FILE_JAVA = makeIcon("netbeans/class_source.gif");
    protected final Icon FILE_LST = makeIcon("structure/file-lst.gif");
    protected final Icon INTERFACE = makeIcon("netbeans/interface.png");
    protected final Icon ENUM = makeIcon("netbeans/enum.png");
    protected final Icon ENUM_VALUE = makeIcon("netbeans/constant.png");
    protected final Icon METHOD = makeIcon("netbeans/methodPublic.png");
    protected final Icon METHOD_PACKAGE = makeIcon("netbeans/methodPackage.png");
    protected final Icon INTRO_METHOD_PACKAGE = makeIcon("netbeans/introMethodPackage.png");
    protected final Icon METHOD_PRIVATE = makeIcon("netbeans/methodPrivate.png");
    protected final Icon INTRO_METHOD_PRIVATE = makeIcon("netbeans/introMethodPrivate.png");
    protected final Icon METHOD_PROTECTED = makeIcon("netbeans/methodProtected.png");
    protected final Icon INTRO_METHOD_PROTECTED = makeIcon("netbeans/introMethodProtected.png");
    protected final Icon METHOD_PUBLIC = makeIcon("netbeans/methodPublic.png");
    protected final Icon INTRO_METHOD_PUBLIC = makeIcon("netbeans/introMethodPublic.png");
    protected final Icon METHOD_ST_PUBLIC = makeIcon("netbeans/methodStaticPublic.png");
    protected final Icon INTRO_METHOD_ST_PUBLIC = makeIcon("netbeans/introMethodStaticPublic.png");
    protected final Icon METHOD_ST_PACKAGE = makeIcon("netbeans/methodStaticPackage.png");
    protected final Icon INTRO_METHOD_ST_PACKAGE = makeIcon("netbeans/introMethodStaticPackage.png");
    protected final Icon METHOD_ST_PRIVATE = makeIcon("netbeans/methodStaticPrivate.png");
    protected final Icon INTRO_METHOD_ST_PRIVATE = makeIcon("netbeans/introMethodStaticPrivate.png");
    protected final Icon METHOD_ST_PROTECTED = makeIcon("netbeans/methodStaticProtected.png");
    protected final Icon INTRO_METHOD_ST_PROTECTED = makeIcon("netbeans/introMethodStaticProtected.png");
    protected final Icon INTRODUCTION = makeIcon("structure/introduction.gif");
    protected final Icon INTRODUCTION_METHOD = INTRO_METHOD_PUBLIC;
    protected final Icon INTRODUCTION_CONSTRUCTOR = INTRO_CONSTRUCTOR_PUBLIC;
    protected final Icon INTRODUCTION_FIELD = INTRO_FIELD_PUBLIC;
    protected final Icon PACKAGE_NB = makeIcon("netbeans/package.gif");
    protected final Icon PACKAGE_PUBLIC = PACKAGE_NB;
    protected final Icon PACKAGE_PRIVATE = makeIcon("netbeans/packagePrivate.gif");
    //protected final Icon PACKAGE = makeIcon("netbeans/package.gif");
    protected final Icon POINTCUT = makeIcon("structure/pointcut.gif");
    protected final Icon POINTCUT_PRIVATE = makeIcon("structure/ajdt/pointcutPrivate.png");
    protected final Icon POINTCUT_PROTECTED = makeIcon("structure/ajdt/pointcutProtected.png");
    protected final Icon POINTCUT_PACKAGE = makeIcon("structure/ajdt/pointcutPackage.png");
    protected final Icon POINTCUT_STATIC = makeIcon("structure/pointcutStatic.png");
    protected final Icon POINTCUT_STATIC_PRIVATE = makeIcon("structure/ajdt/pointcutStaticPrivate.png");
    protected final Icon POINTCUT_STATIC_PROTECTED = makeIcon("structure/ajdt/pointcutStaticProtected.png");
    protected final Icon POINTCUT_STATIC_PACKAGE = makeIcon("structure/ajdt/pointcutStaticPackage.png");
    protected final Icon PROJECT = makeIcon("structure/project.gif");
    //protected final Icon REF_FWD = makeIcon("structure/referenceForward.gif");
    protected final Icon REF_FWD = makeIcon("structure/ajdt/references.png");
    private final Icon ACCESSIBILITY_PACKAGE = makeIcon(
            "accessibility-package.gif");
    private final Icon ACCESSIBILITY_PRIVATE = makeIcon(
            "accessibility-private.gif");
    private final Icon ACCESSIBILITY_PRIVILEGED = makeIcon(
            "accessibility-privileged.gif");
    private final Icon ACCESSIBILITY_PROTECTED = makeIcon(
            "accessibility-protected.gif");
    private final Icon ACCESSIBILITY_PUBLIC = makeIcon(
            "netbeans/public.gif");
    private final Icon AJBROWSER = makeIcon("structure/advice.gif");
    private final Icon AJBROWSER_DISABLED = makeIcon(
            "actions/browserDisabled.gif");
    private final Icon AJBROWSER_ENABLED = makeIcon(
            "actions/browserEnabled.gif");
    private final Icon AJDE_SMALL = makeIcon("actions/ajdeSmall.gif");
    private final Icon BACK = makeIcon("actions/back.png");
    private final Icon BROWSER_OPTIONS = makeIcon("actions/browseroptions.gif");
    private final Icon BUILD = makeIcon("actions/build.gif");
    private final Icon CLOSE_CONFIG = makeIcon("actions/closeConfig.gif");
    private final Icon DEBUG = makeIcon("actions/debug.gif");
    private final Icon ERROR = makeIcon("structure/error.gif");
    private final Icon EXECUTE = makeIcon("netbeans/runProject.png");
    private final Icon RUNNABLE = makeIcon("netbeans/java-main-class.png");
    private final Icon FILTER = makeIcon("actions/filter.gif");
    private final Icon FORWARD = makeIcon("actions/forward.png");
    private final Icon GRANULARITY = makeIcon("actions/granularity.gif");
    private final Icon HIDE_ASSOCIATIONS = makeIcon(
            "actions/hideAssociations.gif");
    private final Icon HIDE_NON_AJ = makeIcon("actions/hideNonAJ.gif");
    private final Icon INFO = makeIcon("structure/info.png");
    private final Icon MERGE_STRUCTURE_VIEW = makeIcon(
            "actions/mergeStructureView.gif");
    private final Icon OPEN_CONFIG = makeIcon("actions/openConfig.gif");
    private final Icon ORDER = makeIcon("actions/sortAlpha.png");
    private final Icon POPUP = makeIcon("actions/popup.gif");
    private final Icon RELATIONS = makeIcon("actions/relations.gif");
    private final Icon SAVE = makeIcon("actions/save.gif");
    private final Icon SAVE_ALL = makeIcon("actions/saveAll.gif");
    private final Icon SEARCH = makeIcon("actions/search.gif");
    protected final Icon FILE_ASPECTJ = makeIcon("structure/file-aspectj.gif");
    private final Icon SPLIT_STRUCTURE_VIEW = makeIcon(
            "actions/splitStructureView.gif");
    private final Icon START_AJDE = makeIcon("actions/startAjde.gif");
    private final Icon STOP_AJDE = makeIcon("actions/stopAjde.gif");
    private final Icon STRUCTURE_VIEW = makeIcon("actions/structureView.gif");
    private final Icon WARNING = makeIcon("structure/warning.gif");
    private final Icon ZOOM_STRUCTURE_TO_FILE_MODE = makeIcon(
            "actions/zoomStructureToFileMode.gif");
    private final Icon ZOOM_STRUCTURE_TO_GLOBAL_MODE = makeIcon(
            "actions/zoomStructureToGlobalMode.gif");
    private final Icon DECLARE_ANNOTATION = makeIcon(
            "structure/ajdt/dec_annotation.gif");
    private final Icon DECLARE_PRECEDENCE = makeIcon(
            "structure/ajdt/dec_precedence.gif");
//    protected final Icon ENUM_VALUE = makeIcon("structure/field.gif"); // ??? should be enum value icon
//    protected final Icon ENUM = makeIcon("netbeans/enum.png");
    protected final Icon ANNOTATION = makeIcon("netbeans/annotation.png");
    protected final AbstractIcon RELATION_ADVICE_FORWARD = createIcon("structure/ajdt/advises.gif");
    protected final AbstractIcon RELATION_ADVICE_BACK = createIcon("structure/adviceBack.gif");
    protected final AbstractIcon RELATION_INHERITANCE_FORWARD = createIcon("structure/inheritanceForward.gif");
    protected final AbstractIcon RELATION_INHERITANCE_BACK = createIcon("structure/inheritanceBack.gif");
    protected final AbstractIcon RELATION_REFERENCE_FORWARD = createIcon("structure/ajdt/references.png");
    protected final AbstractIcon RELATION_REFERENCE_BACK = createIcon("structure/referenceBack.gif");
    protected final Icon AFTER = makeIcon("structure/ajdt/after_advice.gif");
    protected final Icon AROUND = makeIcon("structure/ajdt/around_advice.gif");
    protected final Icon BEFORE = makeIcon("structure/ajdt/before_advice.gif");
    
    
    protected final Icon AFTER_DYNAMIC = makeIcon("structure/ajdt/dynamic_after_advice.gif");
    protected final Icon AROUND_DYNAMIC = makeIcon("structure/ajdt/dynamic_around_advice.gif");
    protected final Icon BEFORE_DYNAMIC = makeIcon("structure/ajdt/dynamic_before_advice.gif");

    /**
     * DOCUMENT ME!
     *
     * @param kind DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override
    public AbstractIcon getIcon(IRelationship.Kind relationship) {
        //System.out.println("want to have relationship kind: "+relationship);
        if (relationship == IRelationship.Kind.ADVICE) {
            return RELATION_ADVICE_FORWARD;
        } else if (relationship == IRelationship.Kind.DECLARE) {
            return RELATION_ADVICE_FORWARD;
        //		} else if (relationship == IRelationship.Kind.INHERITANCE) {
        //			return RELATION_INHERITANCE_FORWARD;
        } else {
            return RELATION_REFERENCE_FORWARD;
        }
    }

    @Override
    public AbstractIcon getIcon(IProgramElement.Kind kind) {
        Icon icon = getStructureSwingIcon(kind);

        if (icon == null) {
            return super.getIcon(kind);
        }

        return new AbstractIcon(icon);
    }

    /**
     * DOCUMENT ME!
     *
     * @param kind DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override
    public Icon getStructureSwingIcon(IProgramElement.Kind kind) {
        return getStructureSwingIcon(kind, IProgramElement.Accessibility.PUBLIC);
    }

    @Override
    public Icon getStructureSwingIcon(IProgramElement.Kind kind,
            IProgramElement.Accessibility accessibility) {
        return getStructureSwingIcon(kind, accessibility, new ArrayList());
    }

    /**
     * DOCUMENT ME!
     *
     * @param kind DOCUMENT ME!
     * @param accessibility DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Icon getStructureSwingIcon(IProgramElement.Kind kind,
            IProgramElement.Accessibility accessibility, List modifiers) {
//        System.out.println("want to have element kind "+kind);
//        System.out.println("want to have element accessibility "+accessibility);
        //IProgramElement.MODIFIERS.STATIC
        if (kind == IProgramElement.Kind.PROJECT) {
            return PROJECT;
        } else if (kind == IProgramElement.Kind.PACKAGE) {
            if (accessibility == IProgramElement.Accessibility.PUBLIC) {
                return PACKAGE_PUBLIC;
            } else if (accessibility == IProgramElement.Accessibility.PRIVATE) {
                return PACKAGE_PRIVATE;
            }
            return PACKAGE_NB;
        } else if (kind == IProgramElement.Kind.FILE) {
            return FILE;
        } else if (kind == IProgramElement.Kind.FILE_JAVA) {
            return FILE_JAVA;
        } else if (kind == IProgramElement.Kind.FILE_ASPECTJ) {



            return FILE_ASPECTJ;
        } else if (kind == IProgramElement.Kind.FILE_LST) {
            return FILE_LST;
        } else if (kind == IProgramElement.Kind.CLASS) {
            return CLASS;
        } else if (kind == IProgramElement.Kind.INTERFACE) {
            return INTERFACE;
        } else if (kind == IProgramElement.Kind.ASPECT) {
            if (accessibility.equals(IProgramElement.Accessibility.PUBLIC)) {
                return ASPECT;
            } else if (accessibility.equals(IProgramElement.Accessibility.PACKAGE)) {
                return ASPECT_PAC;
            } else if (accessibility.equals(IProgramElement.Accessibility.PRIVATE)) {
                return ASPECT_PRI;
            } else if (accessibility.equals(IProgramElement.Accessibility.PROTECTED)) {
                return ASPECT_PRO;
            } else {
                return ASPECT;
            }
        } else if (kind == IProgramElement.Kind.INITIALIZER) {
            return INITIALIZER;
        } else if (kind == IProgramElement.Kind.INTER_TYPE_CONSTRUCTOR) {
            if (accessibility.equals(IProgramElement.Accessibility.PUBLIC)) {
                return INTRO_CONSTRUCTOR_PUBLIC;
            } else if (accessibility.equals(IProgramElement.Accessibility.PACKAGE)) {
                return INTRO_CONSTRUCTOR_PACKAGE;
            } else if (accessibility.equals(IProgramElement.Accessibility.PRIVATE)) {
                return INTRO_CONSTRUCTOR_PRIVATE;
            } else if (accessibility.equals(IProgramElement.Accessibility.PROTECTED)) {
                return INTRO_CONSTRUCTOR_PROTECTED;
            } else {
                return INTRODUCTION_CONSTRUCTOR;
            }
        } else if (kind == IProgramElement.Kind.INTER_TYPE_FIELD) {
            if (accessibility.equals(IProgramElement.Accessibility.PUBLIC)) {
                return INTRO_FIELD_PUBLIC;
            } else if (accessibility.equals(IProgramElement.Accessibility.PACKAGE)) {
                return INTRO_FIELD_PACKAGE;
            } else if (accessibility.equals(IProgramElement.Accessibility.PRIVATE)) {
                return INTRO_FIELD_PRIVATE;
            } else if (accessibility.equals(IProgramElement.Accessibility.PROTECTED)) {
                return INTRO_FIELD_PROTECTED;
            } else {
                return INTRODUCTION_FIELD;
            }
        } else if (kind == IProgramElement.Kind.INTER_TYPE_METHOD) {
            if (accessibility.equals(IProgramElement.Accessibility.PUBLIC)) {
                return INTRO_METHOD_PUBLIC;
            } else if (accessibility.equals(IProgramElement.Accessibility.PACKAGE)) {
                return INTRO_METHOD_PACKAGE;
            } else if (accessibility.equals(IProgramElement.Accessibility.PRIVATE)) {
                return INTRO_METHOD_PRIVATE;
            } else if (accessibility.equals(IProgramElement.Accessibility.PROTECTED)) {
                return INTRO_METHOD_PROTECTED;
            } else {
                return INTRODUCTION_METHOD;
            }
        } else if (kind == IProgramElement.Kind.CONSTRUCTOR) {
            if (accessibility == IProgramElement.Accessibility.PUBLIC) {
                return CONSTRUCTOR_PUBLIC;
            } else if (accessibility == IProgramElement.Accessibility.PRIVATE) {
                return CONSTRUCTOR_PRIVATE;
            } else if (accessibility == IProgramElement.Accessibility.PACKAGE) {
                return CONSTRUCTOR_PACKAGE;
            } else if (accessibility == IProgramElement.Accessibility.PROTECTED) {
                return CONSTRUCTOR_PROTECTED;
            }
            return CONSTRUCTOR;
        } else if (kind == IProgramElement.Kind.METHOD) {
            if (accessibility == IProgramElement.Accessibility.PUBLIC) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return METHOD_ST_PUBLIC;
                } else {
                    return METHOD_PUBLIC;
                }
            } else if (accessibility == IProgramElement.Accessibility.PRIVATE) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return METHOD_ST_PRIVATE;
                } else {
                    return METHOD_PRIVATE;
                }
            } else if (accessibility == IProgramElement.Accessibility.PACKAGE) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return METHOD_ST_PACKAGE;
                } else {
                    return METHOD_PACKAGE;
                }
            } else if (accessibility == IProgramElement.Accessibility.PROTECTED) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return METHOD_ST_PROTECTED;
                } else {
                    return METHOD_PROTECTED;
                }
            }
            return METHOD;


        } else if (kind == IProgramElement.Kind.FIELD) {
            if (accessibility == IProgramElement.Accessibility.PUBLIC) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return FIELD_ST_PUBLIC;
                } else {
                    return FIELD_PUBLIC;
                }
            } else if (accessibility == IProgramElement.Accessibility.PRIVATE) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return FIELD_ST_PRIVATE;
                } else {
                    return FIELD_PRIVATE;
                }
            } else if (accessibility == IProgramElement.Accessibility.PACKAGE) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return FIELD_ST_PACKAGE;
                } else {
                    return FIELD_PACKAGE;
                }
            } else if (accessibility == IProgramElement.Accessibility.PROTECTED) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return FIELD_ST_PROTECTED;
                } else {
                    return FIELD_PROTECTED;
                }
            }
            return FIELD;
        } else if (kind == IProgramElement.Kind.ENUM_VALUE) {
            return ENUM_VALUE;
        } else if (kind == IProgramElement.Kind.POINTCUT) {
            if (accessibility.equals(IProgramElement.Accessibility.PUBLIC)) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return POINTCUT_STATIC;
                } else {
                    return POINTCUT;
                }
            } else if (accessibility.equals(IProgramElement.Accessibility.PACKAGE)) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return POINTCUT_STATIC_PACKAGE;
                } else {
                    return POINTCUT_PACKAGE;
                }
            } else if (accessibility.equals(IProgramElement.Accessibility.PRIVATE)) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return POINTCUT_STATIC_PRIVATE;
                } else {
                    return POINTCUT_PRIVATE;
                }
            } else if (accessibility.equals(IProgramElement.Accessibility.PROTECTED)) {
                if (modifiers.contains(IProgramElement.Modifiers.STATIC)) {
                    return POINTCUT_STATIC_PROTECTED;
                } else {
                    return POINTCUT_PROTECTED;
                }
            } else {
                return POINTCUT;
            }
        } else if (kind == IProgramElement.Kind.ADVICE) {
            return ADVICE;
        } else if (kind == IProgramElement.Kind.DECLARE_PARENTS) {
            return DECLARE_PARENTS;
        } else if (kind == IProgramElement.Kind.DECLARE_WARNING) {
            return DECLARE_WARNING;
        } else if (kind == IProgramElement.Kind.DECLARE_ERROR) {
            return DECLARE_ERROR;
        } else if (kind == IProgramElement.Kind.DECLARE_SOFT) {
            return DECLARE_SOFT;
        } else if (kind == IProgramElement.Kind.DECLARE_PRECEDENCE) {
            return DECLARE_PRECEDENCE;
        } else if (kind == IProgramElement.Kind.CODE) {
            return CODE;
        } else if (kind == IProgramElement.Kind.ERROR) {
            return ERROR;
        //		} else if (kind == IProgramElement.Kind.IMPORT_REFERENCE) {
        //			return RELATION_REFERENCE_FORWARD;
        } else if (kind == IProgramElement.Kind.ANNOTATION) {
            return ANNOTATION;
        } else if (kind == IProgramElement.Kind.DECLARE_ANNOTATION_AT_CONSTRUCTOR) {
            return DECLARE_ANNOTATION;
        } else if (kind == IProgramElement.Kind.DECLARE_ANNOTATION_AT_FIELD) {
            return DECLARE_ANNOTATION;
        } else if (kind == IProgramElement.Kind.DECLARE_ANNOTATION_AT_METHOD) {
            return DECLARE_ANNOTATION;
        } else if (kind == IProgramElement.Kind.DECLARE_ANNOTATION_AT_TYPE) {
            return DECLARE_ANNOTATION;
        } else if (kind == IProgramElement.Kind.ENUM) {
            return ENUM;
        } else if (kind == IProgramElement.Kind.IMPORT_REFERENCE) {
            return IMPORTS;
        } else {
//            System.err.println("NB AJDE Message: unresolved icon kind " + kind);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param path DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override
    protected AbstractIcon createIcon(String path) {
        //return super.createIcon(path);
        return new AbstractIcon(createNBIcon(path));
    }

    /**
     * DOCUMENT ME!
     *
     * @param string DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected Icon createNBIcon(String string) {
        //	URL url
        //	    = TopManager.getDefault().currentClassLoader().getResource(string);
        URL url = this.getClass().getResource(RESOURCE_PATH + string);

        //URL url = this.getClass().getResource("/org/aspectj/tools/ajde/netbeans/resources/accessibility-package.gif");
        ImageIcon imageicon;

        try {
            imageicon = new ImageIcon(url);
        } catch (Exception exception) {
            return null;
        }

        return imageicon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param path DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override
    protected Icon makeIcon(String path) {
        //	URL url = TopManager.getDefault().currentClassLoader()
        //		      .getResource(RESOURCE_PATH + string);
        //	ImageIcon imageicon;
        //	try {
        //	    imageicon = new ImageIcon(url);
        //	} catch (Exception exception) {
        //	    return null;
        //	}
        //	return imageicon;
        //URL url = this.getClass().getResource("/org/aspectj/tools/ajde/netbeans/resources/accessibility-package.gif");
        URL url = this.getClass().getResource(RESOURCE_PATH + path);
        //System.out.println(RESOURCE_PATH + path);

        ImageIcon imageicon;

        try {
            imageicon = new ImageIcon(url);
        } catch (Exception exception) {
            return null;
        }

        return imageicon;
    }

    @Override
    public Icon getAccessibilitySwingIcon(Accessibility arg0) {
        if (arg0.equals(Accessibility.PRIVATE)) {
            return ACCESSIBILITY_PRIVATE;
        } else {
            return super.getAccessibilitySwingIcon(arg0);
        }
    }

    public Icon getAdviceIcon(String adviceInfo) {
        return getAdviceIcon(adviceInfo, false);
    }

    public Icon getAdviceIcon(String adviceInfo, boolean dynamic) {
        if (adviceInfo.equals("before")) {
            if (dynamic) return BEFORE_DYNAMIC;
            else return BEFORE;
        } else if (adviceInfo.equals("after")) {
            if (dynamic) return AFTER_DYNAMIC;
            else return AFTER;
        } else if (adviceInfo.equals("around")) {
            if (dynamic) return AROUND_DYNAMIC;
            else return AROUND;
        } else {
            return null;
        }
    }

    @Override
    public Icon getOrderIcon() {
        return ORDER;
    }
    private final Icon ORDER_BY_SOURCE = makeIcon("actions/sortPosition.png");

    public Icon getOrderBySourceIcon() {
        return ORDER_BY_SOURCE;
    }

    public Icon getRunnableIcon() {
        return RUNNABLE;
    }

    @Override
    public Icon getBackIcon() {
        return BACK;
    }

    @Override
    public Icon getForwardIcon() {
        return FORWARD;
    }

    @Override
    public Icon getHideNonAJIcon() {
        return NO_JAVA;
    }

    public Icon getShowAdviceIcon() {
        return getStructureSwingIcon(IProgramElement.Kind.ADVICE);
    }

    public Icon getShowPointcutIcon() {
        return getStructureSwingIcon(IProgramElement.Kind.POINTCUT);

    }
    private final Icon SHOW_STATIC = makeIcon("actions/filterHideStatic.png");

    public Icon getShowStaticIcon() {
        return SHOW_STATIC;
    }
    private final Icon SHOW_NON_PUBLIC = makeIcon("actions/filterHideNonPublic.png");

    public Icon getShowNonPublicIcon() {
        return SHOW_NON_PUBLIC;
    }
    private final Icon SHOW_FIELDS = makeIcon("actions/filterHideFields.gif");

    public Icon getShowFieldsIcon() {
        return SHOW_FIELDS;
    }
    private final Icon SHOW_METHODS = makeIcon("actions/showMethod.png");

    public Icon getShowMethodsIcon() {
        return SHOW_METHODS;
    }

    @Override
    public Icon getInfoIcon() {
        return INFO;
    }
}
