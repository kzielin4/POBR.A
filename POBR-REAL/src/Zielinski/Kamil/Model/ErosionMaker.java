package Zielinski.Kamil.Model;

/*
 * Klasa odpowiedzialna za erozj� obrazu
 */
public class ErosionMaker
{
	private static final boolean[][] DEFAULT_KERNEL = new boolean[][]
	{

			{ false, true, true },
			{ false, false, true },
			{ true, true, true } };

	// Funkcja wywo�uj�ca proces erozji
	Pixel[][] erode(final Pixel[][] source)
	{
		return erode(source, DEFAULT_KERNEL);
	}

	// Funkcja na podstawie j�dra erozji obciana brzegowe pixele (s�siaduj�ce z
	// t�em)
	private Pixel[][] erode(final Pixel[][] source, final boolean[][] kernel)
	{
		final Pixel[][] result = PixelMatrix.deepCopy(source);
		for (int i = kernel.length / 2; i < (source.length - (kernel.length / 2)); i++)
		{
			for (int j = kernel.length / 2; j < (source[0].length - (kernel.length / 2)); j++)
			{
				if (source[i][j] == Pixel.BLACK)
				{
					for (int k = -1; k <= 1; k++)
					{
						for (int l = -1; l <= 1; l++)
						{
							if (kernel[k + 1][l + 1])
							{
								result[i + k][j + l] = Pixel.BLACK;
							}
						}
					}
				}
			}
		}
		return result;
	}
}
