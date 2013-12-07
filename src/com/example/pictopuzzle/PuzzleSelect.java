package com.example.pictopuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class PuzzleSelect extends Activity {

	LinearLayout puzzlesLayout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puzzle_select);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		
		ScrollView scroll = (ScrollView) findViewById(R.id.scrollView1);
		
		for ( PuzzleType p : PuzzleType.values() )
		{
			Button b = new Button(this);
			b.setText( p.stringName );
			b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Button b = (Button)v;
					PuzzleType p = Constants.puzzleTypeMap.get(b.getText());
					
					Intent intent = new Intent(PuzzleSelect.this, p.splashClass);
					startActivity(intent);
					
				}
			});
			((LinearLayout)scroll.getChildAt(0)).addView(b);
		}
		
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.puzzle_select, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
