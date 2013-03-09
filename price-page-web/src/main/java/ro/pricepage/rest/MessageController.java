package ro.pricepage.rest;

import ro.pricepage.json.dto.ContactMessageDTO;
import ro.pricepage.json.dto.ProposalMessageDTO;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Stateless
@Path("/mail")
public class MessageController
{
//    @Resource(mappedName = "java:jboss/mail/PricePage")
//    private Session mailSession;
//
//    @POST
//    @Path("/contact")
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response addContactMessage(ContactMessageDTO message){
//        try{
//            if(message.getEmail() == null || message.getEmail().equals("")) throw new Exception("Sender email address must not be null");
//            if(message.getMessage() == null || message.getMessage().equals("")) throw new Exception("Message to send must not be null");
//
//            MimeMessage m = new MimeMessage(mailSession);
//
//            Address from = new InternetAddress(message.getEmail());
//            Address to = new InternetAddress("pagina.preturilor@gmail.com");
//
//            m.setFrom(from);
//            m.setRecipient(Message.RecipientType.TO, to);
//            m.setSubject("contact@PricePage");
//            m.setSentDate(new Date());
//            m.setContent(message.getName() + "(" + message.getPhone() + ")" + message.getEmail() + " a scris: \n" + message.getMessage(), "text/plain");
//
//            Transport.send(m);
//
//            return Response.status(Response.Status.OK).build();
//        } catch (Exception e){
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
//        }
//    }
//
//    @POST
//    @Path("/proposal")
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_XML})
//    public Response addProposalMessage(ProposalMessageDTO proposal){
//        try{
//            if(proposal.getStoreName() == null || proposal.getStoreName().equals("")) throw new Exception("store name cannot be null");
//            if(proposal.getAddress() == null || proposal.getAddress().equals("")) throw new Exception("address cannot be null");
//
//            MimeMessage m = new MimeMessage(mailSession);
//
//            Address from = new InternetAddress("proposal@pricepage.ro");
//            Address to = new InternetAddress("pagina.preturilor@gmail.com");
//
//            m.setFrom(from);
//            m.setRecipient(Message.RecipientType.TO, to);
//            m.setSubject("Propunere adaugare magazin");
//            m.setSentDate(new Date());
//
//            StringBuilder sb = new StringBuilder();
//            sb.append("Numele magazinului: ").append(proposal.getStoreName()).append("\n")
//              .append("Categoria magazinului: ").append(proposal.getStoreType()).append("\n")
//              .append("Adresa: ").append(proposal.getAddress()).append("\n")
//              .append("Telefon: ").append(proposal.getPhone()).append("\n")
//              .append("Tipuri produse: ").append(proposal.getCategory());
//
//            m.setContent(sb.toString(), "text/plain");
//
//            Transport.send(m);
//
//            return Response.status(Response.Status.OK).build();
//        } catch (Exception e){
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
//        }
//    }
}
