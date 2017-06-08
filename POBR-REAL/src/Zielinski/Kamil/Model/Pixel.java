package Zielinski.Kamil.Model;



class Pixel
{

	final static Pixel BLACK = new Pixel(0, 0, 0);
	final static Pixel WHITE = new Pixel(255, 255, 255);
	final static Pixel BLUE = new Pixel(0, 0, 255);

	private final int red;
	private final int green;
	private final int blue;

	Pixel(int rgbColor)
	{
		red = (rgbColor >> 16) & 0xFF;
		green = (rgbColor >> 8) & 0xFF;
		blue = rgbColor & 0xFF;
	}

	Pixel(Pixel pixel)
	{
		red = pixel.red;
		green = pixel.green;
		blue = pixel.blue;
	}

	public Pixel(int red, int green, int blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getRed()
	{
		return red;
	}

	public int getGreen()
	{
		return green;
	}

	public int getBlue()
	{
		return blue;
	}
}
