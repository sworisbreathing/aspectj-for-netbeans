/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aspectj.tools.ajde.netbeans.configeditor;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.aspectj.tools.ajde.netbeans.configeditor.AjcOptions.AjcOption;

/**
 *
 * @author Ramos
 */
public class AjcOptionsPanel extends JPanel{

//    public AjcOptionsPanel() {
//        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
//        setBorder(BorderFactory.createTitledBorder("Ajc Options"));
//        AjcOption[] options = AjcOptions.getDefault().parseOptions(new ArrayList<String>());
//        for (AjcOption ajcOption : options) {
//            if (ajcOption.isText()){
//                add(buildTextOptionPanel(ajcOption));
//            }
//            else {
//                add(new JCheckBox(ajcOption.getLabel()));
//            }
//        }
//    }

    public AjcOptionsPanel(AjcOption[] options) {
        //NULL??
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Ajc Options"));
        for (AjcOption ajcOption : options) {
            if (ajcOption.isHidden()) continue;
            if (ajcOption.isText()){
                add(buildTextOptionPanel(ajcOption));
            }
            else {
                
                add(new OneCheckBoxPanel(ajcOption));
                
            }
        }
    }
        
        private JPanel buildTextOptionPanel(AjcOption o){
            
//            JLabel label = new JLabel(o.getLabel()+":");
//            JTextField tf = new JTextField();
//            if (o.getValue() != null) tf.setText(o.getValue());
//            JPanel p = new JPanel();
//            p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
//            p.add(label);
//            p.add(tf);
//            return p;
            return new OneTextOptionPanel(o);
        }

    }
