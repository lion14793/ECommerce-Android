package com.appdynamics.pmdemoapps.android.ECommerceAndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.appdynamics.eumagent.runtime.InfoPoint;
import com.appdynamics.eumagent.runtime.Instrumentation;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.Constants;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.UserPrefActivity;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.model.Item;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.tabs.CustomTabListener;

/**
 * Unfortunately the two pane mode does not work because we are using 
 * tabs in the page here which require fragments. So unless android and 
 * actionbarsherlock give an option for nested fragments, we will have to go 
 * with standard one pane display
 */

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ItemDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details (if present) is a
 * {@link ItemDetailFragment}.
 * <p>
 * This activity also implements the required {@link ItemListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class ItemListActivity extends FragmentActivity implements
        ItemListFragment.Callbacks, CartFragment.Callbacks {
    private static final String TAG = ItemListActivity.class.getName();

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	
	/**
	 * Use a static field to store and transfer the item selected to the detail fragment
	 * http://developer.android.com/guide/faq/framework.html#3
	 */
	public static Item selectedItem = null;
	
	//Hold a reference to cart fragment
	private CartFragment currentCartFragment;
	
	@Override
	public void storeCartFragment(CartFragment cartFragment) {
		this.currentCartFragment = cartFragment;
	}

	@Override
    @InfoPoint
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_item_list);

		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the 'activated' state when touched.
            ((ItemListFragment) getFragmentManager().findFragmentById(
                    R.id.item_list)).setActivateOnItemClick(true);
		}
		
		//Create Tabs
        final ActionBar actionBar = getActionBar();
        // Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    //actionBar.setDisplayShowTitleEnabled(false);
	    
	    actionBar.addTab(
                actionBar.newTab()
                        .setText("List")
                        .setTabListener(new CustomTabListener<ItemListFragment>(this, "List", ItemListFragment.class)));
        actionBar.addTab(
	        actionBar.newTab()
	                 .setText("Cart")
	                 .setTabListener(new CustomTabListener<CartFragment>(this, "Cart", CartFragment.class)));
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
            case R.id.action_logout:
                logoutUser();
                return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void openSettingsPage() {
		Intent detailIntent = new Intent(this, UserPrefActivity.class);
		startActivity(detailIntent);
	}

    private void logoutUser() {
        SharedPreferences settings = getSharedPreferences(Constants.COMMON_PREFS_FILE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(Constants.COMMON_PREFS_USERNAME);
        editor.remove(Constants.COMMON_PREFS_PASSWORD);
        editor.commit();

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

	/**
	 * Callback method from {@link ItemListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Item item) {
		selectedItem = item;
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			//Bundle arguments = new Bundle();

			ItemDetailFragment fragment = new ItemDetailFragment();
			//fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			startActivity(detailIntent);
		}
	}

    // Delete items and update cart
	public void deleteFromCartAction(View view){
        Instrumentation.leaveBreadcrumb("Delete from cart");
        currentCartFragment.removeItemFromCart();
    }

    // Checkout and update cart
	public void checkoutCartAction(View view){ 
	    Instrumentation.leaveBreadcrumb("Checkout");
        currentCartFragment.checkoutCart();
	}

    // Crash the app!
    public void crashAction(View view) {
        currentCartFragment.crashMe();
    }
}
