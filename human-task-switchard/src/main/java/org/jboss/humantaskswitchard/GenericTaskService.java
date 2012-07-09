/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.humantaskswitchard;

import org.apache.camel.builder.RouteBuilder;
import org.jboss.human.interactions.api.TaskInstanceService;
import org.switchyard.component.camel.Route;

/**
 *
 * @author salaboy
 */
@Route(TaskInstanceService.class)
public class GenericTaskService extends RouteBuilder {
    
    public void configure() {
        from("switchyard://GenericTaskService")
            .log("Message received in GenericTaskService Route")
            .log("${body}")
            .split(body(String.class).tokenize("\n"))
            .to("switchyard://XMLService?operationName=newTask");
    }
    
}
