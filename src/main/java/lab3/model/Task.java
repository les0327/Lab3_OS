package lab3.model;

public class Task {
    private double inTime;
    private double solveTime;
    private double onSolving;
    private double outTime;

    public Task(double enterTime, double solveTime) {
        this.inTime = enterTime;
        this.solveTime = solveTime;
    }

    public double getInTime() {
        return inTime;
    }

    public void setInTime(double inTime) {
        this.inTime = inTime;
    }

    public double getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(double solveTime) {
        this.solveTime = solveTime;
    }

    public double getOnSolving() {
        return onSolving;
    }

    public void setOnSolving(double onSolving) {
        this.onSolving = onSolving;
    }

    public double getOutTime() {
        return outTime;
    }

    public void setOutTime(double outTime) {
        this.outTime = outTime;
    }

    public double getWaitTime() {
        return getOutTime() - getInTime();
    }

}
