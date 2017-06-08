package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SegmentsCreator
{
	Set<Segment> process(Pixel[][] pixels)
	{
		Set<Segment> segments = new HashSet<>();
		System.out.println("pkt");
		int size = pixels.length * pixels[0].length;
		int x=0;
		for (int i = 0; i < pixels.length; i++)
		{
			for (int j = 0; j < pixels[0].length; j++)
			{
				if (pixels[i][j] != Pixel.BLACK)
				{
					Segment seg = extractSegment(pixels, i, j);
					if(seg.getPoints().size()>20 && seg.getPoints().size()<0.2*size)
						segments.add(seg);
				}
				if(j==272  && i== 221)
				{
					x=1;
				}
			}
		}
		if(x==1)
		{
			System.out.println("Size: "+segments.size());
		}
		return segments;
	}

	private Segment extractSegment(Pixel[][] pixels, int height, int width)
	{
		int imageHeight = pixels.length;
		int imageWidth = pixels[0].length;
		List<Point> segmentPoints = new ArrayList<>();
		Deque<Point> stack = new LinkedList<>();
		Pixel comparator = new Pixel(pixels[height][width]);
		Boolean isBlue= false;
		if( comparator == Pixel.BLUE)
			isBlue=true;
		stack.push(new Point(height, width));
		while (!stack.isEmpty())
		{
			Point top = stack.pop();
			pixels[top.x][top.y] = Pixel.BLACK;
			segmentPoints.add(top);
			for (int i = top.x - 1; i <= top.x + 1; i++)
			{
				for (int j = top.y - 1; j <= top.y + 1; j++)
				{
					if (i > 0 && i < imageHeight && j > 0 && j < imageWidth && ((pixels[i][j]==Pixel.WHITE && !isBlue)|| pixels[i][j]==Pixel.BLUE && isBlue))
					{
						stack.push(new Point(i, j));
					}
				}
			}
		}
		return new Segment(SegmentType.UNKNOWN, segmentPoints);
	}
	
	
}
