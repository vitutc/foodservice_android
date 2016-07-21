package br.com.leomarvic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.os.Environment;
import android.util.Log;

public class HTTPHandler {
	
	public static final String TAG = "HTTPHandler";
	public static final String PATH_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath()
						+"/br.com.leomarvic/";
	public static final String PATH_IMAGES = PATH_ROOT+"images/";
	public static final String URL_ROOT = "http://146.164.69.2/leomarvic/";
	public static final String XML_FILE_NAME = "main.xml";
	
	/**
	* Sends an HTTP GET request to a url
	*
	* @param endpoint - The URL of the server. (Example: " http://www.yahoo.com/search")
	* @param requestParameters - all the request parameters (Example: "param1=val1&param2=val2"). Note: This method will add the question mark (?) to the request - DO NOT add it yourself
	* @return - The response from the end point
	*/
	private String sendGetRequest(String endpoint, String requestParameters, String outFile)
	{
	String result = null;
	if (endpoint.startsWith("http://"))
	{
		// Send a GET request to the servlet
		try
		{
			// Send data
			String urlStr = endpoint;
			if (requestParameters != null && requestParameters.length () > 0)
			{
				urlStr += "?" + requestParameters;
			}
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection ();
			

			//If no outFile specified, get as string, else get as binary data
			if(outFile.equals(""))
			{
				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null)
				{
					sb.append(line);
				}
				rd.close();
				result = sb.toString();
			}
			else
			{
				// Get the response
				InputStream rd = conn.getInputStream();
				StringBuffer sb = new StringBuffer();
				int line;

				OutputStream out = new FileOutputStream(outFile);

				while ((line = rd.read()) != -1)
				{
					out.write(line);
					sb.append(line);
				}
			  out.close();
			rd.close();
			result = sb.toString();
			}

		} catch (Exception e)
		{
		e.printStackTrace();
		}
	}
	return result;
	}
	
	HTTPHandler()
	{
		//Try to create directories used by App: PATH_IMAGES
 	   File imgDir = new File(PATH_IMAGES);
 	   
 	   //If directory doesn't exists, try to create it
 	   if(!imgDir.exists())
 	   {
			if (!imgDir.mkdirs()) {
				Log.e(TAG, "Create dir failed: " + imgDir );
				return;
			}
 	   }
	}
	
	public void getImage(String imageID) {  //this is the downloader method
		//Get image from http get
		sendGetRequest(URL_ROOT+"getImage","imageID="+imageID,PATH_IMAGES+imageID);
}

	public void getMainXML()
	{
		//Get main XML from server via http get
		sendGetRequest(URL_ROOT+"getMainXML","",PATH_ROOT+XML_FILE_NAME);
	}
	
	public String getUserBalance(String user_name)
	{
		//Get user Balance from HTTP GET call
		return sendGetRequest(URL_ROOT+"getUserBalance","userName="+user_name,"");
	}
	
	public String setUserBalance(String user_name, float new_balance)
	{
		//Set user Balance from HTTP GET call
		return sendGetRequest(URL_ROOT+"setUserBalance","userName="+user_name+"&newBalance="+new_balance,"");
	}

	public String checkUserPassword(String user_name, String raw_password)
	{
		//Generate HEX MD5 of raw password
		String hash_pass = MD5Hex.MD5Hex(raw_password);
		
		//Check User and Password validity via HTTP GET call
		return sendGetRequest(URL_ROOT+"checkUserPassword","userName="+user_name+"&password="+hash_pass,"");
	}
	
	/*	OLD CLASS, FOR REFERENCE ONLY!
	public void getDataFromURL() {  //this is the downloader method
        try {
                URL url = new URL(URL_ROOT+"?getMainXML"); //you can write here any link
                File file = new File(PATH_ROOT+XML_FILE_NAME);

                long startTime = System.currentTimeMillis();
                Log.d("XMLManager", "download begining");
                Log.d("XMLManager", "download url :" + url);
                Log.d("XMLManager", "downloaded file name: " + XML_FILE_NAME);
                //Open a connection to that URL.
                URLConnection ucon = url.openConnection();

                
                 //Define InputStreams to read from the URLConnection.
                 
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                
                 //Read bytes to the Buffer until there is nothing more to read(-1).
                 
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                }

                //Convert the Bytes read to a String.
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());
                fos.close();
                Log.d("ImageManager", "download ready in"
                                + ((System.currentTimeMillis() - startTime) / 1000)
                                + " sec");

        } catch (IOException e) {
                Log.d("ImageManager", "Error: " + e);
        }

}
	*/
}