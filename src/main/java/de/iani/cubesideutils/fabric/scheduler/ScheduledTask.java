package de.iani.cubesideutils.fabric.scheduler;

import java.util.concurrent.Executor;

public class ScheduledTask implements Comparable<ScheduledTask> {
    private Runnable task;
    private volatile boolean cancelled;
    private long nextExecutionTick;
    private int delay;
    private int intervall;
    private Executor executor;

    public ScheduledTask(Runnable task, int delay, int intervall, Executor executor) {
        this.task = task;
        this.delay = delay;
        this.intervall = intervall;
        this.executor = executor;
    }

    public void cancel() {
        cancelled = true;
        task = null;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    void execute() {
        Runnable task = this.task;
        if (!isCancelled() && task != null) {
            if (executor != null) {
                executor.execute(task);
            } else {
                try {
                    task.run();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    public int getDelay() {
        return delay;
    }

    void setScheduledOnTick(long scheduledOnTick, boolean forFirstExecution) {
        if (forFirstExecution) {
            this.nextExecutionTick = scheduledOnTick + delay;
        } else {
            this.nextExecutionTick = scheduledOnTick + intervall;
        }
    }

    long getScheduledOnTick() {
        return nextExecutionTick;
    }

    public int getIntervall() {
        return intervall;
    }

    long getExecutionTick() {
        return nextExecutionTick;
    }

    @Override
    public int compareTo(ScheduledTask o) {
        return Long.signum(nextExecutionTick - o.nextExecutionTick);
    }
}
