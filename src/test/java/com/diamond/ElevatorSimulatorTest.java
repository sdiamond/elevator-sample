package com.diamond;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class ElevatorSimulatorTest {

    @Test
    public void testSimple (){
        // create three people getting on ground floor and going to floor three
        HashMap<Integer, ArrayList<ElevatorRequest>> map = new HashMap<Integer, ArrayList<ElevatorRequest>>();
        ArrayList<ElevatorRequest> requestList = new ArrayList<ElevatorRequest>();
        ElevatorRequest req = new ElevatorRequest (10, 0, 1);
        requestList.add(req);
        
        map.put(10, requestList);

        // now have simple test data, lets run
        ElevatorSimulator sim  = new ElevatorSimulator(50);
        sim.startSimulation(map); //metrics are reset so lets check some metrics and see how we did

        ElevatorMetrics.printOutMetrics();
        assertEquals("total rides", 1, ElevatorMetrics.getTotalRides());
        assertEquals("total requests", 1,ElevatorMetrics.getTotalRequests());
        assertEquals("avarege riders per ride", 1, ElevatorMetrics.getAvgNumberRidersPerRide(), 0.0001);
        assertEquals("total stops", 1,ElevatorMetrics.getTotalStops());
        assertEquals("only one elevator", 1, sim.getElevatorManager().getElevators().size());
        assertEquals("empty traversals", 1, ElevatorMetrics.getEmptyTraversals());
        // the on elevator should be at the bottom floor
        Elevator elevator = sim.getElevatorManager().getElevators().get(0);
        assertEquals("elevator on ground floor)", ElevatorSimulator.GROUND_FLOOR, elevator.getFloor());
        assertTrue("elevator is empty", elevator.isEmpty());
    }

    @Test
 
    public void testSimpleWithTwoRiders (){
        // create three people getting on ground floor and going to floor three
        HashMap<Integer, ArrayList<ElevatorRequest>> map = new HashMap<Integer, ArrayList<ElevatorRequest>>();
        ArrayList<ElevatorRequest> requestList = new ArrayList<ElevatorRequest>();
        ElevatorRequest req = new ElevatorRequest (10, 0, 1);
        requestList.add(req);
        
        map.put(10, requestList);
        ElevatorRequest req2 = new ElevatorRequest(11, 1, 2);
        ArrayList<ElevatorRequest> reqList2 = new ArrayList<ElevatorRequest>();
        reqList2.add(req2);
        map.put(11, reqList2);

        // now have simple test data, lets run
        ElevatorSimulator sim  = new ElevatorSimulator(90);
        sim.startSimulation(map); //metrics are reset so lets check some metrics and see how we did

        ElevatorMetrics.printOutMetrics();
        assertEquals("total rides", 1, ElevatorMetrics.getTotalRides());
        assertEquals("total requests", 2,ElevatorMetrics.getTotalRequests());
        assertEquals("avarege riders per ride", 2, ElevatorMetrics.getAvgNumberRidersPerRide(), 0.0001);
        assertEquals("total stops", 2,ElevatorMetrics.getTotalStops());
        assertEquals("only one elevator", 1, sim.getElevatorManager().getElevators().size());
        assertEquals("empty traversals", 1, ElevatorMetrics.getEmptyTraversals());
        // the on elevator should be at the bottom floor
        Elevator elevator = sim.getElevatorManager().getElevators().get(0);
        assertEquals("elevator on ground floor)", ElevatorSimulator.GROUND_FLOOR, elevator.getFloor());
        assertTrue("elevator is empty", elevator.isEmpty());
    }

    @Test
    
    public void testSimpleWithOverlappingRiders (){
        // create three people getting on ground floor and going to floor three
        HashMap<Integer, ArrayList<ElevatorRequest>> map = new HashMap<Integer, ArrayList<ElevatorRequest>>();
        ArrayList<ElevatorRequest> requestList = new ArrayList<ElevatorRequest>();
        ElevatorRequest req = new ElevatorRequest (10, 0, 3);
        requestList.add(req);
        
        map.put(10, requestList);
        ElevatorRequest req2 = new ElevatorRequest(11, 1, 2);
        ArrayList<ElevatorRequest> reqList2 = new ArrayList<ElevatorRequest>();
        reqList2.add(req2);
        map.put(11, reqList2);

        // now have simple test data, lets run
        ElevatorSimulator sim  = new ElevatorSimulator(110);
        sim.startSimulation(map); //metrics are reset so lets check some metrics and see how we did

        ElevatorMetrics.printOutMetrics();
        assertEquals("total rides", 1, ElevatorMetrics.getTotalRides());
        assertEquals("total requests", 2,ElevatorMetrics.getTotalRequests());
        assertEquals("avarege riders per ride", 2, ElevatorMetrics.getAvgNumberRidersPerRide(), 0.0001);
        assertEquals("total stops", 3,ElevatorMetrics.getTotalStops());
        assertEquals("only one elevator", 1, sim.getElevatorManager().getElevators().size());
        assertEquals("empty traversals", 1, ElevatorMetrics.getEmptyTraversals());
        // the on elevator should be at the bottom floor
        Elevator elevator = sim.getElevatorManager().getElevators().get(0);
        assertEquals("elevator on ground floor)", ElevatorSimulator.GROUND_FLOOR, elevator.getFloor());
        assertTrue("elevator is empty", elevator.isEmpty());
    }

    @Test
  
    public void testSimpleWithOverlappingRidersNoEmptyTraversals (){
        // create three people getting on ground floor and going to floor three
        HashMap<Integer, ArrayList<ElevatorRequest>> map = new HashMap<Integer, ArrayList<ElevatorRequest>>();
        ArrayList<ElevatorRequest> requestList = new ArrayList<ElevatorRequest>();
        ElevatorRequest req = new ElevatorRequest (10, 0, 3);
        requestList.add(req);
        
        map.put(10, requestList);
        ElevatorRequest req2 = new ElevatorRequest(11, 1, 2);
        ArrayList<ElevatorRequest> reqList2 = new ArrayList<ElevatorRequest>();
        reqList2.add(req2);
        map.put(11, reqList2);

        ElevatorRequest req3 = new ElevatorRequest(73, 2, 0);
        ArrayList<ElevatorRequest> reqList3 = new ArrayList<ElevatorRequest>();
        reqList3.add(req3);
        map.put(73, reqList3);
        // now have simple test data, lets run
        ElevatorSimulator sim  = new ElevatorSimulator(130);
        sim.startSimulation(map); //metrics are reset so lets check some metrics and see how we did

        ElevatorMetrics.printOutMetrics();
        assertEquals("total rides", 2, ElevatorMetrics.getTotalRides());
        assertEquals("total requests", 3,ElevatorMetrics.getTotalRequests());
        assertEquals("avarege riders per ride", 1.5, ElevatorMetrics.getAvgNumberRidersPerRide(), 0.0001);
        assertEquals("total stops", 4,ElevatorMetrics.getTotalStops());
        assertEquals("only one elevator", 1, sim.getElevatorManager().getElevators().size());
        assertEquals("empty traversals", 0, ElevatorMetrics.getEmptyTraversals());
        // the on elevator should be at the bottom floor
        Elevator elevator = sim.getElevatorManager().getElevators().get(0);
        assertEquals("elevator on ground floor)", ElevatorSimulator.GROUND_FLOOR, elevator.getFloor());
        assertTrue("elevator is empty", elevator.isEmpty());
    }

    @Test

    public void testSimpleLotsofPeopleatOnceTwoDirections (){
        // create three people getting on ground floor and going to floor three
        HashMap<Integer, ArrayList<ElevatorRequest>> map = new HashMap<Integer, ArrayList<ElevatorRequest>>();
        ArrayList<ElevatorRequest> requestList = new ArrayList<ElevatorRequest>();
        for (int i=0; i<20; i++){
            ElevatorRequest req = new ElevatorRequest (10, 0, 1);
            requestList.add(req);
        }
        
        map.put(10, requestList);

        // now have simple test data, lets run
        ElevatorSimulator sim  = new ElevatorSimulator(50);
        sim.startSimulation(map); //metrics are reset so lets check some metrics and see how we did

        ElevatorMetrics.printOutMetrics();
        assertEquals("total rides", 2, ElevatorMetrics.getTotalRides());
        assertEquals("total requests", 20,ElevatorMetrics.getTotalRequests());
        assertEquals("avarege riders per ride", 10, ElevatorMetrics.getAvgNumberRidersPerRide(), 0.0001);
        assertEquals("total stops", 2,ElevatorMetrics.getTotalStops());
        assertEquals("two elevator", 2, sim.getElevatorManager().getElevators().size());
        assertEquals("empty traversals", 2, ElevatorMetrics.getEmptyTraversals());
        // the on elevator should be at the bottom floor
        Elevator elevator = sim.getElevatorManager().getElevators().get(0);
        assertEquals("elevator on ground floor)", ElevatorSimulator.GROUND_FLOOR, elevator.getFloor());
        assertTrue("elevator is empty", elevator.isEmpty());
        assertEquals("elevator on ground floor)", ElevatorSimulator.GROUND_FLOOR, sim.getElevatorManager().getElevators().get(1).getFloor());
    }

    @Test

    public void testSimpleLotsofPeopleatOnce (){
        // create three people getting on ground floor and going to floor three
        HashMap<Integer, ArrayList<ElevatorRequest>> map = new HashMap<Integer, ArrayList<ElevatorRequest>>();
        ArrayList<ElevatorRequest> requestList = new ArrayList<ElevatorRequest>();
        for (int i=0; i<15; i++){
            ElevatorRequest req = new ElevatorRequest (10, 0, 1);
            requestList.add(req);
        }
        for (int i=0; i<5; i++){
            ElevatorRequest req = new ElevatorRequest (10, 5, 4);
            requestList.add(req);
        }
        
        map.put(10, requestList);

        // now have simple test data, lets run
        ElevatorSimulator sim  = new ElevatorSimulator(100);
        sim.startSimulation(map); //metrics are reset so lets check some metrics and see how we did

        ElevatorMetrics.printOutMetrics();
        assertEquals("total rides", 3, ElevatorMetrics.getTotalRides());
        assertEquals("total requests", 20,ElevatorMetrics.getTotalRequests());
        assertEquals("avarege riders per ride", 6.6666, ElevatorMetrics.getAvgNumberRidersPerRide(), 0.0001);
        assertEquals("total stops", 4,ElevatorMetrics.getTotalStops());
        assertEquals("three elevators", 3, sim.getElevatorManager().getElevators().size());
        assertEquals("empty traversals", 3, ElevatorMetrics.getEmptyTraversals());
        // the on elevator should be at the bottom floor
        Elevator elevator = sim.getElevatorManager().getElevators().get(0);
        assertEquals("elevator on ground floor)", ElevatorSimulator.GROUND_FLOOR, elevator.getFloor());
        assertTrue("elevator is empty", elevator.isEmpty());
        assertEquals("elevator on ground floor)", ElevatorSimulator.GROUND_FLOOR, sim.getElevatorManager().getElevators().get(1).getFloor());
    }
}