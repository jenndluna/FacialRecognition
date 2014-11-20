package com.foodrecognitionapp.mobilecomputing;

import com.facialrecognitionapp.mobilecomputing.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
/*import android.media.FaceDetector;
import android.media.FaceDetector.Face;*/
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FoodScan extends ActionBarActivity{
	
	/*private static final int MAX_FACES = 10;*/
	ImageView ivThumbnailPhoto;
    Bitmap bitMap;
    static int TAKE_PICTURE = 1;
    Button analyzePhotoBtn;
    int[] pixels;
    int redPixels, greenPixels, whitePixels = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_scan);
		
		ivThumbnailPhoto = (ImageView) findViewById(R.id.ivThumbnailPhoto);
		
		//set button click listener
		Button btn = (Button)findViewById(R.id.photoButton);
		analyzePhotoBtn = (Button)findViewById(R.id.analyzeButton);
	       
        btn.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
				// TODO Auto-generated method stub
        		takePhoto();     
        	}
       });
        
        analyzePhotoBtn.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
				// TODO Auto-generated method stub
        		analyzePhoto();     
        	}
       });
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
            ivThumbnailPhoto.setImageBitmap(bitMap);
 
        }
        
    }
    
    public String getRGBColor(int alpha, int red, int green, int blue)
    {
    	return String.format("#%02X%02X%02X%02X", alpha, red, green, blue);
    }
    
    public int getColorBucket(int red, int green, int blue)
    {
    	if(red > 100 && red <= 255)
    	{
    		redPixels++;
    		return Color.RED;
    	}
    	
    	return Color.BLACK;
    }
    
    //put on next screen after process button pressed
    public void findFood()
    {
    	pixels = new int[bitMap.getWidth() * bitMap.getHeight()];
    	bitMap.getPixels(pixels, 0, bitMap.getWidth(), 0, 0, bitMap.getWidth(), bitMap.getHeight());
    	
    	//testing with first pixel, will loop thru and calculate percentages
    	//todo: implement color pie chart, and touch event on picture to show individual stats
    	int alpha = Color.alpha(pixels[0]);
        int red = Color.red(pixels[0]);
        int blue = Color.blue(pixels[0]);
        int green = Color.green(pixels[0]);
        String testcolor = getRGBColor(alpha, red, green, blue);
        Log.i("RGB", testcolor);

    	//loop through pixels, put into buckets and change pixel to highlight on screen after bucketed     
    	for(int i = 0; i < pixels.length; i++)
    	{
    		int colorBucket = getColorBucket(Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]));
    		pixels[i] = colorBucket;
    		
    	}
    	
    	//reset pixels to bucketed values
    	bitMap.setPixels(pixels, 0, bitMap.getWidth(), 0, 0, bitMap.getWidth(), bitMap.getHeight());  	 		 	
    }
    
    public void analyzePhoto()
    {
    	/*Toast.makeText(getApplicationContext(), "analyzed!!! =)",
    			   Toast.LENGTH_LONG).show();*/
    	
    	//see if we can find food colors
        findFood();
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
}
