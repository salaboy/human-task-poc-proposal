/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ht;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 * @author salaboy
 * Two options for bootstraping SY
 * CDI https://github.com/jboss-switchyard/core/blob/master/deploy/cdi/src/main/java/org/switchyard/deploy/cdi/SwitchYardCDIDeployer.java
 * 
 * Instatiation: https://github.com/jboss-switchyard/core/blob/master/deploy/base/src/main/java/org/switchyard/SwitchYard.java
 * 
 */
public class TaskServiceSE {

    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        
        
    }
}
