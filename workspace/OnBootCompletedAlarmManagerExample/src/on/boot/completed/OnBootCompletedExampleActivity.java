package on.boot.completed;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

public class OnBootCompletedExampleActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Clear the Notification Bar
		NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nMgr.cancelAll();
	}
}