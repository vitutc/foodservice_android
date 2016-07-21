package br.com.leomarvic;

import java.util.ArrayList;

import br.com.leomarvic.R.id;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ItemListScreen extends ListActivity {
    /** Called when the activity is first created. */
	
	public static Item selectedItem = null;
	private ArrayList<Item> m_itemArrayList = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    	m_itemArrayList = RestMainCategoryListScreen.selectedCategory.getItems();

        
		setListAdapter(new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1,m_itemArrayList));
    
	}
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//String item = getListAdapter().getItem(position).toString();
		selectedItem = (Item) getListAdapter().getItem(position);
		//Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
		Thread initScreen = new Thread() {
			public void run() {
				startActivity(new Intent("br.com.leomarvic.BUYITEMSCREEN"));
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