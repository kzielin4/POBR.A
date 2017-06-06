package Zielinski.Kamil.Model;

public class UnsharpMask
{
	private static final float[][] KERNEL;

	static
	{
		float sigma = 1f;
		int size = 3;
		KERNEL = new float[size][size];
		float mean = size / 2;
		float sum = 0.0f;
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				KERNEL[i][j] = (float) (Math.exp(-0.5 * Math.pow((i - mean) / sigma, 2) + Math.pow((j - mean) / sigma, 2)) / 2 * Math.PI* sigma * sigma);
				sum += KERNEL[i][j];
			}
		}
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				KERNEL[i][j] /= sum;
			}
		}
	}

	Pixel[][] putMask(Pixel[][] pixels)
	{
		Pixel[][] blur = new GaussianBlur(KERNEL).blur(pixels);
		for (int i = 0; i < pixels.length; i++)
		{
			for (int j = 0; j < pixels[0].length; j++)
			{
				pixels[i][j] = new Pixel(Math.max(0, Math.min(2 * pixels[i][j].getRed() - blur[i][j].getRed(), 255)),
						Math.max(0, Math.min(2 * pixels[i][j].getGreen() - blur[i][j].getGreen(), 255)),
						Math.max(0, Math.min(2 * pixels[i][j].getBlue() - blur[i][j].getBlue(), 255)));
			}
		}
		return pixels;
	}
}
