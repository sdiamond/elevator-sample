package com.diamond;

import java.util.ArrayList;
import java.util.Map;

public class ElevatorSimulator {

    public static final int GROUND_FLOOR = 0;
    private int endTime =  86400;
    public static int TOP_FLOOR = 10;
    public ElevatorManager manager;

    public ElevatorSimulator(){
    }

    /**
     * for debug to shorten the end time if needed.
     */
    public ElevatorSimulator(int _endTime){
        this.endTime = _endTime;
    }
    public void startSimulation(Map<Integer, ArrayList<ElevatorRequest>> requests) {
        // create elevatorManager
        System.out.println("start simulation ***************");
        manager = new ElevatorManager();
        ElevatorMetrics.reset();
        // create elevatorMetrics instance or at least reset metrics
        for (int currentTime = 0; currentTime < endTime; currentTime++) { 
            manager.addRequests(currentTime, requests.get(currentTime));
            manager.moveElevators(currentTime);

        }
        System.out.println("done simulation");
    }

    public ElevatorManager getElevatorManager() {
        return this.manager;
    }

}