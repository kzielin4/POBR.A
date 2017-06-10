package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SegmentsCreator
{
	Set<Segment> process(final Pixel[][] pixels)
	{
		final Set<Segment> segments = new HashSet<>();
		System.out.println("pkt");
		final int size = pixels.length * pixels[0].length;
		final int x = 0;
		for (int i = 0; i < pixels.length; i++)
		{
			for (int j = 0; j < pixels[0].length; j++)
			{
				if (pixels[i][j] != Pixel.BLACK)
				{
					final Segment seg = extractSegment(pixels, i, j);
					if ((seg.getPoints().size() > 17) && ((seg.getPoints().size() < (0.5 * size))
							&& ((seg.widthInterval()._2 - seg.widthInterval()._1) < (0.25 * pixels[0].length)))) {
						segments.add(seg);
					}
				}
			}
		}

		return segments;
	}

	private Segment extractSegment(final Pixel[][] pixels, final int height, final int width)
	{
		final int imageHeight = pixels.length;
		final int imageWidth = pixels[0].length;
		final List<Point> segmentPoints = new ArrayList<>();
		final Deque<Point> stack = new LinkedList<>();
		final Pixel comparator = new Pixel(pixels[height][width]);
		Boolean isBlue = false;
		if ((comparator.getBlue()==255) && (comparator.getRed()==0))
		{
			//System.out.println("nieb");
			isBlue = true;
		}
		stack.push(new Point(height, width));
		while (!stack.isEmpty())
		{
			final Point top = stack.pop();
			pixels[top.x][top.y] = Pixel.BLACK;
			segmentPoints.add(top);
			for (int i = top.x - 1; i <= (top.x + 1); i++)
			{
				for (int j = top.y - 1; j <= (top.y + 1); j++)
				{
					if ((i > 0) && (i < imageHeight) && (j > 0) && (j < imageWidth) && ((pixels[i][j].equals(Pixel.WHITE) && !isBlue)
							|| (pixels[i][j].equals(Pixel.BLUE)&& isBlue)))
					{
						stack.push(new Point(i, j));
					}
				}
			}
		}
		return new Segment(SegmentType.UNKNOWN, segmentPoints);
	}

}
