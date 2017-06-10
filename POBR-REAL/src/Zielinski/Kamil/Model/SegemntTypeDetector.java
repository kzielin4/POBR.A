package Zielinski.Kamil.Model;

import Zielinski.Kamil.Util.Tuple;
import Zielinski.Kamil.Util.Tuple3;

public class SegemntTypeDetector
{
	// Zwraca typ segmentu
	SegmentType detectType(final Segment segment,final Pixel[][] orpixels)
	{
		final Pixel[][] pixels = extractSegmentWithBox(segment);
		final Pixel pix= orpixels[segment.getPoints().get(1).x][segment.getPoints().get(1).y];
		//System.out.println("rgb"+ pix.getBlue()+" y:"+segment.getPoints().get(1).x +" x:"+segment.getPoints().get(1).y);
		final Tuple3<Double, Double, Double> moments = new MomentsCounter().getSegmentMoments(pixels);
		// System.out.println("M1: " + moments._1 + " M3: " + moments._2 + "M7:
		// " + moments._3);
		final double shapeFactor = new MainowskiCounter().malinowskiej(pixels);
		final double shapeFactor2 = new MainowskiCounter().W2(pixels);
		final SegmentType type = matchType(moments._1, moments._2, moments._3, shapeFactor,pix,shapeFactor2);
		for (final Point point : segment.getPoints())
		{
			if ((point.x == 216) && (point.y == 782))
			{
				System.out.println("-----------------------------");
				System.out.println("Points : x1-" +segment.heightInterval()._1+" x2-"+segment.heightInterval()._2 +" y1-"+segment.widthInterval()._1+" y2-"+segment.widthInterval()._2);
				System.out.println("M1: " + moments._1);
				System.out.println("M7: " + moments._3);
				System.out.println("w3: " + shapeFactor);
				System.out.println("w9: " + shapeFactor2);
				System.out.println("TYP: " + type.name());
				System.out.println("-----------------------------");
			}
		}
		// System.out.println("w3: "+shapeFactor);
		/*if (!type.equals(SegmentType.UNKNOWN))
		{
			System.out.println("Points : x1-" +segment.heightInterval()._1+" x2-"+segment.heightInterval()._2 +" y1-"+segment.widthInterval()._1+" y2-"+segment.widthInterval()._2);
			System.out.println("M1: " + moments._1 + " M3: " + moments._2 + "M7: " + moments._3);
			System.out.println("w3: " + shapeFactor);
			System.out.println("w9: " + shapeFactor2);
			System.out.println("TYP: " + type.toString());

		}*/



		return type;
		//return SegmentType.UNKNOWN;
	}

	// zwraca tablice pixeli dla segmentu
	private Pixel[][] extractSegmentWithBox(final Segment segment)
	{
		final Tuple<Integer, Integer> widthInterval = segment.widthInterval();
		final int maxWidth = widthInterval._2;
		final int minWidth = widthInterval._1;
		final Tuple<Integer, Integer> heightInterval = segment.heightInterval();
		final int maxHeight = heightInterval._2;
		final int minHeight = heightInterval._1;
		final Pixel[][] segmentImage = segmentPixels(maxHeight - minHeight, maxWidth - minWidth);
		segment.getPoints().forEach(p -> segmentImage[(p.x - minHeight) + 3][(p.y - minWidth) + 3] = Pixel.WHITE);
		return segmentImage;
	}

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

	private SegmentType matchType(final double m1, final double m3, final double m7, final double w3 , final Pixel pix, final double w2)
	{
		if ((m1 > 0.21) && (m1 < 0.35) && (m7 > 0.0109) && (m7 < 0.0276) && (w3 > 1.1) && (w3 < 2.5) && pix.equals(Pixel.WHITE))
		{
			return SegmentType.LA;
		}
		else if ((m1 > 0.26) && (m1 < 0.415) && (m7 > 0.0085) && (m7 < 0.013) && (w3 > 0.6) && (w3 < 1.45) && pix.equals(Pixel.WHITE)) // 0.92
		{
			return SegmentType.LR;
		}
		/*else if (m1 > 0.17 && m1 < 0.33 && m7 > 0.003 && m7 < 0.0165 && w3 >= 1.1 && w3 < 2 && pix.equals(Pixel.WHITE))
		{
			return SegmentType.LE;
		}*/
		else if ((m1 > 0.335) && (m1 < 0.48) && (m7 > 0.005) && (m7 < 0.095) && (w3 > 0.5) && (w3 < 1.35)  && pix.equals(Pixel.WHITE))
		{
			return SegmentType.LL;
		}
		else if ((m1 > 0.19) && (m1 <= 0.256) && (m7 > 0.004) && (m7 < 0.01) && (w3 > 0.275) && (w3 < 1.2) && pix.equals(Pixel.BLUE))
		{
			return SegmentType.KPION;
		}
		else if ((m1 > 0.256) && (m1 < 0.33) && (m7 > 0.004) && (m7 < 0.01) && (w3 > 0.29) && (w3 < 0.7) && pix.equals(Pixel.BLUE))
		{
			//System.out.println("BLUE: "+pix.getBlue());
			return SegmentType.KPOZIOM;
		}
		else
		{
			return SegmentType.UNKNOWN;
		}
	}
}
