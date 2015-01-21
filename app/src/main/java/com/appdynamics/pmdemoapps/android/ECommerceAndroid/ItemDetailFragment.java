package com.appdynamics.pmdemoapps.android.ECommerceAndroid;

import java.text.NumberFormat;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.GlobalDataProvider;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.model.Item;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "selected_item";

    /**
     * The dummy content this fragment is presenting.
     */
    private Item mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItem = ItemListActivity.selectedItem;
        //new ItemRequestService().execute(url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail,
                container, false);


        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail))
                    .setText(mItem.getTitle());

            String url = getImageEndpoint() + mItem.getImagePath();

            ImageView imageView = (ImageView) rootView.findViewById(R.id.ImageView1);


            DisplayImageOptions options =
                    new DisplayImageOptions.Builder()
                            .delayBeforeLoading(0)//Good way to simulate slow performance
                            .cacheInMemory(true) // default
                            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                            .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                            .displayer(new SimpleBitmapDisplayer()) // default
                            .handler(new Handler()) // default
                            .build();
            ImageLoader.getInstance().displayImage(url, imageView, options);
            TextView priceView = (TextView) rootView.findViewById(R.id.priceText);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String moneyString = formatter.format(mItem.getPrice());
            priceView.setText(moneyString);
        }

        return rootView;
    }


    public String getImageEndpoint() {
        return GlobalDataProvider.getInstance().getImageUrl();

    }

}
