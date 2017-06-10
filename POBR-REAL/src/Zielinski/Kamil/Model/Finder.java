package Zielinski.Kamil.Model;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

public class Finder {
	public BufferedImage find(final BufferedImage file) {
		System.out.println("START");
		Pixel[][] pixels = PixelMatrix.mapImage(file);
		pixels = new UnsharpMask().putMask(pixels);
		pixels = new Segmentation().threshold(pixels);
		pixels = new ErosionMaker().erode(pixels);
		final BufferedImage result2 = new BufferedImage(file.getWidth(), file.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final int[] array2 = ((DataBufferInt) result2.getRaster().getDataBuffer()).getData();
		System.arraycopy(PixelMatrix.mapPixels(pixels), 0, array2, 0, array2.length);
		// System.out.println(pixels.length + " "+sourcePixels[0].length);
		final File output = new File("source2.png");
		try {
			ImageIO.write(result2, "png", output);
			System.out.println("done");
		} catch (final IOException e) {
			e.printStackTrace();
		}
		final Pixel[][] sourcePixels = PixelMatrix.deepCopy(pixels);
		final Set<Segment> uknowSeg = new HashSet<>();
		final Set<Segment> segments = new SegmentsCreator().process(pixels);// pomocnicza
		for (final Segment segment : segments) {
			segment.setSegmentType(new SegemntTypeDetector().detectType(segment, sourcePixels));
			if (segment.getSegmentType() == SegmentType.UNKNOWN) {
				uknowSeg.add(segment);
			}
			// System.out.println("Points : x1-" +segment.heightInterval()._1+"
			// x2-"+segment.heightInterval()._2 +"
			// y1-"+segment.widthInterval()._1+"
			// y2-"+segment.widthInterval()._2);
		}
		// System.out.println(""+sourcePixels[0].length + "
		// "+sourcePixels.length);
		System.out.println("l1" + segments.size());
		segments.removeAll(uknowSeg);
		System.out.println("l2" + segments.size());
		// (Segment segment : segments)
		// {
		// if(segment.getSegmentType()==(SegmentType.UNKNOWN)){
		// System.out.println("uknow");
		// }
		// }
		pixels = drawBoundingBox(sourcePixels, new ArrayList<Segment>(segments));
		System.out.println(segments.size());
		final BufferedImage result = new BufferedImage(file.getWidth(), file.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final int[] array = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();
		System.arraycopy(PixelMatrix.mapPixels(pixels), 0, array, 0, array.length);
		return result;
	}

	private Pixel[][] drawBoundingBox(final Pixel[][] pixels, final List<Segment> segments) {
		for (final Segment segment : segments) {
			final int minWidth = segment.widthInterval()._1;
			final int maxWidth = segment.widthInterval()._2;
			final int minHeight = segment.heightInterval()._1;
			final int maxHeight = segment.heightInterval()._2;
			Pixel pix;
			if (segment.getSegmentType() == SegmentType.LR) {
				pix = new Pixel(255, 0, 0);
			} else if (segment.getSegmentType() == SegmentType.LL) {
				pix = new Pixel(0, 255, 0);
			} else if (segment.getSegmentType() == SegmentType.LA) {
				pix = new Pixel(255, 165, 0);
			} else if (segment.getSegmentType() == SegmentType.KPION) {
				pix = new Pixel(184, 3, 255);
			} else {
				pix = new Pixel(255, 255, 0);
			}

			for (int i = minHeight; i < maxHeight; i++) {
				pixels[i][minWidth] = pix;
				pixels[i][maxWidth] = pix;
			}
			for (int i = minWidth; i < maxWidth; i++) {
				pixels[minHeight][i] = pix;
				pixels[maxHeight][i] = pix;
			}
		}
		return pixels;
	}
}
