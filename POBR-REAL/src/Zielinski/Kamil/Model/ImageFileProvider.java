package Zielinski.Kamil.Model;

import java.io.File;

class ImageFileProvider
{

	private static final String DEFAULT_PHOTOS_DIR_NAME = "photos";
	private final String photosPath;

	ImageFileProvider()
	{
		photosPath = new File("").getAbsolutePath() + File.separator + DEFAULT_PHOTOS_DIR_NAME + File.separator;
	}

	ImageFileProvider(String photosPath)
	{
		this.photosPath = photosPath;
	}

	File getFile(String imageFileName)
	{
		return new File(photosPath + imageFileName);
	}
}
