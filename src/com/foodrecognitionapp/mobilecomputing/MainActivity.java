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
        
	}

	 private void buildColorNutrientsDB() {
		// add info!
		//red
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "red", 
				 "vegetable", "Lycopene", "A carotenoid, lycopene is a powerful antioxidant that has been associated with a reduced risk of some cancers, especially prostate cancer, and protection against heart attacks");
	    //green
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "green", 
				 "vegetable", "Isothiocyanates", "induce enzymes in the liver that assist the body in removing potentially carcinogenic compounds");
		//orange
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "orange", 
				 "vegetable", "Vitamin C", "Vitamin C is a water-soluble vitamin that is necessary for normal growth and development.");
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "orange", 
				 "vegetable", "Carotenoids", "The carotenoids lutein and zeaxanthin play an important role in eye health.");
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "orange", 
				 "vegetable", "Bioflavonoids", "Bioflavonoids have been used in alternative medicine as an aid to enhance the action of vitamin C, to support blood circulation, as an antioxidant, and to treat allergies, viruses, or arthritis and other inflammatory conditions.");
		 //orange_yellow
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "orange_yellow", 
				 "vegetable", "Beta-cryptoxanthin", "orange-friendly carotenoid and can be converted in the body to vitamin A, a nutrient integral for vision and immune function, as well as skin and bone health, according to information from the PBH.");
		//yellow_green
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "yellow_green", 
				 "vegetable", "Lutein", "lutein helps protect against age-related macular degeneration.");
		//yellow
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "yellow", 
				 "vegetable", "Beta-Carotenes", "increased immunity, a decreased risk of some cancers, and healthy eyes and skin.");
		//blue_purple
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "blue_purple", 
				 "vegetable", "Anthocyanins", "promote bone health, and have been shown to lower the risk of some cancers, improve memory, and increase urinary-tract health.");
		//white
		 mDBHelper.insertNutrient(mDBHelper.getWritableDatabase(), "white", 
				 "vegetable", "Manganese", "increased immunity.");
	}

	public void callPhotoPage(){
	    	Intent gamePageIntent = new Intent(this, FoodScan.class);
	    	startActivity(gamePageIntent);
	    
	    }
	
}
