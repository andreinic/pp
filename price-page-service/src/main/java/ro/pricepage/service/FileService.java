package ro.pricepage.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.imageio.ImageIO;
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
	public String uploadImageForStore(int storeId, InputStream content, boolean isHead, boolean forceResize) throws LoginException, RepositoryException, FileSystemException, IOException{
		if(forceResize){
			content = FileUtils.resize(FileUtils.STORE_IMAGE_WIDTH, FileUtils.STORE_IMAGE_HEIGHT, ImageIO.read(content));
		}

		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();	
			Node storesNode = getNodeRecursively(root, FileUtils.STORE_IMAGES_PATH + "/" + storeId);
			String leafNodeName = UUID.randomUUID().toString();
			if(storesNode.hasNode(leafNodeName)){
				storesNode.getNode(leafNodeName).remove();
			}
			Node leafNode = storesNode.addNode(leafNodeName);
			copyFileContentToNode(leafNode, s, content);
			if(isHead){
				storesNode.setProperty(FileUtils.HEAD_ID_PROPERTY_NAME, leafNodeName);
			}
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
	public String uploadImageForChain(int chainId, InputStream content, boolean forceResize) throws LoginException, RepositoryException, FileSystemException, IOException{
		if(forceResize){
			content = FileUtils.resize(FileUtils.CHAIN_IMAGE_WIDTH, FileUtils.CHAIN_IMAGE_HEIGHT, ImageIO.read(content));
		}
		
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
	public String uploadImageForProduct(int productId, InputStream content, boolean isHead, boolean forceResize) throws LoginException, RepositoryException, FileSystemException, IOException{
		if(forceResize){
			content = FileUtils.resize(FileUtils.PRODUCT_IMAGE_WIDTH, FileUtils.PRODUCT_IMAGE_HEIGHT, ImageIO.read(content));
		}
		
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();	
			Node productsNode = getNodeRecursively(root, FileUtils.PRODUCT_IMAGES_PATH + "/" + productId);
			String leafNodeName = UUID.randomUUID().toString();
			if(productsNode.hasNode(leafNodeName)){
				productsNode.getNode(leafNodeName).remove();
			}
			Node leafNode = productsNode.addNode(leafNodeName);
			copyFileContentToNode(leafNode, s, content);
			if(isHead){
				productsNode.setProperty(FileUtils.HEAD_ID_PROPERTY_NAME, leafNodeName);
			}
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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setHeadForStore(int storeId, String headId) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();			
			Node storesNode = root.getNode(FileUtils.STORE_IMAGES_PATH + "/" + storeId);
			if(!(storesNode.getNodes(headId).hasNext())){
				throw new FileSystemException("Invalid image id for store ID " + storeId);
			}
			storesNode.setProperty(FileUtils.HEAD_ID_PROPERTY_NAME, headId);
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
	public void setHeadForProduct(int productId, String headId) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();			
			Node storesNode = root.getNode(FileUtils.PRODUCT_IMAGES_PATH + "/" + productId);
			if(!(storesNode.getNodes(headId).hasNext())){
				throw new FileSystemException("Invalid image id for product ID " + productId);
			}
			storesNode.setProperty(FileUtils.HEAD_ID_PROPERTY_NAME, headId);
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
				retList.add(children.nextNode().getPath().substring(1));
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
				retList.add(children.nextNode().getPath().substring(1));
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
	public String getImagePathForStoreHead(int storeId) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();
			Node storeNode = getNodeRecursively(root, FileUtils.STORE_IMAGES_PATH + "/" + storeId);
			if(!storeNode.hasProperty(FileUtils.HEAD_ID_PROPERTY_NAME)){
				return null;
			}
			Property headIdProperty = storeNode.getProperty(FileUtils.HEAD_ID_PROPERTY_NAME);
			if(headIdProperty == null){
				return null;
			}
			String headId = headIdProperty.getString();
			NodeIterator headIt = storeNode.getNodes(headId);
			if(!(headIt.hasNext())){
				return null;
			}
			String retStr = headIt.nextNode().getPath().substring(1);
			return retStr;
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
	public String getImagePathForProductHead(int productId) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();
			Node productNode = getNodeRecursively(root, FileUtils.PRODUCT_IMAGES_PATH + "/" + productId);
			if(!productNode.hasProperty(FileUtils.HEAD_ID_PROPERTY_NAME)){
				return null;
			}
			Property headIdProperty = productNode.getProperty(FileUtils.HEAD_ID_PROPERTY_NAME);
			if(headIdProperty == null){
				return null;
			}
			String headId = headIdProperty.getString();
			NodeIterator headIt = productNode.getNodes(headId);
			if(!(headIt.hasNext())){
				return null;
			}
			String retStr = headIt.nextNode().getPath().substring(1);
			return retStr;
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
	public void deleteNode(String path) throws LoginException, RepositoryException, FileSystemException{
		Session s = repository.login(new SimpleCredentials(FileUtils.ADMIN, FileUtils.ADMIN.toCharArray()));
		try{
			Node root = s.getRootNode();
			Node node = root.getNode(path);
			node.remove();
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
	
	public void deleteProductNode(int productId) throws LoginException, RepositoryException, FileSystemException{
		deleteNode(FileUtils.PRODUCT_IMAGES_PATH + "/" + productId);
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
