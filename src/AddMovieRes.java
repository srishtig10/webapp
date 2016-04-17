
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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
 * Servlet implementation class AddMovieRes
 */
@WebServlet("/AddMovieRes")
public class AddMovieRes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataSource dataSource;
	Connection connection;

	public void init() throws ServletException {
		try {
			// Get DataSource
			Context initContext = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/moviedb");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		PrintWriter out = response.getWriter();
		String message = request.getParameter("message");
		HttpSession session = request.getSession();

		// if (message != null)
		synchronized (session) {
			session.setAttribute("Email", null);

		}

		try {
			Connection connection = dataSource.getConnection();
			// Statement select = connection.createStatement();

			
			
			  String title = request.getParameter("m_title");
			String year = request.getParameter("m_year");
			String director = request.getParameter("dir");
			String firstName = request.getParameter("fn");
			String lastName = request.getParameter("lname");
			String genre = request.getParameter("genre");

			

			CallableStatement cs =  connection.prepareCall("{CALL add_movie11(?,?,?,?,?,?,?,?)}");

			
			cs.setNull(1, Types.NULL);
			cs.setString(2, title);
			cs.setString(3, year);
			cs.setString(4, director);
			cs.setString(5, firstName);
			cs.setString(6, lastName);
			cs.setString(7, genre);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.execute();
			request.setAttribute("Message", cs.getString(8));
			RequestDispatcher rd = request.getRequestDispatcher("/AddMovie");
			rd.forward(request, response);
			

		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
