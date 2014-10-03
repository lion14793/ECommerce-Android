package com.appdynamics.pmdemoapps.android.cart.test;

import com.appdynamics.pmdemoapps.android.cart.EntryActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

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
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.EntryActivity'
		solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.EntryActivity.class, 2000);
        //Set default small timeout to 101011 milliseconds
		Timeout.setSmallTimeout(101011);
        //Enter the text: 'test'
		solo.clearEditText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.username));
		solo.enterText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.username), "test");
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.password));
        //Enter the text: 'appdynamics'
		solo.clearEditText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.password));
		solo.enterText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.password), "appdynamics");
        //Click on Sign in or register
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.sign_in_button));
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemListActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        int noOfBooks = randInt(1,8);
        for (int i = 1; i < noOfBooks; i++) {
            doAddBook(i);
        }

        //Click on Cart
		solo.clickOnText(java.util.regex.Pattern.quote("Cart"));
        //Click on Checkout
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Click on List
		solo.clickOnText(java.util.regex.Pattern.quote("List"));
	}

    private void doAddBook (int book) {
        solo.clickInList(book, 0);
        assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.oops_button));
        assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));
    }

    private static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
