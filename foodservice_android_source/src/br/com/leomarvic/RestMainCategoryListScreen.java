package br.com.leomarvic;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RestMainCategoryListScreen extends ListActivity {
    /** Called when the activity is first created. */
	
	public static Category selectedCategory = null;
	//private CategoryAdapter m_adapter;
	public static ArrayList<Category> m_catArrayList = FoodService.xHandler.getRestCategories(RestaurantListScreen.selectedRestaurant);
	
	/*private class CategoryAdapter extends ArrayAdapter<Category> {

		private ArrayList<Category> categoryItens;

		public CategoryAdapter(Context context, int textViewResourceId,
				ArrayList<Category> categoryItens) {
			super(context, textViewResourceId, categoryItens);
			this.categoryItens = categoryItens;
		}
	}*/
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //setContentView(R.layout.category_list);
        
		//this.m_adapter = new CategoryAdapter(this, R.layout.category_list, m_catArrayList);
		setListAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1,m_catArrayList));
    
	/*getListView().setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			// Toast.makeText(getApplicationContext(),
			// ((TextView) view.findViewById(R.id.rest_name)).getText(),
			// Toast.LENGTH_SHORT).show();
			
			selectedCategory = ((TextView) view.findViewById(R.id.rest_name)).getText().toString();
			
			Thread initScreen = new Thread() {
				public void run() {
					startActivity(new Intent("br.com.leomarvic.RESTMAINSCREEN"));
				}
			};
			initScreen.start();
		}
	});*/
	}
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//String item = getListAdapter().getItem(position).toString();
		selectedCategory = (Category) getListAdapter().getItem(position);
		//Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
		Thread initScreen = new Thread() {
			public void run() {
				startActivity(new Intent("br.com.leomarvic.ITEMLISTSCREEN"));
			}
		};
		initScreen.start();
	}
    
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
}