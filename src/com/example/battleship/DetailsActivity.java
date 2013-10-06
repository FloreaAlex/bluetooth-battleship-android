package com.example.battleship;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DetailsActivity extends Activity {
	
	private Button horizontal, vertical, left, right, done;
	public static String orientation, direction;
	private EditText first, second;
	public static int i, j;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		horizontal = (Button)findViewById(R.id.horizonatal);
		vertical = (Button)findViewById(R.id.vertical);
		left = (Button)findViewById(R.id.left);
		right = (Button)findViewById(R.id.right);
		done = (Button)findViewById(R.id.done);
		first = (EditText)findViewById(R.id.editText1);
		second = (EditText)findViewById(R.id.editText2);
		
		horizontal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DetailsActivity.orientation = "horizontal";
				left.setText("left");
				right.setText("right");
			}
		});
		
		vertical.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DetailsActivity.orientation = "vertical";
				left.setText("down");
				right.setText("up");
			}
		});
		
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DetailsActivity.direction = left.getText() + "";
			}
		});

		right.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
			DetailsActivity.direction = right.getText() + "";
			}
		});
		
		done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DetailsActivity.i = Integer.valueOf(first.getText().toString());
				DetailsActivity.j = Integer.valueOf(second.getText().toString());
				setBoard(PlayActivity.type, DetailsActivity.orientation, DetailsActivity.direction, PlayActivity.id,
						 DetailsActivity.i, DetailsActivity.j);
				finish ();
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}
	
	private void setBoard (String type, String orientation, String direction, int id, int i, int j) {
		if (type.equalsIgnoreCase("small")) {
			if (orientation.equalsIgnoreCase("vertical")) {
				if (direction.equalsIgnoreCase("up")) {
					PlayActivity.board[i - 1][j - 1] = id;
					PlayActivity.board[i - 2][j - 1] = id;
				}
				else {
					PlayActivity.board[i - 1][j - 1] = id;
					PlayActivity.board[i][j - 1] = id;
				}
			}
			else {
				if (direction.equalsIgnoreCase("right")) {
					PlayActivity.board[i - 1][j - 1] = id;
					PlayActivity.board[i - 1][j] = id;
				}
				else {
					PlayActivity.board[i - 1][j - 1] = id;
					PlayActivity.board[i - 1][j - 2] = id;
				}
			}
		}
		else {
			if (orientation.equalsIgnoreCase("vertical")) {
				if (direction.equalsIgnoreCase("up")) {
					PlayActivity.board[i - 1][j - 1] = id;
					PlayActivity.board[i - 2][j - 1] = id;
					PlayActivity.board[i - 3][j - 1] = id;
				}
				else {
					PlayActivity.board[i - 1][j - 1] = id;
					PlayActivity.board[i][j - 1] = id;
					PlayActivity.board[i + 1][j - 1] = id;
				}
			}
			else {
				if (direction.equalsIgnoreCase("right")) {
					PlayActivity.board[i - 1][j - 1] = id;
					PlayActivity.board[i - 1][j] = id;
					PlayActivity.board[i - 1][j + 1] = id;
				}
				else {
					PlayActivity.board[i - 1][j - 1] = id;
					PlayActivity.board[i - 1][j - 2] = id;
					PlayActivity.board[i - 1][j - 3] = id;
				}
			}
		}
	}

}
