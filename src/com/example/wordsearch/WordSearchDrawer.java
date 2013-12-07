package com.example.wordsearch;

import java.util.HashMap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

public class WordSearchDrawer implements SurfaceHolder.Callback{

	public char[][] wordSearchData; 
	public HashMap<String, WordSearchResult> circledWords;
	
	public WordSearchDrawer() {
		circledWords = new HashMap<String, WordSearchResult>();
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		draw(holder);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		draw(holder);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(SurfaceHolder holder)
	{
		Canvas canvas = holder.lockCanvas();
		RectF screenRect = new RectF(0,0,canvas.getWidth(), canvas.getWidth());
		Paint screenPaint = new Paint();
		screenPaint.setColor(Color.WHITE);
		canvas.drawRect(screenRect, screenPaint);
		screenPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		
	

		float blockSize = (canvas.getWidth() + 0.0f) / wordSearchData[0].length;
		if ( blockSize * wordSearchData.length > canvas.getHeight() )
		{
			blockSize = (canvas.getHeight() + 0.0f) / wordSearchData.length;
		}
		float circleRadius = blockSize / 2 - blockSize / 10;
		
		
		Paint fontPaint = new Paint();
		fontPaint.setColor(Color.BLACK);
		fontPaint.setTextAlign(Align.CENTER);
		
		Paint circlePaint = new Paint();
		circlePaint.setStrokeWidth(3);
		circlePaint.setColor( Color.rgb(128, 128, 255) );
		circlePaint.setStyle(Style.STROKE);

		circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		
		for ( int i = 2; i < 2000; i ++ )
		{
			fontPaint.setTextSize(i);
			FontMetrics f = fontPaint.getFontMetrics();
			int max = (int) Math.max(-f.top + f.bottom, 0.0 );
			if ( max > blockSize )
			{
				fontPaint.setTextSize( i - 1 );
				break;
			}
		}
		
		
		FontMetrics fontPaintMetrics = fontPaint.getFontMetrics();
		float yOffset = ( fontPaintMetrics.bottom - fontPaintMetrics.descent ) * 10;
		
		
		for ( int i = 0; i < wordSearchData.length; i++ )
		{
			for ( int j = 0; j < wordSearchData[0].length; j++ )
			{
				RectF r = new RectF(j * blockSize, i * blockSize, j * blockSize + blockSize, i * blockSize + blockSize);
				
				canvas.drawText("" + wordSearchData[i][j], r.left + blockSize/2, r.top + blockSize/2 + yOffset, fontPaint);
			}
		}
		
		for ( WordSearchResult v : circledWords.values() )
		{
			Point[] p = v.convertToPoints();
			double startLeft = p[0].x * blockSize + blockSize/2;
			startLeft-=circleRadius;
			double startTop = p[0].y * blockSize + blockSize/2;
			startTop -= circleRadius;
			
			double dx = p[1].x - p[0].x;
			double dy = p[1].y - p[0].y;

			double startHyp = Math.sqrt(dx * dx + dy * dy );
			
			double startAngle = Math.asin(dy / startHyp );
			if ( dx < 0 )
			{
				startAngle = (Math.PI - startAngle);
			}
			
			double pstart1x = startLeft + circleRadius + Math.cos(startAngle - Math.PI/2) * circleRadius;
			double pstart1y = startTop + circleRadius + Math.sin(startAngle - Math.PI/2) * circleRadius;

			double pstart2x = startLeft + circleRadius + Math.cos(startAngle + Math.PI/2) * circleRadius;
			double pstart2y = startTop + circleRadius + Math.sin(startAngle + Math.PI/2) * circleRadius;
			
			startAngle = startAngle * 180.0 / Math.PI;
		
			
			RectF startCircle = new RectF((float)startLeft, (float)startTop, (float)startLeft + circleRadius * 2, (float)startTop + circleRadius * 2 );
			
			canvas.drawArc(startCircle, (float) (startAngle + 90), 180, false, circlePaint);
		
			
			
			double endLeft = p[1].x * blockSize + blockSize/2;
			endLeft-=circleRadius;
			double endTop = p[1].y * blockSize + blockSize/2;
			endTop -= circleRadius;
			
			dx = -dx;
			dy=-dy;
		
			double endHyp = Math.sqrt(dx * dx + dy * dy );
			
			double endAngle = Math.asin(dy / endHyp );
			if ( dx < 0 )
			{
				endAngle = (Math.PI - endAngle);
			}
			
			double pend1x = endLeft + circleRadius + Math.cos(endAngle - Math.PI/2) * circleRadius;
			double pend1y = endTop + circleRadius + Math.sin(endAngle - Math.PI/2) * circleRadius;

			double pend2x = endLeft + circleRadius + Math.cos(endAngle + Math.PI/2) * circleRadius;
			double pend2y = endTop + circleRadius + Math.sin(endAngle + Math.PI/2) * circleRadius;
			
			endAngle = endAngle * 180.0 / Math.PI;
		
			
			RectF endCircle = new RectF((float)endLeft, (float)endTop, (float)endLeft + circleRadius * 2, (float)endTop + circleRadius * 2 );
			
			Log.e("", "" + endAngle);
			canvas.drawArc(endCircle, (float) (endAngle + 90), 180, false, circlePaint);

			canvas.drawLine((float)pstart2x, (float)pstart2y, (float)pend1x, (float)pend1y, circlePaint);
			canvas.drawLine((float)pstart1x, (float)pstart1y, (float)pend2x, (float)pend2y, circlePaint);
			
			
			/*double endLeft = p[1].x * blockSize + blockSize/2;
			endLeft-=circleRadius;
			double endTop = p[1].y * blockSize + blockSize/2;
			endTop -= circleRadius;
			
			dx = -dx;
			dy = -dy;
			
			double endHyp = Math.sqrt(dx * dx + dy * dy );
			
			double endAngle = Math.asin(dy / endHyp );
			if ( dx < 0 ) endAngle += Math.PI;
			

			double pend1x = endLeft + circleRadius + Math.cos(endAngle - Math.PI) * circleRadius;
			double pend1y = endTop + circleRadius + Math.sin(endAngle - Math.PI) * circleRadius;

			double pend2x = endLeft + circleRadius + Math.cos(endAngle + Math.PI) * circleRadius;
			double pend2y = endTop + circleRadius + Math.sin(endAngle + Math.PI) * circleRadius;
			
			endAngle = endAngle * 180.0 / Math.PI;
		
			
			RectF endCircle = new RectF((float)endLeft, (float)endTop, (float)endLeft + circleRadius * 2, (float)endTop + circleRadius * 2 );
			
			canvas.drawArc(endCircle, (float) (endAngle - 90), 180, false, circlePaint);
			
			
			
			*/
		}
		
		
		holder.unlockCanvasAndPost(canvas);
	}
	
	
}
