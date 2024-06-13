package de.iani.cubesideutils.fabric.scheduler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Scheduler {
    private Thread ownerThread;
    private ConcurrentLinkedQueue<ScheduledTask> queueFromAsync;
    private PriorityQueue<ScheduledTask> timedTasks;
    private ArrayDeque<ScheduledTask> immediateTasks;
    private ArrayList<ScheduledTask> everyTickTasks;
    private long currentTick;
    private final Executor defaultExecutor = Executors.newCachedThreadPool();

    static final Scheduler INSTANCE = new Scheduler();

    private Scheduler() {
        this.queueFromAsync = new ConcurrentLinkedQueue<>();
        this.timedTasks = new PriorityQueue<>();
        this.immediateTasks = new ArrayDeque<>();
        this.everyTickTasks = new ArrayList<>();
    }

    public static ScheduledTask scheduleSyncRepeatingTask(Runnable task, int delay, int inverval) {
        return INSTANCE.scheduleTaskInternal(task, delay, inverval, null);
    }

    public static ScheduledTask scheduleSyncTask(Runnable task, int delay) {
        return INSTANCE.scheduleTaskInternal(task, delay, -1, null);
    }

    public static ScheduledTask scheduleImmediateSyncTask(Runnable task) {
        return INSTANCE.scheduleTaskInternal(task, 0, -1, null);
    }

    public static ScheduledTask scheduleImmediateAsynchronousTask(Runnable task) {
        return INSTANCE.scheduleTaskInternal(task, 0, -1, INSTANCE.defaultExecutor);
    }

    public static ScheduledTask scheduleAsynchronousTask(Runnable task, int delay) {
        return INSTANCE.scheduleTaskInternal(task, delay, -1, INSTANCE.defaultExecutor);
    }

    public static ScheduledTask scheduleAsynchronousRepeatingTask(Runnable task, int delay, int interval) {
        return INSTANCE.scheduleTaskInternal(task, delay, interval, INSTANCE.defaultExecutor);
    }

    public static ScheduledTask scheduleImmediateAsynchronousTask(Runnable task, Executor executor) {
        return INSTANCE.scheduleTaskInternal(task, 0, -1, executor == null ? INSTANCE.defaultExecutor : executor);
    }

    public static ScheduledTask scheduleAsynchronousTask(Runnable task, int delay, Executor executor) {
        return INSTANCE.scheduleTaskInternal(task, delay, -1, executor == null ? INSTANCE.defaultExecutor : executor);
    }

    public static ScheduledTask scheduleAsynchronousRepeatingTask(Runnable task, int delay, int interval, Executor executor) {
        return INSTANCE.scheduleTaskInternal(task, delay, interval, executor == null ? INSTANCE.defaultExecutor : executor);
    }

    private ScheduledTask scheduleTaskInternal(Runnable task, int delay, int inverval, Executor executor) {
        if (inverval < 1) {
            inverval = -1;
        }
        if (delay < 0) {
            delay = 0;
        }
        return schedule(new ScheduledTask(task, delay, inverval, executor));
    }

    private ScheduledTask schedule(ScheduledTask scheduledTask) {
        if (ownerThread == Thread.currentThread()) {
            if (scheduledTask.getDelay() <= 0) {
                immediateTasks.addLast(scheduledTask);
            } else {
                timedTasks.add(scheduledTask);
            }
            scheduledTask.setScheduledOnTick(currentTick, true);
        } else {
            queueFromAsync.add(scheduledTask);
        }
        return scheduledTask;
    }

    void processOnTick() {
        // process new tasks from async
        ScheduledTask scheduledTask = queueFromAsync.poll();
        while (scheduledTask != null) {
            if (scheduledTask.getDelay() <= 0) {
                immediateTasks.addLast(scheduledTask);
            } else {
                timedTasks.add(scheduledTask);
            }
            scheduledTask.setScheduledOnTick(currentTick, true);
            scheduledTask = queueFromAsync.poll();
        }

        // process tasks for every tick
        int everyTickTasksSize = everyTickTasks.size();
        for (int i = 0; i < everyTickTasksSize; i++) {
            scheduledTask = everyTickTasks.get(i);
            scheduledTask.execute();
            if (scheduledTask.isCancelled()) {
                everyTickTasks.remove(i);
                --i;
                --everyTickTasksSize;
            }
        }

        // process immediate tasks (might be repeating)
        scheduledTask = immediateTasks.pollFirst();
        while (scheduledTask != null) {
            scheduledTask.execute();
            if (scheduledTask.getIntervall() > 0 && !scheduledTask.isCancelled()) {
                if (scheduledTask.getIntervall() == 1) {
                    everyTickTasks.add(scheduledTask);
                } else {
                    scheduledTask.setScheduledOnTick(currentTick, false);
                    timedTasks.add(scheduledTask);
                }
            }
            scheduledTask = immediateTasks.pollFirst();
        }

        // process timed tasks (might be repeating)
        scheduledTask = timedTasks.peek();
        while (scheduledTask != null && scheduledTask.getExecutionTick() <= currentTick) {
            timedTasks.poll();
            scheduledTask.execute();
            if (scheduledTask.getIntervall() > 0 && !scheduledTask.isCancelled()) {
                if (scheduledTask.getIntervall() == 1) {
                    everyTickTasks.add(scheduledTask);
                } else {
                    scheduledTask.setScheduledOnTick(currentTick, false);
                    timedTasks.add(scheduledTask);
                }
            }

            scheduledTask = immediateTasks.peek();
        }

        currentTick += 1;
    }

    void initialize(Thread serverThread) {
        this.ownerThread = serverThread;
    }
}
