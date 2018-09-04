package com.diamond;

public class ElevatorMetrics {

    private static long totalWaitTime = 0;
    private static int totalRequests = 0;
    private static int totalRides = 0; // number of elevator rides ;a ride is one direction;
    private static int totalEmptyTraversals = 0;  //not sure how to count this
    private static int totalStops= 0; // how many time all elevators stop, combine with total rides and number of riders
       // to get number of stops in each ride per rider.

    public static void addWaitTime (long waitTime){
        ElevatorMetrics.totalWaitTime = ElevatorMetrics.totalWaitTime + waitTime;
    }

    public static void incrementTotalRequests(){
        ElevatorMetrics.totalRequests++;
    }

    public static void incrementTotalRides(){
        ElevatorMetrics.totalRides++;
    }

    public static void incrementEmptyTraversals (){
        ElevatorMetrics.totalEmptyTraversals++;
    }

    public static void incrementTotalStops(){
        ElevatorMetrics.totalStops++;
    }

    public static int getTotalRides (){
        return totalRides;
    }

    public static int getTotalRequests(){
        return totalRequests;
    }

    public static int getTotalStops(){
        return totalStops;
    }

    public static int getEmptyTraversals(){
        return totalEmptyTraversals;
    }

    public static float getAvgNumberRidersPerRide(){
        if (totalRides != 0){
            return (float)ElevatorMetrics.totalRequests/ElevatorMetrics.totalRides;
        } else{
            return 0;
        }
    }

    public static float getStopsPerRidePerRider(){
        if (totalRides > 0 && totalRequests > 0){
            return (ElevatorMetrics.totalStops/(ElevatorMetrics.totalRides*ElevatorMetrics.totalRequests));
        }
        return 0;
    }
    public static void printOutMetrics(){
        System.out.println("*************** Metrics *****************");
        System.out.println("total reqeusts: "+ ElevatorMetrics.totalRequests);
        System.out.println("total rides: "+ totalRides);
        System.out.println("total stops: "+ totalStops);
        if (totalRequests != 0){
            System.out.println("Wait Time Per Request: "+ ElevatorMetrics.totalWaitTime/ElevatorMetrics.totalRequests);
        } else{
            System.out.println("Wait Time not calculated due to zero value total requests");
        }
        
        System.out.println("Number of Riders on Each ride (average): "+ getAvgNumberRidersPerRide());
        
        System.out.println("Idle Traversals: "+ ElevatorMetrics.totalEmptyTraversals);
        System.out.println("Number of stops in each ride per rider (average): "+ getStopsPerRidePerRider());
    }

	public static void reset() {
        totalWaitTime = 0;
        totalRequests = 0;
        totalRides = 0;
        totalEmptyTraversals = 0;
        totalStops = 0;
	}
}