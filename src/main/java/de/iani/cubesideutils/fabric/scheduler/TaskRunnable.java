package de.iani.cubesideutils.fabric.scheduler;

import java.util.concurrent.Executor;

public abstract class TaskRunnable {
    private ScheduledTask task;

    public abstract void run();

    public final TaskRunnable scheduleSyncRepeating(int delay, int inverval) {
        checkNotScheduled();
        task = Scheduler.scheduleSyncRepeatingTask(this::run, delay, inverval);
        return this;
    }

    public final TaskRunnable scheduleSync(int delay) {
        checkNotScheduled();
        task = Scheduler.scheduleSyncTask(this::run, delay);
        return this;
    }

    public final TaskRunnable scheduleImmediateSync() {
        checkNotScheduled();
        task = Scheduler.scheduleImmediateSyncTask(this::run);
        return this;
    }

    public final TaskRunnable scheduleAsynchronousRepeating(int delay, int inverval) {
        checkNotScheduled();
        task = Scheduler.scheduleAsynchronousRepeatingTask(this::run, delay, inverval);
        return this;
    }

    public final TaskRunnable scheduleAsynchronous(int delay) {
        checkNotScheduled();
        task = Scheduler.scheduleAsynchronousTask(this::run, delay);
        return this;
    }

    public final TaskRunnable scheduleImmediateAsynchronous() {
        checkNotScheduled();
        task = Scheduler.scheduleImmediateAsynchronousTask(this::run);
        return this;
    }

    public final TaskRunnable scheduleAsynchronousRepeating(int delay, int inverval, Executor executor) {
        checkNotScheduled();
        task = Scheduler.scheduleAsynchronousRepeatingTask(this::run, delay, inverval, executor);
        return this;
    }

    public final TaskRunnable scheduleAsynchronous(int delay, Executor executor) {
        checkNotScheduled();
        task = Scheduler.scheduleAsynchronousTask(this::run, delay, executor);
        return this;
    }

    public final TaskRunnable scheduleImmediateAsynchronous(Executor executor) {
        checkNotScheduled();
        task = Scheduler.scheduleImmediateAsynchronousTask(this::run, executor);
        return this;
    }

    public final void cancel() {
        checkScheduled();
        task.cancel();
    }

    private void checkNotScheduled() {
        if (task != null) {
            throw new IllegalStateException("This task is already scheduled");
        }
    }

    private void checkScheduled() {
        if (task == null) {
            throw new IllegalStateException("This task is not scheduled");
        }
    }
}
