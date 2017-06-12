/*
 * Klasa odpowiedzialna za segmentacje przez progowanie obrazu 
 * Czarny - t³o
 * Bia³y - literki R E A L
 * Niebieski - kreska pozioma i pionowa
 * 
 */
package Zielinski.Kamil.Model;

public class Segmentation
{
	static int MINBLUE = 58; //68
	static int MINRED = 68; //87
	static int MAXRG = 180;
	static int MAXRB = 150;
	static int MAXBG = 140;
	static int MAXBR = 140;

	Pixel[][] threshold(final Pixel[][] source)
	{
		for (int i = 0; i < source.length; i++)
		{
			for (int j = 0; j < source[0].length; j++)
			{
				final Pixel pixel = source[i][j];
				if (((pixel.getBlue()>pixel.getRed()) && (pixel.getBlue()>pixel.getGreen()) && (pixel.getBlue() > MINBLUE)  && (pixel.getGreen()<MAXBG) && (pixel.getRed()<MAXBR) )||  ((pixel.getRed() >= MINRED)  && (pixel.getBlue()<pixel.getRed()) && (pixel.getRed()>pixel.getGreen()) && (pixel.getGreen()<MAXRG) && (pixel.getBlue()<MAXRB) ) )//130 130 150 172
				{
					if(pixel.getBlue()> pixel.getRed()) {
						source[i][j] = Pixel.BLUE;
					} else {
						source[i][j] = Pixel.WHITE;
					}
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
