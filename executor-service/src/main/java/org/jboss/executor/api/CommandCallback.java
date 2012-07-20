package org.jboss.executor.api;

public interface CommandCallback {

    void onCommandDone(CommandContext ctx, ExecutionResults results);
}
