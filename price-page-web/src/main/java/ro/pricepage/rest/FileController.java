package ro.pricepage.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	@Path("/store/{storeId}/put/head")
	public Response uploadHeadForStore(@PathParam("storeId") int storeId,
			MultipartFormDataInput multipart){
		try{
			InputStream is = extractFileStreamFromMultipart(multipart);
			fileService.uploadImageForStore(1, is, true, true);
			is.close();
		}
		catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.OK).build();
	}
	
	private InputStream extractFileStreamFromMultipart(MultipartFormDataInput multipart) throws IOException{
		Map<String, List<InputPart>> multipartFormData =  multipart.getFormDataMap();
		List<InputPart> fileContentParts = multipartFormData.get(FILE_CONTENT_FORM_KEY);
		return fileContentParts.get(0).getBody(InputStream.class, null);
	}
}
