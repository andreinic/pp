package ro.pricepage.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
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
	public String uploadImageForStore(int storeId, InputStream content, boolean isHead, boolean forceResize) throws LoginException, RepositoryException, FileSystemException{
		//TODO: resize

		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
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
			return FileUtils.STORE_IMAGES_PATH + "/" + storeId + "/" + leafNodeName;
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
	public String uploadImageForChain(int chainId, InputStream content, boolean forceResize) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();	
			Node chainsNode = getNodeRecursively(root, FileUtils.CHAIN_IMAGES_PATH + "/" + chainId);
			String leafNodeName = FileUtils.HEAD;
			if(chainsNode.hasNode(leafNodeName)){
				chainsNode.getNode(leafNodeName).remove();
			}
			Node leafNode = chainsNode.addNode(leafNodeName);
			copyFileContentToNode(leafNode, s, content);
			s.save();
			return FileUtils.CHAIN_IMAGES_PATH + "/" + chainId + "/" + FileUtils.HEAD;
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
	public String uploadImageForProduct(int productId, InputStream content, boolean isHead, boolean forceResize) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();	
			Node productsNode = getNodeRecursively(root, FileUtils.PRODUCT_IMAGES_PATH + "/" + productId);
			String leafNodeName = isHead ? FileUtils.HEAD : UUID.randomUUID().toString();
			if(productsNode.hasNode(leafNodeName)){
				productsNode.getNode(leafNodeName).remove();
			}
			Node leafNode = productsNode.addNode(leafNodeName);
			copyFileContentToNode(leafNode, s, content);
			s.save();
			return FileUtils.PRODUCT_IMAGES_PATH + "/" + productId + "/" + leafNodeName;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FileSystemException(e.getMessage(), e);
		}
		finally{
			s.logout();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public InputStream getFileContent(String path) throws FileSystemException, LoginException, RepositoryException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();	
			Node n = root.getNode(path);
			Property content = n.getProperty(FileUtils.FILE_CONTENT_PROPERTY_NAME);
			return content.getBinary().getStream();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FileSystemException(e.getMessage(), e);
		}
		finally{
			s.logout();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> getImagePathsForStore(int storeId) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			List<String> retList = new ArrayList<>();
			Node root = s.getRootNode();
			Node storeNode = getNodeRecursively(root, FileUtils.STORE_IMAGES_PATH + "/" + storeId);
			NodeIterator children = storeNode.getNodes();
			while(children.hasNext()){
				retList.add(children.nextNode().getPath());
			}
			return retList;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FileSystemException(e.getMessage(), e);
		}
		finally{
			s.logout();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> getImagePathsForProduct(int productId) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			List<String> retList = new ArrayList<>();
			Node root = s.getRootNode();
			Node productNode = getNodeRecursively(root, FileUtils.PRODUCT_IMAGES_PATH + "/" + productId);
			NodeIterator children = productNode.getNodes();
			while(children.hasNext()){
				retList.add(children.nextNode().getPath());
			}
			return retList;
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
