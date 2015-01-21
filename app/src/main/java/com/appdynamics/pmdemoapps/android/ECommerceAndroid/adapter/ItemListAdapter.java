package com.appdynamics.pmdemoapps.android.ECommerceAndroid.adapter;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdynamics.pmdemoapps.android.ECommerceAndroid.R;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.UserPrefActivity;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.model.Item;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ItemListAdapter extends ArrayAdapter<Item> {
    private final Context context;
	private final List<Item> items;

	public ItemListAdapter(Context context, List<Item> items) {
	    super(context, R.layout.item_list, items);
	    this.context = context;
	    this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	    Item currentItem = items.get(position);
	    View rowView = inflater.inflate(R.layout.item_list, parent, false);
	    String url = getImageEndpoint() + currentItem.getImagePath();

        ImageView imageView = (ImageView) rowView.findViewById(R.id.il_ImageView1);
	    ImageLoader.getInstance().displayImage(url, imageView) ;

	    TextView textView = (TextView) rowView.findViewById(R.id.il_textView1);
	    textView.setText(currentItem.getTitle());

	    return rowView;
	}

	public String getImageEndpoint(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		String server_url = sharedPref.getString(UserPrefActivity.KEY_PREF_SERVER_ENDPOINT, "");

        if (server_url.endsWith("/")){
	        return server_url;
		}
		else{
			return server_url+"/";
		}
    }
}
