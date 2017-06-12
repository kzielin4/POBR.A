package Zielinski.Kamil.Model;
/*
 * Klasa reprezntuj¹ca pojedyñczy segment
 */
import java.util.List;

import Zielinski.Kamil.Util.Tuple;

public class Segment
{
	private SegmentType segmentType;
	private List<Point> points;
	//Pole przechoduje skrajne wspó³rzêdne szerokoœci segmentu
	private Tuple<Integer, Integer> widthInterval;
	//Pole przechoduje skrajne wspó³rzêdne wysokoœci segmentu
	private Tuple<Integer, Integer> heightInterval;

	public Segment(SegmentType segmentType, List<Point> points, Tuple<Integer, Integer> widthInterval,
			Tuple<Integer, Integer> heightInterval)
	{
		this.segmentType = segmentType;
		this.points = points;
		this.widthInterval = widthInterval;
		this.heightInterval = heightInterval;
	}

	public Segment(SegmentType type, List<Point> segmentPoints)
	{
		points = segmentPoints;
		segmentType = type;
	}

	Tuple<Integer, Integer> heightInterval()
	{
		if (widthInterval == null)
		{
			int maxX = 0;
			int minX = 999999999;
			for (Point point : points)
			{
				if(point.getX()>maxX)
				{
					maxX=point.getX();
				}
				if(point.getX()< minX)
				{
					minX=point.getX();
				}
			}
			widthInterval = Tuple.from(minX,maxX);
		}
		return widthInterval;
	}

	Tuple<Integer, Integer> widthInterval()
	{
		if (heightInterval == null)
		{
			int maxY = 0;
			int minY = 999999999;
			for (Point point : points)
			{
				if(point.getY()>maxY)
				{
					maxY=point.getY();
				}
				if(point.getY()< minY)
				{
					minY=point.getY();
				}
			}
			heightInterval = Tuple.from(minY,maxY);
		}
		return heightInterval;
	}

	public Tuple<Integer, Integer> getWidthInterval()
	{
		return widthInterval;
	}

	public void setWidthInterval(Tuple<Integer, Integer> widthInterval)
	{
		this.widthInterval = widthInterval;
	}

	public Tuple<Integer, Integer> getHeightInterval()
	{
		return heightInterval;
	}

	public void setHeightInterval(Tuple<Integer, Integer> heightInterval)
	{
		this.heightInterval = heightInterval;
	}

	public SegmentType getSegmentType()
	{
		return segmentType;
	}

	public List<Point> getPoints()
	{
		return points;
	}

	public void setSegmentType(SegmentType segmentType)
	{
		this.segmentType = segmentType;
	}
}
