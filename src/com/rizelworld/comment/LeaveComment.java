package com.rizelworld.comment;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rizelworld.email.EmailSending;
import com.rizelworld.email.EmailUtility;
import com.rizelworld.sms.BulkSmsGateway;

/**
 * Servlet implementation class LeaveComment
 */
@WebServlet("/wp-comments-post.php")
public class LeaveComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String host;
    private String port;
    private String user;
    private String pass;
    
    static final Logger LOGGER = Logger.getLogger(LeaveComment.class);
	   public void init() {
	        // reads SMTP server setting from web.xml file
	        ServletContext context = getServletContext();
	        host = context.getInitParameter("host");
	        port = context.getInitParameter("port");
	        user = context.getInitParameter("user");
	        pass = context.getInitParameter("pass");
	    }  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaveComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in Leavel A Commonet doGet");
		System.out.println(" START :::");
    	System.out.println("**********SERVER *******"+request.getServerName());
    	System.out.println("**********PORT *******"+request.getServerPort());
    	System.out.println("**********CONTEXT PATH *******"+request.getContextPath());
    	System.out.println("**********SESSION *******"+request.getSession().getId());
    	System.out.println("**********AUTH TYPE *******"+request.getAuthType());
    	System.out.println("**********CHARACTER ENCODING *******"+request.getCharacterEncoding());
    	System.out.println("**********LOCAL PORT *******"+request.getLocalPort());
    	System.out.println("**********REMOTE PORT *******"+request.getRemotePort());
    	System.out.println("**********REMOTE HOST *******"+request.getRemoteHost());
    	System.out.println("**********SERVER PORT *******"+request.getServerPort());
    	System.out.println("**********PROTOCOL *******"+request.getProtocol());
    	System.out.println("**********isSECURE *******"+request.isSecure());
    	System.out.println("**********LOCAL ADD *******"+request.getLocalAddr());
    	System.out.println("**********METHOD *******"+request.getMethod());
    	System.out.println("**********PROTOCOL *******"+request.getProtocol());
    	System.out.println("**********QUERY STRING *******"+request.getQueryString());
    	System.out.println("**********IS SECURE *******"+request.isSecure());
    	System.out.println("*********HEADER NAME *******"+request.getHeaderNames());
    	System.out.println("**********SERVLET NAME *******"+request.getServletPath());
    	System.out.println("**********PRINCIPAL*******"+request.getUserPrincipal());
    	 
          
    	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in Leavel A Commonet doPost");
		
		System.out.println(" START :::");
    	System.out.println("**********SERVER *******"+request.getServerName());
    	System.out.println("**********PORT *******"+request.getServerPort());
    	System.out.println("**********CONTEXT PATH *******"+request.getContextPath());
    	System.out.println("**********SESSION *******"+request.getSession().getId());
    	System.out.println("**********AUTH TYPE *******"+request.getAuthType());
    	System.out.println("**********CHARACTER ENCODING *******"+request.getCharacterEncoding());
    	System.out.println("**********LOCAL PORT *******"+request.getLocalPort());
    	System.out.println("**********REMOTE PORT *******"+request.getRemotePort());
    	System.out.println("**********REMOTE HOST *******"+request.getRemoteHost());
    	System.out.println("**********SERVER PORT *******"+request.getServerPort());
    	System.out.println("**********PROTOCOL *******"+request.getProtocol());
    	System.out.println("**********isSECURE *******"+request.isSecure());
    	System.out.println("**********LOCAL ADD *******"+request.getLocalAddr());
    	System.out.println("**********METHOD *******"+request.getMethod());
    	System.out.println("**********PROTOCOL *******"+request.getProtocol());
    	System.out.println("**********QUERY STRING *******"+request.getQueryString());
    	System.out.println("**********IS SECURE *******"+request.isSecure());
    	System.out.println("*********HEADER NAME *******"+request.getHeaderNames());
    	System.out.println("**********SERVLET NAME *******"+request.getServletPath());
    	System.out.println("**********PRINCIPAL*******"+request.getUserPrincipal());
    	  String name = request.getParameter("author");
          String email = request.getParameter("email");
          String comment = request.getParameter("comment");
          String referer = request.getHeader("Referer");
          System.out.println("**********REFERER*******"+referer);
          String content = comment+"\n"+referer;
          response.sendRedirect(referer);
          String welcomeMailSubjectRizel = "Hello Rizel !You have one comment by Mr. " + name + " Contact @ :" + email;
          String welcomeMailSubjectClient = "Thanks for your valuble feedback !! One of us will conntact you soon.";
          String adminMail ="rizelbroadband@gmail.com";
          String welcomeMailMessage = "";
          String resultMessage ="";
          try {
              try {
                  System.out.println(welcomeMailMessage);
                  EmailUtility.sendEmail((String)this.host, (String)this.port, (String)this.user, (String)this.pass, (String)adminMail, (String)welcomeMailSubjectRizel, (String)content);
                  LOGGER.info("The Leave-A-Comment e-mail was sent successfully to Rizel  "+(String)this.host+" , "+ (String)this.port+" , "+ (String)this.user+" , "+ (String)this.pass+" , "+ (String)this.user+" , "+ (String)welcomeMailSubjectRizel+" , "+ (String)comment);
                  EmailUtility.sendEmail((String)this.host, (String)this.port, (String)this.user, (String)this.pass, (String)email, (String)welcomeMailSubjectClient, (String)welcomeMailMessage);
                  LOGGER.info("The Leave-A-Comment response reverted to  "+(String)this.host+ " , " + (String)this.port+ " , " + (String)this.user+ " , " + (String)this.pass+ " , " + (String)email+ " , " + (String)welcomeMailSubjectClient);
              }
              catch (Exception ex) {
                  ex.printStackTrace();
                  resultMessage = "There were an error: " + ex.getMessage();
                  LOGGER.error("   "+ex.getMessage());
                  
              }
          }
          finally {
              request.setAttribute("Message", (Object)resultMessage);
              LOGGER.info("SUCCESS: END ");
             
          }
	}

}
