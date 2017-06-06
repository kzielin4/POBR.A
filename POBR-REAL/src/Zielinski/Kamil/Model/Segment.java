package Zielinski.Kamil.Model;

import java.util.List;

import Zielinski.Kamil.Util.Tuple;

public class Segment
{
	private SegmentType segmentType;
	private List<Point> points;
	private Tuple<Integer, Integer> widthInterval;
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
	/*
	Tuple<Integer, Integer> widthInterval()
	{
		if (widthInterval == null)
		{
			widthInterval = Tuple.from(extract(points, p -> p.width, reverseIntegerComparator(), 0),
					extract(points, p -> p.width, Integer::compare, 0));
		}
		return widthInterval;
	}

	Tuple<Integer, Integer> heightInterval()
	{
		if (heightInterval == null)
		{
			heightInterval = Tuple.from(extract(points, p -> p.height, reverseIntegerComparator(), 0),
					extract(points, p -> p.height, Integer::compare, 0));
		}
		return heightInterval;
	}
*/
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
}
