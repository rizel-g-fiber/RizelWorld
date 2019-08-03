
package com.rizelworld.email;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rizelworld.sms.BulkSmsGateway;

 
@WebServlet("/EmailSendingServlet")
public class EmailSending extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String host;
    private String port;
    private String user;
    private String pass;
    static final Logger LOGGER = Logger.getLogger(EmailSending.class);
    public void init() {
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }
 
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	LOGGER.info(" START :::");
    	LOGGER.debug("**********SERVER *******"+request.getServerName());
    	LOGGER.debug("**********PORT *******"+request.getServerPort());
    	LOGGER.debug("**********CONTEXT PATH *******"+request.getContextPath());
    	LOGGER.debug("**********SESSION *******"+request.getSession().getId());
    	LOGGER.debug("**********AUTH TYPE *******"+request.getAuthType());
    	LOGGER.debug("**********CHARACTER ENCODING *******"+request.getCharacterEncoding());
    	LOGGER.debug("**********LOCAL PORT *******"+request.getLocalPort());
    	LOGGER.debug("**********REMOTE PORT *******"+request.getRemotePort());
    	LOGGER.debug("**********REMOTE HOST *******"+request.getRemoteHost());
    	LOGGER.debug("**********SERVER PORT *******"+request.getServerPort());
    	LOGGER.debug("**********PROTOCOL *******"+request.getProtocol());
    	//****Response Set****
    	String text = "Update successfull"; //message you will recieve 
        String name = request.getParameter("name");
        PrintWriter out = response.getWriter();
        out.println(name + " " + text);
        ///*******Write Back*************
        String recipient = request.getParameter("email");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String tel = request.getParameter("tel");
        String details = request.getParameter("detail");
        String content = request.getParameter("message");
        String welcomeSMSMessage = "Hi " + fname + "," + " Thanks for choosing Rizel G-Fiber NextGenBroadband" + " We have recieved your enquiry." + " Our relationship manager will contact you soon." + "Get ready to feel thrilling super fast internet Experience";
        String resultMessage = "";
        String senderId = "RIZELN";
        String link = "www.rizelworld.com/packages/index.html";
        String welcomeMailSubjectRizel = "Enquiry for Broadband by Mr. " + fname + " " + lname + " Contact @ :" + tel;
        String welcomeMailSubjectClient = "Thanks for Choosing Rizel G-Fiber NextGenBroadband, We are the fastest growing and most reliable home broadband and  Internent services provider of Bhiwadi";
        String adminMail ="rizelbroadband@gmail.com";
        StringBuilder contentBuilder = new StringBuilder();
        try {
            String str;
            BufferedReader in = new BufferedReader(new FileReader("/home/orionoptibeam/Documents/orionoptibeam/contactUsMail/contactUsWelcomMail.html"));
            while ((str = in.readLine()) != null) {
                if (str.contains("{Name}")) {
                    System.out.println("Here Name is replaced by fname lname" + fname + " " + lname);
                    str.replaceAll("Name", fname);
                    System.out.println("After Name replacement with runtime value ::" + str.toString());
                }
                contentBuilder.append(str);
            }
            in.close();
        }
        catch (IOException in) {
            // empty catch block
        }
        String welcomeMailMessage = contentBuilder.toString();
        if (welcomeMailMessage.contains("{Name}")) {
            System.out.println("Here Name is replaced by fname lname" + fname + " " + lname);
            welcomeMailMessage.replace("{Name}", "bhanu");
            System.out.println("After Name replacement with runtime value ::" + welcomeMailMessage.toString());
        }
        try {
            try {
                System.out.println(welcomeMailMessage);
                EmailUtility.sendEmail((String)this.host, (String)this.port, (String)this.user, (String)this.pass, (String)adminMail, (String)welcomeMailSubjectRizel, (String)content);
                LOGGER.info("The Enquiry e-mail was sent successfully to Rizel  "+(String)this.host+" , "+ (String)this.port+" , "+ (String)this.user+" , "+ (String)this.pass+" , "+ (String)this.user+" , "+ (String)welcomeMailSubjectRizel+" , "+ (String)content);
                EmailUtility.sendEmail((String)this.host, (String)this.port, (String)this.user, (String)this.pass, (String)recipient, (String)welcomeMailSubjectClient, (String)welcomeMailMessage);
                LOGGER.info("The Welcome e-mail was sent successfully to  "+(String)this.host+ " , " + (String)this.port+ " , " + (String)this.user+ " , " + (String)this.pass+ " , " + (String)recipient+ " , " + (String)welcomeMailSubjectClient);
                if (tel.length() == 10) {
                    BulkSmsGateway.sendSms((String)tel, (String)welcomeSMSMessage, (String)senderId, (String)link);
                }else{
                	LOGGER.error("Mobile Number is not correct: "+tel);
                }
                resultMessage = "The e-mail was sent successfully";
            }
            catch (Exception ex) {
                ex.printStackTrace();
                resultMessage = "There were an error: " + ex.getMessage();
                LOGGER.error("   "+ex.getMessage());
                request.setAttribute("Message", (Object)resultMessage);
                this.getServletContext().getRequestDispatcher("/index.jsp").forward((ServletRequest)request, (ServletResponse)response);
            }
        }
        finally {
            request.setAttribute("Message", (Object)resultMessage);
            LOGGER.info("SUCCESS: END ");
            this.getServletContext().getRequestDispatcher("/index.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
   }
