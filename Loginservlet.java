package HCMS;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Loginservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 String dbdriver,dburl,dbuser,dbpass;
	    
	    
	    @Override
		public void init(ServletConfig config) throws ServletException {
			super.init(config);
			ServletContext ctx=getServletContext();
			String dbdriver=ctx.getInitParameter("dbdriver");
			String dburl=ctx.getInitParameter("dbuser");
			String dbuser=ctx.getInitParameter("dbpass");
			String dbpass=ctx.getInitParameter("dbpass");

		}

    
	
    public Loginservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();

		 
		try {
			Class.forName(dbdriver);
			Connection con = DriverManager.getConnection(dburl,dbuser,dbpass);
			String query="select * from healthcare where loginid=? and password=?";
        	String user=request.getParameter("user");
    		String pass=request.getParameter("pass");
    		PreparedStatement st=con.prepareStatement(query);
    		st.setString(1, user);
    		st.setString(2,pass);
    		
    		 ResultSet rs=st.executeQuery();
    		 if(rs.next()) {
    			 
    			 HttpSession session=request.getSession();
    			 session.setAttribute("accno",request.getParameter("username"));
    			 RequestDispatcher rd=request.getRequestDispatcher("Main.html");
    			 out.print("<h1>Welcome "+rs.getString("fullname")+"</h1>");
    		 rd.include(request, response);

    			}
    		 else {
    			
    			RequestDispatcher rd=request.getRequestDispatcher("invalid.html");
 				rd.forward(request, response);
    		 }
        	   st.close();
        	   con.close();
        		   
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

}

}
