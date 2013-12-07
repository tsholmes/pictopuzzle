package com.example.pictopuzzle;

import java.util.HashMap;

public class Constants {
	
	public static HashMap<String, PuzzleType> puzzleTypeMap;
	static 
	{
		puzzleTypeMap = new HashMap<String, PuzzleType>();
		for ( PuzzleType p : PuzzleType.values() )
		{
			puzzleTypeMap.put(p.stringName, p);
		}
	}

}
