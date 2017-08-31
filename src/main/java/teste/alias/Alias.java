package teste.alias;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class Alias {
	private Set<String> names; 
	private Set<String> emails; 
	private Map<String,Set<String>> aliases; 
	private NormalizedLevenshtein l;
	
	public Alias(String name, String email) {
		names = new HashSet<String>(); 
		names.add(name);
		emails = new HashSet<String>();
		emails.add(email);
		
		aliases = new HashMap<String,Set<String>>();
		aliases.put(email, new HashSet<String>()); 
		aliases.get(email).add(name); 
		
		l = new NormalizedLevenshtein();
	}
	
	public Boolean checkEqualEmail(String email){ 
		return emails.contains(email); 
	}
	public Boolean checkEqualName(String name){ 
		return names.contains(name); 
	}
	
	public double getSimilarityEmail(String email, String name){ 
		double val=1.0, aux;
		int valIndex = email.indexOf('@'); 
		String auxE, auxEmail; 
		
		if(valIndex != -1)
			auxEmail= email.substring(0, email.indexOf('@'));
		else 
			auxEmail = email; 
		for(String e: emails){
			valIndex = e.indexOf('@'); 
			
			if(valIndex != -1)
				auxE= e.substring(0, valIndex);
			else 
				auxE = e; 
			
			aux = l.distance(auxEmail, auxE); 
			if(val > aux)
				val = aux;
			aux = l.distance(name, auxE);
			if(val > aux)
				val = aux;
		}
		
		return val;
	}
	
	public double getSimilarityName(String email, String name){ 
		double val=1.0, aux;
		
		int valIndex = email.indexOf('@'); 
		String auxE, auxEmail; 
		
		if(valIndex != -1)
			auxEmail= email.substring(0, valIndex);
		else 
			auxEmail = email; 
		
		for(String n: names){
			
			aux = l.distance(auxEmail, n); 
			if(val > aux)
				val = aux;
			aux = l.distance(name, n);
			if(val > aux)
				val = aux;
		}
		
		return val;  
	}
	
	public void addUser(String email, String name){
		emails.add(email); 
		names.add(name);
		
		if(aliases.containsKey(email)){ 
			aliases.get(email).add(name);
		}
		else { 
			aliases.put(email, new HashSet<String>()); 
			aliases.get(email).add(name);
		}
	}
	
	public Map<String, Set<String>> getAliases() { 
		return aliases; 
	}
	
	public Set<String> getNames(){ 
		return names;
	}
	public Set<String> getEmails(){ 
		return emails;
	}
}
