package Zielinski.Kamil.Model;

/*
 * Klasa odpowiedzialna za obliczenia momentów geometrycznych
 */
import Zielinski.Kamil.Util.Tuple;
import Zielinski.Kamil.Util.Tuple3;

public class MomentsCounter
{
	// Funkcja zwraca 1,3 i 7 niezmiennik geometryczny
	Tuple3<Double, Double, Double> getSegmentMoments(Pixel[][] pixels)
	{
		return Tuple3.from(M1(pixels), M3(pixels), M7(pixels));
	}

	// Funkcja zwracaj¹ca momenty zwyk³e geometryczne
	private double m(int p, int q, Pixel[][] pixels)
	{
		double sum = 0;
		for (int i = 0; i < pixels.length; i++)
		{
			for (int j = 0; j < pixels[0].length; j++)
			{
				if (pixels[i][j] != Pixel.BLACK)
				{
					sum += Math.pow(i, p) * Math.pow(j, q);
				}
			}
		}
		return sum;
	}

	// Funkcja zwracaj¹ca momenty zwyk³e geometryczne
	private double M(int p, int q, Pixel[][] pixels)
	{
		Tuple<Double, Double> center = imageCenter(pixels);
		double sum = 0;
		for (int i = 0; i < pixels.length; i++)
		{
			for (int j = 0; j < pixels[0].length; j++)
			{
				if (pixels[i][j] != Pixel.BLACK)
				{
					sum += Math.pow(i - center._1, p) * Math.pow(j - center._2, q);
				}
			}
		}
		return sum;
	}

	// Funkcja zwraca m10 i m01
	private Tuple<Double, Double> imageCenter(Pixel[][] pixels)
	{
		return Tuple.from(m(1, 0, pixels) / m(0, 0, pixels), m(0, 1, pixels) / m(0, 0, pixels));
	}

	// Funkcja zwraca centralny moment 1 (niezmiennik geometryczny)
	private double M1(Pixel[][] pixels)
	{
		return (M(2, 0, pixels) + M(0, 2, pixels)) / Math.pow(m(0, 0, pixels), 2);
	}

	// Funkcja zwraca centralny moment 3 (niezmiennik geometryczny)
	private double M3(Pixel[][] pixels)
	{
		return (Math.pow(M(3, 0, pixels) - 3 * M(1, 2, pixels), 2) + Math.pow(3 * M(2, 1, pixels) + M(0, 3, pixels), 2))
				/ Math.pow(m(0, 0, pixels), 5);
	}

	// Funkcja zwraca centralny moment 7 (niezmiennik geometryczny)
	private double M7(Pixel[][] pixels)
	{
		return (M(2, 0, pixels) * M(0, 2, pixels) - Math.pow(M(1, 1, pixels), 2)) / Math.pow(m(0, 0, pixels), 4);
	}
}
