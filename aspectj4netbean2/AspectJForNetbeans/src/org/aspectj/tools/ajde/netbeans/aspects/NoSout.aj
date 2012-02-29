package org.aspectj.tools.ajde.netbeans.aspects;

public aspect NoSout{
  pointcut sout():
    get(* System.out)
    && !within(org.aspectj.tools.ajde.netbeans.util.InspectionUtilities);
    
    declare warning: sout():"sout!";
}
