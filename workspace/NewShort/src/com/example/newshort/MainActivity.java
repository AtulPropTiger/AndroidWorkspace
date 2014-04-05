package com.example.newshort;

 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.ContentValues;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
 

public class MainActivity extends Activity {
 
	ArrayList<PInfo> allapps;
	String current_app_package;
	String current_app_name;
	Drawable current_app_icon;
	public BroadcastReceiver receiver;
	//BroadcastReceiver myReceiver;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String main_pack = "com.example.newshort";
        int main_lay_id = getResources().getIdentifier( "layout", "id", getPackageName() );
        Log.i("main_lay id", ""+ main_lay_id);
        Log.i("mmain_acivity actual id", ""+ R.layout.activity_main);
        //View yep =  findViewById(main_lay_id);
        //setContentView(main_lay_id);
        
        allapps = getPackages(); // this is a list of all the apps
        PInfo current_app = allapps.get(58); //some random app, tryin gto get FB idk why
        current_app_package = current_app.pname;
        current_app_name = current_app.appname;
        current_app_icon = current_app.icon;

        
	    IntentFilter filter1 = new IntentFilter();
	    

	    //filter1 = new IntentFilter("com.android.providers.calendar.CalendarReceiver");
	    //registerReceiver(myReceiver, filter1);
	  
	    Intent shortcutIntent = getPackageManager().getLaunchIntentForPackage(current_app_package);
	    filter1.addAction(shortcutIntent.getAction());
	    if (shortcutIntent.getScheme() != null){
	    	filter1.addDataScheme(shortcutIntent.getScheme());
	    }
	    //filter1.addDataAuthority(shortcutIntent.getData().getAuthority(), null);
	    //filter1.addDataPath(shortcutIntent.getData().getPath(),0 );
	    /*
	    try {
			filter1.addDataType(shortcutIntent.getType());
		} catch (MalformedMimeTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	    Set<String> cat_list = shortcutIntent.getCategories();
	    
	    //idk if below is working correctly
	    Iterator itr = cat_list.iterator();
	    while(itr.hasNext()) {
	       Object element = itr.next();
	       filter1.addCategory(element.toString());
	       Log.v("facebook category: ", element.toString());
	    }
	 	
	 	//Class b = shortcutIntent.getClass();
	 	//Class b = shortcutIntent.getClass();
	    //registerReceiver(myReceiver, filter1); //finally register the receiver
	    
	    
        //Add listener to add shortcut button
        Button add = (Button) findViewById(R.id.buttonAddShortcut);
        add.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                addShortcut(); //Add shortcut on Home screen
            }
        });
         
        //Add listener to remove shortcut button
        Button remove = (Button) findViewById(R.id.buttonRemoveShortcut);
        remove.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                removeShortcut(); //Remove shortcut from Home screen
            }
        });     
    }
     
    private void addShortcut() {
        //Adding shortcut for MainActivity 
        //on Home screen
    	//String b = apps.appname ;
    	
        //Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);

    	
    	/*
    	
        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class );
    	
        shortcutIntent.setAction(Intent.ACTION_MAIN);
 
        Intent addIntent = new Intent();
        addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "HelloWorldShortcut");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
            Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                        R.drawable.ic_launcher));
 
        addIntent
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
        
        */
    	createShortcutOnDesktop(this);
    	
    }
     
    private void removeShortcut() {
         
        //Deleting shortcut for MainActivity 
        //on Home screen
        /*Intent shortcutIntent = new Intent(getApplicationContext(),
                MainActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
         
        Intent addIntent = new Intent();
        addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "HelloWorldShortcut");
 
        addIntent
                .setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
        */
    	
    	removeShortcutOnDesktop(this);
    	
    }
    
    
	
	private void createShortcutOnDesktop(MainActivity app) {

		//need to unhide this and fix
		//ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(this,  R.drawable.icon);
	    /*
		Intent shortcutIntent = new Intent();
	    //Intent launchIntent = new Intent(this, MainActivity.class); // pretty sure shouldnt be main activity here

	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, app.getIntentShortcut());
	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, current_app_name); //Intent.ShortcutIconResource.fromContext(context, current_app_icon)
	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, current_app_icon);
	    shortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	    */
		Intent shortcutIntent = getPackageManager().getLaunchIntentForPackage(current_app_package);
    	

		
        shortcutIntent.setAction(Intent.ACTION_MAIN);
 
        Bitmap BM = drawableToBitmap(current_app_icon);
      
        int resourceID = getResources().getIdentifier( "icon", "id", current_app_package );
        
        int viewID = getResources().getIdentifier( "view", "id", current_app_package );
        
       // int reso = Resources.getResourseIdByName( current_app_package, "drawable", "icon" );
        
       
        
        Intent addIntent = new Intent("com.facebook.keyguardservice.KeyguardServiceBooter.USER_ACTION");
        addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, current_app_name); //shortcut name 
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BM);
            //Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));
 
        addIntent
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        
        
        
        
        
        getApplicationContext().sendStickyBroadcast(addIntent);
		
	    
        
        //probalby need to asuser sendbroadcast , hide that unhide below
	    //this.sendBroadcast(shortcutIntent);
	    UserHandle u = null;
	    //KeyEvent temp_key_string = shortcutIntent.E
	    Log.v("Not entering MY LIST", "why youoooooo??");
	    List<ResolveInfo> ResInf=getPackageManager().queryBroadcastReceivers(shortcutIntent, 0);
	    for(int i=0;i<ResInf.size();i++) {
            Log.v("Reciever MY LIST: ", ""+ ResInf.get(i));
	    }
	    //startActivity(shortcutIntent);
	    
	    
	    
	    PackageManager packageManager2 = getPackageManager();

	    List<String> startupApps = new ArrayList<String>();
	            final Intent intent = new Intent("android.intent.action.BOOT_COMPLETED");
	            final List<ResolveInfo> activities = packageManager2.queryBroadcastReceivers(intent, 0);
	            for (ResolveInfo resolveInfo : activities) {
	                ActivityInfo activityInfo = resolveInfo.activityInfo;
	                if (activityInfo != null){
	                    startupApps.add(activityInfo.name);
	                }
	                Log.i("THESE ARE MY RECEIVERS","receiver: " + activityInfo.name);
	            }
	    
	
	   //why is the first and second all recievers??? shouldln't second be lest receivers its just shortcut
	   List<String> startupApps2 = new ArrayList<String>();
	            //final Intent intent2 = new Intent("android.intent.action.BOOT_COMPLETED");
	   			Intent intent2 = shortcutIntent;
	            final List<ResolveInfo> activities2 = packageManager2.queryBroadcastReceivers(intent2, 0);
	            for (ResolveInfo resolveInfo2 : activities) {
	                ActivityInfo activityInfo2 = resolveInfo2.activityInfo;
	                if (activityInfo2 != null){
	                    startupApps.add(activityInfo2.name);
	                }
	                Log.i("INTENT SHORCUT RECIVER CUSTOM","receiver: " + activityInfo2.name);
	            }	            
	            
	    
	    
	    
	            
	         // Register for the battery changed event
	            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

	            // Intent is sticky so using null as receiver works fine
	            // return value contains the status
	            Intent batteryStatus = this.registerReceiver(null, filter);

	            // Are we charging / charged?
	            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
	            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
	              || status == BatteryManager.BATTERY_STATUS_FULL;

	            boolean isFull = status == BatteryManager.BATTERY_STATUS_FULL;

	            // How are we charging?
	            int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
	            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
	            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
	            
	            
        //this.sendBroadcast(shortcutIntent );

        
	    
	    
	    //View rootView = ((Activity)_context).Window.DecorView.FindViewById(Android.Resource.Id.Content);
	    
	    //finish();
	    

	}
	
	

	
	private void removeShortcutOnDesktop(MainActivity app) {

		//need to unhide this and fix
		//ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(this,  R.drawable.icon);
	    /*
		Intent shortcutIntent = new Intent();
	    //Intent launchIntent = new Intent(this, MainActivity.class); // pretty sure shouldnt be main activity here

	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, app.getIntentShortcut());
	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, current_app_name); //Intent.ShortcutIconResource.fromContext(context, current_app_icon)
	    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, current_app_icon);
	    shortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	    */
		Intent shortcutIntent = getPackageManager().getLaunchIntentForPackage(current_app_package);
    	
        shortcutIntent.setAction(Intent.ACTION_MAIN);
 
 
      
        int resourceID = getResources().getIdentifier( "icon", "id", current_app_package );
        
       // int reso = Resources.getResourseIdByName( current_app_package, "drawable", "icon" );
        
        
        
        Intent addIntent = new Intent();
        addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, current_app_name); //shortcut name 
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, 
            Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));
 
        addIntent
                .setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
		
	    
	    this.sendBroadcast(shortcutIntent);
	    //finish();
	    

	}	
	
	

	public Intent getIntentShortcut()
	{       

	    Intent i = new Intent();
	    i.setClassName(current_app_package, current_app_name);
	    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	    return i;
	}
    
    class PInfo {
        private String appname = "";
        private String pname = "";
        private String versionName = "";
        private int versionCode = 0;
        private Drawable icon;
        private void prettyPrint() {
        	// i added app stuff at the beginning idk why a log type wasn't there?
            Log.v("app stuff",appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
        }
    }

    private ArrayList<PInfo> getPackages() {
        ArrayList<PInfo> apps = getInstalledApps(false); /* false = no system packages */
        final int max = apps.size();
        for (int i=0; i<max; i++) {
            apps.get(i).prettyPrint();
        }
        return apps;
    }

    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();        
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());

            res.add(newInfo);
        }
        return res; 
    }
    
    
    
    //hey
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

    
    /*
    
	  //The BroadcastReceiver that listens for bluetooth broadcasts
	public BroadcastReceiver myReceiver = new BroadcastReceiver() {
	
	  @Override
	  public void onReceive(Context context, Intent intent) {
	      // TODO Auto-generated method stub
		    Toast.makeText(context, "we up in this receive up in this yo yo yo",
		            Toast.LENGTH_LONG).show();
		  Log.v("INSIDE BROADCAST RECEIVER","face book receiver thing, before iffff");
		  
		  addShortcut();
		  
		  
	      if(intent.getAction().equalsIgnoreCase("com.android.providers.calendar.CalendarReceiver"))
	      {
	          Log.d("INSIDE BROADCAST RECEIVER","face book receiver thing");
	          addShortcut();
	      }
	      
	
	  }//h
	};
	
	
	*/
	
	/*
	@Override
	public void onDestroy() {
	  i need to ddestroy i just want to try and get it to work first
	  super.onDestroy();
	  unregisterReceiver(myReceiver);

	}
	  */  

	
}
     

