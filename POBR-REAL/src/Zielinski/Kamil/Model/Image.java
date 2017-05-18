package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Image
{
	private Mat img;

	public Image()
	{
		img = new Mat();
	}

	public int loadPicture(String path)
	{
		img = Imgcodecs.imread(path);
		if (img.empty())
		{
			return 0;
		}
		return 1;
	}

	public void testMethod()
	{
		System.out.println(img.depth());
		System.out.println(img.rows());
		System.out.println(img.get(100, 100));
		List<Mat> lRgb = new ArrayList<Mat>(3);
		Core.split(img, lRgb);
		System.out.println(lRgb.get(0));
	}
}
