package ro.pricepage.service;

import java.io.InputStream;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Binary;
import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.core.fs.FileSystemException;

import ro.pricepage.file.FileUtils;
import ro.pricepage.qualifiers.JcrRepository;

@Named("fileService")
@Stateless
public class FileService extends BaseService{
	private static final long serialVersionUID = -5555518563355534756L;
	
	@Inject
	@JcrRepository
	private Repository repository;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void uploadImageForStore(int storeId, InputStream content, boolean isHead, boolean forceResize) throws LoginException, RepositoryException, FileSystemException{
		//TODO: resize
		
		//TODO: neaparat de scos in profil configul de JCR
		Session s = repository.login();
		try{
			Node root = s.getRootNode();	
			Node storesNode = getNodeRecursively(root, FileUtils.STORE_IMAGES_PATH + "/" + storeId);
			String leafNodeName = isHead ? FileUtils.HEAD : UUID.randomUUID().toString();
			if(storesNode.hasNode(leafNodeName)){
				storesNode.getNode(leafNodeName).remove();
			}
			Node leafNode = storesNode.addNode(leafNodeName);
			copyFileContentToNode(leafNode, s, content);
			s.save();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FileSystemException(e.getMessage(), e);
		}
		finally{
			s.logout();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void uploadImageForChain(int chainId, InputStream content, boolean forceResize) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login();
		try{
			//TODO:
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FileSystemException(e.getMessage(), e);
		}
		finally{
			s.logout();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void uploadImageForProduct(int chainId, InputStream content, boolean isHead, boolean forceResize) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login();
		try{
			//TODO:
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FileSystemException(e.getMessage(), e);
		}
		finally{
			s.logout();
		}
	}
	
	private Node getNodeRecursively(Node parent, String relPath) throws ItemExistsException, RepositoryException, VersionException, ConstraintViolationException, LockException, RepositoryException{
		String[] nodeNames = relPath.split("/");
		Node auxNode = parent;
		for(String nodeName : nodeNames){
			if(!auxNode.hasNode(nodeName)){
				auxNode.addNode(nodeName);
			}
			auxNode = auxNode.getNode(nodeName);
		}
		return auxNode;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private Node copyFileContentToNode(Node node, Session s, InputStream is) throws UnsupportedRepositoryOperationException, RepositoryException{
		Binary binary = s.getValueFactory().createBinary(is);
		node.setProperty(FileUtils.FILE_CONTENT_PROPERTY_NAME, binary);
		return node;
	}

}
