/*
 * Klasa przetwarzaj¹ca BufferedImage do tablicy Pixeli 2D
 * PixelMatrix umo¿lwia tworzenie g³êbokiej kopi tablicy Pixeli 2D
 * 
 */
package Zielinski.Kamil.Model;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class PixelMatrix
{
	private PixelMatrix()
	{
	}

	//Funkcja na podstawie obrazu zwraca tablice Pixeli 2D
	public static Pixel[][] mapImage(BufferedImage image)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		return mapToPixelsMatrix(width, height, pixels);
	}
	
    //Funkcja tworzy tablice pixeli 2D
	private static Pixel[][] mapToPixelsMatrix(int width, int height, int[] pixels)
	{
		Pixel[][] pixelMatrix = new Pixel[height][width];
		int row = 0;
		int column = 0;
		for (int pixel : pixels)
		{
			pixelMatrix[row][column] = new Pixel(pixel);
			if (column >= width - 1)
			{
				column = 0;
				row++;
			}
			else
			{
				column++;
			}
		}
		return pixelMatrix;
	}
	
	//Funkcja zwraca tablice z wartoœciami jasnoœci RGB potrzebn¹ do utworzenia obrazu wyjœciowego
	static int[] mapPixels(Pixel[][] pixels)
	{
		int[] result = new int[pixels.length * pixels[0].length];
		int counter = 0;
		for (Pixel[] pixelsArray : pixels)
		{
			for (Pixel pixel : pixelsArray)
			{
				int rgb = 0xFF;
				rgb = (rgb << 8) + pixel.getRed();
				rgb = (rgb << 8) + pixel.getGreen();
				rgb = (rgb << 8) + pixel.getBlue();
				result[counter] = rgb;
				counter++;
			}
		}
		return result;
	}

	//Funkcja tworzy g³ebok¹ kopie tablicy Pixeli 2D
	static Pixel[][] deepCopy(Pixel[][] pixels)
	{
		int width = pixels[0].length;
		int height = pixels.length;
		Pixel[][] pixelMatrix = new Pixel[height][width];
		for (int i = 0; i < height; i++)
			pixelMatrix[i] = Arrays.copyOf(pixels[i], width);
		return pixelMatrix;
	}
}
