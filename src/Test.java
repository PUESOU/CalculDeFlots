
import java.util.List;
import java.util.Iterator;

public class Test {
	public static void main(String[] args) {
		ReseauTransport reseauTransport = new ReseauTransport(5, 5);// {E, 1, 2, 3, 4, 5, a, b, c, d, e, S}
		
		print(reseauTransport);
	}
	
	/**
	 * Retourne une cha�ne de caract�res construite � partir d'une liste, les �l�ments �tant s�par�s par un s�parateur donn�.
	 * @param <T>
	 * @param list la liste � partir de laquelle cr�er la cha�ne.
	 * @param separator le s�parateur devant s�parer les �l�ments.
	 * @return la cha�ne de caract�res repr�sentant la liste.
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
		
		reseauTransport.findArc("E", "1").setCapacite(45);
		reseauTransport.findArc("E", "2").setCapacite(25);
		reseauTransport.findArc("E", "3").setCapacite(25);
		reseauTransport.findArc("1", "a").setCapacite(10);
		reseauTransport.findArc("1", "b").setCapacite(15);
		reseauTransport.findArc("1", "d").setCapacite(20);
		reseauTransport.findArc("2", "a").setCapacite(20);
		reseauTransport.findArc("2", "b").setCapacite(5);
		reseauTransport.findArc("2", "c").setCapacite(5);
		reseauTransport.findArc("3", "c").setCapacite(10);
		reseauTransport.findArc("3", "d").setCapacite(10);
		reseauTransport.findArc("a", "S").setCapacite(30);
		reseauTransport.findArc("b", "S").setCapacite(10);
		reseauTransport.findArc("c", "S").setCapacite(20);
		reseauTransport.findArc("d", "S").setCapacite(30);
		
		return reseauTransport;
	}
	
	public static void print(ReseauTransport reseauTransport) {
		Noeud.stringType = 1;// Affichage des n�uds : "id(suivants�)".
		
		System.out.println("\n---- Noeud(suivants�) ----");
		System.out.println(reseauTransport);
		
		Noeud.stringType = 0;// Affichage des n�uds : "id".
		
		System.out.println("\n---- Branches ----");
		System.out.println(listJoin(reseauTransport.getNoeud("E").getBranches(), "\n"));
		
		System.out.println("\n---- Chemins ----");
		System.out.println(listJoin(reseauTransport.getNoeud("E").getPaths(), "\n"));
		
		Noeud.stringType = 1;// Remet l'affichage par d�faut.
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
		
		reseauTransport.findArc("E", "1").setCapacite(30).setPoids(7);
		reseauTransport.findArc("E", "2").setCapacite(20).setPoids(6);
		reseauTransport.findArc("1", "2").setCapacite(25).setPoids(5);
		reseauTransport.findArc("1", "3").setCapacite(10).setPoids(4);
		reseauTransport.findArc("2", "3").setCapacite(20).setPoids(2);
		reseauTransport.findArc("2", "S").setCapacite(25).setPoids(2);
		reseauTransport.findArc("3", "S").setCapacite(20).setPoids(1);
		
		return reseauTransport;
	}
	
}
