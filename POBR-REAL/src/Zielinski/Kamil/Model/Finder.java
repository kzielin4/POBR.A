/*
 * Klasa g³owna analizuj¹ca wczytany obraz
 */
package Zielinski.Kamil.Model;

import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import Zielinski.Kamil.Util.Tuple;
import Zielinski.Kamil.Util.Tuple3;

public class Finder
{
	// Funkcja g³owna - analizuj¹ca obraz
	public BufferedImage find(final BufferedImage file)
	{
		System.out.println("START");
		Pixel[][] pixels = PixelMatrix.mapImage(file);
		final Pixel[][] sourcePixels2 = PixelMatrix.deepCopy(pixels);
		pixels = new UnsharpMask().putMask(pixels);
		pixels = new Segmentation().threshold(pixels);
		pixels = new ErosionMaker().erode(pixels);
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
		}
		final BufferedImage result2 = new BufferedImage(file.getWidth(), file.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final int[] array2 = ((DataBufferInt) result2.getRaster().getDataBuffer()).getData();
		List<Segment> segs1 = new ArrayList<Segment>(segments);
		System.arraycopy(PixelMatrix.mapPixels(drawBoundingBox2(sourcePixels, segs1)), 0, array2, 0, array2.length);
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
		System.out.println("l1" + segments.size());
		segments.removeAll(uknowSeg);
		System.out.println("l2" + segments.size());
		List<Segment> segs = new ArrayList<Segment>(segments);
		pixels = drawBoundingBox(sourcePixels2 , groupSegments(segs));
		System.out.println(segments.size());
		final BufferedImage result = new BufferedImage(file.getWidth(), file.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final int[] array = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();
		System.arraycopy(PixelMatrix.mapPixels(pixels), 0, array, 0, array.length);
		return result;
	}

	// Funkcja rysuj¹ca boundingbox
	private Pixel[][] drawBoundingBox2(final Pixel[][] pixels, final List<Segment> segments)
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
			}
			else if (segment.getSegmentType() == SegmentType.LL)
			{
				pix = new Pixel(0, 255, 0);
			}
			else if (segment.getSegmentType() == SegmentType.LAE)
			{
				pix = new Pixel(255, 165, 0);
			}
			else if (segment.getSegmentType() == SegmentType.KPION)
			{
				pix = new Pixel(184, 3, 255);
			}
			else
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

	private Pixel[][] drawBoundingBox(Pixel[][] pixels, List<List<Segment>> segments)
	{
		
		for (List<Segment> segment : segments)
		{
			int minWidth = 99999,maxWidth = 0, minHeight = 999999,maxHeight = 0;
			for (Segment segment2 : segment)
			{
			
				minWidth = Math.min(minWidth,segment2.widthInterval()._1-2);
				maxWidth = Math.max(maxWidth,segment2.widthInterval()._2)+2;
				minHeight = Math.min(minHeight,segment2.heightInterval()._1-2);
				maxHeight =  Math.max(maxHeight,segment2.heightInterval()._2+2);
			}
			Pixel pix = new Pixel(255, 165, 0);
			for (int i = minHeight-1; i < maxHeight+1; i++)
			{
				pixels[i][minWidth] = pix;
				pixels[i][maxWidth] = pix;
			}
			for (int i = minWidth-1; i < maxWidth+1; i++)
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
		List<List<Segment>> sREAL = new ArrayList<List<Segment>>();
		for (Segment rLetter : lettersR)
		{
			int rd = (rLetter.widthInterval()._2 - rLetter.widthInterval()._1) / 3;
			int rWidtht = (rLetter.widthInterval()._2 - rLetter.widthInterval()._1);
			int rHeight = (rLetter.heightInterval()._2 - rLetter.heightInterval()._1);
			Rectangle recR = new Rectangle(rLetter.widthInterval()._1, rLetter.heightInterval()._1, rWidtht, rHeight);
			List<Segment> tmpReal = new ArrayList<Segment>();
			tmpReal.add(rLetter);
			boolean r = false;
			for (Segment eLetter : lettersAE)
			{
				int eWidtht = eLetter.widthInterval()._2 - eLetter.widthInterval()._1;
				int eHeight = eLetter.heightInterval()._2 - eLetter.heightInterval()._1;
				Rectangle recE = new Rectangle(eLetter.widthInterval()._1, eLetter.heightInterval()._1, eWidtht,
						eHeight);
				if (recE.overlaps(recR) && rLetter.equals(tmpReal.get(0)))
				{
					if (tmpReal.size() == 1)
						tmpReal.add(eLetter);
					r = true;
				}
				boolean re = false;
				for (Segment aLetter : lettersAE)
				{
					if (r == false)
					{
						break;
					}
					int aWidtht = (aLetter.widthInterval()._2 - aLetter.widthInterval()._1);
					int aHeight = (aLetter.heightInterval()._2 - aLetter.heightInterval()._1);
					Rectangle recA = new Rectangle(aLetter.widthInterval()._1, aLetter.heightInterval()._1, aWidtht,
							aHeight);
					if (!aLetter.equals(eLetter) && recA.overlaps(recE) && eLetter.equals(tmpReal.get(1)))
					{
						System.out.println("wbilem");
						if (tmpReal.size() == 2)
						{
							tmpReal.add(aLetter);
							System.out.println("xA: " + aLetter.widthInterval()._1.intValue() + " yA"
									+ aLetter.heightInterval()._1.intValue() + " wid: " + aWidtht + " he" + aHeight);
							System.out.println("xE: " + eLetter.widthInterval()._1.intValue() + " yA"
									+ eLetter.heightInterval()._1.intValue() + " wid: " + eWidtht + " he" + eHeight);
						}
						re = true;
					}
					boolean rea = false;
					for (Segment lLetter : lettersL)
					{
						if (re == false)
						{
							break;
						}
						int lWidtht = (lLetter.widthInterval()._2 - lLetter.widthInterval()._1);
						int lHeight = (lLetter.heightInterval()._2 - lLetter.heightInterval()._1);
						Rectangle recL = new Rectangle(lLetter.widthInterval()._1, lLetter.heightInterval()._1, lWidtht,
								lHeight);
						if (recL.overlaps(recA) && aLetter.equals(tmpReal.get(2)))
						{
							if (tmpReal.size() == 3)
							{
								tmpReal.add(lLetter);
								System.out.println("xA: " + aLetter.widthInterval()._1.intValue() + " yA"
										+ aLetter.heightInterval()._1.intValue() + " wid: " + aWidtht + " he"
										+ aHeight);
								System.out.println("xL: " + lLetter.widthInterval()._1.intValue() + " yA"
										+ lLetter.heightInterval()._1.intValue() + " wid: " + lWidtht + " he"
										+ lHeight);
							}
							rea = true;
						}
						boolean real = false;
						for (Segment verLine : verticalLine)
						{
							if (rea == false)
							{
								break;
							}
							int vWidth = (verLine.widthInterval()._2 - verLine.widthInterval()._1);
							int vHeight = (verLine.heightInterval()._2 - verLine.heightInterval()._1);
							Rectangle recV = new Rectangle(verLine.widthInterval()._1, verLine.heightInterval()._1,
									vWidth, vHeight);
							if (recV.overlaps(recL) && lLetter.equals(tmpReal.get(3)))
							{
								if (tmpReal.size() == 4)
								{
									tmpReal.add(verLine);
								}
								real = true;
							}
							boolean real1 = false;
							for (Segment horLine : horizontalLine)
							{
								if (real == false)
								{
									break;
								}
								int hWidth = (horLine.widthInterval()._2 - horLine.widthInterval()._1);
								int hHeight = (horLine.heightInterval()._2 - horLine.heightInterval()._1);
								Rectangle recH = new Rectangle(horLine.widthInterval()._1, horLine.heightInterval()._1,
										hWidth, hHeight);

								if (recH.overlaps(recV) && verLine.equals(tmpReal.get(4)))
								{
									if (tmpReal.size() == 5)
									{
										tmpReal.add(horLine);
									}
									real = true;
								}
							}
						}
					}
				}

			}
			if(tmpReal.size()==6)
			{
				sREAL.add(tmpReal);
			}
			System.out.println("Size:" + tmpReal.size());

		}
		return sREAL;
	}

	// Funkcja zwraca listê segementów o wskazanym typie
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
		System.out.println("Typ: " + segType.name() + " Ilosc: " + listS.size());
		return listS;
	}

}
