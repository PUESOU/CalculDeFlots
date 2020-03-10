
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
    private HashMap<Integer, Noeud> hmap;
    
    public Graphe() {
        noeuds = new LinkedList<Noeud>();
        hmap = new HashMap<Integer, Noeud>();
    }
    
    public Graphe(int k) {
        this();
        
        for(int i = 1; i <= k; ++i) {
            addNoeud(new Noeud(i));
        }
    }
    
    public Graphe(String file) {
    	this();
    	
    	try {
    		CSVParser csvParser = CSVParser.parse(new File(file), Charset.forName("UTF-8"), CSVFormat.DEFAULT);
    		Iterator<CSVRecord> it = csvParser.iterator();
    		
    		while(it.hasNext()) {
    			CSVRecord record = it.next();
    			
    			// if(record.size() == 2) {
    				try {
	    				int x = Integer.parseInt(record.get(0));
	    				int y = Integer.parseInt(record.get(1));
	    				
	    				addNoeud(x);
	    				addNoeud(y);
	    				addArc(x, y);
    				} catch(NumberFormatException e) {
    		    		
    		    	}
    			// }
    			
    			// System.out.println(record);
    		}
    	} catch(IOException e) {
    		
    	}
    }
    
    protected void addNoeud(Noeud noeud) {
        noeuds.add(noeud);
        hmap.put(noeud.getId(), noeud);
    }
    
    public void addNoeud(int n) {
        ListIterator<Noeud> it = noeuds.listIterator();
        
        while(it.hasNext()) {
            Noeud noeud = it.next();
            
            if(noeud.matchId(n)) {
                return;
            }
        }
        
        addNoeud(new Noeud(n));
    }
    
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
    
    public Noeud getNoeud(int n) {
        return hmap.get(n);
    }
    
    public void addArc(int x, int y) {
        Noeud source = getNoeud(x);
        Noeud cible = getNoeud(y);
        
        if(source != null && cible != null) {
            source.addArc(cible);
        }
    }
    
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
    	
    	HashMap<Integer, String> profondeurNoeuds = new HashMap<Integer, String>();
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
    
    public void addArcDouble(int x, int y) {
        Noeud source = getNoeud(x);
        Noeud cible = getNoeud(y);
        
        if(source != null && cible != null) {
            source.addArc(cible);
            cible.addArc(source);
        }
    }
    
    public void export() {
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
    	
    	File outputFile = new File(this.getClass() + ".csv");
    	FileWriter out;
    	
    	try {
    		out = new FileWriter(outputFile);
    		
    		out.write(buff.toString());
    		out.close();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public boolean hasArcDouble(int x, int y) {
    	Noeud source = getNoeud(x), cible = getNoeud(y);
    	
    	if(source == null || cible == null) {
    		return false;
    	}
    	
    	return source.hasSuccesseur(y) && cible.hasSuccesseur(x);
    }
    
    public void removeArcDouble(int x, int y) {
    	Noeud source = getNoeud(x);
    	Noeud cible = getNoeud(y);
    	
    	if(source != null) {
    		source.removeSuccesseur(y);
    	}
    	
    	if(cible != null) {
    		cible.removeSuccesseur(x);
    	}
    }
    
    public List<Noeud> getNoeuds() {
    	return noeuds;
    }
    
    public int getNoeudCount() {
    	return noeuds.size();
    }
    
    public static void main(String[] args) {
    	// System.out.println(new Graphe("test.csv"));
    	// System.out.println(new Graphe("Arcs-toy2.csv"));
    	
    	////  ////
    	
    	Graphe graphe = new Graphe(9);
    	
    	graphe.addArc(1, 3);
    	graphe.addArc(1, 5);
    	graphe.addArc(1, 6);
    	graphe.addArc(1, 7);
    	graphe.addArc(2, 1);
    	graphe.addArc(3, 2);
    	graphe.addArc(3, 6);
    	graphe.addArc(4, 2);
    	graphe.addArc(4, 9);
    	graphe.addArc(7, 3);
    	graphe.addArc(7, 6);
    	graphe.addArc(8, 2);
    	graphe.addArc(8, 4);
    	graphe.addArc(8, 5);
    	
    	System.out.println(graphe);
    	
    	graphe.parcours();
    	graphe.profI();
    	graphe.largeur();
    	
    	////  ////
    	
    	graphe.export();
    }
}
