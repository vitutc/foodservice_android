package br.com.leomarvic;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FoodService extends Activity {

	private Boolean splashOver = false;
	static Boolean shouldDownload = false;
	static HTTPHandler hHandler = null;
	static XMLHandler xHandler = null;
	static Thread downloaderThread = null;
	static String active_user = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		setContentView(R.layout.splash);
		
		startActivityForResult(new Intent("br.com.leomarvic.DOWNLOADDIALOG"), 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		Thread downloadImagesThread = new Thread() {
			public void run() {
				FoodService.hHandler = new HTTPHandler();
				FoodService.hHandler.getMainXML();
				FoodService.xHandler = new XMLHandler(HTTPHandler.PATH_ROOT
						+ HTTPHandler.XML_FILE_NAME);		
				
				for (int i = 0; i < FoodService.xHandler.getNumberRestaurants(); i++) {
					String restaurantName = FoodService.xHandler.getRestaurantNames().get(i);
					String restaurantLogoID = FoodService.xHandler.getRestaurantLogo(restaurantName);
					FoodService.hHandler.getImage(restaurantLogoID);
					
					ArrayList<Image> picsArray = FoodService.xHandler.getRestImages(restaurantName);
					for (int j = 0; j < picsArray.size(); j++){
						FoodService.hHandler.getImage(picsArray.get(j).getID());
					}
				}
			}
		};
		if (shouldDownload) { downloadImagesThread.start(); }
		
		else {
			FoodService.xHandler = new XMLHandler(HTTPHandler.PATH_ROOT
				+ HTTPHandler.XML_FILE_NAME);
			FoodService.hHandler = new HTTPHandler();
		}
		
		splashOver = true;
		
		startActivity(new Intent("br.com.leomarvic.STARTSCREEN"));
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		// Used to avoid going back to the Splash Screen
		// once the DownloadDialog has been dismissed
		super.onResume();
		
		if (splashOver) { finish(); }	 
	}
}