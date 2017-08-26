package teste.alias;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
	private File localPath;
	
	public Repo(String remoteURL){ 
		this.remoteURL = remoteURL; 
		result = null;
		localPath = null; 
	}
	
	private void downloadRepo() {
		localPath = new File("TesteAlias");
		
        System.out.println("Cloning from " + remoteURL + " to " + localPath); 
        
        try {
        	result = Git.cloneRepository()
                .setURI(remoteURL)
                .setDirectory(localPath)
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
	
	public Map<String, Set<String>> getRepoAliases() throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException{
		downloadRepo();
		
		Repository repo = result.getRepository();
        RevWalk revWalk = new RevWalk( repo ); 
		ObjectId commitId = repo.resolve( "refs/heads/master" );
		revWalk.markStart( revWalk.parseCommit( commitId ) );
		
		
		Map<String, Set<String>> userAlias = new HashMap<String, Set<String>>(); 
		
		for( RevCommit commit : revWalk ) {
		    
			String email = commit.getAuthorIdent().getEmailAddress().substring(0, commit.getAuthorIdent().getEmailAddress().indexOf('@')); 
			
		    if(userAlias.get(email) != null) { 
		    	userAlias.get(email).add(commit.getAuthorIdent().getName());
		    }
		    else { 
		    	
		    	Set<Entry<String, Set<String>>> set = userAlias.entrySet();
				Iterator it = set.iterator();
				
				JaroWinkler jw = new JaroWinkler();
				Boolean found = false; 
				while(it.hasNext() && !found){
					Entry<String, Set<String>> entry = (Entry)it.next();
					String keyEmail = entry.getKey().substring(0, entry.getKey().indexOf('@'));
					if(jw.similarity(email, keyEmail) >= 0.8 || jw.similarity(keyEmail, commit.getAuthorIdent().getName()) >= 0.8){
						userAlias.get(entry.getKey()).add(commit.getAuthorIdent().getName());
						found = true;
					}
					else if(jw.similarity(email, keyEmail) >= 0.6 || jw.similarity(keyEmail, commit.getAuthorIdent().getName()) >= 0.6){
						for(String s: entry.getValue()) {
							if(jw.similarity(email, s) >= 0.8 || jw.similarity(s, commit.getAuthorIdent().getName()) >= 0.8){
								userAlias.get(entry.getKey()).add(commit.getAuthorIdent().getName());
								found = true; 
								break;
							}
						}
					}
				}
		    	
				if(!found) {
					userAlias.put(commit.getAuthorIdent().getEmailAddress(), new HashSet<String>()); 
					userAlias.get(commit.getAuthorIdent().getEmailAddress()).add(commit.getAuthorIdent().getName());
				}
		    }
		}
		
		deleteDir(localPath);
		return userAlias; 
	}
}
