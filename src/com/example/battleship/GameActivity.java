package com.example.battleship;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class GameActivity extends Activity {
	
	private Button atack;
	private SurfaceView oponentBoard, playerBoard;
	private SurfaceHolder oponentHold, playerHold;	
	private Bitmap grid;
	private int width1, height1, width2, height2;
	public static String friend;
	public static int cnt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		atack = (Button)findViewById(R.id.atack_button);
		oponentBoard = (SurfaceView)findViewById(R.id.surfaceView1);
		playerBoard = (SurfaceView)findViewById(R.id.surfaceView2);
		oponentHold = oponentBoard.getHolder();
		playerHold = playerBoard.getHolder();
		
		playerHold.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder arg0) {
				
				playerBoard.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						float x = arg1.getX();
						float y = arg1.getY();
						System.out.println(x + " " + y);
						width1 = playerBoard.getWidth();
						height1 = playerBoard.getHeight();
						Paint myPaint = new Paint();
						myPaint.setColor(Color.rgb(0, 255, 0));
						myPaint.setStrokeWidth(10);
						grid = BitmapFactory.decodeResource(getResources(), R.raw.grid);
						grid =  Bitmap.createScaledBitmap(grid, width1, height1, false);
						Canvas canvas = playerHold.lockCanvas();
						canvas.drawBitmap(grid, 0, 0, null);
						for (int  i = 0; i < 10; i ++) {
							for (int j = 0; j < 10; j ++) {
								if (PlayActivity.board[i][j] == 1 || PlayActivity.board[i][j] == 2) {
									canvas.drawRect((float)(j * 26.9), (float)(i * 26.9), (float)((j + 1) * 26.9), (float)((i + 1) * 26.9), myPaint);
								}
								else if (PlayActivity.board[i][j] == 3 || PlayActivity.board[i][j] == 4) {
									myPaint.setColor(Color.rgb(0, 0, 255));
									canvas.drawRect((float)(j * 26.9), (float)(i * 26.9), (float)((j + 1) * 26.9), (float)((i + 1) * 26.9), myPaint);
								}
									
							}
						}
						playerHold.unlockCanvasAndPost(canvas);
						return false;
					}
				});
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
		oponentHold.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder arg0) {
				width2 = oponentBoard.getWidth();
				height2 = oponentBoard.getHeight();
				grid = BitmapFactory.decodeResource(getResources(), R.raw.grid);
				grid =  Bitmap.createScaledBitmap(grid, width2, height2, false);
				Canvas canvas = oponentHold.lockCanvas();
				canvas.drawBitmap(grid, 0, 0, null);
				oponentHold.unlockCanvasAndPost(canvas);
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
