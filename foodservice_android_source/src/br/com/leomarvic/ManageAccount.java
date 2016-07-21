package br.com.leomarvic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.leomarvic.HTTPHandler;

public class ManageAccount extends Activity {
    /** Called when the activity is first created. */
	TextView google;
    EditText getuser;
    EditText getpasswd;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manacc);
        
        google = (TextView) findViewById(R.id.txtlogin);
        getuser = (EditText) findViewById(R.id.EdUser);
        getpasswd = (EditText) findViewById(R.id.EdPassword);
        
        
        //Check if logged user, then change text view to show which user
        TextView loggedUserTextView = (TextView) findViewById(R.id.textViewLoggedUser);
        if (FoodService.active_user == null)
        {
        	loggedUserTextView.setText("Nenhum Usuário Ativo");
        	loggedUserTextView.setTextColor(Color.RED);
        }
        else
        {
        	String balance = FoodService.hHandler.getUserBalance(FoodService.active_user);
        	loggedUserTextView.setText(" Usuário Ativo: " + FoodService.active_user + " \n " + "Saldo: " + balance);
        	loggedUserTextView.setTextColor(Color.GREEN);
        }

        
        ( (Button)findViewById(R.id.BtLogin) ).setOnClickListener(new View.OnClickListener() {	

        	public void onClick(View v) {
        		String user = getuser.getText().toString();
        		String password = getpasswd.getText().toString();
        		String check_response = FoodService.hHandler.checkUserPassword(user, password);
				
        		//If OK, register active user
        		if (check_response.equalsIgnoreCase("True")) 
    			{
    				FoodService.active_user = user;
    				//Toast OK
    				Context context = getApplicationContext();
    				CharSequence text = "Usuário e Senha Ok!";
    				int duration = Toast.LENGTH_SHORT;

    				Toast toast = Toast.makeText(context, text, duration);
    				toast.show();
    				
    				TextView textView = (TextView) findViewById(R.id.textViewLoggedUser);
    	        	String balance = FoodService.hHandler.getUserBalance(FoodService.active_user);
    	        	textView.setText(" Usuário Ativo: " + FoodService.active_user + " \n " + "Saldo: " + balance);
    	            textView.setTextColor(Color.GREEN);
    			}
        		else
        		{
    				FoodService.active_user = null;
    				Context context = getApplicationContext();
    				CharSequence text = "Usuário ou Senha Inaválidos!";
    				int duration = Toast.LENGTH_SHORT;

    				Toast toast = Toast.makeText(context, text, duration);
    				toast.show();
        		}
        		
        		//HTTPHandler obj = new HTTPHandler();
        		//obj.getImage("google.jpg");
        		/*
        		 //Faz uma requisicao a uma URL
        		 String str = "***";

                try
            	{
            		HttpClient hc = new DefaultHttpClient();
            		HttpPost post = new HttpPost("http://www.yahoo.com");

            		HttpResponse rp = hc.execute(post);

            		if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            		{
            			str = EntityUtils.toString(rp.getEntity());
            		}
            		google.setText(str);
            	}catch(IOException e){
            		e.printStackTrace();
            	} */ 
            	
        		/*
        		 //Request mais funcional que o anterior
        		try {
        		    // Create a URL for the desired page
        		    URL url = new URL("http://www.gta.ufrj.br/~cardoso");

        		    // Read all the text returned by the server
        		    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        		    String str;
        		    String source="";
        		    while ((str = in.readLine()) != null) {
        		        // str is one line of text; readLine() strips the newline character(s)
        		    	source+=str;
        		    }
        		    in.close();
        		    google.setText(source);
        		} catch (MalformedURLException e) {
        		} catch (IOException e) {
        		}*/
        		
        		/*
        		 //Request com passagem de parï¿½metros de formulï¿½rio .php  
        		// Create a new HttpClient and Post Header
        		
        	    HttpClient httpclient = new DefaultHttpClient();
        	    HttpPost httppost = new HttpPost("http://www.mod-x.co.uk/verify.php");
        	    String str = "***";
        	    try {
        	        // Add your data
        	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        	        nameValuePairs.add(new BasicNameValuePair("username", "skullferno"));
        	        nameValuePairs.add(new BasicNameValuePair("password", "JHOa5CtB"));
        	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        	        // Execute HTTP Post Request
        	        HttpResponse response = httpclient.execute(httppost);
        	        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            		{
            			str = EntityUtils.toString(response.getEntity());
            		}
        	        google.setText(str);
        	    } catch (ClientProtocolException e) {
        	        // TODO Auto-generated catch block
        	    } catch (IOException e) {
        	        // TODO Auto-generated catch block
        	    }*/        	}
        });
    }
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
}