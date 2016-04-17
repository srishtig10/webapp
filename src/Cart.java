

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String Email = null;
    private static String Pass = null;
    private static String Page = "Cart";
	private DataSource dataSource;
    private Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException 
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
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
	    synchronized(session) 
	    {
	         Email = (String) session.getAttribute("Email");
	         
	         Pass = (String) session.getAttribute("Pass");
	         
	         
	         Page=request.getRequestURI()+"?"+request.getQueryString();
	         session.setAttribute("Page", Page);
	         
        }
	    try {
			print(response, request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void print(HttpServletResponse response, HttpServletRequest request) throws SQLException, IOException
	{
		connection = (Connection) dataSource.getConnection();
		PrintWriter out = response.getWriter();
	    headerFooter base = new headerFooter(request.getSession());
	    out.println(base.header());
		out.println("<HEAD><TITLE>My Shopping Cart</TITLE></HEAD>");
		out.println(base.banner());
		out.println("<div id=\"cart_title\"><span>My Shopping Cart</span>");
		
		String custid = "Select id from customers where email = '" + Email + "' and password = '" + Pass + "';";
		PreparedStatement ps_custid = (PreparedStatement) connection.prepareStatement(custid);
		ResultSet id = ps_custid.executeQuery();
		int customer_id =0;
		
		if (id.next())
			customer_id = Integer.parseInt(id.getString("id"));
			//out.println("<h2>"+customer_id+"</h2>");
		
		
		int movie_id = Integer.parseInt(request.getParameter("MovieID"));
		String req = request.getParameter("req");
		if (req.equals("Clear"))
		{
			String removeAll = "DELETE from `moviedb`.`cart` where `customer_id` = '" + customer_id + "';";
			PreparedStatement ps_cart_removeAll = (PreparedStatement) connection.prepareStatement(removeAll);
			ps_cart_removeAll.executeUpdate();
		}
		if (movie_id != 0)
		{
			String movie_check = "Select * from cart where movie_id = '" + movie_id + "' and customer_id = '" + customer_id + "';";
			PreparedStatement ps_cart_check = (PreparedStatement) connection.prepareStatement(movie_check);
			ResultSet cart_check = ps_cart_check.executeQuery();
			
			if (cart_check.next() && !req.equals("Delete") && !req.equals("View") && !req.equals("Clear"))
			{
				int qty = Integer.parseInt(request.getParameter("qty"));
				if (req.equals("Add"))
					qty += Integer.parseInt(cart_check.getString("quantity"));
				String update = "update `moviedb`.`cart` set `quantity` = '" + qty + "' where "
						+ "`movie_id` =  '" + movie_id + "' and `customer_id` = '" + customer_id + "';";
				PreparedStatement ps_cart_update = (PreparedStatement) connection.prepareStatement(update);
				ps_cart_update.executeUpdate();
				
			}
			else
			{
				if (req.equals("Add"))
				{
					String movie = "Select * from movies where id = '" + movie_id + "';";
					PreparedStatement ps_movie = (PreparedStatement) connection.prepareStatement(movie);
					ResultSet movies = ps_movie.executeQuery();
					movies.next();
					String insert = "INSERT INTO cart (`title`, `price`, `quantity`, `customer_id`, `movie_id`) "
							+ "VALUES ('" + movies.getString("title") +  "(" + movies.getString("year") + ")', '$15.99', '1', '" + customer_id + "', '" + movie_id + "');";
					PreparedStatement ps_cart_insert = (PreparedStatement) connection.prepareStatement(insert);
					ps_cart_insert.executeUpdate();
					
				}
				else if (req.equals("Delete"))
				{
					String remove = "DELETE from `moviedb`.`cart` where `customer_id` = '" + customer_id + "' and `movie_id` = '" + movie_id + "';";
					PreparedStatement ps_cart_remove = (PreparedStatement) connection.prepareStatement(remove);
					ps_cart_remove.executeUpdate();
					
				}
			}
		}
		
		String query = "Select * from cart where customer_id like '" + customer_id + "'";
		PreparedStatement ps_cart = (PreparedStatement) connection.prepareStatement(query);
		ResultSet cart = ps_cart.executeQuery();
		
		if (cart.next())
		{
			out.println("<a class=\"cart\" style=\"margin-right:10px;font-size:20px;float:right;\" href=\"Cart?MovieID=0&qty=0&req=Clear\">"
			+ "<img src='http://goo.gl/XrNSZm?gdriveurl' height='24' width='24'>Empty Cart</a>"
			+ "<a class=\"cart\" style=\"margin-right:10px;font-size:20px;float:right;\" href=\"CustInfo\">"
			+ "<img src='http://goo.gl/iCLKUa?gdriveurl' height='24' width='24'>Check Out</a></div>");
		out.println("<table  cellpadding=\"10\" id=\"cart_res\"><tr style=\"background-color:#00CCFF;\" align=\"left\">"
					+ "<th>Movie Title</th>"
					+ "<th>Price</th>"
					+ "<th>Quantity</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>");
			
			int iter_form = 0;
			do
			{
				out.println("<tr style='color:brown;'><td>" + cart.getString("title") + "</td>");
				out.println("<td>" + cart.getString("price") + "</td>");
				out.println("<form id=\"form" + iter_form + "\"><td><input type='text' name='qty' value=" + cart.getString("quantity") + "></td>");
				out.println("<td><button class=\"cart_btn\" name='req' type='submit' value='Update'>"
						+ "<img src='http://goo.gl/STfkLN?gdriveurl' height='20' width='20'>Update</button></td>");
				out.println("<td><button class=\"cart_btn\" name='req' type='submit' value='Delete'>"
						+ "<img src='http://goo.gl/ULy6Mw?gdriveurl' height='20' width='20'>Delete</button></td>"
						+ "<input type='hidden' name='MovieID' value='" + cart.getString("movie_id") + "'></form></tr>");
				iter_form++;
			} while(cart.next());
			out.println("</table><br><br><br><br>");
		}
		else
			out.println("</div><div id='cart_title' style='border-radius: 0px 0px 0px 0px;'>"
					+ "<center><span>Your Cart seems empty now. Why don't you have a look at our awesome movie collection?</span>"
					+ "</center></div><br><br><br><br>");
		out.println(base.footer());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
