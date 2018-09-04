package com.diamond;

public class ElevatorRequest {
 private int start;  //start floor
 private int end;  // end floor
 private int startTime; // request start time, time the button to call to a floor is pushed
 private int enterTime;
 private int exitTime;

 public ElevatorRequest (int _startTime, int _start, int _end){
     this.startTime = _startTime;
     this.start = _start;
     this.end = _end;
 }

 /**
 * @return the exitTime
 */
public int getExitTime() {
	return exitTime;
}

/**
 * @param exitTime the exitTime to set
 */
public void setExitTime(int exitTime) {
	this.exitTime = exitTime;
}

/**
 * @return the enterTime
 */
public int getEnterTime() {
	return enterTime;
}

/**
 * @param enterTime the enterTime to set
 */
public void setEnterTime(int enterTime) {
	this.enterTime = enterTime;
}

/**
 * @return the startTime
 */
public int getStartTime() {
	return startTime;
}
/**
 * @return the start
 */
public int getStart() {
	return start;
}
/**
 * @param start the start to set
 */
public void setStart(int start) {
	this.start = start;
}
/**
 * @return the end
 */
public int getEnd() {
	return end;
}
/**
 * @param end the end to set
 */
public void setEnd(int end) {
	this.end = end;
}
/**
 * @param startTime the startTime to set
 */
public void setStartTime(int startTime) {
	this.startTime = startTime;
}

public ElevatorEnums.Direction getDirection(){
    ElevatorEnums.Direction direction = ElevatorEnums.Direction.UP; //default to up
    if (this.end < this.start){
        direction = ElevatorEnums.Direction.DOWN;
    }
    return direction;
}

}