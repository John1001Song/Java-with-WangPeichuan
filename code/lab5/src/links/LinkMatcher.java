package links;

import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for finding links in an html file.
 * Modified by Prof. Karpenko from the assignment of Prof. Engle.
 */
public class LinkMatcher {

	// This regex should match an HTML anchor tag such as
	// <a href = "http://cs.www.usfca.edu" >
	// where the actual hyperlink is captured in a group.
	// See the following link regarding the format of the anchor tag:
	// https://developer.mozilla.org/en-US/docs/Web/HTML/Element/a

	// double check if the \" should have ? after
	public static final String REGEX = "<\\s*[a]\\s*[^>]*?href\\s*=\\s*\"([^>\\s]*?)(#.*)*?\""; // FILL IN regex here
	public static int PORT = 80;
	public static final String INPUT_DIR = "test";
	public static final String SECONDREGEX = "(.*?)/";
	/**
	 * Take an html file and return a list of hyperlinks in that html that
	 * satisfy the following requirements: 1. The list should not contain
	 * duplicates. For the purpose of this assignment, duplicates are either
	 * (a) the links that are the same, except for the fragment.
	 * Example: you should consider these two links as equal
	 * "java/lang/StringBuffer.html#StringBuffer" and
	 * "java/lang/StringBuffer.html#StringBuffer-java.lang.String"
	 * (because they are the same if you remove the fragment).
	 * (b) the links that are different only because of / at the end, like "/calendar" and "/calendar/"
	 * 
	 * 2. Do not include links that take you to the same page (links that start
	 * with the fragment).
	 * 
	 * You are required to use a regular expression to find links (use variable
	 * REGEX defined on top of the class - fill in the actual pattern). You are
	 * required to use classes Pattern and Matcher in this method. Do not use
	 * any other classes or packages (except String, ArrayList, Pattern,
	 * Matcher, BufferedReader etc.)
	 * 
	 * @param filename
	 *            The name of the HTML file.
	 * @return An ArrayList of links
	 */
	public static List<String> findLinks(String filename) {
		List<String> links = new ArrayList<>();
		Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				while (matcher.find()) {
					String res = matcher.group(1);
					if (!res.equals("")) {
						links.add(res);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return links;
	}

	/**
	 * Send an HTTP request to fetch a given resource from the given host.
	 * Return the response as a string.
	 *
	 * @param host
	 *
	 * @param pathAndResource
	 *
	 * @return HTML code in string
	 * */
	public static String fetch(String host, String pathAndResource) {
		StringBuffer stringBuffer = new StringBuffer();

		try (Socket socket = new Socket(host, PORT)) {
			// create a connection to the web server
			// get the output stream from socket
			OutputStream outputStream = socket.getOutputStream();
			// get the input stream from socket
			InputStream inputStream = socket.getInputStream();

			// wrap the input stream to make it easier to read from
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			// create and send request
			String request = getRequest(host, pathAndResource);
			// debug purpose and check if the request is ready
			//System.out.println("Request = " + request);
			outputStream.write(request.getBytes());
			outputStream.flush();

			// receive response (header on)
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuffer.append(line + System.lineSeparator());
				line = bufferedReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("HTTPFetcher::IOException occured during download: " + e.getMessage());
		}

		return stringBuffer.toString();
	}

	/**
	 * A method that creates a GET request for the given host and resource
	 *
	 * @param host
	 * 			- host in String
	 * @param pathResourceQuery
	 * 			- path, resource and query in string
	 *
	 * @return HTTP GET request returned as a string
	 * */
	private static String getRequest(String host, String pathResourceQuery) {
		String request = "GET " + pathResourceQuery + " HTTP/1.1" + System.lineSeparator() // GET
			// request
			+ "Host: " + host + System.lineSeparator() // Host header required for HTTP/1.1
			+ "Connection: close" + System.lineSeparator() // make sure the server closes the connection after fetching
			+ System.lineSeparator();
		return request;
	}

	/**
	 * Take a URL, fetch an html page at this URL (using sockets), and find all
	 * unique hyperlinks on that webpage. The list should not contain
	 * "duplicates" (see the previous comment) or links that take you to the
	 * same page. The difference with the previous method is that it should
	 * fetch the HTML from the server first.
	 * 
	 * @param url
	 * @return An ArrayList of links
	 */
	public static List<String> fetchAndFindLinks(String url) {
		List<String> links = new ArrayList<>();
		URL currentURL = null;
		String host = "";
		String path = "";
		try {
			currentURL = new URL(url);
			host = currentURL.getHost();
			path = currentURL.getPath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String htmlString = fetch(host, path);
		// debug purpose and print the html code
		//System.out.println(htmlString);

		// find links and delete duplicates because of /
		Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
		Pattern pattern1 = Pattern.compile(SECONDREGEX, Pattern.CASE_INSENSITIVE);

		try (BufferedReader bufferedReader = new BufferedReader(new StringReader(htmlString))) {
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				while (matcher.find()) {
					String res = matcher.group(1);
					if (res.substring(res.length()-1).equals("/")) {
						res = res.substring(0, res.length()-1);
					}

					links.add(res);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// clean exactly same duplicates
		Set<String> linksSet = new HashSet<>();
		linksSet.addAll(links);
		links.clear();
		links.addAll(linksSet);

		return links;
	}
}
