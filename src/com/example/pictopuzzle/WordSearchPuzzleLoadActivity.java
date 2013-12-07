package com.example.pictopuzzle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Scale;
import com.googlecode.tesseract.android.TessBaseAPI;

public class WordSearchPuzzleLoadActivity extends Activity implements SurfaceHolder.Callback, Camera.PictureCallback {
	
	private SurfaceView cameraSurface;
	private SurfaceHolder holder;
	private Camera camera;
	
	private TessBaseAPI api;
	
	private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/pictopuzzle/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search_puzzle_load);
        
        cameraSurface = (SurfaceView)findViewById(R.id.cameraSurface);
        
        holder = cameraSurface.getHolder();
        
        holder.addCallback(this);
        
        cameraSurface.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				camera.autoFocus(new AutoFocusCallback() {
					
					@Override
					public void onAutoFocus(boolean arg0, Camera arg1) {
						camera.takePicture(null, null, WordSearchPuzzleLoadActivity.this);
					}
				});
			}
		});
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	
    	camera = Camera.open();
    	Parameters params = camera.getParameters();
    	params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
    	params.setFlashMode(Parameters.FLASH_MODE_TORCH);
    	camera.setParameters(params);
    	//camera.setDisplayOrientation(90);
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

	@Override
	public void onPictureTaken(byte[] pic, Camera camera) {
    	Parameters params = camera.getParameters();
    	params.setFlashMode(Parameters.FLASH_MODE_OFF);
    	camera.setParameters(params);
		
		Pix pix = ReadFile.readMem(pic);

		pix = Scale.scale(pix, 0.25f);
		//pix = Rotate.rotate(pix, 90f);
		pix = Binarize.otsuAdaptiveThreshold(pix);
		
		String lower = "abcdefghijklmnopqrstuvwxyz";
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		api.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, lower + upper);
		api.setImage(pix);
		
		String text = api.getUTF8Text();
		api.clear();
		
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
		
		Log.e("TEXT", text);

		api.clear();
	}
    
}
