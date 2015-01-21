package com.appdynamics.pmdemoapps.android.ECommerceAndroid.test;

import com.appdynamics.pmdemoapps.android.ECommerceAndroid.EntryActivity;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.R;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class AcmeRobotiumCheckout extends ActivityInstrumentationTestCase2<EntryActivity> {
  	private Solo solo;

  	public AcmeRobotiumCheckout() {
		super(EntryActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}

    public void testRun() {
        doLogin();

        assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.ECommerceAndroid.ItemListActivity.class));

        //changeSettings();

        int noOfBooks = randInt(1,8);
        ArrayList<Integer> shuffled = shuffleItems(0,noOfBooks);
        for (int i = 0; i < noOfBooks; i++) {
            doAddBook(shuffled.get(i));
        }

        doCheckout();
    }

    private ArrayList<Integer> shuffleItems(int min, int max) {
        ArrayList<Integer> elements = new ArrayList<Integer>();
        for (int i = min; i < max; i++) {
            elements.add(i, i);
        }
        Collections.shuffle(elements);
        return elements;
    }

    private void doLogin () {
        solo.waitForActivity(com.appdynamics.pmdemoapps.android.ECommerceAndroid.EntryActivity.class, 2000);
        Timeout.setSmallTimeout(101011);
        solo.clearEditText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.ECommerceAndroid.R.id.username));
        solo.enterText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.ECommerceAndroid.R.id.username), "test");

        solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.ECommerceAndroid.R.id.password));
        solo.clearEditText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.ECommerceAndroid.R.id.password));
        solo.enterText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.ECommerceAndroid.R.id.password), "appdynamics");

        solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.ECommerceAndroid.R.id.sign_in_button));
    }

    private void doCheckout() {
        solo.clickOnText(java.util.regex.Pattern.quote("Cart"));
        solo.clickOnView(solo.getView(R.id.checkout_button));
        solo.clickOnText(java.util.regex.Pattern.quote("List"));
    }

    private void doAddBook (int book) {
        solo.clickInList(book, 0);
        assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.ECommerceAndroid.ItemDetailActivity.class));
        solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.ECommerceAndroid.R.id.oops_button));
        assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.ECommerceAndroid.ItemListActivity.class));
    }

    private void changeSettings() {
        solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        solo.clickOnActionBarItem(com.appdynamics.pmdemoapps.android.ECommerceAndroid.R.id.action_settings);
        assertTrue("com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.UserPrefActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.UserPrefActivity.class));
        solo.clickInList(1, 0);
        solo.waitForDialogToOpen(5000);

        solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
        solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "http://54.203.82.235/appdynamicspilot/");
        solo.clickOnView(solo.getView(android.R.id.button1));
        solo.clickInList(2, 0);
        solo.waitForDialogToOpen(5000);

        solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
        solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "DEMO-AAB-AUM");
        solo.clickOnView(solo.getView(android.R.id.button1));
        solo.clickInList(3, 0);
        solo.waitForDialogToOpen(5000);

        solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
        solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "http://54.244.95.83:9001");
        solo.clickOnView(solo.getView(android.R.id.button1));
        solo.goBack();
    }

    private static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
