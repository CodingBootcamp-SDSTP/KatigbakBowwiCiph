import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.RequestDispatcher;

public class Login extends HttpServlet
{
	private static Statement stmt = null;
	private static Connection conn = null;
	private static ResultSet aResultSet = null;

	public static Connection connectdb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/db_projectciph?user=root&" +
			"password=&serverTimezone=UTC&useSSL=false");
		}
		catch(Exception e) {
			conn = null;
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return(conn);
	}

	public Boolean readFromUsertbl(Connection connection, String name, String pass) {
		Boolean stat = false;
		Statement stmt = null;
		try {
			PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM tbl_users WHERE username=(?) AND password=(?);");
			prepStatement.setString(1,name);
			prepStatement.setString(2,pass);
			ResultSet aResultSet = prepStatement.executeQuery();
			stat = aResultSet.next();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
			try { if (aResultSet != null) aResultSet.close(); } catch (Exception e) {};
		}
		return(stat);
	}

	public void init() throws ServletException {
		Connection c = connectdb();
		if(c == null) {
			System.out.println("Error connecting to your MySQL Database...");
		}
		else {
			System.out.println("Connected.");
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		try {
			PrintWriter out = res.getWriter();
			String username = req.getParameter("name");
			String password = req.getParameter("password");
			if(readFromUsertbl(conn, username, password)){
				RequestDispatcher reqDispatcher = req.getRequestDispatcher("/encrypt");
				reqDispatcher.forward(req,res);
			}
			else {
				out.print("Sorry username or password error");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		stmt = null;
		conn = null;
		aResultSet = null;
	}
}
