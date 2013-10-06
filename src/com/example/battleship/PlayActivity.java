package com.example.battleship;

import android.app.Activity;
import android.content.Intent;
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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class PlayActivity extends Activity {
	
	private Button play, setFirstLargeShip, setSecondLargeShip,
				   setFirstSmallShip, setSecondSmallShip;
	private SurfaceView playGround;
	private SurfaceHolder hold;	
	private int width, height;
	public static int id;
	public static String type;
	private Bitmap grid;
	public static int board[][];
 	static ConnectedThread connectedThread;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		playGround = (SurfaceView)findViewById(R.id.surfaceView);
		hold = playGround.getHolder();
		setFirstLargeShip = (Button)findViewById(R.id.SFLS);
		setSecondLargeShip = (Button)findViewById(R.id.SSLS);
		setFirstSmallShip = (Button)findViewById(R.id.SFSS);
		setSecondSmallShip = (Button)findViewById(R.id.SSSS);
		play = (Button)findViewById(R.id.play);
		board = new int[10][10];
		for (int k = 0; k < 10; k ++) {
			for (int l = 0; l < 10; l++) {
				board[k][l] = 0;
			}
		}
		
		hold.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceCreated( SurfaceHolder holder) {
				width = playGround.getWidth();
				height = playGround.getHeight();
				grid = BitmapFactory.decodeResource(getResources(), R.raw.grid);
				grid =  Bitmap.createScaledBitmap(grid, width, height, false);
				Canvas canvas = holder.lockCanvas();
				canvas.drawBitmap(grid, 0, 0, null);
				holder.unlockCanvasAndPost(canvas);
				
				setFirstLargeShip.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						type = "large";
						id = 3;
						
						Intent intent = new Intent(PlayActivity.this, DetailsActivity.class);
						startActivityForResult(intent, 100);
					}
				});
				
				setSecondLargeShip.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						type = "large";
						id = 4;
						
						Intent intent = new Intent(PlayActivity.this, DetailsActivity.class);
						startActivityForResult(intent, 100);
					}
				});
				
				setFirstSmallShip.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						type = "small";
						id = 1;
						
						Intent intent = new Intent(PlayActivity.this, DetailsActivity.class);
						startActivityForResult(intent, 100);
					}
				});
				
				setSecondSmallShip.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						type = "small";
						id = 2;
						
						Intent intent = new Intent(PlayActivity.this, DetailsActivity.class);
						startActivityForResult(intent, 100);
					}
				});
				
				play.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(PlayActivity.this, GameActivity.class);
						startActivityForResult(intent, 100);
						//connectedThread.write(toWrite)
						System.out.println(toSend(board));
						
						
					}
				});
				
				
				playGround.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						//float x = event.getX();
						//float y = event.getY();
						Paint myPaint = new Paint();
						myPaint.setColor(Color.rgb(0, 255, 0));
						myPaint.setStrokeWidth(10);
						Canvas canvas = hold.lockCanvas();
						grid = BitmapFactory.decodeResource(getResources(), R.raw.grid);
						grid =  Bitmap.createScaledBitmap(grid, width, height, false);
						canvas.drawBitmap(grid, 0, 0, null);
						for (int  i = 0; i < 10; i ++) {
							for (int j = 0; j < 10; j ++) {
								if (board[i][j] == 1 || board[i][j] == 2) {
									canvas.drawRect((float)(j * 44.1), (float)(i * 38.1), (float)((j + 1) * 44.1), (float)((i + 1) * 38.1), myPaint);
								}
								else if (board[i][j] == 3 || board[i][j] == 4) {
									myPaint.setColor(Color.rgb(0, 0, 255));
									canvas.drawRect((float)(j * 44.1), (float)(i * 38.1), (float)((j + 1) * 44.1), (float)((i + 1) * 38.1), myPaint);
								}
									
							}
						}
						hold.unlockCanvasAndPost(canvas);
						return false;
					}
				});
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}
	
	public String toSend(int matrix[][]) {
		String str = null;
		for (int i = 0; i < 10; i ++) {	
			for (int j = 0; j < 10; j ++) {
				str += matrix[i][j]  + "";
			}
		}
		return str;
	}
	
	
	private Pair returnCoordinates (float x, float y) {
		int i = 0, j = 0;
		if (x <= 44.9) {
			j = 0;
		}
		else if (x > 44.9 && x <= 89.9 ) {
			j = 1;
		}
		else if (x > 89.9 && x <= 134.7 ) {
			j = 2;
		}
		else if (x > 134.7 && x <= 179.6 ) {
			j = 3;
		}
		else if (x > 179.6 && x <= 224.5 ) {
			j = 4;
		}
		else if (x > 224.5 && x <= 269.4 ) {
			j = 5;
		}
		else if (x > 269.4 && x <= 314.3 ) {
			j = 6;
		}
		else if (x > 314.3 && x <= 359.2 ) {
			j = 7;
		}
		else if (x > 359.2 && x <= 404.1 ) {
			j = 8;
		}
		else if (x > 404.1 && x <= 449.0 ) {
			j = 9;
		}
		
		if (y <= 38.1) {
			i = 0;
		}
		else if (y > 38.1 && y <= 76.2 ) {
			i = 1;
		}
		else if (y > 76.2 && y <= 114.3 ) {
			i = 2;
		}
		else if (y > 114.3 && y <= 152.4 ) {
			i = 3;
		}
		else if (y > 152.4 && y <= 190.5 ) {
			i = 4;
		}
		else if (y > 190.5 && y <= 228.6 ) {
			i = 5;
		}
		else if (y > 228.6 && y <= 266.7 ) {
			i = 6;
		}
		else if (y > 266.7 && y <= 304.8 ) {
			i = 7;
		}
		else if (y > 304.8 && y <= 342.9 ) {
			i = 8;
		}
		else if (y > 342.9 && x <= 381.0 ) {
			i = 9;
		}
		
		Pair pair = new Pair(i, j);
		return pair;
	}
	
	
}




