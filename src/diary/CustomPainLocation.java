package diary;

import java.awt.Point;

public class CustomPainLocation
{
	private Point location;
	
	//Constructors
	public CustomPainLocation(Point location)
	{
		this.location = location;
	}
	public CustomPainLocation(String data)
	{
		String x = data.substring(0, data.indexOf(",")).trim();
		String y = data.substring(data.indexOf(",")+1, data.length()).trim();
		this.location = new Point(Integer.parseInt(x), Integer.parseInt(y));
	}
	
	//Methods
	public String getLocationAsString()
	{
		return Integer.toString(this.location.x) + "," + Integer.toString(this.location.y);
	}
	public Point getLocationCoordinate()
	{
		return this.location;
	}
}
