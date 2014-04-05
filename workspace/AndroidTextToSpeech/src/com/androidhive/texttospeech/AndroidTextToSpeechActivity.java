package com.androidhive.texttospeech;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AndroidTextToSpeechActivity extends Activity implements
		TextToSpeech.OnInitListener {
	/** Called when the activity is first created. */

	private TextToSpeech tts;
	private Button btnSpeak;
	private EditText txtText;
	
	int[] ab_interval_time = new int[94]; // seconds that particular movement takes
	String[] ab_interval_movement = new String[94]; //the movement to do ,time it takes above
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//int[] ab_interval_time = new int[94]; // seconds that particular movement takes
		//String[] ab_interval_movement = new String[94]; //the movement to do ,time it takes above
		ab_interval_time[0] = 1; ab_interval_movement[0] = "ab intevals start";
		ab_interval_time[1] = 5; ab_interval_movement[1] = "child's pose";
		ab_interval_time[2] = 5; ab_interval_movement[2] = "plank";
		ab_interval_time[3] = 5; ab_interval_movement[3] = "child's pose";
		ab_interval_time[4] = 5; ab_interval_movement[4] = "plank";
		ab_interval_time[5] = 5; ab_interval_movement[5] = "child's pose ";
		ab_interval_time[6] = 5; ab_interval_movement[6] = "plank";
		ab_interval_time[7] = 5; ab_interval_movement[7] = "downward dog";
		ab_interval_time[8] = 5; ab_interval_movement[8] = "plank";
		ab_interval_time[9] = 5; ab_interval_movement[9] = "downward dog";
		ab_interval_time[10] = 5; ab_interval_movement[10] = "plank";
		ab_interval_time[11] = 5; ab_interval_movement[11] = "downward dog";
		ab_interval_time[12] = 5; ab_interval_movement[12] = "plank";
		ab_interval_time[13] = 5; ab_interval_movement[13] = "downward dog";
		ab_interval_time[14] = 5; ab_interval_movement[14] = "plank";
		ab_interval_time[15] = 5; ab_interval_movement[15] = "downward dog";
		ab_interval_time[16] = 5; ab_interval_movement[16] = "left spider";
		ab_interval_time[17] = 5; ab_interval_movement[17] = "downward dog";
		ab_interval_time[18] = 5; ab_interval_movement[18] = "right spider";
		ab_interval_time[19] = 5; ab_interval_movement[19] = "downward dog";
		ab_interval_time[20] = 5; ab_interval_movement[20] = "left spider";
		ab_interval_time[21] = 5; ab_interval_movement[21] = "downward dog";
		ab_interval_time[22] = 5; ab_interval_movement[22] = "right spider";
		ab_interval_time[23] = 5; ab_interval_movement[23] = "oblique left";
		ab_interval_time[24] = 5; ab_interval_movement[24] = "downward dog";
		ab_interval_time[25] = 5; ab_interval_movement[25] = "oblique right";
		ab_interval_time[26] = 15; ab_interval_movement[26] = "low plank hold";
		ab_interval_time[27] = 15; ab_interval_movement[27] = "left plank hold";
		ab_interval_time[28] = 5; ab_interval_movement[28] = "sit break";
		ab_interval_time[29] = 15; ab_interval_movement[29] = "vee sit ";
		ab_interval_time[30] = 17; ab_interval_movement[30] = "left side hip up";
		ab_interval_time[31] = 18; ab_interval_movement[31] = "low plank pulses";
		ab_interval_time[32] = 18; ab_interval_movement[32] = "right side hold";
		ab_interval_time[33] = 20; ab_interval_movement[33] = "vee sit hands in air";
		ab_interval_time[34] = 18; ab_interval_movement[34] = "right side hip up";
		ab_interval_time[35] = 4; ab_interval_movement[35] = "stand up";
		ab_interval_time[36] = 45; ab_interval_movement[36] = "cardio recovery two hand cross knee tap";
		ab_interval_time[37] = 5; ab_interval_movement[37] = " lay down";
		ab_interval_time[38] = 28; ab_interval_movement[38] = "flat back alternate leg raise";
		ab_interval_time[39] = 28; ab_interval_movement[39] = "flat back left arm left leg tap";
		ab_interval_time[40] = 28; ab_interval_movement[40] = "switch to right leg and arm";
		ab_interval_time[41] = 28; ab_interval_movement[41] = "both arm and leg taps";
		ab_interval_time[42] = 5; ab_interval_movement[42] = "stand up";
		ab_interval_time[43] = 54; ab_interval_movement[43] = "sprint foward, then back, then shuffle";
		ab_interval_time[44] = 6; ab_interval_movement[44] = "lay down";
		ab_interval_time[45] = 28; ab_interval_movement[45] = "flat back scissors, don't touch heels";
		ab_interval_time[46] = 30; ab_interval_movement[46] = "left arm left leg scissors";
		ab_interval_time[47] = 30; ab_interval_movement[47] = "right arm right leg scissors";
		ab_interval_time[48] = 30; ab_interval_movement[48] = "now both legs and arms";
		ab_interval_time[49] = 5; ab_interval_movement[49] = "stand up";
		ab_interval_time[50] = 60; ab_interval_movement[50] = "hop hop squat";
		ab_interval_time[51] = 5; ab_interval_movement[51] = "sit down";
		ab_interval_time[52] = 30; ab_interval_movement[52] = "alternate leg heel taps heels can touch";
		ab_interval_time[53] = 30; ab_interval_movement[53] = "both heels in and out heels can touch";
		ab_interval_time[54] = 25; ab_interval_movement[54] = "heels up then touch heels down";
		ab_interval_time[55] = 15; ab_interval_movement[55] = "bend right knee in left leg up";
		ab_interval_time[56] = 15; ab_interval_movement[56] = "switch legs";
		ab_interval_time[57] = 5; ab_interval_movement[57] = "stand up";
		ab_interval_time[58] = 55; ab_interval_movement[58] = "sprint turn left turn right squat hold";
		ab_interval_time[59] = 30; ab_interval_movement[59] = "alternate heel taps heels off ground";
		ab_interval_time[60] = 30; ab_interval_movement[60] = "heels in and out both heels off ground";
		ab_interval_time[61] = 15; ab_interval_movement[61] = "hold left leg out right knee bent";
		ab_interval_time[62] = 15; ab_interval_movement[62] = "switch legs";
		ab_interval_time[63] = 15; ab_interval_movement[63] = "left leg again move it up and down";
		ab_interval_time[64] = 15; ab_interval_movement[64] = "switch legs";
		ab_interval_time[65] = 5; ab_interval_movement[65] = "stand up";
		ab_interval_time[66] = 30; ab_interval_movement[66] = "right leg hop kick";
		ab_interval_time[67] = 30; ab_interval_movement[67] = "left leg hop kick";
		ab_interval_time[68] = 30; ab_interval_movement[68] = "superman";
		ab_interval_time[69] = 25; ab_interval_movement[69] = "superman pull up";
		ab_interval_time[70] = 30; ab_interval_movement[70] = "superman arms to side then to front";
		ab_interval_time[71] = 30; ab_interval_movement[71] = "superman hold arms to side";
		ab_interval_time[72] = 5; ab_interval_movement[72] = "stand up";
		ab_interval_time[73] = 60; ab_interval_movement[73] = "side to side mountain climbers";
		ab_interval_time[74] = 2; ab_interval_movement[74] = "elbow plank position";
		ab_interval_time[75] = 28; ab_interval_movement[75] = "alternate leg in then out";
		ab_interval_time[76] = 30; ab_interval_movement[76] = "left leg up then right leg up then back";
		ab_interval_time[77] = 25; ab_interval_movement[77] = "add pike up while legs are in";
		ab_interval_time[78] = 35; ab_interval_movement[78] = "now just pike ups then down";
		ab_interval_time[79] = 6; ab_interval_movement[79] = "sprint";
		ab_interval_time[80] = 10; ab_interval_movement[80] = "left leg tabletop hold";
		ab_interval_time[81] = 6; ab_interval_movement[81] = "sprint";
		ab_interval_time[82] = 10; ab_interval_movement[82] = "right leg tabletop hold";
		ab_interval_time[83] = 6; ab_interval_movement[83] = "sprint";
		ab_interval_time[84] = 10; ab_interval_movement[84] = "left leg";
		ab_interval_time[85] = 6; ab_interval_movement[85] = "sprint";
		ab_interval_time[86] = 10; ab_interval_movement[86] = "right leg";
		ab_interval_time[87] = 6; ab_interval_movement[87] = "sprint";
		ab_interval_time[88] = 50; ab_interval_movement[88] = "down back wide in and out abs";
		ab_interval_time[89] = 25; ab_interval_movement[89] = "high jog";
		ab_interval_time[90] = 20; ab_interval_movement[90] = "slow alternate leg high jog";
		ab_interval_time[91] = 10; ab_interval_movement[91] = "Done!";
		ab_interval_time[92] = 210; ab_interval_movement[92] = "Three minute thirty second stretch";
		ab_interval_time[93] = 6; ab_interval_movement[93] = "Done with stretch";
		
		
		tts = new TextToSpeech(this, this);

		btnSpeak = (Button) findViewById(R.id.btnSpeak);

		txtText = (EditText) findViewById(R.id.txtText);

		// button on click event
		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				speakOut();
			}

		});
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);

			// tts.setPitch(5); // set pitch level

			// tts.setSpeechRate(2); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				btnSpeak.setEnabled(true);
				//speakOut();
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}

	private void speakOut() {

		//String text = txtText.getText().toString();
		String text = " ab intervals";
		ab_interval_time[0] = 1; ab_interval_movement[0] = "ab intevals start";
		for(int i = 0; i < ab_interval_time.length ; i++){
			text = ab_interval_movement[i];
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			SystemClock.sleep(ab_interval_time[i] * 1000);
		}
	}
}