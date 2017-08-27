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
    	
//    	File localPath = new File("/TesteAlias");
////    	if(!localPath.delete()) {
////            throw new IOException("Could not delete temporary file " + localPath);
////        }
//        // then clone
//        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
//        Git result = null; 
//        
//        try {
//        	result = Git.cloneRepository()
//                .setURI(REMOTE_URL)
//                .setDirectory(localPath)
//                .call(); 
//        } catch (InvalidRemoteException e) {
//			// TODO Auto-generated catch block
//        	System.out.println("Having repository: " + result.getRepository().getDirectory());
//		} catch (TransportException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Having repository: " + result.getRepository().getDirectory());
//		} catch (GitAPIException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Having repository: " + result.getRepository().getDirectory());
//		}
//        
//       
//        Repository repository = result.getRepository();
//        RevWalk revWalk = new RevWalk( repository ); 
//		ObjectId commitId = repository.resolve( "refs/heads/master" );
//		revWalk.markStart( revWalk.parseCommit( commitId ) );
//		
//		
//		Map<String, Set<String>> userAlias = new HashMap<String, Set<String>>(); 
//		
//		for( RevCommit commit : revWalk ) {
//		    //System.out.println( commit.getAuthorIdent().getName() + " " + commit.getAuthorIdent().getEmailAddress());
//		    
//			String email = commit.getAuthorIdent().getEmailAddress().substring(0, commit.getAuthorIdent().getEmailAddress().indexOf('@')); 
//			
//		    if(userAlias.get(email) != null) { 
//		    	userAlias.get(email).add(commit.getAuthorIdent().getName());
//		    }
//		    else { 
//		    	
//		    	Set<Entry<String, Set<String>>> set = userAlias.entrySet();
//				Iterator it = set.iterator();
//				
//				JaroWinkler jw = new JaroWinkler();
//				Boolean found = false; 
//				while(it.hasNext() && !found){
//					Entry<String, Set<String>> entry = (Entry)it.next();
//					String keyEmail = entry.getKey().substring(0, entry.getKey().indexOf('@'));
//					if(jw.similarity(email, keyEmail) >= 0.8 || jw.similarity(keyEmail, commit.getAuthorIdent().getName()) >= 0.8){
//						userAlias.get(entry.getKey()).add(commit.getAuthorIdent().getName());
//						found = true;
//					}
//					else if(jw.similarity(email, keyEmail) >= 0.6 || jw.similarity(keyEmail, commit.getAuthorIdent().getName()) >= 0.6){
//						for(String s: entry.getValue()) {
//							if(jw.similarity(email, s) >= 0.8 || jw.similarity(s, commit.getAuthorIdent().getName()) >= 0.8){
//								userAlias.get(entry.getKey()).add(commit.getAuthorIdent().getName());
//								found = true; 
//								break;
//							}
//						}
//					}
//				}
//		    	
//				if(!found) {
//					userAlias.put(commit.getAuthorIdent().getEmailAddress(), new HashSet<String>()); 
//					userAlias.get(commit.getAuthorIdent().getEmailAddress()).add(commit.getAuthorIdent().getName());
//				}
//		    }
//		}
		
		List<Alias> alias = repo.getRepoAliases();
		Boolean print = false; 
		for(Alias a: alias){
			
			for(String s: a.getNames()){
				if(print)
					System.out.print(" - ");
				
				System.out.print(s);
				print = true; 
			}
			
			System.out.println();
			print = false; 
		}
		
    }
}
