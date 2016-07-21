
package br.com.leomarvic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class RestMainPicsScreen extends Activity {
	
	static Bitmap selectedPictureBmp;
	static String selectedPictureDesc;
	private ArrayList<Bitmap> restaurantPics = null;
	
	public class ImageAdapter extends BaseAdapter {
	    private Context mContext;
	    private ArrayList<Bitmap> mThumbDrawables = null;

	    public ImageAdapter(Context c, ArrayList<Bitmap> bmpList) {
	        mContext = c;
	        mThumbDrawables = new ArrayList<Bitmap>(bmpList);
	    }

	    public int getCount() {
	        return mThumbDrawables.size();
	    }

	    public Object getItem(int position) {
	        return mThumbDrawables.get(position);
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(110, 90));
	            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	            //imageView.setPadding(5, 5, 5, 5);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageBitmap((mThumbDrawables.get(position)));
	        return imageView;
	    }
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_pictures);      
        
        restaurantPics = new ArrayList<Bitmap>();
        final ArrayList<Image> picsArray = FoodService.xHandler.getRestImages
		(RestaurantListScreen.selectedRestaurant);
        for (int i = 0; i < picsArray.size(); i++)
		{
			String restaurantPicsID = picsArray.get(i).getID();
			Bitmap restaurantPic = BitmapFactory.decodeFile(HTTPHandler.PATH_IMAGES+restaurantPicsID);
			restaurantPics.add(restaurantPic);
		}
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, restaurantPics));
        
        gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				selectedPictureBmp = restaurantPics.get(position);	
				selectedPictureDesc = picsArray.get(position).getDesc();
				startActivity(new Intent("br.com.leomarvic.PICDISPLAYSCREEN"));
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
