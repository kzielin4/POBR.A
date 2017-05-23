package Zielinski.Kamil.Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;

import javax.imageio.ImageIO;

public class ImageReader
{

	public BufferedImage getImage(String imageFileName)
	{
		File imageFile = new ImageFileProvider().getFile(imageFileName);
		BufferedImage image;
		try
		{
			image = ImageIO.read(imageFile);
		}
		catch (IOException e)
		{
			throw new MissingResourceException("File cannot be opened", imageFile.getAbsolutePath(),
					imageFile.getName());
		}
		return image;
	}
}
