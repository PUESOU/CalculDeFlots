
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;

import java.io.File;
import java.nio.charset.Charset;
import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.csv.*;

import java.util.Stack;

import java.io.FileWriter;

public class Graphe {
    private List<Noeud> noeuds;
    private HashMap<String, Noeud> hmap;
    
    public Graphe() {
        noeuds = new LinkedList<Noeud>();
        hmap = new HashMap<String, Noeud>();
    }
    
    public Graphe(int k) {
        this();
        
        for(int i = 1; i <= k; ++i) {
            addNoeud(new Noeud(i));
        }
    }
    
    public Graphe(String fileName) {
    	this();
    	
    	try {
    		CSVParser csvParser = CSVParser.parse(new File(fileName), Charset.forName("UTF-8"), CSVFormat.DEFAULT);
    		Iterator<CSVRecord> it = csvParser.iterator();
    		
    		if(it.hasNext()) {it.next();}// Ignore first line
    		
    		while(it.hasNext()) {
    			CSVRecord record = it.next();
    			
    			if(record.size() >= 2) {// (Source,Cible)
    				String sourceId = record.get(0);
    				String cibleId = record.get(1);
    				
    				addNoeud(sourceId);
    				addNoeud(cibleId);
    				addArc(sourceId, cibleId);
    			}
    		}
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public Graphe(Graphe graphe) {
    	this();
    	
    	for(Noeud noeud : graphe.noeuds) {
    		addNoeud(new Noeud(noeud));
    	}
    	
    	for(Arc arc : graphe.getArcs()) {
    		addArc(arc.getSourceId(), arc.getCibleId());
    		getArc(arc.getSourceId(), arc.getCibleId())
    		.setPoids(arc.getPoids())
    		.setBorne(arc.getBorne())
    		.setCapacite(arc.getCapacite())
    		.flux = arc.flux;
    	}
    }
    
    protected void addNoeud(Noeud noeud) {
        noeuds.add(noeud);
        hmap.put(noeud.getId(), noeud);
    }
    
    public void addNoeud(String n) {
        ListIterator<Noeud> it = noeuds.listIterator();
        
        while(it.hasNext()) {
            Noeud noeud = it.next();
            
            if(noeud.matchId(n)) {
                return;
            }
        }
        
        addNoeud(new Noeud(n));
    }
    
    public void addNoeud(int n) {addNoeud(n+"");}
    
    // Complexité : O(n)
    
    public Noeud getNoeud_(int n) {
        ListIterator<Noeud> it = noeuds.listIterator();
        
        while(it.hasNext()) {
            Noeud noeud = it.next();
            
            if(noeud.matchId(n)) {
                return noeud;
            }
        }
        
        return null;
    }
    
    public Noeud getNoeud(String n) {
    	return hmap.get(n);
    }
    
    public Noeud getNoeud(int n) {
    	return getNoeud(n+"");
    }
    
    public void addArc(String x, String y) {
        Noeud source = getNoeud(x);
        Noeud cible = getNoeud(y);
        
        if(source != null && cible != null) {
            source.addArc(cible);
        }
    }
    
    public void addArc(int x, int y) {addArc(x+"", y+"");}
    public void addArc(String x, int y) {addArc(x, y+"");}
    public void addArc(int x, String y) {addArc(x+"", y);}
    
    public String toString() {
        String res = "";
        
        ListIterator<Noeud> it = noeuds.listIterator();
        
        while(it.hasNext()) {
            Noeud noeud = it.next();
            
            res += noeud;
            
            if(it.hasNext()) {
                res += "\n";
            }
        }
        
        return res;
    }
    
    public void unmarkNoeuds() {
    	ListIterator<Noeud> it = noeuds.listIterator();
    	
    	while(it.hasNext()) {
    		it.next().mark = false;
    	}
    }
    
    public void parcours() {
    	unmarkNoeuds();
    	
    	ListIterator<Noeud> it = noeuds.listIterator();
    	
    	while(it.hasNext()) {
    		Noeud noeud = it.next();
    		
    		if(!noeud.mark) {
    			profR(noeud);
    			// profI(noeud);
    			// largeur(noeud);
    		}
    	}
    }
    
    private StringBuffer profondeur = new StringBuffer();
    
    private void incProfondeur() {
    	profondeur.append("--");
    }
    
    private void decProfondeur() {
    	int length = profondeur.length();
    	
    	if(length >= 2) {
    		profondeur.delete(length-2, length);
    	}
    }
    
    private void resetProfondeur() {
    	profondeur.delete(0, profondeur.length());
    }
    
    private void printNoeud(Noeud noeud) {
    	System.out.print(profondeur);
    	System.out.println(noeud.getId());
    }
    
    private void profR(Noeud noeud) {
    	noeud.mark = true;
    	
    	printNoeud(noeud);
    	
    	incProfondeur();
    	
    	ListIterator<Arc> it = noeud.arcsIterator();
    	
    	while(it.hasNext()) {
    		Arc arc = it.next();
    		Noeud cible = getNoeud(arc.getCibleId());
    		
    		if(!cible.mark) {
    			profR(cible);
    		}
    	}
    	
    	decProfondeur();
    }
    
    public void profI() {
    	unmarkNoeuds();
    	
    	ListIterator<Noeud> it = noeuds.listIterator();
    	
    	while(it.hasNext()) {
    		Noeud noeud = it.next();
    		
    		if(!noeud.mark) {
    			profI(noeud);
    		}
    	}
    }
    
    private void profI(Noeud noeud) {
    	Stack<Noeud> stack = new Stack<Noeud>();
    	
    	noeud.mark = true;
    	
    	stack.push(noeud);
    	
    	printNoeud(noeud);
    	
    	while(!stack.isEmpty()) {
    		noeud = stack.peek();
    		
    		if(noeud.ciblesAllMarked()) {
    			stack.pop();
    			
    			decProfondeur();
    		} else {
    			ListIterator<Arc> it = noeud.arcsIterator();
    			
    			while(it.hasNext()) {
    				Arc arc = it.next();
    				Noeud cible = arc.getCible();
    				
    				if(!cible.mark) {
    					cible.mark = true;
    					
    					stack.push(cible);
    					
    			    	incProfondeur();
    			    	
    			    	printNoeud(cible);
    					
    					break;
    				}
    			}
    		}
    	}
    }
    
    public void largeur() {
    	unmarkNoeuds();
    	
    	ListIterator<Noeud> it = noeuds.listIterator();
    	
    	while(it.hasNext()) {
    		Noeud noeud = it.next();
    		
    		if(!noeud.mark) {
    			largeur(noeud);
    		}
    	}
    }
    
    private void largeur(Noeud noeud) {
    	LinkedList<Noeud> file = new LinkedList<Noeud>();
    	
    	noeud.mark();
    	
    	file.addFirst(noeud);
    	
    	System.out.println(noeud.getId());
    	
    	HashMap<String, String> profondeurNoeuds = new HashMap<String, String>();
    	profondeurNoeuds.put(noeud.getId(), "");
    	
    	while(!file.isEmpty()) {
    		noeud = file.getLast();
    		file.removeLast();
    		
    		ListIterator<Arc> it = noeud.arcsIterator();
    		
    		while(it.hasNext()) {
    			Arc arc = it.next();
    			Noeud cible = arc.getCible();
    			
    			if(!cible.mark) {
    				cible.mark = true;
    				
    				file.addFirst(cible);
    				
    				profondeurNoeuds.put(cible.getId(), profondeurNoeuds.get(noeud.getId()) + "---");
    				
    				System.out.print(profondeurNoeuds.get(noeud.getId()));
    				System.out.println(noeud.getId() + "--" + cible.getId());
    			}
    		}
    		
    		// System.out.println("Suivants de " + noeud.getId() + ", profondeur " + profondeurNoeuds.get(noeud.getId()));
    		
    		// profondeur.append("---");
    	}
    	
    	// resetProfondeur();
    }
    
    public void addArcDouble(String x, String y) {
        Noeud source = getNoeud(x);
        Noeud cible = getNoeud(y);
        
        if(source != null && cible != null) {
            source.addArc(cible);
            cible.addArc(source);
        }
    }
    
    public void addArcDouble(int x, int y) {addArcDouble(x+"", y+"");}
    public void addArcDouble(String x, int y) {addArcDouble(x, y+"");}
    public void addArcDouble(int x, String y) {addArcDouble(x+"", y);}
    
    public void export(String fileName) {
    	StringBuffer buff = new StringBuffer("Source,Target\n");
    	String sep = ",";
    	
    	for(Noeud n : noeuds) {
    		for(Arc a : n.getSucc()) {
    			buff
    			.append(a.getSourceId())
    			.append(sep)
    			.append(a.getCibleId())
    			.append('\n');
    		}
    	}
    	
    	File outputFile = new File(fileName);
    	FileWriter out;
    	
    	try {
    		out = new FileWriter(outputFile);
    		
    		out.write(buff.toString());
    		out.close();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void export() {
    	export(this.getClass() + ".csv");
    }
    
    public boolean hasArcDouble(String x, String y) {
    	Noeud source = getNoeud(x), cible = getNoeud(y);
    	
    	if(source == null || cible == null) {
    		return false;
    	}
    	
    	return source.hasSuccesseur(y) && cible.hasSuccesseur(x);
    }
    
    public boolean hasArcDouble(int x, int y) {return hasArcDouble(x+"", y+"");}
    public boolean hasArcDouble(String x, int y) {return hasArcDouble(x, y+"");}
    public boolean hasArcDouble(int x, String y) {return hasArcDouble(x+"", y);}
    
    public void removeArcDouble(String x, String y) {
    	Noeud source = getNoeud(x);
    	Noeud cible = getNoeud(y);
    	
    	if(source != null) {
    		source.removeSuccesseur(y);
    	}
    	
    	if(cible != null) {
    		cible.removeSuccesseur(x);
    	}
    }
    
    public void removeArcDouble(int x, int y) {removeArcDouble(x+"", y+"");}
    public void removeArcDouble(String x, int y) {removeArcDouble(x, y+"");}
    public void removeArcDouble(int x, String y) {removeArcDouble(x+"", y);}
    
    public List<Noeud> getNoeuds() {
    	return noeuds;
    }
    
    public int getNoeudCount() {
    	return noeuds.size();
    }
    
    /**
     * Retourne tous les arcs du graphe.
     * @return la liste de tous les arcs.
     */
    
    public List<Arc> getArcs() {
    	List<Arc> arcs = new LinkedList<Arc>();
    	
    	for(Noeud noeud : noeuds) {
    		for(Arc arc : noeud.getSucc()) {
    			arcs.add(arc);
    		}
    	}
    	
    	return arcs;
    }
	
	/**
	 * Retourne la liste des arcs menant vers le noeud donn�.
	 * @param cible le noeud duquel r�cup�rer les pr�d�cesseurs.
	 * @return la liste des arcs pr�c�dant le noeud.
	 */
	
	public List<Arc> getPred(Noeud cible) {
		List<Arc> predecesseurs = new LinkedList<Arc>();
		
		for(Noeud noeud : getNoeuds()) {
			Arc arc = noeud.getArc(cible.getId());
			
			if(arc != null) {
				predecesseurs.add(arc);
			}
		}
		
		return predecesseurs;
	}
	
	/**
	 * Retourne l'arc connectant la source et cible donn�es.
	 * @param sourceId l'identifiant du n�ud source.
	 * @param cibleId l'identifiant du n�ud cible.
	 * @return l'arc connectant les n�uds.
	 */
	
	public Arc getArc(String sourceId, String cibleId) {
		Noeud source = getNoeud(sourceId);
		
		if(source != null) {
			return source.getArc(cibleId);
		}
		
		return null;
	}
	
	/**
	 * Calcule la valeur du flot v(F) sur le r�seau.
	 * @return la valeur du flot.
	 */
	
	public int valeurFlot() {
		int valeur = 0;
		
		for(Arc arc : getArcs()) {
			valeur += arc.flux;
		}
		
		return valeur;
	}
	
	/**
	 * Calcule le co�t du flot c(F) sur le r�seau.
	 * @return le co�t du flot.
	 */
	
	public double coutFlot() {
		int cout = 0;
		
		for(Arc arc : getArcs()) {
			cout += arc.flux * arc.getPoids();
		}
		
		return cout;
	}
}
