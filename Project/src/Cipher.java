import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Cipher extends HttpServlet
{
	private static String cryptoText = "";
	private static int textShift = 0;

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

	public void init() throws ServletException {
		encryptText(cryptoText, textShift);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<form action='./encrypt' method='GET'>");
			out.println("<h3>Text to encrypt:</h3>");
			out.println("<input required type='text' name='encryptedText' id='textInput'>");
			out.println("<h3>Number of shifts:</h3>");
			out.println("<input min='-255' max='255' step='1' required type='number' id='num_id' name='shiftNumber'/>");
			out.println("<input type='submit' value='Submit' id='submit'>");
		out.println("</form>");
		cryptoText = req.getParameter("encryptedText");
		String aString = req.getParameter("shiftNumber");
		textShift = Integer.parseInt(aString);
		try {
			if(cryptoText.matches("[a-zA-Z]+")) {
				int shift = textShift;
				out.println("<h1>Text : </h1>" + "<h3>"+ cryptoText + "</h3>");
				out.println("Shift : " + shift);
				out.println("Cipher: " + encryptText(cryptoText, shift));
			}
			else {
				out.println("Text must be letters only.");
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
	}
}
