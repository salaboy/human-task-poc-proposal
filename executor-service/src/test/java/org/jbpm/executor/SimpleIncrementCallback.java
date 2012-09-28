package org.jbpm.executor;

import org.jbpm.executor.api.CommandCallback;
import org.jbpm.executor.api.CommandContext;
import org.jbpm.executor.api.ExecutionResults;


/**
 *
 * @author salaboy
 */
public class SimpleIncrementCallback implements CommandCallback{

    public void onCommandDone(CommandContext ctx, ExecutionResults results) {
        String businessKey = (String)ctx.getData("businessKey");
        Long increment = (Long)BasicExecutorBaseTest.cachedEntities.get(businessKey);
        System.out.println(" >>> Before Incrementing = "+increment);
        BasicExecutorBaseTest.cachedEntities.put(businessKey, increment + 1);
        System.out.println(" >>> After Incrementing = "+BasicExecutorBaseTest.cachedEntities.get(businessKey));
        
    }  
}
