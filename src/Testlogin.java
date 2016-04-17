
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


/**
 * Servlet implementation class index
 */
@WebServlet("/Testlogin")
public class Testlogin extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	private DataSource dataSource;
	private Connection connection;
	private static String Email = null;
	private static String Pass = null;
	private static String Page = null;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void init() throws ServletException
	{
		try {
			// Get DataSource
			Context initContext  = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/moviedb");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String message = request.getParameter("message");
		HttpSession session = request.getSession();
		// if (message != null)
		synchronized(session) 
		{
			session.setAttribute("Email", null);
			//	 else
			//		 request.getSession().setAttribute("User", User);
			headerFooter base = new headerFooter(session);
			//out.println(base.header());
			//out.println(base.banner());
		}

		out.println("<html>"+"<head>"+"<meta charset="+"ISO-8859-1"+">"+"<title> FABFLIX - LOGIN </title><script src='https://www.google.com/recaptcha/api.js'></script>"+"<link rel="+"stylesheet"+" type="+"text/css"+" href="+"design.css"+">"+
				"<body id="+"stylemain"+" link ="+"white"+">"+"<div align="+"center"+">"+"<table width="+"998"+" border="+"0"+" cellspacing="+"0"+" cellpadding="+"0"+">"+"<tr>"+"<td height="+"10"+">"+"&nbsp;</td>"+"</tr>");
		out.println("<tr>"+"<td height="+"10"+">&nbsp;</td>"+"</tr>"+"<tr><td><div align="+"center"+"><h1><strong><center><font color="+"white"+">FABFLIX - EXPLORE THE WORLD OF MOVIES</center></strong></h1></font>"+"&nbsp;<br/><br><br><br>");


		//out.println("<HEAD><TITLE>Login</TITLE><script src='https://www.google.com/recaptcha/api.js'></script><body>");
		
		out.println("<div id='login'><div style='float:left;width:35%'>"
				+ "<center>"
				+ "<div style='float:right;width:55%'><H2 ALIGN=\"CENTER\">Login</H2><FORM =\"/Fabflix/\" METHOD=\"POST\"></center></div>"
				+ "<center> Emailid: <INPUT id='login_field' TYPE='TEXT' NAME=\"emailid\"><BR><Br> Password: <INPUT id='login_field' TYPE=\"PASSWORD\" NAME=\"pswd\"></center><BR><BR><BR>");
		
		//out.println("<div class="+"g-recaptcha"+" data-sitekey="+"6Le9ERgTAAAAANpfh7aQuFCZ5_GN5Mn6rShzj49M"+"></div>");
		out.println(" <CENTER><button class='login_btn' type='submit' style='font-size:20px;width:60%;'>"
				+ "Confirm</button></center></div></div></form>");


		if(message !=null)
			out.println("<br><div><center><span style=\"color:red;fonts-size:50px;font-weight:bold;\">" + message + "</span></center></div></body></html>");
		
		try
		{
			connection = (Connection) dataSource.getConnection();
			Statement statement = connection.createStatement();
			Email =request.getParameter("emailid");
			Pass =request.getParameter("pswd");
			
			if(Email !=null && Pass !=null)
			{
				String query = "SELECT * from customers where email='"+ Email +"' AND password='"+ Pass +"';";

				// Perform the query
				ResultSet rs = statement.executeQuery(query);

				// Iterate through each row of rs
				//    if(message !=null)
				if (rs.next())
				{

					synchronized(session) 
					{
						session.setAttribute("Email", Email);
						session.setAttribute("Pass", Pass);
						response.sendRedirect("Main");;


					}	


				}else{
					String mess="Username or password incorrect. Please try Again!";
					out.println("<h4>"+mess+"</h4>"); 
				}
				rs.close();
				statement.close();
				connection.close();
			}
		}
		catch (SQLException ex) {
			while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  // end while
		}  // end catch SQLException

		/*  catch(java.lang.Exception ex)
		        {
		            out.println("<HTML>" +
		                        "<HEAD><TITLE>" +
		                        "MovieDB: Error" +
		                        "</TITLE></HEAD>\n<BODY>" +
		                        "<P>Java Lang Exception in doGet: " +
		                        ex.getMessage() + "</P></BODY></HTML>");
						                return;
						            } */
		out.close();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub  
		doPost(request,response);
	} 

}
