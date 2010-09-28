package com.nicolatesser.com;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CheckInOutActivity extends Activity {
	
    private static final String TAG = "TimeTrackoid";
	
	private  WifiManager wifi;
	
	private BroadcastReceiver receiver;
	
	private TimeTrackerService timeTrackerService;
	
	private StorageHelper storageHelper;
	
	
    private TextView mTextView;
    private ListView mListView;

	
	
	
	
    public WifiManager getWifi() {
		return wifi;
	}

	public void setWifi(WifiManager wifi) {
		this.wifi = wifi;
	}
	
	

	public TimeTrackerService getTimeTrackerService() {
		return timeTrackerService;
	}

	public void setTimeTrackerService(TimeTrackerService timeTrackerService) {
		this.timeTrackerService = timeTrackerService;
	}
	
	

	public StorageHelper getStorageHelper() {
		return storageHelper;
	}

	public void setStorageHelper(StorageHelper storageHelper) {
		this.storageHelper = storageHelper;
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_ins_outs);
        
        // gui elements
       
        mTextView = (TextView) findViewById(R.id.text);
        mListView = (ListView) findViewById(R.id.list);

        
        
        // define storage
        this.storageHelper = new StorageHelper(getApplicationContext());
        // define service
        this.timeTrackerService = new TimeTrackerService(storageHelper, this);
        
               
        updateCheckInsOutsView();
        
      
        
        
        
    }


	

	
	
	// view
	

	public void updateCheckInsOutsView() {
		Cursor cursor = this.storageHelper.getCheckInsOuts();
        if (cursor == null) {
            // There are no results
            mTextView.setText("You haven't got any Check-ins nor check-outs so far.");
        } else {
            // Display the number of results
            int count = cursor.getCount();
   
            mTextView.setText("Displaying "+count+" Check-ins/outs.");
            
            // Specify the columns we want to display in the result
            String[] from = new String[] { StorageHelper.LOCATION_NAME,StorageHelper.TIME,StorageHelper.CHECK_TYPE,StorageHelper.MATCH_TYPE };

            // Specify the corresponding layout elements where we want the columns to go
            int[] to = new int[] {R.id.name,R.id.time,R.id.check_type,R.id.match_type};

            // Create a simple cursor adapter for the definitions and apply them to the ListView

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.check_ins_outs_results, cursor, from, to);
            
            mListView.setAdapter(adapter);

        }

	}
	
	
	
}