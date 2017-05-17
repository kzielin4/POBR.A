package Zielinski.Kamil.Model;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Image
{
	private Mat img;

	public Image()
	{
		img = new Mat();
	}

	public void loadPicture(String path)
	{
		img = Imgcodecs.imread(path);
	}
	
	public void testMethod()
	{
		System.out.println(img.depth());
	}
}
