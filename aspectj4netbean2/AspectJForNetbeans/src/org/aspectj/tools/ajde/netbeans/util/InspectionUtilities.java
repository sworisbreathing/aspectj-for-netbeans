/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aspectj.tools.ajde.netbeans.util;

import java.util.List;
import java.util.Set;
import org.aspectj.asm.AsmManager;
import org.aspectj.asm.IProgramElement;
import org.aspectj.asm.IRelationshipMap;
import org.aspectj.asm.internal.Relationship;

/**
 *
 * @author Ramos
 */
public class InspectionUtilities {

    public static void inspectProgrammElement(IProgramElement pe) {
        System.out.println("IProgramElement name: " + pe.getName());
        System.out.println("IProgramElement getDeclaringType: " + pe.getDeclaringType());
        
        System.out.println("IProgramElement getBytecodeName: " + pe.getBytecodeName());
        System.out.println("IProgramElement getBytecodeSignature: " + pe.getBytecodeSignature());
        System.out.println("IProgramElement getParameterSignatures: " + pe.getParameterSignatures());
         System.out.println("IProgramElement isImplementor: " + pe.isImplementor());
         System.out.println("IProgramElement isOverrider: " + pe.isOverrider());
         System.out.println("IProgramElement isRunnable: " + pe.isRunnable());
        
        
        
        System.out.println("IProgramElement details: " + pe.getDetails());
        System.out.println("IProgramElement getAccessibility: " + pe.getAccessibility());
    
        System.out.println("IProgramElement  getChildren:" + pe.getChildren());
        System.out.println("IProgramElement  getCorrespondingType :" + pe.getCorrespondingType());
        System.out.println("IProgramElement  getExtraInfo :" + pe.getExtraInfo());
        System.out.println("IProgramElement  getFormalComment:" + pe.getFormalComment());
        System.out.println("IProgramElement  getHandleIdentifier :"+pe.getHandleIdentifier());
        System.out.println("IProgramElement  getKind:" + pe.getKind());
        System.out.println("IProgramElement  getMessage :" + pe.getMessage());
        System.out.println("IProgramElement  getModifiers:" + pe.getModifiers());
        System.out.println("IProgramElement  getPackageName:" + pe.getPackageName());
        System.out.println("IProgramElement  getParameterNames:" + pe.getParameterNames());
        System.out.println("IProgramElement  getParent :" + pe.getParent());
        System.out.println("IProgramElement  getSourceLocation:"+pe.getSourceLocation());
        System.out.println("IProgramElement  getSourceSignature :" + pe.getSourceSignature());
        
        
        
        
        System.out.println("IProgramElement  toLabelString :" + pe.toLabelString());
        System.out.println("IProgramElement  toLabelString(false) :" + pe.toLabelString(false));
        System.out.println("IProgramElement  toLinkLabelString :" + pe.toLinkLabelString());
        System.out.println("IProgramElement  toLinkLabelString(false) :" + pe.toLinkLabelString(false));
        System.out.println("IProgramElement  toLongString :" + pe.toLongString());
        
        
        System.out.println("IProgramElement  toSignatureString :" + pe.toSignatureString());
        System.out.println("IProgramElement  toSignatureString(false) :" + pe.toSignatureString(false));
        System.out.println("IProgramElement  toString :" + pe.toString());
        
 

    }

    public static void inspectRelationship(Relationship relationship) {
        System.out.println("inspectRelationship: " + relationship);
        System.out.println("relationship.getKind(); " + relationship.getKind());
        System.out.println("relationship.getName " + relationship.getName());
        System.out.println("relationship.getSourceHandle " + relationship.getSourceHandle());
        System.out.println("relationship.getTargets( " + relationship.getTargets());
        System.out.println("relationship.hasRuntimeTest " + relationship.hasRuntimeTest());
        System.out.println("relationship.isAffects " + relationship.isAffects());
    }

    public static void inspectRelMap() {
        System.out.println("Relationship            Source                     Target");
        IRelationshipMap relationshipMap = AsmManager.getDefault().getRelationshipMap();

        Set<String> set = AsmManager.getDefault().getRelationshipMap().getEntries();
        for (String handle : set) {
            IProgramElement programmElement = AsmManager.getDefault().getHierarchy().findElementForHandle(handle);
            List<Relationship> relationships = relationshipMap.get(programmElement);
            for (Relationship relationship : relationships) {
                IProgramElement srcElement = AsmManager.getDefault().getHierarchy().findElementForHandle(relationship.getSourceHandle());
                List<String> targets = relationship.getTargets();
                for (String ref : targets) {
                    IProgramElement pe = AsmManager.getDefault().getHierarchy().findElementForHandle(ref);
                    System.out.println(relationship.getName() + " " + srcElement + " " + pe);
                }

            }
        }
    //System.out.println("HierarchyAdapter: elementsUpdated");
    }

    public static void inspectArray(Object[] array) {
        if (array == null) {
            System.out.println("<array is null>");
            return;
        }
        if (array.length == 0) {
            System.out.println("<0 elements array>");
            return;
        }
        for (Object object : array) {
            System.out.println(object);
        }

    }
    public static void dumpRelationships() {
            IRelationshipMap relationshipMap = AsmManager.getDefault().getRelationshipMap();
            System.out.println("rel map: " + relationshipMap.getEntries());
            Set<String> handles = relationshipMap.getEntries();
            for (String handle : handles) {
                //System.out.println("type: "+object.getClass().getName());
                IProgramElement otherLink = AsmManager.getDefault().getHierarchy().findElementForHandle(handle);
                System.out.println("prog elem: " + otherLink);
            }
        }
}
