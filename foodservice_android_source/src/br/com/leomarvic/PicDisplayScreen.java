package br.com.leomarvic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PicDisplayScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.restaurant_pic_item);
        
        ((ImageView) findViewById(R.id.ImgViewRestPic)).setImageBitmap(RestMainPicsScreen.selectedPictureBmp);
        ((TextView) findViewById(R.id.TxtRestPicDescription)).setText(RestMainPicsScreen.selectedPictureDesc);
	}
    
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
}