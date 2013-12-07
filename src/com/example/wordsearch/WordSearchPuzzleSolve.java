package com.example.wordsearch;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.pictopuzzle.R;

public class WordSearchPuzzleSolve extends Activity {

	HashMap<String, WordSearchResult> searchResults;
	WordSearchDrawer drawer;
	SurfaceView surface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_search_puzzle_solve);
		
		drawer = new WordSearchDrawer();
		
		String BoardData = (String) getIntent().getExtras().get("BOARD_DATA");
		
		
		
		int i = 0;
		drawer.wordSearchData = new char[BoardData.split("\n").length][];
		for( String s :  BoardData.split("\n") )
		{
			Log.e("board line", "" + s);
			drawer.wordSearchData[i] = s.toCharArray();
			i++;
		}

		searchResults = new HashMap<String, WordSearchResult>();
		
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
		

		
		surface = (SurfaceView)findViewById(R.id.surfaceView1);
		SurfaceHolder surfaceHolder = surface.getHolder();
		
		surfaceHolder.addCallback( drawer );
		
		
		// Add items to list view
		final ScrollView listview = (ScrollView) findViewById(R.id.scrollView1);
		
		LinearLayout layout = (LinearLayout)listview.getChildAt(0);
		
		WordSearchSolver solver = new WordSearchSolver(drawer.wordSearchData, dict);
		solver.solve();
		Vector<WordSearchResult> results = solver.getResult();
		
		for ( WordSearchResult r : results )
		{
			searchResults.put( r.getWord(), r );
		}
		
		
		for ( String s : searchResults.keySet() )
		{
			CheckBox cb = new CheckBox(getApplicationContext());
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					String text = buttonView.getText().toString();
					if ( isChecked )
					{
						drawer.circledWords.put(text, searchResults.get(text));
					}
					else 
					{
						drawer.circledWords.remove(text);
					}
					drawer.draw( surface.getHolder() );
					
				}
			});
			cb.setText(s);
			cb.setTextColor(Color.BLACK);
			layout.addView(cb);
		}

	    
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_search_puzzle_solve, menu);
		return true;
	}

}
