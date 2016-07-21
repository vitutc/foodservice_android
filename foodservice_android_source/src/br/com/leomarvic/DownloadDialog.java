package br.com.leomarvic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class DownloadDialog extends Activity {		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.download_dialog);
        
        ((Button) findViewById(R.id.BtDialog1Yes)).setOnClickListener( new View.OnClickListener() {
    		public void onClick(View v) {
    			FoodService.shouldDownload = true;
    			finish();
    		}
    	});
        
        
        ((Button) findViewById(R.id.BtDialog1No)).setOnClickListener( new View.OnClickListener() {
    		public void onClick(View v) {
    			finish();
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