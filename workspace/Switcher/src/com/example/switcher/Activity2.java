package com.example.switcher;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Activity2 extends Activity {


	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        
	    ActionBar actionBar = getActionBar();

	    actionBar.show();
	    getActionBar().setDisplayHomeAsUpEnabled(true);
        Button next = (Button) findViewById(R.id.Button02);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent intent = new Intent();
                //setResult(RESULT_OK, intent);
                //finish();
            	Fragment frag = new Fragment();
            	 NavUtils.navigateUpFromSameTask(frag.getActivity());
            }

        });
    }
    
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		//return true;
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
