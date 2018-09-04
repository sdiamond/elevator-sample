package com.diamond;

import java.util.ArrayList;

import com.diamond.ElevatorEnums.Direction;
import com.diamond.ElevatorEnums.ElevatorState;
import com.diamond.exception.ElevatorMaxLoadException;

public class Elevator {

    public static int MAX_ELEVATOR_CAPACITY = 10;
    private static int ELEVATOR_MOVE_WAIT_TIME = 10;

    private int elevatorNumber;
    private int load = 0;
    private Boolean status; // True in use, false not
    private Direction direction; // direction elevator is heading
    private int floor = 0;
    private ArrayList<ElevatorRequest> riders = new ArrayList<ElevatorRequest>(MAX_ELEVATOR_CAPACITY);
    private ArrayList<ElevatorRequest> requests = new ArrayList<ElevatorRequest>();
    private ElevatorState state = ElevatorState.AVAILABLE;
    private int nextActionTime = 0;
    private boolean isCurrentEmptyTraversal;
    private int targetFloor= 0;

    public Elevator() {
        this.status = Boolean.TRUE;
        this.direction = Direction.UP;
        this.isCurrentEmptyTraversal = true;
    }

    public Elevator(Boolean status, int number) {
        this.status = Boolean.TRUE;
        this.direction = Direction.UP;
        this.elevatorNumber = number;
        this.isCurrentEmptyTraversal = true;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public int getFloor() {
        return this.floor;
    }

    public void setNextActionTime(int nextTime) {
        this.nextActionTime = nextTime;
    }

    public void setState(ElevatorState _state) {
        this.state = _state;
    }

    public ElevatorState getState() {
        return this.state;
    }

    public void setFloor(int _floor) {
        this.floor = _floor;
    }

    public void setStatus(Boolean _status) {
        this.status = _status;
    }

    public int getLoad() {
        return this.load;
    }

    public void increaseLoad() throws ElevatorMaxLoadException {
        if (load == MAX_ELEVATOR_CAPACITY) {
            throw new ElevatorMaxLoadException();
        }
        this.load++;
    }

    public void decreaseLoad() {
        if (this.load > 0) {
            this.load--;
        }
    }

    public void setDirection(Direction _direction) {
        this.direction = _direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void moveFloor(int currentTime) {

        

        // if no riders or requests and at top or bottom, just be available and don't do
        // anything
        if (this.riders.isEmpty() && this.requests.isEmpty()
                && (floor == ElevatorSimulator.TOP_FLOOR || floor == ElevatorSimulator.GROUND_FLOOR)) {
            // set status to available and do nothing..
            if (this.isCurrentEmptyTraversal && this.state == ElevatorState.MOVING){// are we doing this too often?
                ElevatorMetrics.incrementEmptyTraversals();
            }
            this.setState(ElevatorState.AVAILABLE);
            this.nextActionTime = 0; // so basically starting over.
            if (floor == ElevatorSimulator.TOP_FLOOR){
                this.direction = Direction.DOWN;
            } else{
                this.direction = Direction.UP;
            }
            // means we are done a traversal so 
           
            // don't care about direction as we'll fix that when start m
            return; // TODO clean up return, I don't like them here..
        }

        if (this.riders.isEmpty() && this.requests.isEmpty()){
            // if current floor is above halfway point go up otherwise go down
            if (this.floor <= ElevatorSimulator.TOP_FLOOR/2){
                this.direction = Direction.DOWN;
            } else {
                this.direction = Direction.UP;
            }
            this.isCurrentEmptyTraversal = true;
        }
        int amountToMove = 1;
        if (this.direction == Direction.DOWN && (this.targetFloor <= this.floor)) {
            amountToMove = -1;
        }

        if (this.direction == Direction.DOWN && this.targetFloor -1 == this.floor && this.riders.isEmpty()) {
            ElevatorMetrics.incrementEmptyTraversals();
        }

        if (this.targetFloor == this.floor){
            this.targetFloor = ElevatorSimulator.GROUND_FLOOR;
        }
        this.floor += amountToMove;
        if (this.anyToLoadUnload()) {
            this.state = ElevatorState.LOAD_UNLOAD;       
        } else {
            this.state = ElevatorState.MOVING;
        }
        this.nextActionTime = currentTime + Elevator.ELEVATOR_MOVE_WAIT_TIME;
    }

    public void processRiders(int currentTime) {
        // so now move riders and we need to figure out how to keep track of time - will
        // have to add that next
        // first remove riders who are at floor and direction we want
        ArrayList<ElevatorRequest> toRemove = new ArrayList<ElevatorRequest>();
        for (ElevatorRequest rider : this.riders) {
            if (rider.getDirection() == this.direction && rider.getEnd() == this.floor) {
                // so get rider off the floor
                toRemove.add(rider);
                rider.setExitTime(currentTime); // TODO don't care about this I think
            }
        }
        this.riders.removeAll(toRemove);

        // add riders
        toRemove.clear();
        boolean noRiders = this.riders.isEmpty(); // true if empty.

        for (ElevatorRequest request : this.requests) {
            if (request.getDirection() == this.direction && request.getStart() == this.floor) {
                toRemove.add(request);
                this.loadRequest(request, currentTime);
            }
        }
        if (noRiders && !this.riders.isEmpty()) {
            ElevatorMetrics.incrementTotalRides(); // we have a ride as we have added riders where there were none.
        }
        this.requests.removeAll(toRemove);
        this.isCurrentEmptyTraversal = false;

    }

    public boolean addRequest(ElevatorRequest request) {
        this.requests.add(request);
        this.isCurrentEmptyTraversal = false;
        // get target floor - if up then lowest number if down then highest number
        if (this.direction == Direction.UP){
            targetFloor = Math.min(this.floor, request.getStart());
        } else {
            targetFloor = Math.max(this.floor, request.getStart());
        }
        return true;
    }


    public boolean hasCapacity() {
        //todo this will need to get more complicated to be more efficient but we aren't there yet
        return this.riders.size()+this.requests.size() < MAX_ELEVATOR_CAPACITY; // means we can add one more.
    }

    public boolean isRightDirection(ElevatorRequest request) {
        boolean ret = false;
        // conditions if elevator is going in right direction and is above/below floor
        // return true
        // TODO add other conditions in here as we need it
        if (request.getDirection() == this.direction) {
            if ((this.direction == Direction.UP && request.getStart() >= this.floor)
                    || (this.direction == Direction.DOWN && request.getStart() <= this.floor) ||
                    (this.direction == Direction.DOWN && this.floor == ElevatorSimulator.GROUND_FLOOR)) // this means we will have to move up first to then start down..acle
                ret = true;
        }
        return ret;
    }

    public void action(int currentTime) {
        // need current state first we are only going to worry about an elevator moving
        // up or down a floor
        // TODO more this debug code out
        if (currentTime == 0) {
            this.printOutElevatorState(currentTime);
        }
        
        if (this.state == ElevatorState.AVAILABLE) {
            if (loadRequestsToStartThisFloor(currentTime)) {
                this.state = ElevatorState.MOVING;
                this.nextActionTime = currentTime + ELEVATOR_MOVE_WAIT_TIME;
            } else {
                this.moveFloor(currentTime);
            }
        } else {
            loadRequestsToStartThisFloor(currentTime);
            if (this.nextActionTime == currentTime){
                if (this.state == ElevatorState.MOVING) {
                    
                    moveFloor(currentTime);
                    this.nextActionTime = currentTime + ELEVATOR_MOVE_WAIT_TIME;
                    // if any to move get off/on next floor then set to load/unload
                    if (anyToLoadUnload()) {
                        this.state = ElevatorState.LOAD_UNLOAD;
                        // means a stop so add to metrics
                        ElevatorMetrics.incrementTotalStops();

                    }
                    // if no one on, set to available and wait , I think.

                } else if (this.state == ElevatorState.LOAD_UNLOAD) {
                    // so now lets load and unload, then set to move and wait
                    // ELEVATOR_MOVE_WAIT_TIME seconds
                    // unload first
                    this.processRiders(currentTime);
                    if (this.riders.isEmpty() && this.requests.isEmpty()) {
                        this.state = ElevatorState.AVAILABLE;
                        this.nextActionTime = 0; // reset to be avaiblle at anytime
                    } else {
                        this.state = ElevatorState.MOVING;
                        this.nextActionTime = currentTime + ELEVATOR_MOVE_WAIT_TIME;
                    }
                }
            }
        }

        
        this.printOutElevatorState(currentTime);

    }

    private boolean anyToLoadUnload() {
        boolean anyToMove = false;
        // check if any on floor getting off
        for (ElevatorRequest request : this.requests) {
            if (request.getStart() == this.floor) {
                anyToMove = true;
                break;
            }
        }
        for (ElevatorRequest request : this.riders) {
            if (request.getEnd() == this.floor) {
                anyToMove = true;
                break;
            }
        }
        return anyToMove;
    }

    private boolean loadRequestsToStartThisFloor(int currentTime) {
        boolean ret = false;
        ArrayList<ElevatorRequest> toRemove = new ArrayList<ElevatorRequest>();

        boolean noRiders = this.riders.isEmpty();
        for (ElevatorRequest request : this.requests) {
            if (this.floor == request.getStart()) {
                toRemove.add(loadRequest(request, currentTime));
                ret = true;
            }
        }
        this.requests.removeAll(toRemove); // avoid concurrent modification
        // if adding first riders then we now have a new 'ride'
        if (!riders.isEmpty() && noRiders){
            ElevatorMetrics.incrementTotalRides();
        }
        return ret;
    }

    public ElevatorRequest loadRequest(ElevatorRequest request, int currentTime) {
        this.riders.add(request);
        request.setEnterTime(currentTime);
        // this.requests.remove(request);
        ElevatorMetrics.incrementTotalRequests();
        // request now on elevator.
        return request;
    }

    public boolean isEmpty() {
        return this.riders.size() == 0;
    }

    public void printOutElevatorState(int currentTime) {
      /*  System.out.println("time: " + currentTime + " No: " + this.elevatorNumber + " state: " + this.state
                + " direction: " + this.direction + " passengers: " + this.riders.size() + " waiting: "
                + this.requests.size() + " floor: " + this.floor + " nextActionTime: " + this.nextActionTime + " emptytraversal: " + this.isCurrentEmptyTraversal
                + " targetfloor: " + this.targetFloor);*/
    }

	public int getAvailableCapacity() {
        return MAX_ELEVATOR_CAPACITY - this.riders.size() -this.requests.size();
    }
    
    
}
