package br.com.leomarvic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class RestaurantMainScreen extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.restaurant_main);

		((Button) findViewById(R.id.BtRestMainInfo)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread initScreen = new Thread() {
					public void run() {
						startActivity(new Intent("br.com.leomarvic.RESTMAININFOSCREEN"));
					}
				};
				initScreen.start();
			}
		});
		
		((Button) findViewById(R.id.BtRestMainPics)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread initScreen = new Thread() {
					public void run() {
						startActivity(new Intent("br.com.leomarvic.RESTPICSCREEN"));
					}
				};
				initScreen.start();
			}
		});
		
		((Button) findViewById(R.id.BtRestMainPromo)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread initScreen = new Thread() {
					public void run() {
						startActivity(new Intent("br.com.leomarvic.RESTMAINMENUSCREEN"));
					}
				};
				initScreen.start();
			}
		});
		
		((Button) findViewById(R.id.BtRestMainChef)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread initScreen = new Thread() {
					public void run() {
						startActivity(new Intent("br.com.leomarvic.RESTMAINMENUSCREEN"));
					}
				};
				initScreen.start();
			}
		});
		
		((Button) findViewById(R.id.BtRestMainCompleteMenu)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread initScreen = new Thread() {
					public void run() {
						startActivity(new Intent("br.com.leomarvic.RESTCATEGORYLISTSCREEN"));
					}
				};
				initScreen.start();
			}
		});
		
		((Button) findViewById(R.id.BtRestMainViewOrder)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Thread initScreen = new Thread() {
					public void run() {
						startActivity(new Intent("br.com.leomarvic.CHECKOUTSCREEN"));
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