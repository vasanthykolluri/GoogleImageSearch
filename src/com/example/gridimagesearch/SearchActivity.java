
package com.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	GridView gvResults;
	View btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
	String imageSize;
	String imageColor;
	String imageType;
	String siteFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();

		// handle search
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long arg3) {

				Intent i = new Intent(getApplicationContext(),
						ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
/*
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your
				// AdapterView
				customLoadMoreDataFromApi(page);
				// or customLoadMoreDataFromApi(totalItemsCount);
			}

			private void customLoadMoreDataFromApi(int page) {
				// TODO Auto-generated method stub
				
			}
		});*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_icon, menu);
		return true;
	}

	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = findViewById(R.id.btnSearch);
	}

	private void showSearchResults() {
		String query = etQuery.getText().toString();

		AsyncHttpClient client = new AsyncHttpClient();

		String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=12&"
				+ "start="
				+ 0
				+ "&v=1.0&q="
				+ Uri.encode(query)
				+ (imageSize.equals("all") ? "" : "&imgsz=" + imageSize)
				+ (imageColor.equals("all") ? "" : "&imgcolor=" + imageColor)
				+ (imageType.equals("all") ? "" : "&imgtype=" + imageType)
				+ ((siteFilter.equals("")) ? "" : "&as_sitesearch="
						+ siteFilter);

		Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;

				try {
					imageJsonResults = response.getJSONObject("responseData")
							.getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult
							.fromJSONArray(imageJsonResults));

					Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

		});

	}

	public void onImageSearch(View v) {
		showSearchResults();
	}

	public void onSettings(MenuItem mi) {
		Intent FilterIntent = new Intent(this, FilterActivity.class);
		startActivityForResult(FilterIntent, 500);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent filters) {
		if (resultCode == RESULT_OK && requestCode == 500) {
			// get the filter values
			imageSize = filters.getStringExtra("imageSizeKey");
			imageColor = filters.getStringExtra("imageColorKey");
			imageType = filters.getStringExtra("imageTypeKey");
			siteFilter = filters.getStringExtra("siteFilterKey");
			showSearchResults();
		}
	}

}
