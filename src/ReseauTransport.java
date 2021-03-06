
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.*;
import java.io.File;
import java.nio.charset.Charset;
import java.io.IOException;
import java.util.Iterator;
import java.io.FileWriter;

public class ReseauTransport extends Graphe {
	
	public ReseauTransport() {
		super();
	}
	
	/**
	 * Construit un graphe composé d'une entrée (E), de n nœuds numériques suivant E, de m nœuds abécédaires suivant les nœuds numériques, et d'une sortie (S). 
	 * @param n le nombre de nœuds numériques (suivant E).
	 * @param m le nombre de nœuds abécédaires (précédant S).
	 */
	
	public ReseauTransport(int n, int m) {
		super();
		
		//// Nœuds ////
		
		addNoeud("E");
		
		for(int i = 0; i < n; ++i) {
			String id = nid(i);
			
			addNoeud(id);
		}
		
		for(int i = 0; i < m; ++i) {
			String id = cid(i);
			
			addNoeud(id);
		}
		
		addNoeud("S");
		
		//// Arcs ////
		
		// Nœuds numériques
		
		for(int i = 0; i < n; ++i) {
			String id = nid(i);
			
			// Toujours un arc de E au nœud.
			
			addArc("E", id);
			
			// Combien de nœuds doivent suivre le nœud.
			
			int randomCount = irandom(1, m);
			// randomCount = 1;
			
			// Liste mélangée des nœuds suivants.
			
			String[] targetIds = shuffleIds(charIds(m), randomCount);
			
			for(int j = 0; j < randomCount; ++j) {
				addArc(id, targetIds[j]);
			}
		}
		
		// Nœuds abécédaires
		
		for(int i = 0; i < m; ++i) {
			String id = cid(i);
			
			// Combien de nœuds doivent précéder le nœud.
			
			int randomCount = irandom(1, n);
			// randomCount = 1;
			
			// Liste mélangée des nœuds précédents.
			
			String[] sourceIds = shuffleIds(numberIds(n), randomCount);
			
			for(int j = 0; j < randomCount; ++j) {
				addArc(sourceIds[j], id);
			}
			
			// Toujours un arc du nœud à S.
			
			addArc(id, "S");
		}
		
		// Capacités
		
		randomizeCapacites();
	}
	
	public ReseauTransport(ReseauTransport reseauTransport) {
		super(reseauTransport);
	}
	
	public ReseauTransport(String fileName) {
		this();
		
		try {
			CSVParser parser = CSVParser.parse(new File(fileName), Charset.forName("UTF-8"), CSVFormat.DEFAULT);
			Iterator<CSVRecord> it = parser.iterator();
			
			if(it.hasNext()) {it.next();}// Ignore first line
			
			while(it.hasNext()) {
				CSVRecord record = it.next();
				
				if(record.size() >= 2) {// (Source,Cible)
					String sourceId = record.get(0);
					String cibleId = record.get(1);
					
					addNoeud(sourceId);
					addNoeud(cibleId);
					addArc(sourceId, cibleId);
					
					if(record.size() >= 3) {// (Source,Cible,Capacité)
						Arc arc = getArc(sourceId, cibleId);
						
						try {
							int capacite = Integer.parseInt(record.get(2));
							
							arc.setCapacite(capacite);
							
							if(record.size() >= 4) {// (Source,Cible,Capacité,Poids)
								double poids = Double.parseDouble(record.get(3));
								
								arc.setPoids(poids);
								
								if(record.size() >= 5) {// (Source,Cible,Capacité,Poids,Borne)
									int borne = Integer.parseInt(record.get(4));
									
									arc.setBorne(borne);
								}
							}
						} catch(NumberFormatException e) {
							
						}
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne un tableau de strings contenant un nombre donné d'identifiants aléatoires provenant d'un tableau donné.
	 * @param ids les identifiants à mélanger.
	 * @param length la taille du tableau à retourner (nombre d'éléments à prendre du tableau initial).
	 * @return
	 */
	
	private static String[] shuffleIds(String[] ids, int length) {
		String[] shuffled = new String[Math.min(ids.length, length)];
		List<String> idPool = new ArrayList<String>(Arrays.asList(ids));
		int i = 0;
		
		while(!idPool.isEmpty() && i < length) {
			int randomIndex = (int)Math.floor(Math.random() * (idPool.size()));
			String id = idPool.get(randomIndex);
			shuffled[i++] = id;
			idPool.remove(randomIndex);
		}
		
		return shuffled;
	}
	
	/**
	 * Retourne un tableau d'identifiants numériques jusqu'au nombre donné.
	 * @param count le nombre d'identifiants.
	 * @return un tableau d'identifiants {1, 2, …}.
	 */
	
	public static String[] numberIds(int count) {
		String[] res = new String[count];
		
		for(int i = 0; i < count; ++i) {
			res[i] = nid(i);
		}
		
		return res;
	}
	
	/**
	 * Retourne un tableau d'identifiants abécédaires de taille donnée.
	 * @param count le nombre d'identifiants.
	 * @return un tableau d'identifiants {a, b, …}.
	 */
	
	public static String[] charIds(int count) {
		String[] res = new String[count];
		
		for(int i = 0; i < count; ++i) {
			res[i] = cid(i);
		}
		
		return res;
	}
	
	/**
	 * Retourne un identifiant numérique correspondant à un index.
	 * @param index l'index à convertir en identifiant.
	 * @return l'identifiant correspondant à l'index donné (0 → 1, 1 → 2, …).
	 */
	
	private static String nid(int index) {
		return (index + 1) + "";
	}
	
	/**
	 * Retourne un identifiant abécédaire correspondant à un index.
	 * @param index l'index à convertir en identifiant.
	 * @return l'identifiant correspondant à l'index donné (0 → a, 1 → b, …).
	 */
	
	private static String cid(int index) {
		return (char)('a' + index) + "";
	}
	
	/**
	 * Retourne un nombre aléatoire entre les bornes données incluses.
	 * @param min le nombre minimal.
	 * @param max le nombre maximal.
	 * @return un nombre entre min et max.
	 */
	
	private static int irandom(int min, int max) {
		return min + (int)Math.floor(Math.random() * (max - min + 1));
	}
	
	/**
	 * Attribue à chaque arc du graphe une capacité aléatoire.
	 * @param min la capacité minimale pour chaque arc.
	 * @param max la capacité maximale pour chaque arc.
	 */
	
	public void randomizeCapacites(int min, int max) {
		for(Noeud noeud : getNoeuds()) {
			for(Arc arc : noeud.getSucc()) {
				arc.setCapacite(irandom(min, max));
			}
		}
	}
	
	/**
	 * Attribue à chaque arc du graphe une capacité aléatoire entre 5 et 50.
	 */
	
	public void randomizeCapacites() {
		randomizeCapacites(5, 50);
	}
	
	public void export(String fileName) {
		StringBuffer buffer = new StringBuffer("Source,Cible,Capacité,Poids?,Borne?\n");
		String sep = ",";
		
		for(Arc arc : getArcs()) {
			buffer
			.append(arc.getSourceId())
			.append(sep)
			.append(arc.getCibleId())
			.append(sep)
			.append(arc.getCapacite())
			.append(sep)
			.append(arc.getPoids())
			.append(sep)
			.append(arc.getBorne())
			.append('\n');
		}
		
		File outputFile = new File(fileName);
		FileWriter out;
		
		try {
			out = new FileWriter(outputFile);
			
			out.write(buffer.toString());
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
