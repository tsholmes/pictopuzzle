package com.example.wordsearch;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pictopuzzle.R;

public class WordSearchPuzzleSolve extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_search_puzzle_solve);
		
		WordSearchDrawer drawer = new WordSearchDrawer();
		
		String BoardData = (String) getIntent().getExtras().get("BOARD_DATA");
		
		
		
		int i = 0;
		drawer.wordSearchData = new char[BoardData.split("\n").length][];
		for( String s :  BoardData.split("\n") )
		{
			Log.e("", "" + s);
			drawer.wordSearchData[i] = s.toCharArray();
			i++;
		}
//		
//		drawer.wordSearchData = new char[3][3];
//		drawer.wordSearchData[0][0] = 'a';
//		drawer.wordSearchData[0][1] = 'a';
//		drawer.wordSearchData[0][2] = 'a';
//		drawer.wordSearchData[1][0] = 'a';
//		drawer.wordSearchData[1][1] = 'a';
//		drawer.wordSearchData[1][2] = 'a';
//		drawer.wordSearchData[2][0] = 'a';
//		drawer.wordSearchData[2][1] = 'a';
//		drawer.wordSearchData[2][2] = 'a';
		
		drawer.circledWords = new ArrayList<Point[]>();
		Scanner reader = null;
		try {
			reader = new Scanner( new BufferedInputStream(getAssets().open("words.txt") ));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashSet<String> dict = new HashSet<String>();
		while ( reader.hasNext() )
		{
			String s = reader.next();
			if (s.length() >= 3)
				dict.add( s );
			//Log.e("", "" + s);
		}
		
		WordSearchSolver solver = new WordSearchSolver(drawer.wordSearchData, dict);
		solver.solve();
		Vector<WordSearchResult> results = solver.getResult();
		
		for ( WordSearchResult r : results )
		{
			Log.e("", "found word");
			Point[] sol = r.convertToPoints();
			drawer.circledWords.add(sol);
		}
		
		
		SurfaceView surface = (SurfaceView)findViewById(R.id.surfaceView1);
		SurfaceHolder surfaceHolder = surface.getHolder();
		
		surfaceHolder.addCallback( drawer );
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_search_puzzle_solve, menu);
		return true;
	}

}
