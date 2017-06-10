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

import Zielinski.Kamil.Util.Tuple;
import Zielinski.Kamil.Util.Tuple3;

public class Finder
{
	public BufferedImage find(final BufferedImage file)
	{
		System.out.println("START");
		Pixel[][] pixels = PixelMatrix.mapImage(file);
		final Pixel[][] sourcePixels2 = PixelMatrix.deepCopy(pixels);
		pixels = new UnsharpMask().putMask(pixels);
		pixels = new Segmentation().threshold(pixels);
		pixels = new ErosionMaker().erode(pixels);
		final BufferedImage result2 = new BufferedImage(file.getWidth(), file.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final int[] array2 = ((DataBufferInt) result2.getRaster().getDataBuffer()).getData();
		System.arraycopy(PixelMatrix.mapPixels(pixels), 0, array2, 0, array2.length);
		// System.out.println(pixels.length + " "+sourcePixels[0].length);
		final File output = new File("source2.png");
		try
		{
			ImageIO.write(result2, "png", output);
			System.out.println("done");
		} catch (final IOException e)
		{
			e.printStackTrace();
		}
		final Pixel[][] sourcePixels = PixelMatrix.deepCopy(pixels);
		final Set<Segment> uknowSeg = new HashSet<>();
		final Set<Segment> segments = new SegmentsCreator().process(pixels);// pomocnicza
		for (final Segment segment : segments)
		{
			segment.setSegmentType(new SegemntTypeDetector().detectType(segment, sourcePixels));
			if (segment.getSegmentType() == SegmentType.UNKNOWN)
			{
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
		List<Segment> segs = new ArrayList<Segment>(segments);
		groupSegments(segs);
		pixels = drawBoundingBox(sourcePixels2, new ArrayList<Segment>(segments));
		System.out.println(segments.size());
		final BufferedImage result = new BufferedImage(file.getWidth(), file.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final int[] array = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();
		System.arraycopy(PixelMatrix.mapPixels(pixels), 0, array, 0, array.length);
		return result;
	}

	private Pixel[][] drawBoundingBox(final Pixel[][] pixels, final List<Segment> segments)
	{
		for (final Segment segment : segments)
		{
			final int minWidth = segment.widthInterval()._1;
			final int maxWidth = segment.widthInterval()._2;
			final int minHeight = segment.heightInterval()._1;
			final int maxHeight = segment.heightInterval()._2;
			Pixel pix;
			if (segment.getSegmentType() == SegmentType.LR)
			{
				pix = new Pixel(255, 0, 0);
			} else if (segment.getSegmentType() == SegmentType.LL)
			{
				pix = new Pixel(0, 255, 0);
			} else if (segment.getSegmentType() == SegmentType.LAE)
			{
				pix = new Pixel(255, 165, 0);
			} else if (segment.getSegmentType() == SegmentType.KPION)
			{
				pix = new Pixel(184, 3, 255);
			} else
			{
				pix = new Pixel(255, 255, 0);
			}

			for (int i = minHeight; i < maxHeight; i++)
			{
				pixels[i][minWidth] = pix;
				pixels[i][maxWidth] = pix;
			}
			for (int i = minWidth; i < maxWidth; i++)
			{
				pixels[minHeight][i] = pix;
				pixels[maxHeight][i] = pix;
			}
		}
		return pixels;
	}

	private List<List<Segment>> groupSegments(List<Segment> segments)
	{
		List<Segment> verticalLine = extractSegmentWithCenter(segments, SegmentType.KPION);
		List<Segment> horizontalLine = extractSegmentWithCenter(segments, SegmentType.KPOZIOM);
		List<Segment> lettersL = extractSegmentWithCenter(segments, SegmentType.LL);
		List<Segment> lettersAE = extractSegmentWithCenter(segments, SegmentType.LAE);
		List<Segment> lettersR = extractSegmentWithCenter(segments, SegmentType.LR);
		// List<List<Segment> real = new ArrayList<List<Segment>>();
		for (Segment rLetter : lettersR)
		{
			int rCenterWidtht = rLetter.widthInterval()._2 - rLetter.widthInterval()._1;
			int rCenterHeight = rLetter.heightInterval()._1+(rLetter.heightInterval()._2 - rLetter.heightInterval()._1);
			Set<Segment> tmpReal = new HashSet<Segment>();
			tmpReal.add(rLetter);
			boolean r = false;
			for (Segment eLetter : lettersAE)
			{
				int eCenterWidtht = eLetter.widthInterval()._2 - eLetter.widthInterval()._1;
				int eCenterHeight = eLetter.heightInterval()._1+(eLetter.heightInterval()._2 - eLetter.heightInterval()._1);
				if (Math.abs(rCenterHeight - eCenterHeight) < 4 && Math.abs(eLetter.widthInterval()._1 - rLetter.widthInterval()._2) < 4)
				{
					tmpReal.add(eLetter);
					r=true;
				}
				boolean re = false;
				for (Segment aLetter : lettersAE)
				{
					if(r == false)
					{
						break;
					}
					int aCenterWidtht = aLetter.widthInterval()._2 - aLetter.widthInterval()._1;
					int aCenterHeight = aLetter.heightInterval()._1+(aLetter.heightInterval()._2 - aLetter.heightInterval()._1);;
					if (aCenterHeight != eCenterHeight && Math.abs(aCenterHeight - eCenterHeight) < 6 && Math.abs(aLetter.widthInterval()._1 - eLetter.widthInterval()._2) < 4)
					{
						tmpReal.add(aLetter);
						re=true;
					}
					boolean rea = false;
					for (Segment lLetter : lettersL)
					{
						if(re == false)
						{
							if (tmpReal.size()==2)
							{
								System.out.println("lol");
							}
							break;
						}
						int lCenterWidtht = lLetter.widthInterval()._2 - lLetter.widthInterval()._1;
						int lCenterHeight = lLetter.heightInterval()._1+(lLetter.heightInterval()._2 - lLetter.heightInterval()._1);
						if (Math.abs(lCenterHeight - aCenterHeight) < 5 && Math.abs(lLetter.widthInterval()._1 - aLetter.widthInterval()._2) < 4)
						{
							tmpReal.add(lLetter);
							rea=true;
						}
						boolean real = false;
						for (Segment verLine : verticalLine)
						{
							if(rea == false)
							{
								break;
							}
							int vCenterHeight = verLine.widthInterval()._1+(verLine.heightInterval()._2 - verLine.heightInterval()._1);
							if (Math.abs(aCenterHeight - vCenterHeight) < 5 && Math.abs(verLine.widthInterval()._1 - lLetter.widthInterval()._2) < 4)
							{
								//tmpReal.add(verLine);
								real=true;
							}
							boolean real1 = false;
							for (Segment horLine : horizontalLine)
							{
								int hCenterWidtht = horLine.widthInterval()._2 - horLine.widthInterval()._1;
								int hCenterHeight = horLine.heightInterval()._2 - horLine.heightInterval()._1;
							}
						}
					}
				}
			}
			System.out.println("Size:" + tmpReal.size());
		}
		return null;
	}

	private List<Segment> extractSegmentWithCenter(List<Segment> segments, SegmentType segType)
	{
		List<Segment> listS = new ArrayList<Segment>();
		for (Segment segment : segments)
		{
			if (segment.getSegmentType() == segType)
			{
				listS.add(segment);
			}
		}
		return listS;
	}

	/*
	 * private List<Segment> concatLettersAE (List<Segment> segments) {
	 * Set<Segment> seg = new HashSet<Segment>(); for (int i = 0; i <
	 * segments.size(); i++) { for (int j = i; j < segments.size(); j++) { if
	 * (segments.get(j).widthInterval() - segments.get(i).widthInterval()<5) {
	 * seg.add(new Segment(SegmentType., segmentPoints)) }
	 * 
	 * } } return null; }
	 */

}
