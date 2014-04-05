package com.example.shortcutmaker;

import android.os.Bundle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.view.Menu;

public class MainActivity extends Activity {

	Context context;
	public String  packageName = "com.deckedbuilder";
	public String name = "deckedbuilder";
	Application app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createShortcutOnDesktop(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private void createShortcutOnDesktop(MainActivity app) {

		//need to unhide this and fix
		//ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(this,  R.drawable.icon);
	    
		Intent shortcutIntent = new Intent();
	    
	    //Intent launchIntent = new Intent(this, MainActivity.class); // pretty sure shouldnt be main activity here
	    
	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, app.getIntentShortcut());
	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher));
	    shortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	    this.sendBroadcast(shortcutIntent);
	    finish();
	    

	}
	
	

	public Intent getIntentShortcut()
	{       

	    Intent i = new Intent();
	    i.setClassName(packageName, name);
	    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	    return i;
	}
	
	

	
}
