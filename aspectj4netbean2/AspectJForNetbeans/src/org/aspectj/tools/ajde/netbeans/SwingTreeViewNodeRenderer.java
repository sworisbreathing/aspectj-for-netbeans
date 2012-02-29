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
 *     Helen Hawkins  Converted to new interface (bug 148190)  
 * ******************************************************************/
package org.aspectj.tools.ajde.netbeans;

import java.awt.Component;
import java.awt.Font;

import java.util.List;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;


import org.aspectj.ajde.ui.IStructureViewNode;
import org.aspectj.ajde.ui.swing.AjdeUIManager;
import org.aspectj.ajde.ui.swing.SwingTreeViewNode;
import org.aspectj.asm.AsmManager;
import org.aspectj.asm.IProgramElement;
import org.aspectj.asm.internal.Relationship;
import org.aspectj.bridge.IMessage;

/**
 * @author Mik Kersten
 */
class SwingTreeViewNodeRenderer extends DefaultTreeCellRenderer {

    //ColorUIResource focusedNonSelectedBorderColor;
    private static final long serialVersionUID = -4561164526650924465L;

    SwingTreeViewNodeRenderer() {
    //System.out.println("defaults: " + UIManager.getDefaults());
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
            Object treeNode,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {
        if (treeNode == null) {
            return null;
        }
        //this.setFont(NbManager.INSTANCE.getTheFont());       

        SwingTreeViewNode viewNode = (SwingTreeViewNode) treeNode;
        IProgramElement node = viewNode.getStructureNode();



        //        if (viewNode.getKind() == IStructureViewNode.Kind.LINK) {
//            ISourceLocation sourceLoc = node.getSourceLocation();
//            if ((null != sourceLoc) && (null != sourceLoc.getSourceFile().getAbsolutePath())) {
//                setTextNonSelectionColor(AjdeWidgetStyles.LINK_NODE_COLOR);
//            } else {
//                setTextNonSelectionColor(AjdeWidgetStyles.LINK_NODE_NO_SOURCE_COLOR);
//            }
//
//        } else if (viewNode.getKind() == IStructureViewNode.Kind.RELATIONSHIP) {
//            //this.setFont(new Font(this.getFont().getName(), Font.ITALIC, this.getFont().getSize()));
//            setTextNonSelectionColor(new Color(0, 0, 0));
//
//        } else if (viewNode.getKind() == IStructureViewNode.Kind.DECLARATION) {
//            setTextNonSelectionColor(new Color(0, 0, 0));
//        }

        super.getTreeCellRendererComponent(tree, treeNode, sel, expanded, leaf, row, hasFocus);
        if (viewNode.getKind().equals(IStructureViewNode.Kind.LINK)) {
            if (node != null) {
                setText(" " + node.toLinkLabelString(false));
            }
        } else if (node != null) {
            setText(" " + node.toLabelString(false));
        }
        if (viewNode.getKind() == IStructureViewNode.Kind.DECLARATION || viewNode.getKind() == IStructureViewNode.Kind.LINK) {
//            System.out.println("rendering: " + treeNode);
//            System.out.println("modifiers of " + node + ": " + node.getModifiers());
            NbIconRegistry icons = (NbIconRegistry) AjdeUIManager.getDefault().getIconRegistry();
            if (node.getKind().equals(IProgramElement.Kind.ADVICE)) {
                boolean dynamic = false;
                List<Relationship> relationships = AsmManager.getDefault().getRelationshipMap().get(node);
//                System.out.println("getting rels of "+node);
                if (relationships != null && relationships.size() == 1) {
                    Relationship relationship = relationships.get(0);
                    if (relationship.hasRuntimeTest()) {
                        dynamic = true;
                    }
                }
                IProgramElement.ExtraInformation xtra = node.getExtraInfo();
                if (xtra != null) {
                    String adviceInfo = xtra.getExtraAdviceInformation();

                    setIcon(icons.getAdviceIcon(adviceInfo, dynamic));
                } else {
                    //by name
                    setIcon(icons.getAdviceIcon(node.getName()));
                }
                //relation map abfragen?

                } else {
                    setIcon(icons.getStructureSwingIcon(node.getKind(),
                            node.getAccessibility(),
                            node.getModifiers()));
                }
            } else if (viewNode.getIcon() != null && viewNode.getIcon().getIconResource() != null) {
                setIcon((Icon) viewNode.getIcon().getIconResource());
            } else {
                setIcon(null);
            }
            if (node != null) {
                if (node.isRunnable()) {
                    NbIconRegistry icons = (NbIconRegistry) AjdeUIManager.getDefault().getIconRegistry();
                    setIcon(icons.getRunnableIcon());
                }
                if (node.getMessage() != null) {
                    if (node.getMessage().getKind().equals(IMessage.WARNING)) {
                        setIcon(AjdeUIManager.getDefault().getIconRegistry().getWarningIcon());
                    } else if (node.getMessage().getKind().equals(IMessage.ERROR)) {
                        setIcon(AjdeUIManager.getDefault().getIconRegistry().getErrorIcon());
                    } else {
                        setIcon(AjdeUIManager.getDefault().getIconRegistry().getInfoIcon());
                    }
                }

            }

            //System.out.println("this.getfont 1: "+getFont());
            Font f = UIManager.getFont("Tree.font");
            tree.setFont(f);
            //if (node != null && (!node.getKind().isMember())) setText(node.toLinkLabelString(false));
            //if (node != null) setText(node.toLabelString(false));

//        if (sel) {
//            if (!hasFocus) {
//                this.setForeground(UIManager.getColor("Tree.selectionForeground"));
//                this.setBackground(focusedNonSelectedBorderColor);
//                System.out.println("no focus! backg = "+this.getBackground());
//            } else {
//                this.setForeground(UIManager.getColor("Tree.selectionForeground"));
//                this.setBackground(UIManager.getColor("Tree.selecttionBackground"));
//            }
//        } else {
//            this.setForeground(UIManager.getColor("Tree.textForeground"));
//            this.setBackground(UIManager.getColor("Tree.textBackground"));
//        }


            //        System.out.println("jtree font: "+f);
//        setFont(f);  
//        System.out.println("this.getfont 2: "+getFont());
            //this.setBackground(SystemColor.desktop);
            return this;
        }
    }

