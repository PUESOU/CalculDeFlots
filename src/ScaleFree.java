
public class ScaleFree extends Graphe {
	public ScaleFree(int n) {
		super();
		
		for(int i = 0; i < n; ++i) {
			addNoeud(new Noeud(i));
		}
	}
	
	public ScaleFree(int n, int d) {
		this(n);
		
		int[] m = new int[2*n*d];
		
		for(int v = 0; v < n; ++v) {
			for(int i = 0; i < d; ++i) {
				int vdi2 = 2 * (v * d + i);
				
				m[vdi2] = v;
				
				int r = (int)(Math.random() * (vdi2 + 1));
				
				m[vdi2 + 1] = m[r];
			}
		}
		
		for(int i = 0; i < n * d; ++i) {
			addArcDouble(m[2 * i], m[2 * i + 1]);
		}
	}
	
	public static void main(String[] args) {
		ScaleFree graphe = new ScaleFree(10, 3);
		
		// System.out.println(graphe);
		System.out.println(new ScaleFree(25, 1));
	}
}
