package com.example.wordsearch;

import android.graphics.Point;

public class WordSearchResult extends PuzzleResult {
	
	private String word;
	private Direction dir;
	private int row;
	private int col;
	
	public WordSearchResult(String a, Direction b, int c, int d){
		word = a;
		dir = b;
		row = c;
		col = d;
	}
	
	public String getWord(){
		return word;
	}
	
	public Direction getDir(){
		return dir;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}

	public Point[] convertToPoints() {
		Point[] ret = new Point[2];
		ret[0] = new Point( col, row);
		ret[1] = new Point( col + dir.x * (word.length() -1), row + dir.y * (word.length()-1) );
		
		return ret;
	}
}
