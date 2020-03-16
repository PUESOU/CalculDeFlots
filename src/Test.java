
import java.util.List;
import java.util.Iterator;

public class Test {
	public static void main(String[] args) {
		ReseauTransport reseauTransport = new ReseauTransport(5, 5);// {E, 1, 2, 3, 4, 5, a, b, c, d, e, S}
		
		print(reseauTransport);
	}
	
	/**
	 * Retourne une chaîne de caractères construite à partir d'une liste, les éléments étant séparés par un séparateur donné.
	 * @param <T>
	 * @param list la liste à partir de laquelle créer la chaîne.
	 * @param separator le séparateur devant séparer les éléments.
	 * @return la chaîne de caractères représentant la liste.
	 */
	
	public static <T> String listJoin(List<T> list, String separator) {
		StringBuffer buf = new StringBuffer();
		
		for(Iterator<T> it = list.iterator(); it.hasNext(); ) {
			T t = it.next();
			buf.append(t);
			
			if(it.hasNext()) {
				buf.append(separator);
			}
		}
		
		return buf.toString();
	}
	
	public static ReseauTransport example0() {
		ReseauTransport reseauTransport = new ReseauTransport();
		
		reseauTransport.addNoeud("E");
		
		for(int i = 0; i < 3; ++i) {
			reseauTransport.addNoeud(i+1);
		}
		
		for(int i = 0; i < 4; ++i) {
			reseauTransport.addNoeud((char)('a' + i) + "");
		}
		
		reseauTransport.addNoeud("S");
		
		reseauTransport.addArc("E", "1");
		reseauTransport.addArc("E", "2");
		reseauTransport.addArc("E", "3");
		reseauTransport.addArc("1", "a");
		reseauTransport.addArc("1", "b");
		reseauTransport.addArc("1", "d");
		reseauTransport.addArc("2", "a");
		reseauTransport.addArc("2", "b");
		reseauTransport.addArc("2", "c");
		reseauTransport.addArc("3", "c");
		reseauTransport.addArc("3", "d");
		reseauTransport.addArc("a", "S");
		reseauTransport.addArc("b", "S");
		reseauTransport.addArc("c", "S");
		reseauTransport.addArc("d", "S");
		
		reseauTransport.getNoeud("E").findArc("1").setCapacite(45);
		reseauTransport.getNoeud("E").findArc("2").setCapacite(25);
		reseauTransport.getNoeud("E").findArc("3").setCapacite(25);
		reseauTransport.getNoeud("1").findArc("a").setCapacite(10);
		reseauTransport.getNoeud("1").findArc("b").setCapacite(15);
		reseauTransport.getNoeud("1").findArc("d").setCapacite(20);
		reseauTransport.getNoeud("2").findArc("a").setCapacite(20);
		reseauTransport.getNoeud("2").findArc("b").setCapacite(5);
		reseauTransport.getNoeud("2").findArc("c").setCapacite(5);
		reseauTransport.getNoeud("3").findArc("c").setCapacite(10);
		reseauTransport.getNoeud("3").findArc("d").setCapacite(10);
		reseauTransport.getNoeud("a").findArc("S").setCapacite(30);
		reseauTransport.getNoeud("b").findArc("S").setCapacite(10);
		reseauTransport.getNoeud("c").findArc("S").setCapacite(20);
		reseauTransport.getNoeud("d").findArc("S").setCapacite(30);
		
		return reseauTransport;
	}
	
	public static void print(ReseauTransport reseauTransport) {
		Noeud.stringType = 1;// Affichage des nœuds : "id(suivants…)".
		
		System.out.println("\n---- Noeud(suivants…) ----");
		System.out.println(reseauTransport);
		
		Noeud.stringType = 0;// Affichage des nœuds : "id".
		
		System.out.println("\n---- Branches ----");
		System.out.println(listJoin(reseauTransport.getNoeud("E").getBranches(), "\n"));
		
		System.out.println("\n---- Chemins ----");
		System.out.println(listJoin(reseauTransport.getNoeud("E").getPaths(), "\n"));
		
		Noeud.stringType = 1;// Remet l'affichage par défaut.
	}

	public static ReseauTransport example1() {
		ReseauTransport reseauTransport = new ReseauTransport();
		
		reseauTransport.addNoeud("E");
		reseauTransport.addNoeud("1");
		reseauTransport.addNoeud("2");
		reseauTransport.addNoeud("3");
		reseauTransport.addNoeud("S");
		
		reseauTransport.addArc("E", "1");
		reseauTransport.addArc("E", "2");
		reseauTransport.addArc("1", "2");
		reseauTransport.addArc("1", "3");
		reseauTransport.addArc("2", "3");
		reseauTransport.addArc("2", "S");
		reseauTransport.addArc("3", "S");
		
		reseauTransport.getNoeud("E").findArc("1").setCapacite(30).setPoids(7);
		reseauTransport.getNoeud("E").findArc("2").setCapacite(20).setPoids(6);
		reseauTransport.getNoeud("1").findArc("2").setCapacite(25).setPoids(5);
		reseauTransport.getNoeud("1").findArc("3").setCapacite(10).setPoids(4);
		reseauTransport.getNoeud("2").findArc("3").setCapacite(20).setPoids(2);
		reseauTransport.getNoeud("2").findArc("S").setCapacite(25).setPoids(2);
		reseauTransport.getNoeud("3").findArc("S").setCapacite(20).setPoids(1);
		
		return reseauTransport;
	}
	
}
