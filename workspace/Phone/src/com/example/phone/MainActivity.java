package com.example.phone;


import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static TextView textView;
	
    BroadcastReceiver receiver = new BroadcastReceiver(){

        
        public void onReceive(Context context, Intent intent) {
            
            Toast.makeText(context,
                    "Testing PHONE TEST  " ,
                    Toast.LENGTH_LONG).show();
        	
        }
    };
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setContentView(2130837514);
		setContentView(R.layout.activity_main);
		
		
		textView = (TextView) findViewById(R.id.status);
		
		HUD hud = new HUD();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	

	  @Override
	  protected void onResume() {
	    super.onResume();
	    
	    /*
	    Context context = this.getApplicationContext();

	    try {
			context = context.createPackageContext("com.facebook.katana",2);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
	    WindowManager b;
	    //IBinder  c = "18888";
	    //"42e8b160"
	    WallpaperManager wp;
	    //wp = WallpaperManager.getInstance(getApplicationContext());
	    /*
	    SurfaceView sv;
	    wp = (WallpaperManager) getSystemService(WALLPAPER_SERVICE);
	    Display disp = getDisplay("429e51c8");
	    b.LayoutParams (int _type, int _flags, int _format);
	    public WindowManager.LayoutParams (int _type, int _flags, int _format);
	    */
	    
	    //registerReceiver(receiver, new IntentFilter("com.vogella.android.service.receiver"));
	    //fb wont allow me to access this , its saying wont grant acess
	    //context.registerReceiver(receiver, new IntentFilter("com.facebook.keyguardservice.KeyguardServiceBooter"));
	    //registerReceiver(receiver, new IntentFilter("android.intent.action.PHONE_STATE" ));
	    registerReceiver(receiver, new IntentFilter("VoiceActivationReceiver"));
	    //registerReceiver(receiver, new IntentFilter("com.android.settingsaccessibility.assistivetouch.AssistiveTouchServiceEnableBroadcastReceiver"));
	    //registerReceiver(receiver, new IntentFilter("android.intent.action.MAIN")); 
	  }
	  
	  
	  
	  //@Override
	  //protected void onPause() {
	  //  super.onPause();
	    
	    //unregisterReceiver(receiver);
	  //}
	
}
