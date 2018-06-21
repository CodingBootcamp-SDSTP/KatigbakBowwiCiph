import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Deciph extends HttpServlet
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

	public void init() throws ServletException {
		Connection c = connectdb();
		if(c == null) {
			System.out.println("Error connecting to your MySQL Database...");
		}
		else {
			System.out.println("Connected.");
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
		out.println("<decrypt>");
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet aResultSet = stmt.executeQuery("SELECT * FROM tbl_decipher;");
			while(aResultSet.next()) {
				String[] str = {
					aResultSet.getString("id"),
					aResultSet.getString("decryptedTxt"),
					aResultSet.getString("shiftNo")
				};
				for (int i = 0; i < str.length-2; i++) {
					out.print("<data id= '" + str[0] + "' decryptedTxt= '" + str[1] + "' shiftNo= '" + str[2] + "'></data>" + "\n");
				}
			}
			out.println("</decrypt>");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
			try { if (aResultSet != null) aResultSet.close(); } catch (Exception e) {};
		}
	}

	public void destroy() {
		stmt = null;
		conn = null;
		aResultSet = null;
	}
}
