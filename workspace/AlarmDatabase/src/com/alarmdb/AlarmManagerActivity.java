package com.alarmdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;



//removing all the view part from read , see if it matters!!!!!!!!!!!!!!!!!!! 922PM march 31 2014

public class AlarmManagerActivity extends Activity {

	public Ringtone r;
	private AlarmManagerBroadcastReceiver alarm;
	private String file = "mydata11";
	private String data;
	int line_number = 0;
	int alarm_number ;
	String str = "";
	String alarm_line = "";
	Context globalContext;
	AlarmDbRow alarmCV = new AlarmDbRow();
	DatabaseHandler db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager);
        alarm = new AlarmManagerBroadcastReceiver();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        AudioManager mgr=null;
        globalContext = getApplicationContext();
        mgr=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        
        db = new DatabaseHandler(this); // set up the database, where all alarms are stored
        /*
    	alarmCV.setColumn(alarmCV.KEY_ALARM, 1);
    	alarmCV.setColumn(alarmCV.KEY_HOUR, 6);
    	alarmCV.setColumn(alarmCV.KEY_MINUTE, 36);
    	alarmCV.setColumn(alarmCV.KEY_LENGTH, 26);
    	alarmCV.setColumn(alarmCV.KEY_MONDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_TUESDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_WEDNESDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_THURSDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_FRIDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_SATURDAY, 0);
    	alarmCV.setColumn(alarmCV.KEY_SUNDAY, 0);
    	alarmCV.setColumn(alarmCV.KEY_ACTIVE, 0);
        db.addAlarmRow(alarmCV.getColumns());
        //ContentValues cv = alarmCV.getColumns();
        //Toast.makeText(context, "contentvalues = " + cv.keySet() , Toast.LENGTH_LONG).show();
        String dataStr = db.readDatabase();
        */
        
    }
    
    public static Ringtone getRingtone(Context context){
        Uri alarm_ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        return RingtoneManager.getRingtone(context, alarm_ring);
    }
    

    
    @Override
	protected void onStart() {
		super.onStart();
	}

    public void startRepeatingTimer(View view) {
    	Context context = this.getApplicationContext();
    	if(alarm != null){
    		alarm.SetAlarm(context);
    	}else{
    		//toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
    	}
    }
    
    public void cancelRepeatingTimer(View view){
    	Context context = this.getApplicationContext();
    	if(alarm != null){
    		alarm.CancelAlarm(context);
    	}else{
    		//toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
    	}
    }
    
    public void onetimeTimer(View view) /*throws IOException*/{
    	Context context = this.getApplicationContext();
    	EditText hour = (EditText) findViewById(R.id.hour);
    	EditText minutes = (EditText) findViewById(R.id.minutes);
    	EditText alarm_num = (EditText) findViewById(R.id.alarm_number);
    	int hour_int = Integer.parseInt( hour.getText().toString() );
    	int minutes_int = Integer.parseInt( minutes.getText().toString() );
    	alarm_number = Integer.parseInt( alarm_num.getText().toString() );
    	//Log.i("past the string onversion", "past the string conversion");
    	
    	if(alarm != null){
    		//alarm.setOnetimeTimer(context, hour_int, minutes_int, false, alarm_number, getAlarmString(view));
    		//Toast.makeText(context, "minutes = " + minutes_int + " hours = " + hour_int, Toast.LENGTH_SHORT).show();
    		alarm.setOnetimeTimer(context, hour_int, minutes_int, false, alarm_number, getAlarmString());
    	}else{
    		//toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
    	}
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_widget_alarm_manager, menu);
        return true;
    }
	
	@SuppressLint("NewApi")
	public void setAlarmText(View view) throws UnsupportedEncodingException, IOException{//called by save alarm button , writes 
    	Context context = this.getApplicationContext();
    	EditText hour = (EditText) findViewById(R.id.hour);
    	EditText minutes = (EditText) findViewById(R.id.minutes);
    	EditText alarm_num = (EditText) findViewById(R.id.alarm_number);
    	EditText alarm_dur = (EditText) findViewById(R.id.alarm_duration);
    	// populate all the columns
    	int hour_int = Integer.parseInt( hour.getText().toString() );
    	int minutes_int = Integer.parseInt( minutes.getText().toString() );
    	int alarm_number = Integer.parseInt( alarm_num.getText().toString() );
    	int alarm_duration = Integer.parseInt( alarm_dur.getText().toString() );
    	alarmCV.setColumn(alarmCV.KEY_ALARM, alarm_number);
    	alarmCV.setColumn(alarmCV.KEY_HOUR, hour_int);
    	alarmCV.setColumn(alarmCV.KEY_MINUTE, minutes_int);
    	alarmCV.setColumn(alarmCV.KEY_LENGTH, alarm_duration);
    	alarmCV.setColumn(alarmCV.KEY_MONDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_TUESDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_WEDNESDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_THURSDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_FRIDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_SATURDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_SUNDAY, 1);
    	alarmCV.setColumn(alarmCV.KEY_ACTIVE, 1);
    	
        Boolean rowE = db.addAlarmRow(alarmCV.getColumns());
        if (rowE){
        	Toast.makeText(context, "addAlarmRow, the row did EXIST", Toast.LENGTH_LONG).show();
        }
        else{
        	Toast.makeText(context, "Alarm DID NOT exist, NEW alarm", Toast.LENGTH_LONG).show();
        }
        //Boolean dbool = db.dbCreate();
        //Toast.makeText(context, "Database Create called? = " + dbool,Toast.LENGTH_LONG ).show();
        //String dataStr = db.readDatabase();
        //Log.i("database to string", "database to string = " + dataStr);		
	}
	
	public void save(View view){
		      // data = et.getText().toString();
			  //String data = "alarmfile";
		      try {
		        FileOutputStream fOut = openFileOutput(file,MODE_WORLD_READABLE);
		    	  //FileOutputStream fOut = openFileOutput(file,MODE_APPEND);
		    	  
		         fOut.write(data.getBytes(), 0 /*data.getBytes().length/8*/, data.getBytes().length);
		         //fOut.write(data.getBytes());
		         
		         fOut.close();
		         //toast.makeText(getBaseContext(),"file saved",Toast.LENGTH_LONG).show();
		      } catch (Exception e) {
		         // TODO Auto-generated catch block
		    	  //toast.makeText(getBaseContext(),"EXCEPTION in save!!", Toast.LENGTH_SHORT).show();
		         e.printStackTrace();
		      }
	}
	   
	public String read(Boolean showToast){ // figure out if this is the one that trigger son the touch, i think it is one
		    	EditText alarm_num = (EditText) findViewById(R.id.alarm_number);
		    	int alarm_number = Integer.parseInt( alarm_num.getText().toString() );
		    	String temp = db.readTable(alarm_number);
		    	if (showToast){
		    		Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
		    	}
		        return temp;
	}
	
	public void readCurrent(View view){
		read(true); // make a boolean parameter for read so that you can make a toast of all the saved alarms
		//this one should read from the database
		
	}
	
	//for use with setAlarmText, takes in an the new alarm that you want to add, the .txt file that contains all 
	//alarms, the alarm number that you want to add. If you add an alarm 3 it will look to see if there is already
	//an alarm 3 and if there is it will replace it with the new alarm, otherwise it will just add an alarm 3 to
	//the .txt that keeps all of the saved alarms.
	public String appendOrReplaceAlarm(String newAlarm, String oldFile, int alarmNumber){
		String alarmNumStr = "alarm" + Integer.toString(alarmNumber);
		if (oldFile.contains( alarmNumStr )){
			int start = oldFile.indexOf(alarmNumStr);
			int end = oldFile.indexOf("]", start); //get the full length of the old alarm string
			String oldAlarm = oldFile.substring(start, end +1);
			Log.i(" old and new", "oldAlarm = " + oldAlarm + " newAlarm = " + newAlarm);
			return oldFile.replace(oldAlarm, newAlarm);
			
		}
		else{
			//toast.makeText(getApplicationContext(), "we are appending !!", Toast.LENGTH_SHORT).show();
			return oldFile.concat(newAlarm);

		}
		
	}
	
	// whatever user currently has typed in for alarm number this function will take that number and return the 
	//alarm string associated with it
	public String getAlarmString(){
    	//EditText alarm_num = (EditText) findViewById(R.id.alarm_number);
    	//alarm_number = Integer.parseInt( alarm_num.getText().toString() );
    	alarm_number = 1;
    	String alarmNumStr = "alarm" + Integer.toString(alarm_number);
    	String oldFile = read(false);
		if (oldFile.contains( alarmNumStr )){
			int start = oldFile.indexOf(alarmNumStr);
			int end = oldFile.indexOf("]", start); //get the full length of the old alarm string
			return oldFile.substring(start, end); //alarm string we need to pull out
		}
		else{
			//toast.makeText(getApplicationContext(), "There is no Alarm " + alarm_number + " saved!", Toast.LENGTH_SHORT).show();
			return ""; // no alarm x saved
		}
		
	}
		   
}
        
      	 
