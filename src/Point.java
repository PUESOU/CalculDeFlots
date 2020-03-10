
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Point extends Noeud {
	private double x, y;
	
	public Point(int id) {
		super(id);
	}
	
	public Point(int id, double x, double y) {
		this(id);
		
		this.x = x;
		this.y = y;
	}
	
	public static double distance(Point p1, Point p2) {
		double distance = 0;
		
		distance += Math.pow(p2.x - p1.x, 2);
		distance += Math.pow(p2.y - p1.y, 2);
		
		return (distance = Math.sqrt(distance));
	}
	
	public double distance(Point p) {
		return distance(this, p);
	}
	
	public List<Point> triePoints(List<Point> points) {
		List<Point> copie = new ArrayList<Point>(points);
		
		boolean sorted = true;
		
		while(sorted) {
			sorted = false;
			
			for(int i = 0; i < copie.size() - 1; ++i) {
				int j = i + 1;
				double distanceA = distance(this, copie.get(i));
				double distanceB = distance(this, copie.get(j));
				
				if(distanceA > distanceB) {
					Point x = copie.get(i);
					copie.set(i, copie.get(j));
					copie.set(j, x);
					
					sorted = true;
				}
			}
		}
		
		return copie;
	}
	
	public double getX() {return x;}
	public double getY() {return y;}
	
	public static void main(String[] args) {
		Point a = new Point(0, 0, 0);
		Point b = new Point(1, 12, 13);
		Point c = new Point(2, 100, 100);
		Point[] points = {a, c, c, b, c};
		
		System.out.println(a.triePoints(Arrays.asList(points)));
	}
}
