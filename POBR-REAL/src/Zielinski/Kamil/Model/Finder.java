package Zielinski.Kamil.Model;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class Finder
{
	public BufferedImage find(BufferedImage file)
	{
		Pixel[][] pixels = PixelMatrix.mapImage(file);
        pixels = new UnsharpMask().putMask(pixels);
        pixels = new Segmentation().threshold(pixels);
        Pixel[][] sourcePixels = PixelMatrix.deepCopy(pixels);
        BufferedImage result2 = new BufferedImage(file.getWidth(), file.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int[] array2 = ((DataBufferInt) result2.getRaster().getDataBuffer()).getData();
        System.arraycopy(PixelMatrix.mapPixels(pixels), 0, array2, 0, array2.length);
        System.out.println(pixels.length + " "+sourcePixels[0].length);
        File output = new File("source2.png");
        try
		{
			ImageIO.write(result2, "png", output);
			System.out.println("done");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        pixels = new ErosionMaker().erode(pixels);
        Set<Segment> segments = new SegmentsCreator().process(pixels);
        for (Segment segment : segments)
		{
			System.out.println("Points : x1-" +segment.heightInterval()._1+" x2-"+segment.heightInterval()._2 +" y1-"+segment.widthInterval()._1+" y2-"+segment.widthInterval()._2);
		}
        //System.out.println(""+sourcePixels[0].length + " "+sourcePixels.length);
       
        pixels = drawBoundingBox(sourcePixels,  new ArrayList<Segment>(segments));
        System.out.println(segments.size());
        BufferedImage result = new BufferedImage(file.getWidth(), file.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int[] array = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();
        System.arraycopy(PixelMatrix.mapPixels(pixels), 0, array, 0, array.length);
        return result;
	}
	  private Pixel[][] drawBoundingBox(Pixel[][] pixels, List<Segment> segments) 
	  {
		  for (Segment segment : segments)
		{
			   int minWidth =segment.widthInterval()._1 ;
		        int maxWidth =segment.widthInterval()._2  ;
		        int minHeight = segment.heightInterval()._1;
		        int maxHeight = segment.heightInterval()._2;
		        Pixel red = new Pixel(255, 0, 0);
		        for (int i = minHeight; i < maxHeight; i++) {
		            pixels[i][minWidth] = red;
		            pixels[i][maxWidth] = red;
		        }
		        for (int i = minWidth; i < maxWidth; i++) {
		            pixels[minHeight][i] = red;
		            pixels[maxHeight][i] = red;
		        }
		}
	        return pixels;
	    }
}
