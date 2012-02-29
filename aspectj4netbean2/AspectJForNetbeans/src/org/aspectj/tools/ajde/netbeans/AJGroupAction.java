
/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This file is part of the IDE support for the AspectJ(tm)
 * programming language; see http://aspectj.org
 *
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * either http://www.mozilla.org/MPL/ or http://aspectj.org/MPL/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is AspectJ.
 *
 * The Initial Developer of the Original Code is Xerox Corporation. Portions
 * created by Xerox Corporation are Copyright (C) 1999-2002 Xerox Corporation.
 * All Rights Reserved.
 *
 * Contributor(s): Phil Sager (psager@mb.sympatico.ca)
 */
 
 
package org.aspectj.tools.ajde.netbeans;

import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.*;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;
import org.openide.util.actions.SystemAction;

/** Action which just holds a few other SystemAction's for grouping purposes.
 *
 * @author  Phil Sager
 */
public class AJGroupAction extends SystemAction implements Presenter.Menu, Presenter.Popup, Presenter.Toolbar {
    
    public void actionPerformed(ActionEvent ev) {
        // do nothing; should not be called
    }
    
    public String getName() {
        return NbBundle.getMessage(AJGroupAction.class, "LBL_GroupAction");
    }
    
    protected String iconResource() {
        return "/org/aspectj/ajde/resources/actions/startAjde.gif";
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
        // If you will provide context help then use:
        // return new HelpCtx (AJGroupAction.class);
    }
    
    /** Perform extra initialization of this action's singleton. 
     * PLEASE do not use constructors for this purpose! */
      protected void initialize () {
            super.setEnabled(false);
      }
    
    /** List of system actions to be displayed within this one's toolbar or submenu. */
    private static final SystemAction[] grouped() {
        return new SystemAction[] {
                                    SystemAction.get(AJStartBrowserAction.class),
                                    SystemAction.get(AJBuildAction.class),
                                    //SystemAction.get(AJRunAction.class),
                                    SystemAction.get(AJOptionsAction.class)};
    }
    
    private static Icon icon = null;
    public JMenuItem getMenuPresenter() {
        // JMenuPlus reported to avoid a strange Windows-specific native code bug (null pData):
        JMenu menu = new JMenu(getName());
        if (icon == null) icon = new ImageIcon(AJGroupAction.class.getResource(iconResource()));
        menu.setIcon(icon);
        SystemAction[] grouped = grouped();
        for (int i = 0; i < grouped.length; i++) {
            SystemAction action = grouped[i];
            if (action instanceof Presenter.Menu) {
                menu.add(((Presenter.Menu) action).getMenuPresenter());
            }
        }
        return menu;
    }
    
    public JMenuItem getPopupPresenter() {
        JMenu menu = new JMenu(getName());
        // Conventional not to set an icon here.
        SystemAction[] grouped = grouped();
        for (int i = 0; i < grouped.length; i++) {
            SystemAction action = grouped[i];
            if (action == null) {
                menu.addSeparator();
            } else if (action instanceof Presenter.Popup) {
                menu.add(((Presenter.Popup) action).getPopupPresenter());
            }
        }
        return menu;
    }
    
    public Component getToolbarPresenter() {
        JToolBar toolbar = new JToolBar();
        toolbar.setName(getName());
            // In JDK 1.3 you may add: getName ()
        SystemAction[] grouped = grouped();
        for (int i = 0; i < grouped.length; i++) {
            SystemAction action = grouped[i];
            if (action instanceof Presenter.Toolbar && i != 1) {
                toolbar.add(((Presenter.Toolbar) action).getToolbarPresenter());
            }
        }
        return toolbar;
    }
 
}
