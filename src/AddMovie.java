

import java.io.IOException;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;

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
 * Servlet implementation class AddMovie
 */
@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//private DataSource dataSource;
	//private Connection connection;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/*public void init() throws ServletException
	{
		try {
			// Get DataSource
			Context initContext  = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/moviedb");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}*/


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		String message = request.getParameter("message");
		HttpSession session = request.getSession();
		
		 //if (message != null)
		//synchronized(session) 
		//{
			//session.setAttribute("Email", null);
			//	 else
			//		 request.getSession().setAttribute("User", User);
			//headerFooter base = new headerFooter(session);
			//out.println(base.header());
			//out.println(base.banner());
		//}
		
		out.println("<html>"+"<head>"+"<meta charset="+"ISO-8859-1"+">"+"<title> FABFLIX - ADD MOVIES </title>"+"<link rel="+"stylesheet"+" type="+"text/css"+" href="+"design.css"+">"+
				"<body id="+"stylemain"+" link ="+"white"+">"+"<div align="+"center"+">"+"<table width="+"998"+" border="+"0"+" cellspacing="+"0"+" cellpadding="+"0"+">"+"<tr>"+"<td height="+"10"+">"+"&nbsp;</td>"+"</tr>");
		out.println("<tr>"+"<td height="+"10"+">&nbsp;</td>"+"</tr>"+"<tr><td><div align="+"center"+"><h1><strong><center><font color="+"white"+">FABFLIX - EXPLORE THE WORLD OF MOVIES</center></strong></h1></font>"+"&nbsp;<br/><br><br><br>");

		String s = "<div align=\"center\">"+
				"<fieldset style=\"width: 500px\" class=\"field\"><form action="+"AddMovieRes"+">"+
				"<legend><font color= "+"white"+">"+"Enter Following Details </legend></font>"+
				"<table width=\"400\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"font-size: small\">"+
				"<tr>"+
				"<td width=\"15\">&nbsp;</td>"+
				"<td>&nbsp;</td>"+
				"<td>&nbsp;</td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\">&nbsp;</td></td>"+
				"<td width=\"145\"><div align=\"left\">Title:</div></td>"+
				"<td><input name=\"m_title\" type=\"text\" size=\"42\" maxlength=\"100\" /></td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\">&nbsp;</td>"+
				"<td width=\"145\"><div align=\"left\">Year:</div></td>"+
				"<td><input name=\"m_year\" type=\"text\" size=\"42\" maxlength=\"4\" /></td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\">&nbsp;</td>"+
				"<td width=\"145\"><div align=\"left\">Director:</div></td>"+
				"<td><input name=\"dir\" type=\"text\" size=\"42\" maxlength=\"64\" /></td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\">&nbsp;</td>"+
				"<td width=\"145\"><div align=\"left\">Star First Name:</div></td>"+
				"<td><input name=\"fn\" type=\"text\" size=\"42\" maxlength=\"32\" /></td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<tr>"+
				"<td width=\"15\">&nbsp;</td>"+
				"<td width=\"145\"><div align=\"left\">Star Last Name:</div></td>"+
				"<td><input name=\"lname\" type=\"text\" size=\"42\" maxlength=\"32\" /></td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<tr>"+
				"<td width=\"15\">&nbsp;</td>"+
				"<td width=\"145\"><div align=\"left\">Genre:</div></td>"+
				"<td><input name=\"genre\" type=\"text\" size=\"42\" maxlength=\"32\" /></td>"+
				"</tr>"+
				"<tr>"+
				"<td width=\"15\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td width=\"145\"><div class=\"tinyspacer\">&nbsp;</div></td>"+
				"<td><div class=\"tinyspacer\">&nbsp;</div></td>"+
				
				"</tr></table><br>"+"<CENTER><button class='login_btn' type='submit' style='font-size:20px;width:30%;'>"
				+ "Submit</button></center></div></div></form></fieldset>";


		out.println(s);
		String message1 = (String)request.getAttribute("Message");
		if(message1 != null){
			out.println("<center><h3>"+message1+"</h3></center>");
		}
	}
}
