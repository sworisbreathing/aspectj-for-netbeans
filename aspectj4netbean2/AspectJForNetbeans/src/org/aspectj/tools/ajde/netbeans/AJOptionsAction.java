

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

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

import org.aspectj.ajde.ui.swing.AjdeUIManager;

/** Action that can always be invoked and work procedurally.
 *
 * @author  Phil Sager
 */
public class AJOptionsAction extends CallableSystemAction {
    
    public static boolean PROP_ENABLED = false;
    
    public void performAction() {
        AjdeUIManager.getDefault().showOptionsFrame();
    }
    
    public String getName() {
        return NbBundle.getMessage(AJOptionsAction.class, "LBL_OptionsAction");
    }
    
    protected String iconResource() {
        return "/org/aspectj/ajde/resources/actions/browseroptions.gif";
    }
    
    public boolean isEnabled(){
        return PROP_ENABLED;
    }
    
    public void setEnabled(boolean enable){
        PROP_ENABLED = enable;
        super.setEnabled(enable);
    }    
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
        // If you will provide context help then use:
        // return new HelpCtx (OptionsAction.class);
    }
    
    /** Perform extra initialization of this action's singleton. 
     * PLEASE do not use constructors for this purpose! */
	  protected void initialize () {
              //super.initialize();
	        super.setEnabled(false);
	  }
    
}
