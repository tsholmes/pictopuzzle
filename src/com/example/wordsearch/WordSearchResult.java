package com.example.wordsearch;

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
}
