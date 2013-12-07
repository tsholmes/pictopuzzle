package com.example.wordsearch;

public enum Direction {
	
	UP(0), UP_RIGHT(1), RIGHT(2), DOWN_RIGHT(3), DOWN(4), DOWN_LEFT(5), LEFT(6), UP_LEFT(7);
	private int dir;
	
	private Direction(int d){
		dir = d;
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
