package on.boot.completed;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class StarterService extends Service {
	private static final String TAG = "MyService";

	/**
	 * The started service starts the AlarmManager.
	 */
	@Override
	public void onStart(Intent intent, int startid) {
		Intent i = new Intent(this, NotificationBarAlarm.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

		// Repeat the notification every 15 seconds
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 15000, pi);

		Toast.makeText(this, "My Service started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}
}