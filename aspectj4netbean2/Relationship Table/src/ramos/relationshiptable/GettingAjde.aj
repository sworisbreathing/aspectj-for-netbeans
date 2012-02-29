package org.aspectj.tools.ajde.netbeans.aspects;
import org.aspectj.tools.ajde.netbeans.NbManager;
import org.aspectj.ajde.Ajde;
public aspect GettingAjde{
    pointcut getAjde(): 
    call (* Ajde.getDefault())
    && !withincode(* NbManager.*(..)) 
    && !withincode(NbManager.new(..));
    before(): getAjde(){
        if (NbManager.INSTANCE == null){
            NbManager.init();
        }
    }
}
