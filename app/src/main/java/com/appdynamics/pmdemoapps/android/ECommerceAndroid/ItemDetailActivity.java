package com.appdynamics.pmdemoapps.android.ECommerceAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.appdynamics.eumagent.runtime.InfoPoint;
import com.appdynamics.eumagent.runtime.Instrumentation;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.GlobalDataProvider;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.UserPrefActivity;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.model.Item;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.service.http.GetRequestService;

/**
 * An activity representing a single Item detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link ItemListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ItemDetailFragment}.
 */
public class ItemDetailActivity extends FragmentActivity {

    @Override
    @InfoPoint
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            ItemDetailFragment fragment = new ItemDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettingsPage();
                return true;
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettingsPage() {
        Intent detailIntent = new Intent(this, UserPrefActivity.class);
        startActivity(detailIntent);

    }

    public void addToCartAction(View view) {
        addItemToCart(ItemListActivity.selectedItem);

        //Go back to list page
        NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
    }

    public void addItemToCart(Item item) {
        //Avoid duplicates. Re-adding in case there has been some change to the item in the server
        if (CartFragment.currentCartItemsMap.containsKey(item.getId())) {
            CartFragment.currentCartItems.remove(item);
        }
        CartFragment.currentCartItemsMap.put(item.getId(), item);

        CartFragment.convertItemsMaptoListStatic();

        Instrumentation.leaveBreadcrumb("Adding item to Cart: " + item.getId());
        new AddToCartService().execute(GlobalDataProvider
                .getInstance()
                .getRestServiceUrl() + "cart/" + item.getId());
    }

    public class AddToCartService extends GetRequestService {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }

}
