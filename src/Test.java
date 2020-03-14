
import java.util.List;
import java.util.Iterator;

public class Test {
	public static void main(String[] args) {
		ReseauTransport reseauTransport = new ReseauTransport(5, 5);// {E, 1, 2, 3, 4, 5, a, b, c, d, e, S}
		
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
		
		reseauTransport.getNoeud("E").findArc("1").capacite = 45;
		reseauTransport.getNoeud("E").findArc("2").capacite = 25;
		reseauTransport.getNoeud("E").findArc("3").capacite = 25;
		reseauTransport.getNoeud("1").findArc("a").capacite = 10;
		reseauTransport.getNoeud("1").findArc("b").capacite = 15;
		reseauTransport.getNoeud("1").findArc("d").capacite = 20;
		reseauTransport.getNoeud("2").findArc("a").capacite = 20;
		reseauTransport.getNoeud("2").findArc("b").capacite = 5;
		reseauTransport.getNoeud("2").findArc("c").capacite = 5;
		reseauTransport.getNoeud("3").findArc("c").capacite = 10;
		reseauTransport.getNoeud("3").findArc("d").capacite = 10;
		reseauTransport.getNoeud("a").findArc("S").capacite = 30;
		reseauTransport.getNoeud("b").findArc("S").capacite = 10;
		reseauTransport.getNoeud("c").findArc("S").capacite = 20;
		reseauTransport.getNoeud("d").findArc("S").capacite = 30;
		
		return reseauTransport;
	}
}
