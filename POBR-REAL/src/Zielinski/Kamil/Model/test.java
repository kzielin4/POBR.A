package Zielinski.Kamil.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class test
{

	public static void main(String[] args)
	{
		String imageName = "l21.jpg";
		BufferedImage image = new ImageReader().getImage(imageName);
//		int width = image.getWidth();
//		int height = image.getHeight();
//		int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
//		System.out.println(pixels.length + "  x: " + width + " y: " + height);
////		Pixel p1 = new Pixel((height - 1) * 161 + 183);
////		System.out.println(p1.getRed());
////		System.out.println(p1.getGreen());
////		System.out.println(p1.getBlue());
//		ArrayList<Pixel> list = new ArrayList<Pixel>();
//		int x=0;
//		for (int pixel : pixels)
//		{
//			Pixel p1 = new Pixel(pixel);
//			if(p1.getRed()>150 )
//			{
//				++x;
//			}
//		}
		Pixel[][] pixelMatrix = PixelMatrix.mapImage(image);
		BufferedImage resultImage = new Finder().find(image);
        File output = new File("source.png");
        try
		{
			ImageIO.write(resultImage, "png", output);
			System.out.println("done");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
		
	}

}
