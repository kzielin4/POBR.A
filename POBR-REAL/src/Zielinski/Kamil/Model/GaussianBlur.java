/*
 * Klasa tworz¹ca obraz rozmyty zapisany w tablict 2D
 * Na podstawie obrazu wejœciowego i filtru niskopasmowego
 * Algorytm rozmycia Gaussa
 */
package Zielinski.Kamil.Model;

import java.util.Arrays;

public class GaussianBlur
{
	// niskopasmowa maska filtracji
	private final float[][] kernel;

	GaussianBlur(float[][] kernel) {
		this.kernel = kernel;
	}

	// Funkcja tworz¹ca obraz rozmyty - romycie Gaussa
	Pixel[][] blur(Pixel[][] pixels)
	{
		Pixel[][] blur = new Pixel[pixels.length][pixels[0].length];
		for (int i = 0; i < blur.length; i++)
		{
			for (int j = 0; j < blur[0].length; j++)
			{
				float red = 0;
				float green = 0;
				float blue = 0;
				// Przechodzimy po otoczeniu punktu
				for (int k = 0; k < kernel.length; k++)
				{
					for (int l = 0; l < kernel.length; l++)
					{
						// Zabezpiecznie przed wyjœciem poza tablice tablic
						Pixel pixel = new Pixel(0, 0, 0);
						if (!(i - 1 + k < 0 || i - 1 + k >= blur.length || j - 1 + l < 0
								|| j - 1 + l >= blur[0].length))
						{
							pixel = pixels[i - 1 + k][j - 1 + l];
						}
						red += kernel[k][l] * pixel.getRed();
						green += kernel[k][l] * pixel.getGreen();
						blue += kernel[k][l] * pixel.getBlue();
					}
				}
				//Losowanie wartoœci z przedzia³u <SUM-COLOR,255> dla ka¿dego pixela
				blur[i][j] = new Pixel(Math.min(Math.round(red), 255), Math.min(Math.round(green), 255),
						Math.min(Math.round(blue), 255));
			}
		}
		return blur;
	}
}
