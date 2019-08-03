package com.rizelworld.hitCounter;
//Import required java libraries
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rizelworld.email.EmailSending;
@WebServlet("/index1.jsp")
public class hitCounter extends HttpServlet {
	Properties prop = new Properties(); 
	//Instance variable used for counting hits on this servlet
	static final Logger LOGGER = Logger.getLogger(hitCounter.class);

	private String hit_Counter;
	/**
	* init method just initializes the hitCounter to zero
	*/
	 
	public void init() throws ServletException {
	//iHitCounter = 0;
		
	try{
	//prop.load(new FileInputStream("C:/Bhanu/Orion PD/eclipse-3/Logs/hitCounter.properties"));
	prop.load(new FileInputStream("/home/orionoptibeam/Documents/orionoptibeam/hitCounter.properties"));
	hit_Counter =prop.getProperty("hit_Counter");
	
	}catch (IOException ex){
		ex.printStackTrace();
	}
	}
	 
	/**
	* Work horse of this servlet
	* Displays a welcome msg along with hitCounter
	* Increments the hitCounter
	*/
	 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		

		try{
			//prop.load(new FileInputStream("C:/Bhanu/Orion PD/eclipse-3/Logs/hitCounter.properties"));
			prop.load(new FileInputStream("/home/orionoptibeam/Documents/orionoptibeam/hitCounter.properties"));
			hit_Counter =prop.getProperty("hit_Counter");
			
			}catch (IOException ex){
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				ex.printStackTrace();
			}
	PrintWriter out =  response.getWriter();
	out.println("<h2>Welcome to SimpleCounterServlet.java</h2>");
	
	int hitCounter = Integer.parseInt(hit_Counter);
	++hitCounter;
	out.println("Hits on this servlet so far: "+ (hitCounter));
	request.setAttribute("message", hitCounter);
    request.getRequestDispatcher("/index.jsp").forward(request, response);

    try {
        //set the properties value
        prop.setProperty("hit_Counter", Integer.toString(hitCounter));
        //save properties to project root folder
        //prop.store(new FileOutputStream("C:/Bhanu/Orion PD/eclipse-3/Logs/hitCounter.properties"), null);
        prop.store(new FileOutputStream("/home/orionoptibeam/Documents/orionoptibeam/hitCounter.properties"), null);
        
    } catch (IOException ex) {
    	request.getRequestDispatcher("/index.jsp").forward(request, response);
        ex.printStackTrace();
    }
	}
	 
	/**
	* Passes the call to doGet method. 
	* Thus, works similar to doGet
	*/
	 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	doGet(request, response);
	}
	}