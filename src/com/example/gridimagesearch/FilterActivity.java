package com.example.gridimagesearch;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class FilterActivity extends Activity {

	Spinner spnrImageSize;
	Spinner spnrImageColor;
	Spinner spnrImageType;
	EditText etSiteFilter;

	Filters filters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		setupViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}

	private void setupViews() {
		spnrImageSize = (Spinner) findViewById(R.id.spnrImageSize);
		spnrImageColor = (Spinner) findViewById(R.id.spnrImageColor);
		spnrImageType = (Spinner) findViewById(R.id.spnrImageType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);

		ArrayList<String> imageSizeList = new ArrayList<String>(Filters
				.getImageSizes().keySet());
		ArrayAdapter<String> imageSizeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, imageSizeList);
		spnrImageSize.setAdapter(imageSizeAdapter);

		ArrayList<String> imageColorList = new ArrayList<String>(Filters
				.getImageColors().keySet());
		ArrayAdapter<String> imageColorAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, imageColorList);
		spnrImageColor.setAdapter(imageColorAdapter);

		ArrayList<String> imageTypeList = new ArrayList<String>(Filters
				.getImageTypes().keySet());
		ArrayAdapter<String> imageTypeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, imageTypeList);
		spnrImageType.setAdapter(imageTypeAdapter);
		
		etSiteFilter.setText("");
	}

	public void onFilterSave(View v) {
		Intent searchIntent = new Intent(this, SearchActivity.class);
		
		String imageSizeKey = Filters.getImageSizes().get(spnrImageSize.getSelectedItem().toString());
		String imageColorKey = Filters.getImageColors().get(spnrImageColor.getSelectedItem().toString());
		String imageTypeKey = Filters.getImageTypes().get(spnrImageType.getSelectedItem().toString());
		String siteFilterKey = etSiteFilter.getText().toString();

		searchIntent.putExtra("imageSizeKey", imageSizeKey);
		searchIntent.putExtra("imageColorKey", imageColorKey);
		searchIntent.putExtra("imageTypeKey", imageTypeKey);
		searchIntent.putExtra("siteFilterKey", siteFilterKey);
		
		setResult(RESULT_OK, searchIntent);
		finish();
	}
}
