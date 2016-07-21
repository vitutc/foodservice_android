package br.com.leomarvic;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantListScreen extends ListActivity {

	private ProgressDialog m_ProgressDialog = null;
	private ArrayList<RestaurantItem> m_restArrayList = null;
	private RestaurantAdapter m_adapter;
	private Runnable viewRestaurants;

	public class RestaurantItem {

		private String name;
		private Bitmap logo;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Bitmap getLogo() {
			return logo;
		}

		public void setLogo(Bitmap logo) {
			this.logo = logo;
		}
	}

	private class RestaurantAdapter extends ArrayAdapter<RestaurantItem> {

		private ArrayList<RestaurantItem> restaurantItems;

		public RestaurantAdapter(Context context, int textViewResourceId,
				ArrayList<RestaurantItem> restaurantItems) {
			super(context, textViewResourceId, restaurantItems);
			this.restaurantItems = restaurantItems;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}

			RestaurantItem tmpItem = restaurantItems.get(position);
			if (tmpItem != null) {
				TextView tt = (TextView) v.findViewById(R.id.rest_name);
				ImageView imgView = (ImageView) v.findViewById(R.id.rest_logo);

				if (tt != null) {
					tt.setText(tmpItem.getName());
				}
				if (imgView != null) {
					imgView.setImageBitmap(tmpItem.getLogo());
				}
			}
			return v;
		}
	}

	private Runnable returnRes = new Runnable() {

		public void run() {
			if (m_restArrayList != null && m_restArrayList.size() > 0) {
				m_adapter.notifyDataSetChanged();
				for (int i = 0; i < m_restArrayList.size(); i++)
					m_adapter.add(m_restArrayList.get(i));
			}
			m_ProgressDialog.dismiss();
			m_adapter.notifyDataSetChanged();
		}
	};

	private void getRestaurants() {
		try {
			m_restArrayList = new ArrayList<RestaurantItem>();
			for (int i = 0; i < FoodService.xHandler.getNumberRestaurants(); i++) {		
				String restaurantName = FoodService.xHandler.getRestaurantNames().get(i);
				String restaurantLogoID = FoodService.xHandler.getRestaurantLogo(restaurantName);
				Bitmap restaurantLogo = BitmapFactory.decodeFile(HTTPHandler.PATH_IMAGES+restaurantLogoID);
			 
				RestaurantItem restItem = new RestaurantItem();
				restItem.setName(restaurantName);
				restItem.setLogo(restaurantLogo);
				m_restArrayList.add(restItem);
			}
			
			Log.i("ARRAY", "" + m_restArrayList.size());
		} catch (Exception e) {
			Log.e("BACKGROUND_PROC", e.getMessage());
		}
		//runOnUiThread(returnRes);
		for (int i = 0; i < m_restArrayList.size(); i++){
			m_adapter.add(m_restArrayList.get(i));}
		m_adapter.notifyDataSetChanged();
	}

	public static String selectedRestaurant = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.restaurants_list);

		m_restArrayList = new ArrayList<RestaurantItem>();
		this.m_adapter = new RestaurantAdapter(this, R.layout.row, m_restArrayList);
		setListAdapter(this.m_adapter);

		viewRestaurants = new Runnable() {
			public void run() {
				//getRestaurants();
				/*
				try {
					m_restArrayList = new ArrayList<RestaurantItem>();
					for (int i = 0; i < FoodService.xHandler.getNumberRestaurants(); i++) {		
						String restaurantName = FoodService.xHandler.getRestaurantNames().get(i);
						String restaurantLogoID = FoodService.xHandler.getRestaurantLogo(restaurantName);
						Bitmap restaurantLogo = BitmapFactory.decodeFile(HTTPHandler.PATH_IMAGES+restaurantLogoID);
					 
						RestaurantListScreen.RestaurantItem restItem = new RestaurantItem();
						restItem.setName(restaurantName);
						restItem.setLogo(restaurantLogo);
						RestaurantListScreen.m_restArrayList.add(restItem);
					}
					
					Log.i("ARRAY", "" + m_restArrayList.size());
				} catch (Exception e) {
					Log.e("BACKGROUND_PROC", e.getMessage());
				}
				//runOnUiThread(returnRes);
				for (int i = 0; i < RestaurantListScreen.m_restArrayList.size(); i++){
					RestaurantListScreen.m_adapter.add(RestaurantListScreen.m_restArrayList.get(i));}
				RestaurantListScreen.m_adapter.notifyDataSetChanged();*/
			}
		};

		getRestaurants();
		//Thread thread = new Thread(null, viewRestaurants, "UpdaterBackground");
		//thread.start();
		//m_ProgressDialog = ProgressDialog.show(RestaurantListScreen.this,
		//		"Please wait...", "Retrieving Restaurants ...", true);

		getListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// Toast.makeText(getApplicationContext(),
				// ((TextView) view.findViewById(R.id.rest_name)).getText(),
				// Toast.LENGTH_SHORT).show();
				
				selectedRestaurant = ((TextView) view.findViewById(R.id.rest_name)).getText().toString();
				
				Thread initScreen = new Thread() {
					public void run() {
						startActivity(new Intent("br.com.leomarvic.RESTMAINSCREEN"));
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