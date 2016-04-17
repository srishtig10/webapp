import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class AddStarRes
 */
@WebServlet("/AddStarRes")
public class AddStarRes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private Connection connection;
	private static String Page = null;

	/**
	 * @see HttpServlet#HttpServlet()
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		String message = request.getParameter("message");


		try
		{

			connection = (Connection) dataSource.getConnection();
			Statement select = connection.createStatement();
			String fname = request.getParameter("fn");
			String lname = request.getParameter("ln");
			String dob = request.getParameter("dob");
			String photourl = request.getParameter("purl");
			
			if(dob.equals(""))
			{
				dob="0001-01-01";
			}



			if (fname.equals("")) {
				String sql1 = "insert into stars (first_name,last_name,dob,photo_url) values (''," + "'" + lname + "'"
						+ "," + "'" + dob + "'" + "," + "'" + photourl + "'" + ")";
				select.executeUpdate(sql1);
			}
			// if last_name is empty, insert first_name as last_name
			else if (lname.equals("") && !fname.equals("")) {
				String sql2 = "insert into stars (first_name,last_name,dob,photo_url) values (''," + "'" + fname + "'"
						+ "," + "'" + dob + "'" + "," + "'" + photourl + "'" + ")";
				select.executeUpdate(sql2);
			}
			// both not empty
			else {
				String sql3 = "insert into stars (first_name,last_name,dob,photo_url) values ('" + fname + "'," + "'"
						+ lname + "'" + "," + "'" + dob + "'" + "," + "'" + photourl + "'" + ")";
				select.executeUpdate(sql3);
			}

			if(fname.equals("")&&lname.equals("")&& photourl.equals(""))
			{
				String mess="Please enter valid details!";
				request.setAttribute("Message",mess);
				RequestDispatcher rd = request.getRequestDispatcher("/AddStar");
				rd.forward(request,response);

			}
			else
			{
				String mess="Successfully inserted!";
				request.setAttribute("Message",mess);
				RequestDispatcher rd = request.getRequestDispatcher("/AddStar");
				rd.forward(request,response);
			}

		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  // end while
		}  // end catch SQLException

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
