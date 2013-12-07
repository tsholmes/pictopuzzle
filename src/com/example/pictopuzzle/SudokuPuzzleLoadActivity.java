package com.example.pictopuzzle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.wordsearch.WordSearchPuzzleSolve;
import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Rotate;
import com.googlecode.leptonica.android.Scale;
import com.googlecode.tesseract.android.TessBaseAPI;

public class SudokuPuzzleLoadActivity extends Activity implements SurfaceHolder.Callback, Camera.PictureCallback {

	private SurfaceView cameraSurface;
	private SurfaceHolder holder;
	private Camera camera;
	
	private TessBaseAPI api;
	
	private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/pictopuzzle/";

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sudoku_puzzle_load);
		// Show the Up button in the action bar.
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.sudoku_puzzle_load, menu);
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

    private void setMaxSize(Parameters params) {
    	int max = 0;
    	int maxx = 0;
    	int maxy = 0;
    	for (Size sz : params.getSupportedPictureSizes()) {
    		int val = sz.width * sz.height;
    		if (val > max && val < 8000000) {
    			max = val;
    			maxx = sz.width;
    			maxy = sz.height;
    		}
    	}
    	params.setPictureSize(maxx, maxy);
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	
    	camera = Camera.open();
    	Parameters params = camera.getParameters();
    	setMaxSize(params);
    	params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
    	params.setFlashMode(Parameters.FLASH_MODE_TORCH);
    	camera.setParameters(params);
    	camera.setDisplayOrientation(90);
    	String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };
    	
    	String TAG = "pictopuzzle";
    	String lang = "eng";

    	for (String path : paths) {
    		File dir = new File(path);
    		if (!dir.exists()) {
    			if (!dir.mkdirs()) {
    				Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
    				return;
    			} else {
    				Log.v(TAG, "Created directory " + path + " on sdcard");
    			}
    		}

    	}

    	// lang.traineddata file with the app (in assets folder)
    	// You can get them at:
    	// http://code.google.com/p/tesseract-ocr/downloads/list
    	// This area needs work and optimization
    	if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
    		try {

    			AssetManager assetManager = getAssets();
    			InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
    			//GZIPInputStream gin = new GZIPInputStream(in);
    			OutputStream out = new FileOutputStream(DATA_PATH
    					+ "tessdata/" + lang + ".traineddata");

    			// Transfer bytes from in to out
    			byte[] buf = new byte[1024];
    			int len;
    			//while ((lenf = gin.read(buff)) > 0) {
    			while ((len = in.read(buf)) > 0) {
    				out.write(buf, 0, len);
    			}
    			in.close();
    			//gin.close();
    			out.close();

    			Log.v(TAG, "Copied " + lang + " traineddata");
    		} catch (IOException e) {
    			Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
    		}
    	}
    	
    	api = new TessBaseAPI();
		api.init(DATA_PATH, "eng");
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	
    	try {
    		camera.stopPreview();
        	Parameters params = camera.getParameters();
        	params.setFlashMode(Parameters.FLASH_MODE_OFF);
        	camera.setParameters(params);
    	} catch (Exception ex) {
    		
    	}
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	try {
    		camera.setPreviewDisplay(holder);
    		camera.startPreview();
        	Parameters params = camera.getParameters();
        	params.setFlashMode(Parameters.FLASH_MODE_TORCH);
        	camera.setParameters(params);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	
    	api.end();
    	
    	camera.stopPreview();
    	
    	camera.release();
    }

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
		try {
			camera.stopPreview();
		} catch (Exception ex) {
			
		}
		
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	private double squareSize;
	private double squareSizeTolerance;
	private static final int pixelIter = 1;
	
	public boolean isBlack ( int n )
	{
		return n == 1;
	}
	
	public Point isEmptySquare( Pix pix, double x, double y )
	{
		int left = -1;
		int right = -1;
		int top = -1;
		int bottom = -1;
		
		for ( int i = 0; i < squareSize; i += pixelIter )
		{
			if ( left == -1 && isBlack(getPixel(pix,(int)(x - i), (int)y)) )
			{
				left = (int)(x - i);
			}
			if ( right == -1 && isBlack(getPixel(pix,(int)(x + i), (int)y)) )
			{
				right = (int)(x + i);
			}
			if ( top == -1 && isBlack(getPixel(pix,(int)(x), (int)(y-i))) )
			{
				top = (int)(y - i);
			}
			if ( bottom == -1 && isBlack(getPixel(pix,(int)(x), (int)(y+i))) )
			{
				bottom = (int)(y + i);
			}
		}
		
		Point ret = null;
		if ( Math.abs( right - left - squareSize ) < squareSizeTolerance )
		{
			if ( Math.abs( bottom - top - squareSize ) < squareSizeTolerance )
			{
				ret = new Point( left, top );
			}
		}
		return ret;
	}
	
	private Point getCellLocation( Pix pix, double x, double y )
	{
		// get y less
		int i;
		for ( i = 0; i < 9; i ++ )
		{
			boolean foundLine = false;
			for ( int iter = 0; iter < squareSize; iter += pixelIter )
			{
				if ( isBlack(getPixel( pix, (int)(x + squareSize / 2), (int)(y + squareSize / 2 - i * squareSize - iter ))))
				{
					foundLine = true;
					break;
				}
			}
			if ( !foundLine ) break;
		}
		i--;
		
		int j;
		for ( j = 0; j < 9; j++ )
		{
			boolean foundLine = false;
			for ( int iter = 0; iter < squareSize; iter += pixelIter )
			{
				if ( isBlack(getPixel( pix, (int)(x + squareSize / 2 + j * squareSize + iter), (int)(y + squareSize / 2  ))))
				{
					foundLine = true;
					break;
				}
			}
			if ( !foundLine ) break;
		}
		j--;
		
		return new Point(j, i);
	}

	private int getPixel(Pix pix, int i, int j) {
		if ( i < 0 || i >= pix.getWidth() )
		{
			return 0;
		}
		if ( j < 0 || j >= pix.getHeight() )
		{
			return 0;
		}
		return pix.getPixel(i, j);
	}

	@Override
	public void onPictureTaken(byte[] pic, Camera camera) {
    	Parameters params = camera.getParameters();
    	params.setFlashMode(Parameters.FLASH_MODE_OFF);
    	camera.setParameters(params);
		
		Pix pix = ReadFile.readMem(pic);

		pix = Scale.scale(pix, 0.25f);
		pix = Rotate.rotate(pix, 90f);
		pix = Binarize.otsuAdaptiveThreshold(pix);
		
		String digits = "123456789";
		
		api.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, digits);
		
		squareSize = pix.getWidth() / 9.0;
		squareSizeTolerance = squareSize / 8.0;
		
		boolean foundStart = false;
		double foundX = pix.getWidth() / 2;
		double foundY = pix.getHeight() / 2;
		
		int searchIndex= 0;
		while ( !foundStart && searchIndex < 81 )
		{
			int dx = searchIndex % 9;
			dx -= 4;
			int dy = searchIndex / 9;
			dy -= 4;
			Point p = isEmptySquare(pix, foundX + (dx * squareSize), foundY + (dy * squareSize));
			if ( p != null )
			{
				foundX = p.x;
				foundY = p.y;
				foundStart = true;
			}
		}
		
		Point cellLoc = getCellLocation( pix, foundX, foundY ); 
		
		api.setImage(pix);
		
		String text = api.getUTF8Text();
		api.clear();
/*		
		int lowcount = 0;
		int upcount = 0;
		for (char c : text.toCharArray()) {
			if (lower.indexOf(c) >= 0) lowcount++;
			if (upper.indexOf(c) >= 0) upcount++;
		}
		
		
		String set = (lowcount>upcount)?lower:upper;
		api.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, set);
		api.setImage(pix);
		
		text = api.getUTF8Text();
		
		String[] lines = text.split("\n");
		
		int max = 0;
		int maxs = 0;
		for (int i = 0; i < lines.length; i++) {
			String str = "";
			for (char c : lines[i].toCharArray()) {
				if (c != ' ') str += c;
			}
			lines[i] = str;
		}
		for (int i = 0; i < lines.length; i++) {
			for (int j = i + 1; j < lines.length; j++) {
				if (lines[i].length() > lines[j].length() || lines[i].length() + 2 < lines[j].length()) break;
				if (j - i + 1 > max) {
					max = j - i+ 1;
					maxs = i;
				}
			}
		}
		
		String[] board = new String[max];
		
		for (int i = maxs; i < maxs + max; i++) {
			board[i-maxs] = lines[i].substring(0,lines[maxs].length());
		}
		
		Log.e("TEXT", text);
		Log.e("BOARD", Arrays.toString(board));
		
		String b = "";
		for (int i = 0; i < board.length; i++) {
			if (i != 0) b += "\n";
			b += board[i];
		}
		
		Intent intent = new Intent(this, WordSearchPuzzleSolve.class);
		intent.putExtra("BOARD_DATA", b);
		startActivity(intent);
		
		finish();

		api.clear();*/
	}
}
