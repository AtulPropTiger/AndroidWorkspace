package com.example.switcher;




import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Activity1 extends Activity {
	
	Intent myIntent = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
	    ActionBar actionBar = getActionBar();

	    actionBar.show();
	    getActionBar().setDisplayHomeAsUpEnabled(true);
        Button next = (Button) findViewById(R.id.Button01);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Activity2.class);
                //startActivityForResult(myIntent, 0);
                startActivity(myIntent);
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
	    // Handle presses on the action bar items
		
	    switch (item.getItemId()) {
	        case R.id.home:
	        	NavUtils.navigateUpFromSameTask(this);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
    
}