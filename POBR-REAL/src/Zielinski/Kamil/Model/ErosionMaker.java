package Zielinski.Kamil.Model;

public class ErosionMaker
{
	private static final boolean[][] DEFAULT_KERNEL = new boolean[][]
	{
			{ false, true, true },
			{ true, false, true },
			{ true, true, false} 
	};

	Pixel[][] erode(Pixel[][] source)
	{
		//System.out.println("l1: "+ (DEFAULT_KERNEL.length / 2)+" l2: "+(source[0].length - DEFAULT_KERNEL.length));
		return erode(source, DEFAULT_KERNEL);
	}

	private Pixel[][] erode(Pixel[][] source, boolean[][] kernel)
	{
		Pixel[][] result = PixelMatrix.deepCopy(source);
		//przechodmy po pixelach 
		for (int i = kernel.length / 2; i < source.length - kernel.length / 2; i++)
		{
			for (int j = kernel.length / 2; j < source[0].length - kernel.length / 2; j++)
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