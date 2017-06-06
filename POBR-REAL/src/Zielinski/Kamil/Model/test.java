package Zielinski.Kamil.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test
{

	public static void main(String[] args)
	{
		String imageName = "duze.jpg";
		BufferedImage image = new ImageReader().getImage(imageName);
		int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        Pixel p1=new Pixel(height*86+105);
        System.out.println(p1.getRed());
        System.out.println(p1.getGreen());
        System.out.println(p1.getBlue());
	}

}
