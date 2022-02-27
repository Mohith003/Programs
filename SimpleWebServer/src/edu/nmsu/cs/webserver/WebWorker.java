package edu.nmsu.cs.webserver;

/**
 * Web worker: an object of this class executes in its own new thread to receive and respond to a
 * single HTTP request. After the constructor the object executes on its "run" method, and leaves
 * when it is done.
 *
 * One WebWorker object is only responsible for one client connection. This code uses Java threads
 * to parallelize the handling of clients: each WebWorker runs in its own thread. This means that
 * you can essentially just think about what is happening on one client at a time, ignoring the fact
 * that the entirety of the webserver execution might be handling other clients, too.
 *
 * This WebWorker class (i.e., an object of this class) is where all the client interaction is done.
 * The "run()" method is the beginning -- think of it as the "main()" for a client interaction. It
 * does three things in a row, invoking three methods in this class: it reads the incoming HTTP
 * request; it writes out an HTTP header to begin its response, and then it writes out some HTML
 * content for the response content. HTTP requests and responses are just lines of text (in a very
 * particular format).
 * 
 * @author Jon Cook, Ph.D.
 *
 **/

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class WebWorker implements Runnable
{

	private Socket socket;

	/**
	 * Constructor: must have a valid open socket
	 **/
	public WebWorker(Socket s)
	{
		socket = s;
	}

	/**
	 * Worker thread starting point. Each worker handles just one HTTP request and then returns, which
	 * destroys the thread. This method assumes that whoever created the worker created it with a
	 * valid open socket object.
	 **/
	public void run()
	{
		System.err.println("Handling connection...");
		try
		{
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			String reqPath = readHTTPRequest(is);
			writeContent(os, reqPath.substring(1));
			os.flush();
			socket.close();
		}
		catch (Exception e)
		{
			System.err.println("Output error: " + e);
		}
		System.err.println("Done handling connection.");
		return;
	}

	/**
	 * Read the HTTP request header.
	 **/
	private String readHTTPRequest(InputStream is)
	{
		String line;
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		String path = "";
		while (true)
		{
			try
			{
				while (!r.ready())
					Thread.sleep(1);
				line = r.readLine();
				System.err.println("Request line: (" + line + ")");
				if(line.contains("GET")){
					path = line.substring(4, line.indexOf("HTTP") - 1);
				}
				if (line.length() == 0)
					break;
			}
			catch (Exception e)
			{
				System.err.println("Request error: " + e);
				break;
			}
		}
		return path;
	}

	/**
	 * Write the HTTP header lines to the client network connection.
	 * 
	 * @param os
	 *          is the OutputStream object to write to
	 * @param contentType
	 *          is the string MIME content type (e.g. "text/html")
	 **/
	private void writeHTTPHeader(OutputStream os, String contentType, String responseCode) throws Exception
	{
		Date d = new Date();
		DateFormat df = DateFormat.getDateTimeInstance();
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		os.write((responseCode + "\n").getBytes());
		// os.write("Date: ".getBytes());
		// os.write((df.format(d)).getBytes());
		// os.write("\n".getBytes());
		// os.write("Server: Jon's very own server\n".getBytes());
		// os.write("Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT\n".getBytes());
		// os.write("Content-Length: 438\n".getBytes());
		os.write("Connection: close\n".getBytes());
		os.write("Content-Type: ".getBytes());
		os.write(contentType.getBytes());
		os.write("\n\n".getBytes()); // HTTP header ends with 2 newlines
		return;
	}

	/**
	 * Write the data content to the client network connection. This MUST be done after the HTTP
	 * header has been written out.
	 * 
	 * @param os
	 *          is the OutputStream object to write to
	 **/
	private void writeContent(OutputStream os, String path) throws Exception
	{
		try {
			path = "www/" + path;
			System.out.println("Path: " + path);
			File file;
			if(path.endsWith("favicon.ico")){
				file = new File("www/res/acc/favicon.ico");
				if (!file.exists()) throw new Exception("File does not exist! return 404");
				writeHTTPHeader(os, "image/x-icon", "HTTP/1.1 200 OK");
				writeToOs(new FileInputStream(file), os);
			}else {
				if (path.contains(".png")) {
					file = new File(path);
					if (!file.exists()) throw new Exception("File does not exist! return 404");
					writeHTTPHeader(os, "image/png", "HTTP/1.1 200 OK");
					writeToOs(new FileInputStream(file), os);
				} else if (path.contains(".jpeg")) {
					file = new File(path);
					if (!file.exists()) throw new Exception("File does not exist! return 404");
					writeHTTPHeader(os, "image/jpeg", "HTTP/1.1 200 OK");
					writeToOs(new FileInputStream(file), os);
				} else if (path.contains(".gif")) {
					file = new File(path);
					if (!file.exists()) throw new Exception("File does not exist! return 404");
					writeHTTPHeader(os, "image/gif", "HTTP/1.1 200 OK");
					writeToOs(new FileInputStream(file), os);
				} else {
					Scanner htmlScanner = getLocalHTMLScanner(path);
					writeHTTPHeader(os, "text/html", "HTTP/1.1 200 OK");
					while (htmlScanner.hasNext()) {
						String text = htmlScanner.nextLine();
						if (text.contains("cs371")) {
							SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
							text = text.replace("<cs371date>", dateFormatter.format(new Date()));
							text = text.replace("<cs371server>", "Mohith's server");
						}
						os.write(text.getBytes());
					}
				}
			}
		}catch(Exception e){
			writeHTTPHeader(os, "text/html", "HTTP/1.1 404 Not Found");
		}
	}

	private Scanner getLocalHTMLScanner(String path) throws Exception {
		System.err.println("Getting file: " + path);
		File html = new File(path);
		System.err.println("Getting file: " + html.getAbsolutePath());
		return new Scanner(html);
	}

	private void writeToOs(InputStream fileInputStream, OutputStream os) throws IOException {
		int byteRead = -1;

		while ((byteRead = fileInputStream.read()) != -1) {
			os.write(byteRead);
		}
	}

} // end class
