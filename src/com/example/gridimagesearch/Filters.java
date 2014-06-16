package com.example.gridimagesearch;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Filters {
	private static final Map<String, String> imageSizes;
	private static final Map<String, String> imageColors;
	private static final Map<String, String> imageTypes;

	static {
		imageSizes = new LinkedHashMap<String, String>();
		imageSizes.put("Icon", "icon");
		imageSizes.put("Small", "small");
		imageSizes.put("Medium", "medium");
		imageSizes.put("Large", "large");
		imageSizes.put("Extra-Large", "xlarge");
		imageSizes.put("XXLarge", "xxlarge");
		imageSizes.put("Huge", "huge");

		imageColors = new LinkedHashMap<String, String>();
		imageColors.put("Black","black");
		imageColors.put("Blue","blue");
		imageColors.put("Brown","brown");
		imageColors.put("Gray","gray");
		imageColors.put("Green","green");
		imageColors.put("Orange","orange");
		imageColors.put("Pink","pink");
		imageColors.put("Purple","purple");
		imageColors.put("Red","red");
		imageColors.put("Teal","teal");
		imageColors.put("White","white");
		imageColors.put("Yellow","yellow");

		imageTypes = new LinkedHashMap<String, String>();
		imageTypes.put("Face", "face");
		imageTypes.put("Photo", "photo");
		imageTypes.put("Clip Art", "clipart");
		imageTypes.put("Line Art", "lineart");
	}

	public static Map<String, String> getImageSizes() {
		return imageSizes;
	}

	public static Map<String, String> getImageColors() {
		return imageColors;
	}

	public static Map<String, String> getImageTypes() {
		return imageTypes;
	}

}
