package teste.alias;

import java.util.HashSet;
import java.util.Set;

import info.debatty.java.stringsimilarity.JaroWinkler;

public class Alias {
	private Set<String> names; 
	private Set<String> emails; 
	private JaroWinkler jw; 
	
	public Alias(String name, String email) {
		names = new HashSet<String>(); 
		names.add(name);
		emails = new HashSet<String>();
		emails.add(email);
		jw = new JaroWinkler();
	}
	
	public Boolean checkEqualEmail(String email){ 
		return emails.contains(email); 
	}
	public Boolean checkEqualName(String name){ 
		return names.contains(name); 
	}
	
	public double getSimilarityEmail(String email, String name){ 
		double val1=0.0, val2=0.0;
		for(String e: emails){
			val1 += jw.similarity(email, e); 
			val2 += jw.similarity(name, e);
		}
		
		val1 = val1/emails.size();
		val2 = val2/emails.size();
		
		if(val1 > val2)
			return val1; 
		else 
			return val2;
	}
	
	public double getSimilarityName(String email, String name){ 
		double val1=0.0, val2=0.0;
		for(String n: names){
			val1 += jw.similarity(email, n); 
			val2 += jw.similarity(name, n);
		}
		
		val1 = val1/emails.size();
		val2 = val2/emails.size();
		
		if(val1 > val2)
			return val1; 
		else 
			return val2;
	}
	
	public void addEmail(String email){
		emails.add(email); 
	}
	public void addName(String name){
		names.add(name); 
	}
	
	public Set<String> getNames(){ 
		return names;
	}
	public Set<String> getEmails(){ 
		return emails;
	}
}
