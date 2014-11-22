package com.facialrecognitionapp.mobilecomputing;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.os.Build;

public class ResultActivity extends ActionBarActivity {

	
    private static int[] COLORS;

    private static double[] VALUES;

    private static String[] NAME_LIST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		Bundle theX = getIntent().getExtras();
		if( theX != null )
		{
			COLORS = theX.getIntArray("COLORS");
			VALUES = theX.getDoubleArray("VALUES");
			NAME_LIST = theX.getStringArray("NAMES");
		}
      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

	    //===== PIE CHART VARS ===============
	    


	    private CategorySeries mSeries = new CategorySeries("");

	    private DefaultRenderer mRenderer = new DefaultRenderer();

	    private GraphicalView mChartView;
	    
	    //===== PIE CHART VARS ===============
	    
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_result,
					container, false);
			
			mRenderer.setApplyBackgroundColor(true);
	        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
	        mRenderer.setChartTitleTextSize(20);
	        mRenderer.setLabelsTextSize(15);
	        mRenderer.setLegendTextSize(15);
	        mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
	        mRenderer.setZoomButtonsVisible(true);
	        mRenderer.setStartAngle(90);
			
	        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.chart);
	        mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
	        
	        for (int i = 0; i < VALUES.length; i++) {
	            mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
	            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
	            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
	            mRenderer.addSeriesRenderer(renderer);
	        }

	        if (mChartView != null) {
	            mChartView.repaint();
	        }
	        
	        layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	        
	        if (mChartView != null) {
	            mChartView.repaint();
	        }
	        
			return rootView;
		}
	}
}
