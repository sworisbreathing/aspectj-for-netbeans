/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aspectj.tools.ajde.netbeans;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import org.aspectj.ajde.Ajde;
import org.aspectj.bridge.IMessage;
import org.aspectj.bridge.Message;

/**
 *
 * @author Ramos
 */
public class NetBeansMultiViewPanel extends JPanel{
JSplitPane views_splitPane;
    BorderLayout borderLayout1 = new BorderLayout();

	public NetBeansMultiViewPanel(JPanel topPanel, JPanel bottomPanel) {
    	super();
        try {
        	views_splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
            jbInit(); 
        } catch(Exception e) {
        	Message msg = new Message("Could not initialize GUI.",IMessage.ERROR,e,null);
        	Ajde.getDefault().getMessageHandler().handleMessage(msg);
        }
	}

    private void jbInit() throws Exception {
        this.setBorder(null);
        this.setLayout(borderLayout1);
        views_splitPane.setBorder(null);
        this.add(views_splitPane, BorderLayout.CENTER);
        //views_splitPane.setDividerSize(4);
        views_splitPane.setDividerLocation(180);
    }

}
