package com.example.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class TestRec extends BroadcastReceiver {

	
  //private TextView textView;
	
  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle extras = intent.getExtras();
    String b = "hey";
    
    Toast.makeText(context,
            "Testing PHONE TEST  " ,
            Toast.LENGTH_LONG).show();
    		//textView.setText("PHONE STUFFFFFF");
    
    		
    /*
    if (extras != null) {
      String state = extras.getString(TelephonyManager.EXTRA_STATE);
      Log.w("MY_DEBUG_TAG", state);
      if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
        String phoneNumber = extras
            .getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.w("MY_DEBUG_TAG", phoneNumber);
      }
    }
    */
    
    
  }
} 