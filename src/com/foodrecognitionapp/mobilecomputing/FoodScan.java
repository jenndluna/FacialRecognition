package com.foodrecognitionapp.mobilecomputing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
/*import android.media.FaceDetector;
import android.media.FaceDetector.Face;*/
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facialrecognitionapp.mobilecomputing.R;
import com.facialrecognitionapp.mobilecomputing.ResultActivity;

public class FoodScan extends ActionBarActivity{
	
	/*private static final int MAX_FACES = 10;*/
	ImageView ivThumbnailPhoto;
    Bitmap bitMap;
    static final int TAKE_PICTURE = 1;
    static int TOON_LEVEL = 2;
    Button analyzePhotoBtn;
    Button analyzeBucketPhotoBtn;
    int[] pixels;
    List<ColorBucket> colorList;
    Map<Integer, Integer> colorHistogram;
    Map<Integer, String> colorToNutrientMap;
    public DatabaseHelper mDBHelper;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_scan);
		
		ivThumbnailPhoto = (ImageView) findViewById(R.id.ivThumbnailPhoto);
		
		
		       
		//set button click listener
		Button btn = (Button)findViewById(R.id.photoButton);
		analyzePhotoBtn = (Button)findViewById(R.id.analyzeButton);
		Button analyzeBucketsBtn = (Button)findViewById(R.id.buttonBucketColors);
		
				
        btn.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
				// TODO Auto-generated method stub
        		takePhoto();     
        	}
       });
        
        colorHistogram = new HashMap<Integer, Integer>();
        
      //set up color list
      	colorList = new ArrayList<ColorBucket>(3);
      	colorList =	setListItems();
      		
//        NumberPicker numPick = (NumberPicker)findViewById( R.id.toonLevel);
//        numPick.setMaxValue(10);
//        numPick.setMinValue(2);
//        numPick.setValue(4);
//        numPick.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);        
//        numPick.setOnValueChangedListener( new OnValueChangeListener() 
//        {
//			
//			@Override
//			public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
//			{
//				TOON_LEVEL = newVal;		
//				analyzePhoto();
//			}
//		});
        
        analyzePhotoBtn.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
				// TODO Auto-generated method stub
        		analyzePhoto("real");     
        	}
       });
        
        analyzeBucketsBtn.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
				// TODO Auto-generated method stub
        		analyzePhoto("buckets");     
        	}
       });
	}
	
	public List<ColorBucket> setListItems()
	{
		//red		
		ColorBucket red = new ColorBucket();	
		red.colorName = "red";
		red.colorValue = 0xFF0000;
		colorList.add(red);
		//green
		ColorBucket green = new ColorBucket();	
		green.colorName = "green";
		green.colorValue = 0xFFDF00;
		colorList.add(green);
		//white
		ColorBucket white = new ColorBucket();	
		white.colorName = "white";
		white.colorValue = 0xFFFFFF;
		colorList.add(white);
		//yellow
		ColorBucket yellow = new ColorBucket();	
		yellow.colorName = "yellow";
		yellow.colorValue = 0xFFDF00;
		colorList.add(yellow);
		//yellow_green
		ColorBucket yellow_green = new ColorBucket();	
		yellow_green.colorName = "yellow_green";
		yellow_green.colorValue = 0xADFF2F;
		colorList.add(yellow_green);
		//blue_purple
		ColorBucket blue_purple = new ColorBucket();	
		blue_purple.colorName = "blue_purple";
		blue_purple.colorValue = 0x8F49E6;
		colorList.add(blue_purple);
		//orange
		ColorBucket orange = new ColorBucket();	
		orange.colorName = "orange";
		orange.colorValue = 0xFF9900;
		colorList.add(orange);
		//orange_yellow
		ColorBucket orange_yellow = new ColorBucket();	
		orange_yellow.colorName = "orange";
		orange_yellow.colorValue = 0xFFAE42;
		colorList.add(orange_yellow);
		
		return colorList;
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.face_scan, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public void takePhoto() {
 
        // create intent with ACTION_IMAGE_CAPTURE action 
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 
        // start camera activity
        startActivityForResult(intent, TAKE_PICTURE);
 
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
 
        if (requestCode == TAKE_PICTURE && resultCode== RESULT_OK && intent != null){
            // get bundle
            Bundle extras = intent.getExtras();
 
            // get bitmap
            bitMap = (Bitmap) extras.get("data");
            setThumbnail( bitMap );
        }
        
    }
    
    private void setThumbnail( Bitmap aBitmap )
    {
    	ivThumbnailPhoto.setImageBitmap(aBitmap);
    }
    
    public String getRGBColor(int alpha, int red, int green, int blue)
    {
    	return String.format("#%02X%02X%02X%02X", alpha, red, green, blue);
    }
      
    private int toonSegment( int aIn, int aSegCount )
    {
    	int theJump = 255 / aSegCount;
    	for( int i = 0; i <= 255; i+= theJump )
    	{
    		if( aIn > (i) && aIn < i+theJump )
    		{
    			return Math.min(i+theJump, 255);
    		}
    	}
    	return 0;
    }
    
    private int toonShade(int red, int green, int blue)
    {
    	red = toonSegment(red, TOON_LEVEL);
    	green = toonSegment(green, TOON_LEVEL);
    	blue = toonSegment(blue, TOON_LEVEL);
    	return Color.rgb(red, green, blue);
    	
    }
    
    //put on next screen after process button pressed
    public void findFood(String type)
    {
    	pixels = new int[bitMap.getWidth() * bitMap.getHeight()];
    	bitMap.getPixels(pixels, 0, bitMap.getWidth(), 0, 0, bitMap.getWidth(), bitMap.getHeight());
    	
    	//this is for mapping the toon color values found to nutrients
    	colorToNutrientMap = new HashMap<Integer, String>();

    	//loop through pixels, put into buckets and change pixel to highlight on screen after bucketed     
    	for(int i = 0; i < pixels.length; i++)
    	{
    		Pair<Integer, String> colorIntNamePair = getMappedColor(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]));
    		//int mappedColor = getMappedColor(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]));
    		//check if real color or bucket
    		
    		int colorBucket = type == "real" ? toonShade(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i])) : toonShade(Color.red(colorIntNamePair.first), Color.green(colorIntNamePair.first), Color.blue(colorIntNamePair.first));
    		pixels[i] = colorBucket;
    		Integer theColorKey = colorBucket;
    		
    		//for mapping toon color values to string color names
    		if(!colorToNutrientMap.containsKey(theColorKey))
    		{
    			colorToNutrientMap.put(theColorKey, colorIntNamePair.second);
    		}
    		
    		
    		if( colorHistogram.containsKey( theColorKey ))
    		{
    			Integer theCount = colorHistogram.get(theColorKey);
    			theCount++;
    			colorHistogram.put(theColorKey,  theCount );
    		}
    		else
    		{
    			colorHistogram.put( theColorKey, 1 );
    		}
    	}
    	
    	//reset pixels to bucketed values
    	Bitmap theCopy = bitMap.copy(bitMap.getConfig(), true );
    	theCopy.setPixels(pixels, 0, bitMap.getWidth(), 0, 0, bitMap.getWidth(), bitMap.getHeight());  	
    	setThumbnail( theCopy );

    }
    
    //find closest distance from this rgb to our static list of rgbs
    public Pair<Integer, String> getMappedColor(int x, int y, int z)
    {
    	double shortestDistance = 100000000;
    	int color = 0;
    	String name = "";
    	
    	for(ColorBucket i : colorList)
    	{
    		//calculate distance from this point to value in list
    		double d = getDistance(x,y,z,i.colorValue);
    		if(d < shortestDistance)
    		{
    			shortestDistance = d;
    			color = i.colorValue;
    			name = i.colorName;
    		}	
    	}
    	
    	return new Pair<Integer, String>(color, name);
    }
    
    public double getDistance(int x, int y, int z, int color)
    {
    	//find euclidean distance of color and xyz of our mapped color
    	return Math.sqrt(Math.pow((x - Color.red(color)), 2) + Math.pow((y - Color.green(color)), 2) + Math.pow((z - Color.blue(color)), 2));
    }
    
    public void analyzePhoto(String type)
    {
    	/*Toast.makeText(getApplicationContext(), "analyzed!!! =)",
    			   Toast.LENGTH_LONG).show();*/
    	
    	//see if we can find food colors
        findFood(type);
    	drawPie();
    }
    
    public void drawPie()
    {
    	int count = colorHistogram.keySet().size();
    	
    	double[] VALUES = new double[count];
    	int[] COLORS = new int[count];
    	String[] NAME_LIST = new String[count];
        Iterator<Entry<Integer, Integer>> it = colorHistogram.entrySet().iterator();
        mDBHelper = new DatabaseHelper( this );
        List<String> list = new ArrayList<String>();
        
        while (it.hasNext()) 
        {
            Entry<Integer, Integer> pairs = it.next();
            --count;
            VALUES[count] = (Integer) pairs.getValue();
            COLORS[count] = (Integer)pairs.getKey();
            
            //get nutrient from DB
            //right now will jsut show color name, hook up to DB next jdl here
            String colorName = colorToNutrientMap.get((Integer)pairs.getKey());
            list = mDBHelper.getNutrients(mDBHelper.getWritableDatabase(), colorName, "vegetable"); //only vegetable group for now
            String colorTitle = colorToNutrientMap.get((Integer)pairs.getKey()) + "\nNutrients: ";
            
            for(String i : list)
            {
            	colorTitle += i + ",";
            }
            
            NAME_LIST[count] = colorTitle;
            
            it.remove(); // avoids a ConcurrentModificationException
        }        
        Bundle theX = new Bundle();
		theX.putIntArray("COLORS", COLORS);
		theX.putDoubleArray("VALUES", VALUES);
		theX.putStringArray("NAMES", NAME_LIST);
    	Intent resultPageIntent = new Intent(this, ResultActivity.class);
    	resultPageIntent.putExtras( theX );
    	startActivity(resultPageIntent);
    }
    
    private Bitmap convert(Bitmap bitmap, Bitmap.Config config) {
    	//Object mybitmapss = BitmapFactory.decodeResource(getResources(), R.drawable.familyportrait2,bitmapFatoryOptions);
        Bitmap convertedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        Canvas canvas = new Canvas(convertedBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return convertedBitmap;
    }
    
    //our colors object
    public class ColorBucket
    {
    	String colorName;
    	int colorValue;
    	
    }
}
