package com.foodrecognitionapp.mobilecomputing;

import java.util.ArrayList;
import java.util.List;

import com.facialrecognitionapp.mobilecomputing.R;


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
	
	public DatabaseHelper mDBHelper;

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
        
        //Create DB and add entries
        mDBHelper = new DatabaseHelper( this );
        buildColorNutrientsDB();
        List<String> list = new ArrayList<String>();
        list = mDBHelper.getNutrients(mDBHelper.getWritableDatabase(), "red", "vegetable");
        
	}

	 private void buildColorNutrientsDB() {
		// add info!
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "red", 
				 "vegetable", "Lycopene", "A carotenoid, lycopene is a powerful antioxidant that has been associated with a reduced risk of some cancers, especially prostate cancer, and protection against heart attacks");
	}

	public void callPhotoPage(){
	    	Intent gamePageIntent = new Intent(this, FoodScan.class);
	    	startActivity(gamePageIntent);
	    
	    }
	
}
