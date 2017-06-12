package Zielinski.Kamil.Model;
/*
 * Klasa zawieraj¹ca strukturê geometryczno-analityczn¹ segmentu
 * Klasa s³u¿y do sprawdzenia intersekcji pomiêdzy dwoma segmentami
 */
import java.awt.geom.Rectangle2D;

public class Rectangle
{
	private int x;
	private int y;
	private int width;
	private int height;
	private int centerX;
	private int centerY;

	public Rectangle(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.centerX = (2 * x + width) / 2;
		this.centerY = (2 * y + height) / 2;
	}
	
	//Funkcja sprawda czy dwa prostok¹ty s¹ w swoim s¹siedztwie
	public boolean overlaps(Rectangle r)
	{
		int paramW, paramH;
		if (width / 6 < 10)
		{
			paramW = 10;
		} else
			paramW = width / 6;
		if (height / 6 < 10)
		{
			paramH = 10;
		} else
		{
			paramH = height / 6;
		}
		return x - paramW < r.x + r.width && x + width + paramW> r.x && y -paramH  < r.y + r.height && y + height +paramH > r.y;
	}

}
