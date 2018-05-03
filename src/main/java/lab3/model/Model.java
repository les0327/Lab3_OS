package lab3.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
public class Model {

    private double LENGTH;
    private double LAMBDA;
    private double MU;

    private double T;
    private ArrayList<Task> solvedTasks;
    private Queue<Task> queue;

    private boolean isFree = true;
    private double t1 = 0;
    private double t2 = Double.MAX_VALUE;
    private double tp;
    private int k = 1;
    private Task currTask = null;

    public Model(@Value("${model.LENGTH}") double LENGTH, @Value("${model.LAMBDA}") double LAMBDA, @Value("${model.MU}") double MU) {
        this.LENGTH = LENGTH;
        this.LAMBDA = LAMBDA;
        this.MU = MU;
        this.queue = new SFQueue();
        this.T = 0;
        this.solvedTasks = new ArrayList<>();
    }

    public void step() {
        tp = getSolveTime();
        if (t1 < t2) {
            T = t1;
            if (isFree) {
                currTask = new Task(t1, tp);
                currTask.setOnSolving(T);
                t2 = T + tp;
                currTask.setOutTime(t2);
                isFree = false;
            } else {
                queue.add(new Task(t1, tp));
            }
            t1 = genNextEnterTime();
            k++;
        } else {
            T = t2;
            solvedTasks.add(currTask);
            if (queue.isEmpty()) {
                t2 = Double.MAX_VALUE;
                isFree = true;
            } else {
                currTask = queue.get();
                currTask.setOnSolving(T);
                t2 = T + currTask.getSolveTime();
                currTask.setOutTime(t2);
                isFree = false;
            }
        }
    }

    public void simulate() {
        while (k < LENGTH) {
            step();
        }
    }


    public double getEvTime() {
        return solvedTasks.stream().mapToDouble(task -> task.getOutTime() - task.getInTime()).sum() / solvedTasks.size();
    }

    public double getReactionTime() {
        return solvedTasks.stream().mapToDouble(task -> task.getOnSolving() - task.getInTime()).sum() / solvedTasks.size();
    }

    public double getRatio() {
        return (double) solvedTasks.size() / (solvedTasks.size() + queue.getTasks().size());
    }


    //Count solve time for task
    private double getSolveTime() {
        Random r = new Random();
        return - 1 / MU * Math.log(r.nextDouble());
    }


    //Count the time when the next task comes into the system
    private double genNextEnterTime() {
        Random r = new Random();
        return T - 1 / LAMBDA * Math.log(r.nextDouble());
    }

    public double getLENGTH() {
        return LENGTH;
    }

    public double getLAMBDA() {
        return LAMBDA;
    }

    public double getMU() {
        return MU;
    }

    public double getT() {
        return T;
    }

    public ArrayList<Task> getSolvedTasks() {
        return solvedTasks;
    }

    public Queue<Task> getQueue() {
        return queue;
    }

    public boolean isFree() {
        return isFree;
    }

    public Task getCurrTask() {
        return currTask;
    }

    public int getK() {
        return k;
    }
}
