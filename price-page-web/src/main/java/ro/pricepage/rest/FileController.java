package ro.pricepage.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.jackrabbit.core.fs.FileSystemException;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import ro.pricepage.service.FileService;

@Stateless
@Path("/file")
public class FileController {
	private static final String FILE_CONTENT_FORM_KEY = "fileContent";

	@Inject
	private FileService fileService;

	@PUT
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Path("/store/{storeId}/put/head")
	public Response uploadHeadForStore(@PathParam("storeId") int storeId,
			MultipartFormDataInput multipart) {
		try {
			InputStream is = extractFileStreamFromMultipart(multipart);
			String path = fileService.uploadImageForStore(storeId, is, true,
					true);
			is.close();
			return Response.status(Status.OK).entity(path).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Path("/store/{storeId}/put")
	public Response uploadImageForStore(@PathParam("storeId") int storeId,
			MultipartFormDataInput multipart) {
		try {
			InputStream is = extractFileStreamFromMultipart(multipart);
			String path = fileService.uploadImageForStore(storeId, is, false,
					true);
			is.close();
			return Response.status(Status.OK).entity(path).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Path("/chain/{chainId}/put")
	public Response uploadImageForChain(@PathParam("chainId") int chainId,
			MultipartFormDataInput multipart) {
		try {
			InputStream is = extractFileStreamFromMultipart(multipart);
			String path = fileService.uploadImageForChain(chainId, is, true);
			is.close();
			return Response.status(Status.OK).entity(path).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Path("/product/{productId}/put/head")
	public Response uploadHeadForProduct(@PathParam("productId") int productId,
			MultipartFormDataInput multipart) {
		try {
			InputStream is = extractFileStreamFromMultipart(multipart);
			String path = fileService.uploadImageForProduct(productId, is,
					true, true);
			is.close();
			return Response.status(Status.OK).entity(path).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Path("/product/{productId}/put")
	public Response uploadImageForProduct(
			@PathParam("productId") int productId,
			MultipartFormDataInput multipart) {
		try {
			InputStream is = extractFileStreamFromMultipart(multipart);
			String path = fileService.uploadImageForProduct(productId, is,
					false, true);
			is.close();
			return Response.status(Status.OK).entity(path).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@Path("/get")
	public InputStream getFileResource(@QueryParam("path") String path)
			throws LoginException, FileSystemException, RepositoryException {
		return fileService.getFileContent(path);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/store/{storeId}/get/paths")
	public Response getImagePathsForStore(@PathParam("storeId") int storeId) {
		try {
			List<String> retList = fileService.getImagePathsForStore(storeId);
			return Response.status(Status.OK).entity(retList).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/product/{productId}/get/paths")
	public Response getImagePathsForProduct(@PathParam("productId") int productId) {
		try {
			List<String> retList = fileService.getImagePathsForProduct(productId);
			return Response.status(Status.OK).entity(retList).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private InputStream extractFileStreamFromMultipart(
			MultipartFormDataInput multipart) throws IOException {
		Map<String, List<InputPart>> multipartFormData = multipart
				.getFormDataMap();
		List<InputPart> fileContentParts = multipartFormData
				.get(FILE_CONTENT_FORM_KEY);
		return fileContentParts.get(0).getBody(InputStream.class, null);
	}
}
