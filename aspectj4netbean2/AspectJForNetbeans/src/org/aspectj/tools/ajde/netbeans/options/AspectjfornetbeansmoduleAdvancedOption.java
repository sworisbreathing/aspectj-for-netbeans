/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aspectj.tools.ajde.netbeans.options;

import org.netbeans.spi.options.AdvancedOption;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle;

public final class AspectjfornetbeansmoduleAdvancedOption extends AdvancedOption {

    public String getDisplayName() {
        return NbBundle.getMessage(AspectjfornetbeansmoduleAdvancedOption.class, "AdvancedOption_DisplayName_Aspectjfornetbeansmodule");
    }

    public String getTooltip() {
        return NbBundle.getMessage(AspectjfornetbeansmoduleAdvancedOption.class, "AdvancedOption_Tooltip_Aspectjfornetbeansmodule");
    }

    public OptionsPanelController create() {
        return new AspectjfornetbeansmoduleOptionsPanelController();
    }
}
