/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.jboss.executor;

import org.jboss.executor.api.Command;
import org.jboss.executor.api.CommandContext;
import org.jboss.executor.api.ExecutionResults;

/**
 *
 * @author salaboy
 */
public class ThrowExceptionCommand implements Command{

    public ExecutionResults execute(CommandContext ctx) {
        System.out.println(">>> Hi This is the Exception command!");
        throw new RuntimeException("Test Exception!");        
    }
    
}
