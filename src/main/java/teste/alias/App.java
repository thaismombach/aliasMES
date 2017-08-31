package teste.alias;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args ) throws IOException, NoHeadException, GitAPIException
    {	
    	
    	Scanner reader = new Scanner(System.in);  
    	String repoURL = reader.next(); 
    	
    	Repo repo = new Repo(repoURL); 
    	
		List<Alias> alias = repo.getRepoAliases();
		Boolean print = false; 
		int count = 1; 
		for(Alias a: alias){
			
			if(print)
				System.out.println();
			
			System.out.print("User " + count + ": ");
			
			for (Map.Entry entry : a.getAliases().entrySet()) {
			    //System.out.println(entry.getKey() + ", " + entry.getValue());
			    
			    for(String n: (Set<String>) entry.getValue()){ 
			    	System.out.print("(" + entry.getKey() + "," + n + ") ");
			    }
			}
			
			print = true;
			count++; 
		}
    }
}
