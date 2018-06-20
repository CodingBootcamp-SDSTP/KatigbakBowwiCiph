import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Cipher extends HttpServlet
{
	private static String cryptoText = "";
	private static int textShift = 0;
	private static Connection v = null;
	private static Statement stmt = null;
	private static ResultSet res = null;

	private static String encryptText(String text, int shift) {
		String encrypted = "";
		for(int i = 0; i < text.length(); i++) {
			int startdec = (Character.isUpperCase(text.charAt(i))) ? 65 : 97;
			int decval = ((int) text.charAt(i) + shift - startdec) % 26;
			decval += (decval >= 0) ? startdec : startdec+26;
			encrypted += (char) decval;
		}
		return(encrypted);
	}

	public static Connection connectdb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			v = DriverManager.getConnection("jdbc:mysql://localhost/db_projectciph?user=root&" +
			"password=&serverTimezone=UTC&useSSL=false");
		}
		catch(Exception e) {
			v = null;
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
		return(v);
	}

	public void init() throws ServletException {
		encryptText(cryptoText, textShift);
		Connection c = connectdb();
		if(c == null) {
			System.out.println("Error connecting to your MySQL Database...");
		}
		else {
			System.out.println("Connected.");
		}
	}

	public static void readFromMySQLDB(Connection conn) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO tbl_cipher(encryptedTxt, shiftNo) VALUES (?,?);");
			stmt.setString(1, cryptoText);
			stmt.setInt(2, textShift);
			stmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if (res != null) res.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.print("<link rel='stylesheet' type='text/css' href='css/encrypt.css'>");
		out.println("<div class='cell' id='cell1'>");
		out.println("<div>");
				out.println("<form action='./encrypt' method='GET'>");
					out.println("<h3>Text to Encrypt/Decrypt:</h3>");
					out.println("<input required type='text' name='encryptedText' id='textInput'>");
					out.println("<h3>Number of Rotation:</h3>");
					out.println("<input min='-255' max='255' step='1' required type='number' id='num_id' name='shiftNumber'/><br><br>");
					out.println("<input type='submit' value='Submit' id='submit'>");
					out.println("<a href='/Project/data'><p>Click to see all encrypted data</p></a>");
				out.println("</form>");
			out.println("</div>");
		out.println("</div>");
		cryptoText = req.getParameter("encryptedText").replaceAll("[ -+.^:,]", "");
		String aString = req.getParameter("shiftNumber");
		textShift = Integer.parseInt(aString);
		readFromMySQLDB(v);
		try {
			if(cryptoText.matches("[a-zA-Z]+")) {
				int shift = textShift;
				out.println("<div class='cell' id='cell2'>");
					out.println("<div>");
						out.println("<h1>Cipher: </h1>" + "<h2>" + encryptText(cryptoText, shift) + "</h2>");
					out.println("</div>");
				out.println("</div>");
			}
			else {
				out.println("<div class='cell' id='cell2'><div><h1>Text must be letters only.<h1></div></div>");
				return;
			}
		}
		catch(Exception e) {
			out.println("Invalid input: " + e);
		}
	}

	public void destroy() {
		cryptoText = null;
		textShift = 0;
		v = null;
		stmt = null;
		res = null;
	}
}
