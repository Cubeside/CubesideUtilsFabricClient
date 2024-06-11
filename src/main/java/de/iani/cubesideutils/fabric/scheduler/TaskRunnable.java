package de.iani.cubesideutils.fabric.scheduler;

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
