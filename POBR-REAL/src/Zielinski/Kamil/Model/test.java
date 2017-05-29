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

	}

}
