package com.appdynamics.pmdemoapps.android.cart.test;

import com.appdynamics.pmdemoapps.android.cart.EntryActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class AcmeRobotiumTest extends ActivityInstrumentationTestCase2<EntryActivity> {
  	private Solo solo;
  	
  	public AcmeRobotiumTest() {
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

        //Click on A Clockwork Orange
		solo.clickInList(1, 0);
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        //Click on Add to Cart
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemListActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        //Click on The Goldfinch: A Novel
		solo.clickInList(2, 0);
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        //Click on Add to Cart
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemListActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        //Click on Personal
		solo.clickInList(3, 0);
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        //Click on Add to Cart
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemListActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        //Click on Farewell To Arms
		solo.clickInList(4, 0);
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        //Click on Add to Cart
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemListActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        //Click on Freakonomics
		solo.clickInList(5, 0);
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        //Click on Add to Cart
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemListActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        //Click on Driven From Within
		solo.clickInList(6, 0);
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        //Click on Add to Cart
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemListActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        //Click on Sacred Hoops
		solo.clickInList(7, 0);
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        //Click on Add to Cart
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemListActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        //Click on Shantaram
		solo.clickInList(8, 0);
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity'
		assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        //Click on Add to Cart
		solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Wait for activity: 'com.appdynamics.pmdemoapps.android.cart.ItemListActivity'
        assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        //Click on Cart
        solo.clickOnText(java.util.regex.Pattern.quote("Cart"));
        //Click on Checkout
        solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        //Click on List
        solo.clickOnText(java.util.regex.Pattern.quote("List"));
	}
}
