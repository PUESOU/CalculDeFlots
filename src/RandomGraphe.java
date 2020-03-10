
public class RandomGraphe extends Graphe {
	private RandomGraphe(int n) {
		super();
		
		for(int i = 0; i < n; ++i) {
			addNoeud(new Noeud(i));
		}
	}
	
	// Modèle de graphes aléatoires (1)
	
	public RandomGraphe(int n, double p) {
		this(n);
		
		int v = 1;
		int w = -1;
		
		while(v < n) {
			double r = Math.random();
			w = w + 1 + (int)Math.floor(Math.log(1 - r) / Math.log(1 - p));
			
			while(w >= v && v < n) {
				w = w - v;
				++v;
			}
			
			if(v < n) {
				// System.out.println(v + "," + w);
				
				addArcDouble(v, w);
			}
		}
	}

	// Modèle de graphes aléatoires (2(b))
	
	public static RandomGraphe algoErdosRenyi(int n, int m) {
		RandomGraphe res = new RandomGraphe(n);
		
		for(int i = 0; i < m; ++i) {
			double r;
			int v, w;
			
			do {
				r = Math.random() * n * (n-1) / 2;
				
				int[] tab = bijection((int)r);
				
				v = tab[0];
				w = tab[1];
				
				// try {Thread.sleep(1000);} catch(Exception e) {}
			} while(res.hasArcDouble(v, w));
			
			res.addArcDouble(v, w); 
		}
		
		return res;
	}
	
	// Modèle de graphes aléatoires (2(a))
	
	private static int[] bijection(int i) {
		int v = 1 + (int)Math.floor(-1/2 + Math.sqrt(1/4 + 2*i));
		int w = i - v * (v-1) / 2;
		
		return new int[]{v, w};
	}
	
	// Modèle de graphes aléatoires (3)
	
	public static RandomGraphe RandomGraphe_lineaire(int n, int m) {
		RandomGraphe res = new RandomGraphe(n);
		int n_2 = n * (n-1) / 2;
		int[] replace = new int[n_2];
		
		if(m > n_2) {
			System.out.println("m = " + m + " > (n 2) = " + n_2);
			m = n_2;
		}
		
		for(int i = 0; i < m; ++i) {
			int r = (int)(i + Math.random() * (n_2 - i));
			int[] tab = bijection(r);
			int v = tab[0], w = tab[1];
			
			if(!res.hasArcDouble(v, w)) {
				res.addArcDouble(v, w);
			} else {
				tab = bijection(replace[r]);
				v = tab[0]; w = tab[1];
				
				res.addArcDouble(v, w);
			}
			
			tab = bijection(i);
			v = tab[0]; w = tab[1];
			
			if(!res.hasArcDouble(v, w)) {
				replace[r] = i;
			} else {
				replace[r] = replace[i];
			}
		}
		
		return res;
	}
	
	public static void main(String[] args) {
		// System.out.println(new RandomGraphe(5, 0.5));
		// System.out.println(RandomGraphe.algoErdosRenyi(4, 8));
		// System.out.println(RandomGraphe.algoErdosRenyi(15, 32));
		
		// System.out.println(RandomGraphe.RandomGraphe_lineaire(4, 8));
		System.out.println(RandomGraphe.RandomGraphe_lineaire(15, 32));
	}
}
