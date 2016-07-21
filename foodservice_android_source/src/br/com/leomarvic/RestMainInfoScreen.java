package br.com.leomarvic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class RestMainInfoScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.rest_info);
        
        //XMLHandler xhandler = new XMLHandler("/mnt/sdcard/bluetooth/test2.xml");
        
        ((TextView) findViewById(R.id.TxtRestaurantInfo)).setText
        	(FoodService.xHandler.getRestaurantDesc(RestaurantListScreen.selectedRestaurant));
        
        ((Button) findViewById(R.id.BtInfoScreenPics)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread initScreen = new Thread() {
					public void run() {
						startActivity(new Intent("br.com.leomarvic.RESTPICSCREEN"));
					}
				};
				initScreen.start();
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