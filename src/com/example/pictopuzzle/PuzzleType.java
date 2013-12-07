package com.example.pictopuzzle;

import com.example.wordsearch.WordSearchSplash;

import android.app.Activity;

public enum PuzzleType {
	WORD_SEARCH("Word Search", WordSearchSplash.class), 
	SUDOKU("Sudoku", SudokuSplash.class);
	
	PuzzleType(String s, Class<? extends Activity> c)
	{
		stringName = s;
		splashClass = c;
	}
	
	
	public final String stringName;
	public final Class<? extends Activity> splashClass;
	
}
