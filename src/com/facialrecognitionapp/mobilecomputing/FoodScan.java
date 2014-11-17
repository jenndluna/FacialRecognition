package com.facialrecognitionapp.mobilecomputing;

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
        
        //see if we can find food colors
        findFood();
    }
    
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
        String color = String.format("#%02X%02X%02X%02X", alpha, red, green, blue);
        Log.i("RGB", color);

    	
    	/*BitmapFactory.Options bitmap_options = new BitmapFactory.Options();		 
    	bitmap_options.inPreferredConfig = Bitmap.Config.RGB_565;
    	
    	Bitmap convertedBitmap = convert(bitMap, Bitmap.Config.RGB_565);
    	int width = convertedBitmap.getWidth(); //width must be even!
    	int height = convertedBitmap.getHeight();
    	
    	
    	
    	
    	//old face code
    	/*FaceDetector face_detector = new FaceDetector(
    			convertedBitmap.getWidth(), convertedBitmap.getHeight(),	 
    		          MAX_FACES);
    		 		
    	Face[] faces = new FaceDetector.Face[MAX_FACES];	 
    		 
    	int face_count = face_detector.findFaces(bitMap, faces);
    	
    	if(face_count > 0)
    	{
    		Toast.makeText(getApplicationContext(), "Face found!",
     			   Toast.LENGTH_LONG).show();
    		
    		//make analyze button visible
    		analyzePhotoBtn.setEnabled(true);
    		analyzePhotoBtn.setText("Analyze my face!");
    	}*/
    		 	
    }
    
    public void analyzePhoto()
    {
    	Toast.makeText(getApplicationContext(), "analyzed!!! =)",
    			   Toast.LENGTH_LONG).show();
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