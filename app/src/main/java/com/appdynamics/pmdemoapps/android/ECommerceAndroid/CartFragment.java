package com.appdynamics.pmdemoapps.android.ECommerceAndroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.appdynamics.eumagent.runtime.InfoPoint;
import com.appdynamics.eumagent.runtime.Instrumentation;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.GlobalDataProvider;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.model.Item;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.service.http.DeleteRequestService;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.service.http.GetRequestService;

public class CartFragment extends ListFragment {
    private static final String TAG = CartFragment.class.getName();

	public static List<Item> currentCartItems = new ArrayList<Item>();
	public static Map<Long,Item> currentCartItemsMap = new ConcurrentHashMap<Long,Item>();
	
	/**
	 * The fragment's current callback object
	 */
	private Callbacks mCallbacks = sDummyCallbacks;
	
	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		public void storeCartFragment(CartFragment cartFragment);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void storeCartFragment(CartFragment cartFragment) {
		}
	};
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CartFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<Item>(getActivity(),
				android.R.layout.simple_list_item_multiple_choice,
				android.R.id.text1, currentCartItems));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_cart,container, false);
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ListView listView = getListView();
	    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    listView.setTextFilterEnabled(true);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
		mCallbacks.storeCartFragment(this);
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}
	
    // We keep track of checked items ourselves
    // Android bug: ListView base adapter doesn't report correctly
    private SparseBooleanArray myCheckedItems = new SparseBooleanArray();

    // Listen for click events (check/uncheck) on the CartFragment ListView
    // Update myCheckedItems as items checked/unchecked
	@Override
	public void onListItemClick( ListView l, View v, int position, long id)
	{
        // Update list of checked items
        int index = myCheckedItems.indexOfKey(position);

        // If item already checked, delete entry; otherwise add it
        if (index >= 0)
            myCheckedItems.delete(position);
        else
            myCheckedItems.append(position, true);
	}

	// When called in the context of the same fragment, update the underlying data
	public void convertItemsMaptoList(){
		convertItemsMaptoListStatic();
		((BaseAdapter)this.getListAdapter()).notifyDataSetChanged();
	}
	
	// Static Method for calls by non parent activities and other fragments.
	// Low overhead method. Avoid creating unnecessary items
	public static void convertItemsMaptoListStatic(){
		if (currentCartItemsMap!=null){
			currentCartItems.clear();
			currentCartItems.addAll(currentCartItemsMap.values());
		}
	}

    // REST service to delete from cart on server
    public class DeleteFromCartService extends DeleteRequestService {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Instrumentation.stopTimer("DeleteFromCartService");
        }
    }

    // Remove all checked items and force remaining items to unchecked
    @InfoPoint
    public void removeItemFromCart(){
        removeCheckedItems();
        uncheckAll();
    }

    // Remove any checked items from the cart
    @InfoPoint
    public void removeCheckedItems() {
        try {
            // Get number of items to delete
            int numItems = myCheckedItems.size();
            Log.d(TAG, "Deleting " + numItems + " items out of " + currentCartItems.size());

            // Delete checked items from cart
            for (int i = 0; i < numItems; i++) {
                if (myCheckedItems.valueAt(i)) {
                    int key = myCheckedItems.keyAt(i);
                    Log.d(TAG, "Removing Item: " + currentCartItems.get(key).getTitle());
                    Instrumentation.startTimer("DeleteFromCartService");
                    new DeleteFromCartService()
                            .execute(GlobalDataProvider
                            .getInstance()
                            .getRestServiceUrl()
                            + "cart/" + currentCartItems.get(key).getId());
                    currentCartItemsMap.remove(currentCartItems.get(key).getId());
                }
            }

            // Update list of checked items
            for (int j = 0; j < numItems; j++) {
                int key = myCheckedItems.keyAt(j);
                boolean value = myCheckedItems.valueAt(j);
                myCheckedItems.delete(key);
            }

            // Update base adapter and list view
            convertItemsMaptoList();

        } catch (Exception e) {
            Log.e(TAG, "Exception in removeCheckedItems(): ", e);
        }
    }

    // Set all checkboxes to unchecked
    public void uncheckAll() {
        ListView lv = getListView();
        int size = getListAdapter().getCount();
        for(int i = 0; i<=size; i++) {
            lv.setItemChecked(i, false);
        }
    }

    // TODO: Replace with Dialog/Intent. This will NPE if the user switches fragments
	private void displayToast(CharSequence text){
        try {
            Context context = this.getActivity();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } catch (Exception e) {
            Log.e(TAG, "displayToast", e);
        }
	}

	public void checkoutCart(){
        Log.d(TAG, "checkoutCart(): currentCartItems size = " + currentCartItems.size());
		if (currentCartItems!=null && currentCartItems.size()>0){
			CheckoutTask checkoutReq = new CheckoutTask();
            Instrumentation.reportMetric("CartSize", currentCartItems.size());
            Instrumentation.startTimer("Checkout");
			checkoutReq.execute(getEndpoint() + "cart/co");
			currentCartItemsMap.clear();
			convertItemsMaptoList();
		} else {
			displayToast("There are no items in the cart");
		}
	}

    public void crashMe() {
        String foo = null;
        boolean broken = foo.equals("badcode");
    }

    public class CheckoutTask extends GetRequestService {
	    protected void onPostExecute(String result) {
            Instrumentation.stopTimer("Checkout");
            Log.d(TAG, "CheckoutTask: result = " + result);
            displayToast(result);
	    }
	
    }

	public String getEndpoint(){
		return GlobalDataProvider.getInstance(). getRestServiceUrl();
	}

}
