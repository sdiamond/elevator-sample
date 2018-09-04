# Problem Defintion
You have been hired by Acme Inc, to design and deliver an algorithm for optimal elevator management.  The company manages elevators in Building X and would like to have the most optimal software that manages 3 competing needs:
* Minimum wait time for any person requesting an elevator
* Carry the greatest number of riders possible on every ride to reduce operating costs (wear and tear, power, maintenance)
* Comfort of riders to minimize the number of stops for a ride.

Design an algorithm to manage the elevators with consideration to the above needs.

## Assumptions
* There are n number of elevators in the building.  Feel free to make assumption on n, if necessary.
* The building has 10 floors
* The elevator system is most heavily used during morning(6-9am) and evening(3-6pm) rush hours.  The most heavily used direction during morning is up and most heavily used direction during evening is down.

## Deliverables
1. Code or pseudo-code of the elevator management software.  While code is preferred, it is not required.  
2. Test cases simulating elevator requests in different scenarios and performance of those requests.  Possible performance metrics a) wait time per request b) number of riders in each ride c) number of idle elevator traversals d) number of stops in each ride per rider
3. Detailed README on the implementation

# Solution Notes
Assume written as a simulation so the following assumptions are known:
* Know all riders, floor calls and times at the beginning (it is input data)
* Each floor move and load/unload action takes time - so there is wait time built in.  Assume time in 30 second increments - that is each request or elevator move takes 30 seconds (note some tests shorten this to lessen debugging time)

## Implementation 
The implementation is written in Java 8 (My IDE test runner only supported Java 8, so that was chosen).  Maven was used to build and run tests.  A POM is included.



## Optimizations not written
* Did not optimize for rush hours - as written elevators go to bottom when empty.  Would add code to handle rush hour so in evening elevators to top when empty and have no more requests to handle.
* Did not unbound number of elevators.  So theoretically can have thousands of elevators if enough requests are made at the same time.
* Assignemts of requests to an elevator are done in a simple, not efficient algorithm.  There are at most 10 requests/riders assigned to on elevator.  There is no accounting for where the riders are going.  A more efficient algorithm would account for this by keep track of how full the elevator would be for each floor on the ride the elevator is taking.
* Didn't add good logging to code, need to add a logging framework but didn't have time.  So there are some System.out.println's which are not recommended.
* There are set of TODO's in the code defining other optimizations.