package org.jbpm.executor;

import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Named;
import org.jbpm.executor.api.CommandCallback;
import org.jbpm.executor.api.CommandContext;
import org.jbpm.executor.api.ExecutionResults;

/**
 *
 * @author salaboy
 */
@Named(value = "SimpleIncrementCallback")
public class SimpleIncrementCallback implements CommandCallback {

    public void onCommandDone(CommandContext ctx, ExecutionResults results) {
        String businessKey = (String) ctx.getData("businessKey");
        AtomicLong increment = (AtomicLong) BasicExecutorBaseTest.cachedEntities.get(businessKey);
        System.out.println(" >>> Before Incrementing = " + increment);
        BasicExecutorBaseTest.cachedEntities.put(businessKey, new AtomicLong(increment.incrementAndGet()));
        System.out.println(" >>> After Incrementing = " + BasicExecutorBaseTest.cachedEntities.get(businessKey));

    }
}
