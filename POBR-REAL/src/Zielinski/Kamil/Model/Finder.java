package Zielinski.Kamil.Model;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferedImage;

public class Finder
{
	public BufferedImage find(BufferedImage file)
	{
		Pixel[][] pixels = PixelMatrix.mapImage(file);
        Pixel[][] sourcePixels = PixelMatrix.deepCopy(pixels);
        pixels = new UnsharpMask().putMask(pixels);
        pixels = new Segmentation().threshold(pixels);
        BufferedImage result = new BufferedImage(file.getWidth(), file.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int[] array = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();
        System.arraycopy(PixelMatrix.mapPixels(pixels), 0, array, 0, array.length);
        return result;
	}
}
