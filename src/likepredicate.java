

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class likepredicate
 */
@WebServlet("/reports/like-predicate")
public class likepredicate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		headerFooter base = new headerFooter(request.getSession());
		 PrintWriter out = response.getWriter();
		 out.println(base.header());
		 out.println("<TITLE>" +"Like-predicate" +
               "</TITLE></HEAD>");
		 
		 out.println("<p>We have used 'like' in the files 1. Movie.java 2. advSearchRes.java and 3.Movielist.java to search for movies/titles/stars/genres etc which have names similar to the ones entered by the user. This helps us in displaying all the posssible set of movies as requested by the user. </p>");
		 out.println(base.banner());
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
