package com.example.pictopuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class WordSearchPuzzleSolve extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_search_puzzle_solve);
		
		String BoardData = (String) getIntent().getExtras().get("BOARD_DATA");
		((TextView)findViewById(R.id.textField1)).setText(BoardData);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_search_puzzle_solve, menu);
		return true;
	}

}
