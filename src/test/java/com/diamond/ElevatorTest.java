package com.diamond;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.diamond.ElevatorEnums.Direction;
import com.diamond.ElevatorEnums.ElevatorState;
import com.diamond.exception.ElevatorMaxLoadException;

import org.junit.Test;


/**
 * Unit test for Elevator
 */
public class ElevatorTest 
{
    
    @Test
    public void testIncreaseLoad()
    {
        Elevator e = new Elevator();
        assertEquals(0, e.getLoad());
        e.decreaseLoad();
        assertEquals(0, e.getLoad());
    }

    @Test (expected = ElevatorMaxLoadException.class)
    public void testMaxLoad() throws ElevatorMaxLoadException {
        Elevator elevator = new Elevator();
	
        for (int i=0; i<Elevator.MAX_ELEVATOR_CAPACITY; i++){
            elevator.increaseLoad();
        }
        assertEquals (Elevator.MAX_ELEVATOR_CAPACITY, elevator.getLoad());
        elevator.increaseLoad(); // should get exception here
    }

    @Test
    public void testMoveFloor(){
        Elevator elevator= new Elevator();
        elevator.setDirection(Direction.DOWN);
        // so now on bottom flow and going down so send it down again and floor should be same
        // and direction should change
        elevator.moveFloor(30);
        assertEquals (Direction.UP, elevator.getDirection());
        assertEquals(0, elevator.getFloor());

    }

    @Test
    public void testHasCapacity(){
        Elevator elevator = new Elevator();
        assertTrue(elevator.hasCapacity());
    }

    @Test
    public void testIsRightDirection(){
        Elevator elevator = new Elevator();
        elevator.setDirection(Direction.UP);
        // so now elevator is on ground floor heading up
        ElevatorRequest req = new ElevatorRequest(3600, 0,3);
        assertTrue(elevator.isRightDirection(req));

        ElevatorRequest req1 = new ElevatorRequest(3610, 1, 4);
        assertTrue(elevator.isRightDirection(req1));
    }

    @Test
    public void testAction(){
        // set up elevator with on floor 3
        Elevator elevator = new Elevator();
        elevator.setDirection(Direction.UP);
        elevator.setFloor(3);
        ElevatorRequest req = new ElevatorRequest(3000, 0,4);
        elevator.setState(ElevatorState.MOVING);
        elevator.setNextActionTime(4000);
        elevator.loadRequest(req, 4000);
        elevator.action(4000);
        assertEquals("elevator state", ElevatorState.LOAD_UNLOAD, elevator.getState());
        elevator.action(4010); // time moves in 30 second increments
        assertEquals("elevator now in available state: ", ElevatorState.AVAILABLE, elevator.getState());
        assertTrue(elevator.isEmpty());
        elevator.action(4020);
        assertEquals("elevator now in available state: ", ElevatorState.MOVING, elevator.getState());
        assertTrue(elevator.isEmpty());
        assertEquals(Direction.DOWN, elevator.getDirection());
        elevator.action(4030);
        assertTrue(elevator.isEmpty());
        assertEquals(Direction.DOWN, elevator.getDirection());
        assertEquals(2, elevator.getFloor());
        elevator.action(4040);
        assertTrue(elevator.isEmpty());
        assertEquals(Direction.DOWN, elevator.getDirection());
        assertEquals(1, elevator.getFloor());
        elevator.action(4050);
        assertTrue(elevator.isEmpty());
        assertEquals(Direction.DOWN , elevator.getDirection());
        assertEquals(ElevatorSimulator.GROUND_FLOOR, elevator.getFloor());
        elevator.action(4060);
        assertTrue(elevator.isEmpty());
        assertEquals(Direction.UP , elevator.getDirection());
        assertEquals(ElevatorSimulator.GROUND_FLOOR, elevator.getFloor());

    }
}
