/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aspectj.tools.ajde.netbeans.annotation;

import java.util.List;
import org.aspectj.asm.AsmManager;
import org.aspectj.asm.IProgramElement;
import org.aspectj.asm.internal.Relationship;
import org.openide.text.Annotation;

/**
 *
 * @author ramos
 */
public class AspectJAnnotation extends Annotation
        implements AspectjAnnotationConstants, AspectJNames {

    //tooltip dynamisch
    private String text = "empty";
    private List<Relationship> relationships;
    private IProgramElement sourceElement;
    //annotationType variabel,bestimmt glyph
    private String annotationType = ASPECTJ_ANNOTATION;

    public AspectJAnnotation(List<Relationship> relationships,
            IProgramElement source) {
        this.relationships = relationships;
        this.sourceElement = source;
        //System.out.println("AspectJAnnotation: source: " + source);
        text = constructTooltip(relationships);
        //System.out.println("Annotation tooltip: \n" + text);

        if (relationships.size() == 1) {
            Relationship relationship = relationships.get(0);
            List<String> targets = (List<String>) relationship.getTargets();
            final String relationshipName = relationship.getName();

            if (relationshipName.equals(ADVISES)) {//String constants?
                //InspectionUtilities.inspectProgrammElement(source);
                handleAdvises(source, relationship.hasRuntimeTest());
            } else if (relationshipName.equals(ADVISED_BY)) {
                handleAdvisedBy(targets, relationship.hasRuntimeTest());
            } else if (relationshipName.equals(ASPECT_DECLARATIONS) || relationshipName.equals(ANNOTATED_BY)) {
                annotationType = ITD_TARGET;
            } else if (relationshipName.equals(MATCHES_DECLARE)) /*error,warning?*/ {
                if (targets.size() == 1) {
                    //inspectProgrammElement(source);
                    IProgramElement pe = toProgrammElement(targets.get(0));
                    
                    if (pe.getKind().equals(IProgramElement.Kind.DECLARE_WARNING)) {
                        annotationType = DECLARE_WARNING_TARGET;
                    } else if (pe.getKind().equals(IProgramElement.Kind.DECLARE_ERROR)) {
                        annotationType = DECLARE_ERROR_TARGET;
                    } else {
                        //annotation style
                        //only one target -> find out which kind of error: warning or error
                        //through compiler output
                        //Ajde.getDefault().getTaskListManager()?
                        annotationType = ITD_TARGET;
                    }
                } else {
                    annotationType = ITD_TARGET;
                }
            } else if (relationshipName.equals(DECLARED_ON) || relationshipName.equals(ANNOTATES) || relationshipName.equals(MATCHED_BY)) /*error,warning?*/ {
                //System.out.println("SOURCE:");
                //inspectProgrammElement(source);
                //System.out.println("TARGET:");
                //inspectProgrammElement(toProgrammElement(targets.get(0)));
                //inspectProgrammElement(target);
                //eigentlich nur wenn "matched by"
                IProgramElement pe = source;
                if (pe.getKind().equals(IProgramElement.Kind.DECLARE_WARNING)) {
                    annotationType = DECLARE_WARNING_SOURCE;
                } else if (pe.getKind().equals(IProgramElement.Kind.DECLARE_ERROR)) {
                    annotationType = DECLARE_ERROR_SOURCE;
                } else {
                    annotationType = ITD_SOURCE;
                }
            }
        } else {
            handleMaybeSourceAndTarget(relationships);
        //wenn einmal affects und einmal nicht
        //else "org-aspectj-tools-ajde-netbeans-aspectjannotation"; //implizit
        }
    }

    private void constructAnnotationText(String handle,
            StringBuilder annotationText) {
        IProgramElement targetElement = toProgrammElement(handle);
        if (targetElement == null) {
            return;
        }
        annotationText.append("   ").append(targetElement.toLinkLabelString(false)).append("\n");
    //annotationText.append(" in ").append(targetElement.getParent()).append("\n");
    }

    private void constructAnnotationText2(String handle,
            StringBuilder annotationText) {
        IProgramElement targetElement = toProgrammElement(handle);
        if (targetElement == null) {
            return;
        }
        annotationText.append("&nbsp;&nbsp;&nbsp;").append(htmlize(targetElement.toLinkLabelString(false))).append("<br>");
    //annotationText.append(" in ").append(targetElement.getParent()).append("\n");
    }

    private String htmlize(String arg) {
        return arg.replace("<", "&lt;").replace(">", "&gt;");
//        String two = one.replace(">", "&gt;");
//        return two;
    }

    public String getAnnotationType() {
        return annotationType;
    }

    public String getShortDescription() {
        // Localize this with NbBundle:
        return text;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    private String constructTooltip(List<Relationship> relationships) {
        StringBuilder annotationText = new StringBuilder();
        boolean first = true;
        for (Relationship relationship : relationships) {
            if (!first) {
                annotationText.append("\n");
            }
            first = false;
            //System.out.println("relationship kind " + relationship.getKind());
            annotationText.append(relationship.getName()).append(":\n");
            //System.out.println("relationship name " + relationship.getName());
            List<String> targets = (List<String>) relationship.getTargets();
            for (String handle : targets) {
                constructAnnotationText(handle, annotationText);
            }
        }
//        System.out.println("tooltip: " + annotationText);
        return annotationText.toString();
    }

    private String constructTooltip2(List<Relationship> relationships) {
        StringBuilder annotationText = new StringBuilder("<html>");
        boolean first = true;
        for (Relationship relationship : relationships) {
            if (!first) {
                annotationText.append("<br>");
            }
            first = false;
            //System.out.println("relationship kind " + relationship.getKind());
            annotationText.append("<b>" + relationship.getName() + "</b>").append(":<br>");
            //System.out.println("relationship name " + relationship.getName());
            List<String> targets = (List<String>) relationship.getTargets();
            for (String handle : targets) {
                constructAnnotationText2(handle, annotationText);
            }
        }
        annotationText.append("</html>");
//        System.out.println("tooltip: " + annotationText);
        return annotationText.toString();
    }

    private void handleAdvisedBy(List<String> targets, boolean dynamic) {
        if (targets != null && targets.size() == 1) {
            if (dynamic) {
                annotationType = DYNAMIC_ADVICE_TARGET;
            } else {
                annotationType = ADVICE_TARGET;
            }

            //System.out.println("one adviser");
            //System.out.println("handle: " + targets.get(0));
            IProgramElement target = toProgrammElement(targets.get(0));
            //System.out.println("target: " + target);
            //inspectProgrammElement(target);

            //                    if (target == null) {
//                        annotationType = ADVICE_TARGET;
//                        return;
//                    }
            boolean isAnnotationStyle = true;
            if (target.getName().equals(AROUND)) {
                isAnnotationStyle = false;
                //System.out.println("advised by around");
                if (dynamic) {
                    annotationType = DYNAMIC_ADVISED_BY_AROUND;
                } else {
                    annotationType = ADVISED_BY_AROUND;
                }
            } else if (target.getName().equals(BEFORE)) {
                isAnnotationStyle = false;
                //System.out.println("advised by before");
                if (dynamic) {
                    annotationType = DYNAMIC_ADVISED_BY_BEFORE;
                } else {
                    annotationType = ADVISED_BY_BEFORE;
                }
            } else if (target.getName().equals(AFTER)) {
                isAnnotationStyle = false;
                //System.out.println("advised by after");
                if (dynamic) {
                    annotationType = DYNAMIC_ADVISED_BY_AFTER;
                } else {
                    annotationType = ADVISED_BY_AFTER;
                }
            } else if (isAnnotationStyle) {
                //getExtraInfo
                IProgramElement.ExtraInformation xtra = target.getExtraInfo();
                if (xtra == null) {
                    return;
                }
                String adviceInfo = xtra.getExtraAdviceInformation();
                if (adviceInfo.equals(AFTER)) {
                    //System.out.println("advised by after");
                    if (dynamic) {
                        annotationType = DYNAMIC_ADVISED_BY_AFTER;
                    } else {
                        annotationType = ADVISED_BY_AFTER;
                    }
                } else if (adviceInfo.equals(BEFORE)) {
                    //System.out.println("advised by before");
                    if (dynamic) {
                        annotationType = DYNAMIC_ADVISED_BY_BEFORE;
                    } else {
                        annotationType = ADVISED_BY_BEFORE;
                    }
                } else if (adviceInfo.equals(AROUND)) {
                    //System.out.println("advised by around");
                    if (dynamic) {
                        annotationType = DYNAMIC_ADVISED_BY_AROUND;
                    } else {
                        annotationType = ADVISED_BY_AROUND;
                    }
                }
            }
        } else {
            //System.out.println("advised by many advisers");
            if (dynamic) {
                annotationType = DYNAMIC_ADVICE_TARGET;
            } else {
                annotationType = ADVICE_TARGET;
            }
        }
    }

    private void handleAdvises(IProgramElement source, boolean dynamic) {
        if (dynamic) {
//            System.out.println("DYNAMIC!!!: " + source);
        }
        //String constants?
        if (dynamic) {
            annotationType = DYNAMIC_ADVICE;
        } else {
            annotationType = ADVICE;
        }
        boolean isAnnotationStyle = true;
        //inspectProgrammElement(source);
        if (source.getName().equals(AROUND)) {
            isAnnotationStyle = false;
            //System.out.println("around adviser");
            if (dynamic) {
                annotationType = DYNAMIC_AROUND_ADVISER;
            } else {
                annotationType = AROUND_ADVISER;
            }
        } else if (source.getName().equals(BEFORE)) {
            isAnnotationStyle = false;
            //System.out.println("before adviser");
            if (dynamic) {
                annotationType = DYNAMIC_BEFORE_ADVISER;
            } else {
                annotationType = BEFORE_ADVISER;
            }
        } else if (source.getName().equals(AFTER)) {
            isAnnotationStyle = false;
            //System.out.println("after adviser");
            //return new AfterAdviserAnnotation(text);
            if (dynamic) {
                annotationType = DYNAMIC_AFTER_ADVISER;
            } else {
                annotationType = AFTER_ADVISER;
            }
        } else if (isAnnotationStyle) {
            //getExtraInfo
            IProgramElement.ExtraInformation xtra = source.getExtraInfo();
            String adviceInfo = xtra.getExtraAdviceInformation();
            if (adviceInfo.equals(AFTER)) {
                //System.out.println("advised by after");
                if (dynamic) {
                    annotationType = DYNAMIC_AFTER_ADVISER;
                } else {
                    annotationType = AFTER_ADVISER;
                }
            } else if (adviceInfo.equals(BEFORE)) {
                //System.out.println("advised by before");
                if (dynamic) {
                    annotationType = DYNAMIC_BEFORE_ADVISER;
                } else {
                    annotationType = BEFORE_ADVISER;
                }
            } else if (adviceInfo.equals(AROUND)) {
                //System.out.println("advised by around");
                if (dynamic) {
                    annotationType = DYNAMIC_AROUND_ADVISER;
                } else {
                    annotationType = AROUND_ADVISER;
                }
            }
        }
    }

    private void handleMaybeSourceAndTarget(List<Relationship> relationships) {
        boolean oneAffectsFound = false;
        boolean oneNotAffectsFound = false;
        boolean dynamic = true;
        for (Relationship relationship : relationships) {
            if (!relationship.hasRuntimeTest()) {
                dynamic = false;
            }
            if (relationship.isAffects()) {
                oneAffectsFound = true;
            } else {
                oneNotAffectsFound = true;
            }
        }
        if (oneNotAffectsFound && oneAffectsFound) {
            if (dynamic) {
                annotationType = DYNAMIC_SOURCE_AND_TARGET;
            } else {
                annotationType = SOURCE_AND_TARGET;
            }
        }
    //wenn einmal affects und einmal nicht
    //else "org-aspectj-tools-ajde-netbeans-aspectjannotation"; //implizit
    }

    private IProgramElement toProgrammElement(String handle) {
        return AsmManager.getDefault().getHierarchy().findElementForHandle(handle);
    }
}
