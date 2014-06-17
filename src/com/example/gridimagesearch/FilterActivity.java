package com.example.gridimagesearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FilterActivity extends Activity {

	Spinner spnrImageSize;
	Spinner spnrImageColor;
	Spinner spnrImageType;
	EditText etSiteFilter;

	ArrayList<String> savedFilters;

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

		// Set the saved filters
		readFilters();

		if (savedFilters.isEmpty()) {
			spnrImageSize.setSelection(0);
			spnrImageColor.setSelection(0);
			spnrImageType.setSelection(0);
			etSiteFilter.setText("");			
		} else {
			spnrImageSize.setSelection(Integer.parseInt(savedFilters.get(0)));
			spnrImageColor.setSelection(Integer.parseInt(savedFilters.get(1)));
			spnrImageType.setSelection(Integer.parseInt(savedFilters.get(2)));
			etSiteFilter.setText(savedFilters.get(3));
		}
	}

	private void readFilters() {
		File filesDir = getFilesDir();
		File filtersFile = new File(filesDir, "savedfilters.txt");

		try {
			savedFilters = new ArrayList<String>(
					FileUtils.readLines(filtersFile));
		} catch (IOException e) {
			savedFilters = new ArrayList<String>();
		}
		System.out.println("VK: readFilters - after reading size="
				+ savedFilters.size());

	}

	private void writeFilters() {
		File filesDir = getFilesDir();
		File filtersFile = new File(filesDir, "savedfilters.txt");
		try {
			FileUtils.writeLines(filtersFile, savedFilters);
			System.out.println("VK:writeFilters - after writing size="
					+ savedFilters.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onFilterSave(View v) {
		Intent searchIntent = new Intent(this, SearchActivity.class);

		String imageSizeKey = Filters.getImageSizes().get(
				spnrImageSize.getSelectedItem().toString());
		String imageColorKey = Filters.getImageColors().get(
				spnrImageColor.getSelectedItem().toString());
		String imageTypeKey = Filters.getImageTypes().get(
				spnrImageType.getSelectedItem().toString());
		String siteFilterKey = etSiteFilter.getText().toString();

		searchIntent.putExtra("imageSizeKey", imageSizeKey);
		searchIntent.putExtra("imageColorKey", imageColorKey);
		searchIntent.putExtra("imageTypeKey", imageTypeKey);
		searchIntent.putExtra("siteFilterKey", siteFilterKey);

		
		savedFilters = new ArrayList<String>();
		
		// Save Filters in a file
		savedFilters.add(Integer.toString(spnrImageSize
				.getSelectedItemPosition()));
		savedFilters.add(Integer.toString(spnrImageColor
				.getSelectedItemPosition()));
		savedFilters.add(Integer.toString(spnrImageType
				.getSelectedItemPosition()));
		savedFilters.add(siteFilterKey);

		writeFilters();

		setResult(RESULT_OK, searchIntent);
		finish();
	}
}
