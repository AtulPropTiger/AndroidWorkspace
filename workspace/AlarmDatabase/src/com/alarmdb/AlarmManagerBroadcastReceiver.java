package com.alarmdb;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.*;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//~RT 04-02-2014 10:25 PM can't pull info form text files yet , alarm works sending reveiving on boot etc. prob need to remove
//repeating alarm


//~RT 03-25-2014 05:19 PM 
//I changed the alarm to am.set() from am.setRepeating() shouldn't cause boot to not repeat but if it does just change it back and
//figure out a way to cut it off through like am.cancel or something

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	final public static String ONE_TIME = "onetime";
	final public static String HOUR  = "hour";
	final public static String MINUTES  = "minutes";
	public Context globeContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		 PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         wl.acquire();
         if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
				setOnetimeTimer(context, 0, 0, true, 0, "FIX THIS LATER");
         }
         globeContext = context;
         //alarmLoopThread.start();
         //Intent loop = new Intent(context, HUD.class);
         //context.startService(loop);
         //below was just added  //~RT 04-12-2014 04:05 PM 

         
         Calendar c = Calendar.getInstance(); 
         double c_min = c.get(Calendar.MINUTE), c_hour = c.get(Calendar.HOUR_OF_DAY); 
         double time = c_hour + (c_min/100);
         DatabaseHandler dbhand = new DatabaseHandler(context);
         String[] daysOfWeek = new String[7];
         daysOfWeek[0] = "sunday";
         daysOfWeek[1] = "monday";
         daysOfWeek[2] = "tuesday";
         daysOfWeek[3] = "wednesday";
         daysOfWeek[4] =  "thursday";
         daysOfWeek[5] = "friday";
         daysOfWeek[6] = "saturday";

         AudioManager mgr= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
         Ringtone alarm_ring = AlarmManagerActivity.getRingtone(context);
         int alarm_stream_type = alarm_ring.getStreamType();
         int alarm_max_volume = mgr.getStreamMaxVolume(alarm_stream_type);
         Uri alert =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         MediaPlayer mMediaPlayer = new MediaPlayer();
         try{
         mMediaPlayer.setDataSource(context, alert);
         mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
         mMediaPlayer.setLooping(true);
         mMediaPlayer.prepare();
         }catch(Exception e) {} //hasnt crashed yet
         
         int count = 0;
         String currentDay = daysOfWeek[c.get(Calendar.DAY_OF_WEEK) -1]; 
         int alarmFromDB = dbhand.getAlarmThatShouldBePlaying(time, currentDay); //returns an alarm that should be playing if there isn't one return -1
         if ( alarmFromDB != -1){
        	 ContentValues conVal = dbhand.getRowContent(alarmFromDB);
        	 double tempHour = conVal.getAsDouble(dbhand.KEY_HOUR),tempMinute =  conVal.getAsDouble(dbhand.KEY_MINUTE), 
        			 tempLength = conVal.getAsDouble(dbhand.KEY_LENGTH);
        	 double alarmStart = tempHour + (tempMinute/100); // ie: 9.2 for 9:20 AM, or 17.4 for 5:40 PM
        	 double alarmEnd = alarmStart + (tempLength/100); // from alarms start time to the duration like 9:20AM til 9:40 , cuz dur = 20
        	 //Toast.makeText(context, "temp hour = " + tempHour + " temp minute = " + tempMinute, Toast.LENGTH_LONG).show();
        	 Toast.makeText(context,"alarm # = " + alarmFromDB + "alarm start = " + alarmStart + " alarm end = " + 
        			 alarmEnd, Toast.LENGTH_SHORT).show();
        	 Log.i("in alarm recieved ", "temp hour  = " + tempHour + " temp minute = " + tempMinute);
        	 Log.i("in alarm received", "alarm# == " + alarmFromDB + " alarm start = " + alarmStart + " alarm end = " + alarmEnd);
        	 
        	 
             //Intent loop = new Intent(context, HUD.class);
             //Intent loop = new Intent(context, AlarmLoop.class);
             //context.startService(loop);
             //Toast.makeText(context, " HUD class started from alarm broadcast receiver class" , Toast.LENGTH_SHORT).show();
             //finish();
             
        	 
        	 if (time>= alarmStart && time <alarmEnd){
                 alarmLoopThread.start();
                 Intent loop = new Intent(context, HUD.class);
                 context.startService(loop);
             }
         }
         /*
         Calendar c = Calendar.getInstance(); 
         double c_min = c.get(Calendar.MINUTE), c_hour = c.get(Calendar.HOUR_OF_DAY); 
         double time = c_hour + (c_min/100);
         DatabaseHandler dbhand = new DatabaseHandler(context);
         String[] daysOfWeek = new String[7];
         daysOfWeek[0] = "sunday";
         daysOfWeek[1] = "monday";
         daysOfWeek[2] = "tuesday";
         daysOfWeek[3] = "wednesday";
         daysOfWeek[4] =  "thursday";
         daysOfWeek[5] = "friday";
         daysOfWeek[6] = "saturday";

         AudioManager mgr= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
         Ringtone alarm_ring = AlarmManagerActivity.getRingtone(context);
         int alarm_stream_type = alarm_ring.getStreamType();
         int alarm_max_volume = mgr.getStreamMaxVolume(alarm_stream_type);
         Uri alert =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         MediaPlayer mMediaPlayer = new MediaPlayer();
         try{
         mMediaPlayer.setDataSource(context, alert);
         mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
         mMediaPlayer.setLooping(true);
         mMediaPlayer.prepare();
         }catch(Exception e) {} //hasnt crashed yet
         
         int count = 0;
         String currentDay = daysOfWeek[c.get(Calendar.DAY_OF_WEEK) -1]; 
         int alarmFromDB = dbhand.getAlarmThatShouldBePlaying(time, currentDay); //returns an alarm that should be playing if there isn't one return -1
         if ( alarmFromDB != -1){
        	 ContentValues conVal = dbhand.getRowContent(alarmFromDB);
        	 double tempHour = conVal.getAsDouble(dbhand.KEY_HOUR),tempMinute =  conVal.getAsDouble(dbhand.KEY_MINUTE), 
        			 tempLength = conVal.getAsDouble(dbhand.KEY_LENGTH);
        	 double alarmStart = tempHour + (tempMinute/100); // ie: 9.2 for 9:20 AM, or 17.4 for 5:40 PM
        	 double alarmEnd = alarmStart + (tempLength/100); // from alarms start time to the duration like 9:20AM til 9:40 , cuz dur = 20
        	 //Toast.makeText(context, "temp hour = " + tempHour + " temp minute = " + tempMinute, Toast.LENGTH_LONG).show();
        	 Toast.makeText(context,"alarm # = " + alarmFromDB + "alarm start = " + alarmStart + " alarm end = " + 
        			 alarmEnd, Toast.LENGTH_SHORT).show();
        	 Log.i("in alarm recieved ", "temp hour  = " + tempHour + " temp minute = " + tempMinute);
        	 Log.i("in alarm received", "alarm# == " + alarmFromDB + " alarm start = " + alarmStart + " alarm end = " + alarmEnd);
        	 
        	 
             //Intent loop = new Intent(context, HUD.class);
             //Intent loop = new Intent(context, AlarmLoop.class);
             //context.startService(loop);
             //Toast.makeText(context, " HUD class started from alarm broadcast receiver class" , Toast.LENGTH_SHORT).show();
             //finish();
             
        	 
        	 while(time>= alarmStart && time <alarmEnd){
	        	 if (count == 0){
	        	 	mMediaPlayer.start(); //only start once obv
	        	 } 
	        	 if (mgr == null){
	        		 Toast.makeText(context, "MGR IS NULLL YOOO ", Toast.LENGTH_LONG).show();
	        	 }
	        	 else if (mgr.getStreamVolume(alarm_stream_type) != alarm_max_volume){ //stop trying to lower the volume sleepy stupid idiot
	        		// mgr.setStreamVolume(alarm_stream_type, alarm_max_volume, 0);
	        	 }

	        	 c_min = c.get(Calendar.MINUTE); c_hour = c.get(Calendar.HOUR_OF_DAY);
	        	 time = c_hour + (c_min/100); 
	        	 count++;	 
	        	 if (mMediaPlayer.getCurrentPosition() >=mMediaPlayer.getDuration()/2 ){
	        		//Toast.makeText(context, "BEFORE sending new alarm", //Toast.LENGTH_SHORT).show();
	        		Log.i(" starting new ALARM", "mMediaPlayer.getcurpos = " + mMediaPlayer.getCurrentPosition() + " video duration = " + 
	        				mMediaPlayer.getDuration());
	        		//time = 25; // will break while, 0-24 are only possible for this while
	        		//mgr = null;
	        		//mMediaPlayer.stop();
	        		//mMediaPlayer.release();
	             	AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	                Intent new_intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
	                // i tried to do intent.putExtra after pending intent and wondered why it wasn't working when I pass the 
	                //intent to pending intent pi then i changed the intent, i am such a dummy,  //~RT 03-22-2014 04:42 PM 
	                new_intent.putExtra(ONE_TIME, true);
	                new_intent.putExtra(HOUR, c.get(Calendar.HOUR));
	                new_intent.putExtra(MINUTES, c.get(Calendar.MINUTE));
	                PendingIntent pi = PendingIntent.getBroadcast(context, 0, new_intent, 0);
	                am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pi);
	                //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, count, pi);
	                //Toast.makeText(context, "after sending new alarm"+ "after sending new alarm", //Toast.LENGTH_SHORT).show(); 
	        	 } 
	         }
        	 
        	 
         }
         */
	}
        
	public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(HOUR, "hours");
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        intent.putExtra(HOUR, "hours");
        intent.putExtra(MINUTES, MINUTES);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 30 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi); 
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
       
    @SuppressLint("NewApi")
	public void setOnetimeTimer(Context context, int hour, int minutes, Boolean from_boot, 
			int alarm_number, String currentAlarmString) {
    	AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE); 	
    	AlarmManagerActivity ama = new AlarmManagerActivity();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
  
        DatabaseHandler dbhand = new DatabaseHandler(context);
        String fullDBStr = dbhand.readDatabase();
        //Toast.makeText(context, fullDBStr, Toast.LENGTH_LONG).show();
        Log.i("full db in setOnetime", fullDBStr);
        
        intent.putExtra(ONE_TIME, true);
        intent.putExtra(HOUR, hour);
        intent.putExtra(MINUTES, minutes);        
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        
        Calendar c = Calendar.getInstance(); 
        double c_min = c.get(Calendar.MINUTE), c_hour = c.get(Calendar.HOUR_OF_DAY);
        double alarm_hour = hour, alarm_minutes = minutes;//int total = (hour*3600000) + (minutes*60000);
        if (( alarm_hour+(alarm_minutes/100) ) < ( c_hour+(c_min/100) ) ){//if alarmtime < current , alarm is tomorrow
        	double c_hourD = (double) c_hour;
        	double c_minD = (double) c_min;
        	double time_til_day_over = 24.0d - (c_hourD +(c_minD/100));
        	alarm_hour += Math.floor(time_til_day_over); //hours left in the day plus hour alarm fires tomorrow
        	alarm_minutes+= time_til_day_over - Math.floor(time_til_day_over);//minutes left in day plus mins tomorrow
        }
        else{
        	alarm_hour -=c_hour; //hours left til alarm fires today
        	alarm_minutes -=c_min; //minutes left til alarm fres today
        }

        long alarm_hour_long = (long) (alarm_hour);
        long alarm_minutes_long = (long) (alarm_minutes);
        Long temp = ( System.currentTimeMillis() + (alarm_hour_long*3600000) + (alarm_minutes_long*60000) )
        		- System.currentTimeMillis() ;
        Log.i(" times milleseconds ",  " time til alarm  = " + temp);
        Log.i("alarm_hour + alarm mins", (alarm_hour_long*3600000) + (alarm_minutes_long*60000) + " ");
        int seconds = c.get(Calendar.SECOND) *1000;

        if (from_boot){
        	am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000,30000, pi);
        }
        else{
        	 //~RT 03-25-2014 05:15 PM 
        	Log.i("time stuff", "hour = " + hour + " minutes = " + minutes);
        	//Toast.makeText(context, "in setonetimetimer(), hour = " + hour + " min = "+ minutes, Toast.LENGTH_SHORT).show();
        	Bundle extras = intent.getExtras();
        	//Toast.makeText(context, "getEXTRASSS in setonetimetimer, = " + extras.getInt(HOUR) + " min = "+ extras.getInt(MINUTES), Toast.LENGTH_SHORT).show();
	        /*am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (alarm_hour_long*3600000) + 
	        		(alarm_minutes_long*60000) - seconds,15000, pi);*/
	        
	        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (alarm_hour_long*3600000) + 
	        		(alarm_minutes_long*60000) - seconds, pi);
	        //am.cancel(null);
        }
    }
    
	
	Thread alarmLoopThread = new Thread()
	{
	    @Override
	    public void run() {
	    	 Context context = globeContext;
	         Calendar c = Calendar.getInstance(); 
	         double c_min = c.get(Calendar.MINUTE), c_hour = c.get(Calendar.HOUR_OF_DAY); 
	         double time = c_hour + (c_min/100);
	         DatabaseHandler dbhand = new DatabaseHandler(context);
	         String[] daysOfWeek = new String[7];
	         daysOfWeek[0] = "sunday";
	         daysOfWeek[1] = "monday";
	         daysOfWeek[2] = "tuesday";
	         daysOfWeek[3] = "wednesday";
	         daysOfWeek[4] =  "thursday";
	         daysOfWeek[5] = "friday";
	         daysOfWeek[6] = "saturday";

	         AudioManager mgr= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	         Ringtone alarm_ring = AlarmManagerActivity.getRingtone(context);
	         int alarm_stream_type = alarm_ring.getStreamType();
	         int alarm_max_volume = mgr.getStreamMaxVolume(alarm_stream_type);
	         Uri alert =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	         MediaPlayer mMediaPlayer = new MediaPlayer();
	         try{
	         mMediaPlayer.setDataSource(context, alert);
	         mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
	         mMediaPlayer.setLooping(true);
	         mMediaPlayer.prepare();
	         }catch(Exception e) {} //hasnt crashed yet
	         
	         int count = 0;
	         String currentDay = daysOfWeek[c.get(Calendar.DAY_OF_WEEK) -1]; 
	         int alarmFromDB = dbhand.getAlarmThatShouldBePlaying(time, currentDay); //returns an alarm that should be playing if there isn't one return -1
	         if ( alarmFromDB != -1){
	        	 ContentValues conVal = dbhand.getRowContent(alarmFromDB);
	        	 double tempHour = conVal.getAsDouble(dbhand.KEY_HOUR),tempMinute =  conVal.getAsDouble(dbhand.KEY_MINUTE), 
	        			 tempLength = conVal.getAsDouble(dbhand.KEY_LENGTH);
	        	 double alarmStart = tempHour + (tempMinute/100); // ie: 9.2 for 9:20 AM, or 17.4 for 5:40 PM
	        	 double alarmEnd = alarmStart + (tempLength/100); // from alarms start time to the duration like 9:20AM til 9:40 , cuz dur = 20
	        	 Log.i("in alarm recieved ", "temp hour  = " + tempHour + " temp minute = " + tempMinute);
	        	 Log.i("in alarm received", "alarm# == " + alarmFromDB + " alarm start = " + alarmStart + " alarm end = " + alarmEnd);

	        	 
	        	 while(time>= alarmStart && time <alarmEnd){
		        	 if (count == 0){
		        	 	mMediaPlayer.start(); //only start once obv
		        	 } 

		        	 if (mgr.getStreamVolume(alarm_stream_type) != alarm_max_volume){ //stop trying to lower the volume sleepy stupid idiot
		        		 mgr.setStreamVolume(alarm_stream_type, alarm_max_volume, 0);
		        	 }
		        	 
		        	 c = c.getInstance();
		        	 c_min = c.get(Calendar.MINUTE); c_hour = c.get(Calendar.HOUR_OF_DAY);
		        	 time = c_hour + (c_min/100); 
		        	 count++;	 
		        	 Log.i("AMBR", "in thread ambr, time = " + time + " alarmStart = " + alarmStart + " alarmEnd = " + alarmEnd);
		        	 Log.i("AMBR", "in thread ambr, hour = " + c_hour + " minute = " + c_min);
		        	 if (mMediaPlayer.getCurrentPosition() >=mMediaPlayer.getDuration()/2 ){
		        		//Toast.makeText(context, "BEFORE sending new alarm", //Toast.LENGTH_SHORT).show();
		        		Log.i(" starting new ALARM", "mMediaPlayer.getcurpos = " + mMediaPlayer.getCurrentPosition() + " video duration = " + 
		        				mMediaPlayer.getDuration());
		                mgr =null;//
		                mMediaPlayer.stop();
		                mMediaPlayer.release();
		                mMediaPlayer = null;
		   	            mgr= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			            alarm_ring = AlarmManagerActivity.getRingtone(context);
			            alarm_stream_type = alarm_ring.getStreamType();
			            alarm_max_volume = mgr.getStreamMaxVolume(alarm_stream_type);
			            alert =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
			            mMediaPlayer = new MediaPlayer();
			            try{
			         	   mMediaPlayer.setDataSource(context, alert);
			         	   mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
			         	   mMediaPlayer.setLooping(true);
			         	   mMediaPlayer.prepare();
			            }catch(Exception e) {} //hasnt crashed yet
			            count = 0;
			          
		        	 } 
		         }
	        	 mMediaPlayer.stop();
	        	 mMediaPlayer.release();
                 Intent loop = new Intent(context, HUD.class);
                 context.stopService(loop);
	        	 
	         }
	    }
	};
    
}
