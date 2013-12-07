
package com.example.wordsearch;

public enum Direction {
	
	UP(0, 0, -1), UP_RIGHT(1, 1, -1), RIGHT(2, 1, 0), DOWN_RIGHT(3, 1, 1), DOWN(4, 0, 1), DOWN_LEFT(5, -1, 1), LEFT(6, -1, 0), UP_LEFT(7, -1, -1);
	public final int dir;
	public final int x;
	public final int y;
	private Direction(int d, int x, int y){
		dir = d;
		this.x = x; 
		this.y = y;
	}
	
	public String toString(){
		
		switch(dir){
		case 0:
			return "UP";
		case 1:
			return "UP_RIGHT";
		case 2:
			return "RIGHT";
		case 3:
			return "DOWN_RIGHT";
		case 4:
			return "DOWN";
		case 5:
			return "DOWN_LEFT";
		case 6:
			return "LEFT";
		case 7:
			return "UP_LEFT";
		default:
			return "ERROR";
		}
	}
}