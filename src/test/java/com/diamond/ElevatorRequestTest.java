package com.diamond;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ElevatorRequestTest {

    @Test
    public void testGetDirection(){
        ElevatorRequest req = new ElevatorRequest(3000,0, 3);

        assertEquals (ElevatorEnums.Direction.UP, req.getDirection());
    }
}