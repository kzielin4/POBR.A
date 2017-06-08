package Zielinski.Kamil.Model;

public class Segmentation
{
	static int MINBLUE = 68; //68
	static int MINRED = 80; //87
	
	Pixel[][] threshold(Pixel[][] source)
	{
		for (int i = 0; i < source.length; i++)
		{
			for (int j = 0; j < source[0].length; j++)
			{
				Pixel pixel = source[i][j];
				if ((pixel.getBlue()>pixel.getRed() && pixel.getBlue()>pixel.getGreen() && pixel.getBlue() > MINBLUE  && pixel.getGreen()<130 && pixel.getRed()<130 )||  (pixel.getRed() >= MINRED  && pixel.getBlue()<pixel.getRed() && pixel.getRed()>pixel.getGreen() && pixel.getGreen()<150 && pixel.getBlue()<172 ) )
				{
					if(pixel.getBlue()> pixel.getRed())
						source[i][j] = Pixel.BLUE;
					else
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
