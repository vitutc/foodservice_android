package br.com.leomarvic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class StartScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.main);
        
        
        
        ( (Button)findViewById(R.id.BtAbout) ).setOnClickListener(new View.OnClickListener() {	
        	
			public void onClick(View v) {
				Thread initScreen = new Thread(){
		        	public void run()
		        	{
		        		startActivity(new Intent("br.com.leomarvic.ABOUTSCREEN"));
		        	}
		        };
		        initScreen.start();
			}
		});
        
        ( (Button)findViewById(R.id.BtSelRest) ).setOnClickListener(new View.OnClickListener() {	

        	public void onClick(View v) {
        		Thread initScreen = new Thread(){
        			public void run()
        			{
        				startActivity(new Intent("br.com.leomarvic.RESTLISTSCREEN"));
        			}
        		};
        		initScreen.start();
        	}
        });
        ( (Button)findViewById(R.id.BtManAcc) ).setOnClickListener(new View.OnClickListener() {	
        	
			public void onClick(View v) {
				Thread initManAcc = new Thread(){
		        	public void run()
		        	{
		        		startActivity(new Intent("br.com.leomarvic.MANAGEACCOUNT"));
		        	}
		        };
		        initManAcc.start();
			}
		});

    }

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
	
	
}