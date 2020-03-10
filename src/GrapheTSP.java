
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class GrapheTSP extends Graphe {
	public GrapheTSP(int n) {
		super();
		
		for(int i = 0; i < n; ++i) {
			addNoeud(new Point(i, Math.random(), Math.random()));
		}
	}
	
	public GrapheTSP(int n, int k) {
		this(n);
		
		if(k >= n) {
			k = n - 1;
		}
		
		for(int i = 0; i < n; ++i) {
			Point p = (Point)getNoeud(i);
			
			List<Point> tries = p.triePoints(getPoints());
			
			for(int j = 1; j <= k; ++j) {
				addArcDouble(i, tries.get(j).getId());
			}
		}
	}
	
	public List<Point> getPoints() {
		List<Noeud> noeuds = getNoeuds();
		List<Point> points = new ArrayList<Point>();
		
		for(ListIterator<Noeud> it = noeuds.listIterator(); it.hasNext(); ) {
			Noeud noeud = it.next();
			
			points.add((Point)noeud);
		}
		
		return points;
	}
	
	public static void main(String[] args) {
		GrapheTSP graphe = new GrapheTSP(10, 1);
		
		for(int i = 0; i < graphe.getNoeudCount(); ++i) {
			Point point = (Point)graphe.getNoeud(i);
			System.out.println(i + "(" + point.getX() + ", " + point.getY() + ")");
			
			for(int j = 0; j < graphe.getNoeudCount(); ++j) {
				if(i != j) {
					System.out.println(j + " : " + point.distance((Point)graphe.getNoeud(j)));
				}
			}
		}
		
		System.out.println(graphe);
	}
}
