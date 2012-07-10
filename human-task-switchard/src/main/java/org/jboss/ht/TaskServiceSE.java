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
 */
public class TaskServiceSE {

    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        
        
    }
}
