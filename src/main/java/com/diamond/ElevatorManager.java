package com.diamond;

import java.util.ArrayList;

// Manage the state of all elevators, need to decide where ; add optimzation later.


public class ElevatorManager {

    private ArrayList<Elevator> elevators = new ArrayList<Elevator>();

    public ElevatorManager(){
        // start with one elevator - at bottom to go up.
        Elevator newElevator = new Elevator(true, 1);
        elevators.add(newElevator);
    }
   
    public ArrayList<Elevator> getElevators(){
        return this.elevators;
    }
	
	
    public void addRequests(int currentTime, ArrayList<ElevatorRequest> arrayList) {
        // need to assign reqeusts to each elevator;
        // find elevator going up/down and request to it if there is room - for now
        // simple count of max size then will get
        // better on maximizing utilization.
        if (arrayList != null) {
            ArrayList<ElevatorRequest> toRemove = new ArrayList<ElevatorRequest> ();
            // determine how many more elevators we need 
           
            for (ElevatorRequest request : arrayList) {
                for (Elevator elevator : this.elevators) {
                    
                    // go through elevators find one going in right direction with capacity, if none
                    // available, make another available.
                    if (elevator.hasCapacity() && elevator.isRightDirection(request)) {
                        elevator.addRequest(request);
                        toRemove.add(request);
                    }
                }
            }
            arrayList.removeAll(toRemove);
            // now process the remaining ones and add to new elevators since we couldn't add these.
            processRemainingRequests(arrayList);
        }
    }

    private void processRemainingRequests (ArrayList<ElevatorRequest> requests){
        // so we want to go through the requests - assume we need to create an elevator - add to it until filled or we need to add another - at most one up one down at a time
        // and yes this is not the most efficient way but it works.
        if (!requests.isEmpty()){
            ArrayList<Elevator> newElevators = new ArrayList<Elevator>();

         //   newElevators.add(AddNewElevator(requests.get(0)));
         //this.elevators.add(AddNewElevator(requests.get(0)));

            
            for (ElevatorRequest request: requests){
                // get the first request, get direction and create an elevator for it
                // go through all new elevators and add the request if it matches on if not, add a new one.
                boolean addedRequest = false;
                for (Elevator el: newElevators){
                    if (el.hasCapacity() && el.isRightDirection(request)) {
                        el.addRequest(request);
                        addedRequest= true;
                    } 
                }
                if (!addedRequest){
                        Elevator newEl = AddNewElevator (request);
                        newElevators.add(newEl);
                        newEl.addRequest(request);
                        this.elevators.add(newEl);
                }
                
            }
            // 
        }
    }

    private Elevator AddNewElevator(ElevatorRequest req){
        Elevator newElevator = new Elevator(true, this.elevators.size()+1);
        newElevator.setDirection(req.getDirection());
        return newElevator;
    }
/*	private void createNewElevators(ArrayList<ElevatorRequest> arrayList) {
        // assume arrayList is not null
        // get total capacity in elevators now
        int totalAvailableUp = 0, totalAvailableDown = 0;

        for (Elevator elevator: this.elevators){
            if (elevator.getDirection() == ElevatorEnums.Direction.UP){
                totalAvailableUp += elevator.getAvailableCapacity();
            } else {
                totalAvailableDown +=elevator.getAvailableCapacity();
            }

        }
        int currentElevatorCount = this.elevators.size();

        // now go through the the requests and get up and down requests totals
        // then see if we have enough capacity and if not - add elevators going up/down (down start at top  - magic :-)
        if (arrayList.size() > totalAvailableCurrent){
            // add some elevators
            for (int i=0; i< (arrayList.size() - totalAvailableCurrent)/Elevator.MAX_ELEVATOR_CAPACITY;i++){
                Elevator elevator = new Elevator(true, currentElevatorCount+i+1);
                this.elevators.add(elevator);
            }
        }
	}*/

	public void moveElevators(int currentTime) {
        // go through each elevator - check state and decide what to do, current time is just to pass on
        for (Elevator elevator: this.elevators){
            if (elevator.getStatus()){  // if elevator is in service is not we don't care about it
                elevator.action(currentTime);  // elevator should do something - wait until filled, move to next floor etc.
            }

        }

    }
    
  
}