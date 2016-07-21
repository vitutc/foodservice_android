package br.com.leomarvic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
        ( (Button)findViewById(R.id.BtAboutConf) ).setOnClickListener(new View.OnClickListener() {	

        	public void onClick(View v) {
        		finish();
        	}
        });
    }
}