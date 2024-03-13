package dtw;

/**
 * @author <a href="mailto:gery.casiez@univ-lille.fr">Gery Casiez</a>
 */

import java.util.Vector;

import javafx.geometry.Point2D;

public class Template
{
	private String name;
	private Vector<Point2D> points;
	
	Template( String name, Vector<Point2D> points)
	{
		this.name = name;
		this.points = points;
	}
	
	public void setPoints(Vector<Point2D> points) {
		this.points = points;
	}

	public Vector<Point2D> getPoints() {
		return points;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
