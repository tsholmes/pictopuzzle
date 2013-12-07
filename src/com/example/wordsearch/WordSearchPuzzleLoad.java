package com.example.wordsearch;

import com.example.pictopuzzle.R;
import com.example.pictopuzzle.R.id;
import com.example.pictopuzzle.R.layout;
import com.example.pictopuzzle.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class WordSearchPuzzleLoad extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_search_puzzle_load);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_search_puzzle_load, menu);
		return true;
	}

    public void solveClick(View view) {
    	Intent intent = new Intent( this, WordSearchPuzzleSolve.class );
    	String boardData = ((EditText)findViewById(R.id.editText1)).getText().toString();
    	intent.putExtra("BOARD_DATA", boardData );
    	startActivity(intent);
        // Do something in response to button
    }
}
