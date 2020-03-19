
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
	
}
