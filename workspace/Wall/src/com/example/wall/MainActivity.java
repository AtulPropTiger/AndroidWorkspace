package com.example.wall;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	WallpaperManager wp ;//= WallpaperManager.getInstance(getApplicationContext());
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wp = WallpaperManager.getInstance(getApplicationContext());
		Drawable current_wall = wp.getDrawable();
		current_wall.setAlpha(20);
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		
	    try {
			//wp.setBitmap(drawableToBitmap(current_wall));
			wp.setResource(R.drawable.ic_launcher);
			//wp.sendWallpaperCommand(this.getWindow().getDecorView().findViewById(android.R.id.content).getApplicationWindowToken(),
			wp.sendWallpaperCommand(this.getWindow().getDecorView().findViewById(android.R.id.home).getApplicationWindowToken(),
			"android.wallpaper.tap", 2, 2, 2, intent.getExtras());
            Toast.makeText(getApplicationContext(),
                    "Testing PHONE TEST  " ,
                    Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "NOT WORKINGGGGGGGGGGGGGGG  " ,
                    Toast.LENGTH_LONG).show();
		}
		//getContentView().getWindowToken()
		//context.re
		//wp.sendWallpaperCommand(, action, x, y, z, extras);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap); 
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
	
}
