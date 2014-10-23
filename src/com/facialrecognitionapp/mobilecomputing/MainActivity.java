package com.facialrecognitionapp.mobilecomputing;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        Button btn = (Button)findViewById(R.id.startButton);
       
        btn.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
				// TODO Auto-generated method stub
        		callPhotoPage();     
        	}
       });
	}

	 public void callPhotoPage(){
	    	Intent gamePageIntent = new Intent(this, FaceScan.class);
	    	startActivity(gamePageIntent);
	    
	    }
	
}
