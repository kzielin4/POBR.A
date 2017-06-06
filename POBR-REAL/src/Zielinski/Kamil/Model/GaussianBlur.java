package Zielinski.Kamil.Model;

import java.util.Arrays;

public class GaussianBlur
{
	// Low-pass convection filtration mask
	private final float[][] kernel;

	/**
	 * @param kernel
	 *            gaussian kernel, odd length matrix
	 */
	GaussianBlur(float[][] kernel)
	{
		//validateKernel(kernel);
		this.kernel = kernel;
	}

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
				for (int k = 0; k < kernel.length; k++)
				{
					for (int l = 0; l < kernel.length; l++)
					{
						//Zabezpiecznie przed wyjœciem poza tablice tablic
						Pixel pixel = new Pixel(0, 0, 0);
						if (!(i - 1 + k < 0 || i - 1 + k >= blur.length || j - 1 + l < 0 || j - 1 + l >= blur[0].length))
						{
							pixel = pixels[i - 1 + k][j - 1 + l];
						}
						red += kernel[k][l] * pixel.getRed(); // œrednia
						green += kernel[k][l] * pixel.getGreen(); // œrednia
						blue += kernel[k][l] * pixel.getBlue(); // œrednia
					}
				}
				blur[i][j] = new Pixel(Math.min(Math.round(red), 255), Math.min(Math.round(green), 255),
						Math.min(Math.round(blue), 255));
			}
		}
		return blur;
	}

	private void validateKernel(float[][] kernel)
	{
		if (kernel.length % 2 == 0
				|| Arrays.stream(kernel).filter(kl -> kl.length != kernel.length).anyMatch(f -> true))
		{
			throw new IllegalArgumentException("Kernel must be odd length matrix");
		}
	}
}
