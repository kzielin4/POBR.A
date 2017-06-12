/*
 * Klasa odpowiedzialna za wykrywanie typu segmentu
 */
package Zielinski.Kamil.Model;

import Zielinski.Kamil.Util.Tuple;
import Zielinski.Kamil.Util.Tuple3;

public class SegemntTypeDetector
{
	// Zwraca typ wskazanego segmentu
	SegmentType detectType(final Segment segment, final Pixel[][] orpixels)
	{
		final Pixel[][] pixels = extractSegmentWithBox(segment);
		final Pixel pix = orpixels[segment.getPoints().get(1).x][segment.getPoints().get(1).y];
		final Tuple3<Double, Double, Double> moments = new MomentsCounter().getSegmentMoments(pixels);
		final int segWidth = segment.widthInterval()._2 - segment.widthInterval()._1;
		final int segHeigh = segment.heightInterval()._2 - segment.heightInterval()._1;
		final double shapeFactor = new MainowskiCounter().malinowskiej(pixels);
		final double shapeFactor2 = new MainowskiCounter().W2(pixels);
		final SegmentType type = matchType(moments._1, moments._2, moments._3, shapeFactor, pix, shapeFactor2, segWidth,
				segHeigh);
		/*
		 * for (final Point point : segment.getPoints()) {
		 * 
		 * if ((point.x == 188) && (point.y == 478)) {
		 * System.out.println("-----------------------------");
		 * System.out.println("Points : x1-" + segment.heightInterval()._1 +
		 * " x2-" + segment.heightInterval()._2 + " y1-" +
		 * segment.widthInterval()._1 + " y2-" + segment.widthInterval()._2);
		 * System.out.println("M1: " + moments._1); System.out.println("M7: " +
		 * moments._3); System.out.println("w3: " + shapeFactor);
		 * System.out.println("w9: " + shapeFactor2); System.out.println("TYP: "
		 * + type.name()); System.out.println("-----------------------------");
		 * System.out.println(segHeigh / segWidth); }
		 * 
		 * }
		 */
		return type;
	}

	// Funkcja zwraca ograniczon¹ dwuwymiarow¹ tablice pixeli dla danego segmentu
	private Pixel[][] extractSegmentWithBox(final Segment segment)
	{
		final Tuple<Integer, Integer> widthInterval = segment.widthInterval();
		final int maxWidth = widthInterval._2;
		final int minWidth = widthInterval._1;
		final Tuple<Integer, Integer> heightInterval = segment.heightInterval();
		final int maxHeight = heightInterval._2;
		final int minHeight = heightInterval._1;
		final Pixel[][] segmentImage = segmentPixels(maxHeight - minHeight, maxWidth - minWidth);
		//wype³nianie pixeli bia³ych w utworzonej tablicy
		segment.getPoints().forEach(p -> segmentImage[(p.x - minHeight) + 3][(p.y - minWidth) + 3] = Pixel.WHITE);
		return segmentImage;
	}

   //Funkcja tworz¹ca tablice Pixeli 2D wype³nion¹ czarnymi pixelami
	private Pixel[][] segmentPixels(final int heightDiff, final int widthDiff)
	{
		final Pixel[][] segmentImage = new Pixel[heightDiff + 5][widthDiff + 5];
		for (int i = 0; i < segmentImage.length; i++)
		{
			for (int j = 0; j < segmentImage[0].length; j++)
			{
				segmentImage[i][j] = Pixel.BLACK;
			}
		}
		return segmentImage;
	}

	// Funkcja wyznaczaj¹ca typ segmentu na podstawie wyliczonych parametrów
	private SegmentType matchType(final double m1, final double m3, final double m7, final double w3, final Pixel pix,
			final double w2, final int width, final int heigh)
	{
		double p1;
		if (width == 0 || heigh == 0)
		{
			return SegmentType.UNKNOWN;
		}
		if (width > heigh)
		{
			p1 = (double) width / heigh;
		}
		else
		{
			p1 = (double) heigh / width;
		}
		if ((m1 > 0.21) && (m1 < 0.42) && (m7 > 0.0109) && (m7 < 0.038) && (w3 > 1.1) && (w3 < 2.5) // W3
																									// 1.1
				&& pix.equals(Pixel.WHITE) && p1 < 1.45)
		{
			return SegmentType.LAE;
		}
		else if ((m1 > 0.26) && (m1 < 0.415) && (m7 > 0.0085) && (m7 < 0.013) && (w3 > 0.6) && (w3 < 1.45)
				&& pix.equals(Pixel.WHITE)) // 0.92
		{
			return SegmentType.LR;
		}
		else if ((m1 > 0.335) && (m1 < 0.52) && (m7 > 0.005) && (m7 < 0.095) && (w3 > 0.5) && (w3 < 1.35)
				&& pix.equals(Pixel.WHITE) && ((width / heigh) >= 2 || (heigh / width) >= 2))
		{
			return SegmentType.LL;
		}
		else if ((m1 > 0.25) && (m1 < 0.47) && (m7 > 0.004) && (m7 < 0.01) && (w3 > 0.29) && (w3 < 0.7)
				&& pix.equals(Pixel.BLUE) && ((width / heigh) >= 2 || (heigh / width) >= 2))
		{
			return SegmentType.KPOZIOM;
		}
		else if ((m1 > 0.19) && (m1 <= 0.29) && (m7 > 0.004) && (m7 < 0.01) && (w3 > 0.12) && (w3 < 1.2)
				&& pix.equals(Pixel.BLUE))
		{
			return SegmentType.KPION;
		}
		else
		{
			return SegmentType.UNKNOWN;
		}
	}
}
