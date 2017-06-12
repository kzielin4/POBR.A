/*
 * Klasa odpowiedzialna za wyznaczania wspó³czyników kszta³u wybranego segmentu
 */
package Zielinski.Kamil.Model;

public class MainowskiCounter
{
	// Funkcja zwraca W3
	double malinowskiej(Pixel[][] pixels)
	{
		return perimeter(pixels) / (2 * Math.sqrt(Math.PI * square(pixels))) - 1;
	}

	// Funkcja zwraca W9
	double W9(Pixel[][] pixels)
	{
		return (2 * Math.sqrt(Math.PI * square(pixels))) / perimeter(pixels);
	}

	// Funkcja zwraca W2
	double W2(Pixel[][] pixels)
	{
		return perimeter(pixels) / Math.PI;
	}
    
	//Funkcja zwraca wartoœæ pola segmentu
	private int square(Pixel[][] pixels)
	{
		int square = 0;
		for (int i = 0; i < pixels.length; i++)
		{
			for (int j = 0; j < pixels[0].length; j++)
			{
				if (pixels[i][j] != Pixel.BLACK)
				{
					square++;
				}
			}
		}
		return square;
	}

	//Funkcja zwraca wartoœæ obwodu segmentu
	private int perimeter(Pixel[][] pixels)
	{
		int perimeter = 0;
		for (int i = 1; i < pixels.length - 1; i++)
		{
			for (int j = 1; j < pixels[0].length - 1; j++)
			{
				if (pixels[i][j] != Pixel.BLACK)
				{
					boolean isPerimeterElement = false;
					for (int k = -1; k < 2; k++)
					{
						for (int l = -1; l < 2; l++)
						{
							if (pixels[i + k][j + l] == Pixel.BLACK)
							{
								isPerimeterElement = true;
							}
						}
					}
					if (isPerimeterElement)
					{
						perimeter++;
					}
				}
			}
		}
		return perimeter;
	}
}
