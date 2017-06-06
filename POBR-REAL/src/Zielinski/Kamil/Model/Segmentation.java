package Zielinski.Kamil.Model;

public class Segmentation
{
	Pixel[][] threshold(Pixel[][] source)
	{
		for (int i = 0; i < source.length; i++)
		{
			for (int j = 0; j < source[0].length; j++)
			{
				Pixel pixel = source[i][j];
				if ((pixel.getBlue()>pixel.getRed() && pixel.getBlue()>pixel.getGreen() && pixel.getBlue() > 68  && pixel.getGreen()<117 && pixel.getRed()<117 )||  (pixel.getRed() >= 89  && pixel.getBlue()<pixel.getRed() && pixel.getRed()>pixel.getGreen() && pixel.getGreen()<117 && pixel.getBlue()<130 ) )
				{
					source[i][j] = Pixel.WHITE;
				}
				else
				{
					source[i][j] = Pixel.BLACK;
				}
			}
		}
		return source;
	}
}
