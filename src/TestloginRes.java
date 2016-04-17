

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestloginRes
 */
@WebServlet("/TestloginRes")
public class TestloginRes extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private Connection connection;
	private static String Email = null;
	private static String Pass = null;
	private static String Page = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	 public String getServletInfo()
	    {
	       return "Servlet connects to MySQL database and displays result of a SELECT";
	    }
	
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		
		PrintWriter out = response.getWriter();
		String message = request.getParameter("message");
		HttpSession session = request.getSession();
		// if (message != null)
		synchronized(session) 
		{
			session.setAttribute("Email", null);
			
		}
				
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
						response.sendRedirect("Main");
					}	


				}else{
					String mess="Username or password incorrect. Please try Again!";
					request.setAttribute("Message",mess);
					RequestDispatcher rd = request.getRequestDispatcher("Testlogin");
					rd.forward(request,response); 
				}
				
				
				/*String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
				System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
				// Verify CAPTCHA.
				boolean valid = VerifyUtils.verify(gRecaptchaResponse);
				if (!valid) {
					//String errorString = "Captcha invalid!";
					out.println("<HTML>" +
							"<HEAD><TITLE>" +
							"MovieDB: Error" +
							"</TITLE></HEAD>\n<BODY>" +
							"<P><center>Recaptcha WRONG!!!! </center></P></BODY></HTML>");
					return;
				}
				*/
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

	}
		
		
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			//(request, response);
			
			
			
		}
		
		
		
	}


