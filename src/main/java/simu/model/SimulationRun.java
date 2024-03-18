package simu.model;

import dao.SimulationRunDAO;

public class SimulationRun {
    private int runNumber;

    public SimulationRun(int runNumber) {
        this.runNumber = runNumber;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    public static int getLastRunNumber() {
        return SimulationRunDAO.getLastRunNumber();
    }

    public static void addNewRunNumber() {
        SimulationRunDAO.addNewRunNumber();
    }
}
