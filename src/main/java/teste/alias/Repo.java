package teste.alias;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import info.debatty.java.stringsimilarity.JaroWinkler;

public class Repo {
	private String remoteURL; 
	private Git result;
	private String localPath;
	
	public Repo(String remoteURL){ 
		this.remoteURL = remoteURL; 
		result = null;
		localPath = null; 
	}
	
	private void downloadRepo() {
		localPath = remoteURL.substring(19, remoteURL.length()-4); 
		localPath = localPath.replace('/', '-');
		
        System.out.println("Cloning from " + remoteURL + " to " + localPath); 
        
        try {
        	result = Git.cloneRepository()
                .setURI(remoteURL)
                .setDirectory(new File(localPath))
                .call(); 
        } catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
        	System.out.println("Having repository: " + result.getRepository().getDirectory());
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			System.out.println("Having repository: " + result.getRepository().getDirectory());
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			System.out.println("Having repository: " + result.getRepository().getDirectory());
		}
	}
	
	private static void deleteDir(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            deleteDir(f);
	        }
	    }
	    file.delete();
	}
	
	public List<Alias> getRepoAliases() throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException{
		downloadRepo();
		
		Repository repo = result.getRepository();
        RevWalk revWalk = new RevWalk( repo ); 
		ObjectId commitId = repo.resolve( "HEAD" );
		revWalk.markStart( revWalk.parseCommit( commitId ) );
		int pos = -1, count = 0; 
		double aux, val1, val2;
		
		List<Alias> userAlias = new ArrayList<Alias>(); 
		
		for( RevCommit commit : revWalk ) {
			String email = commit.getAuthorIdent().getEmailAddress().toLowerCase(); 
			count = 0; 
			pos = -1;
			for(Alias a: userAlias) { 
				if(a.checkEqualEmail(email)){
					pos = count;
					break; 
				}	
				count++;
			}
			
			if(pos == -1) {
				count = 0; 
				for(Alias a: userAlias) { 
					val1 = a.getSimilarityEmail(email, commit.getAuthorIdent().getName().toLowerCase()); 
					val2 = a.getSimilarityName(email, commit.getAuthorIdent().getName().toLowerCase());  
					if(((1-val1)> 0.9 && (1-val2)>0.65)  
								||((1-val1)> 0.65 && (1-val2)>0.9) ){ 
						pos = count; 
						break;
					}
						
					count++;
				}
			}
			
			if(pos != -1) { 
				userAlias.get(pos).addUser(email, commit.getAuthorIdent().getName().toLowerCase());
			}
			else { 
				userAlias.add(new Alias(commit.getAuthorIdent().getName().toLowerCase(), email));
			}
		}
		
		deleteDir(new File(localPath));
		return userAlias; 
	}
}
