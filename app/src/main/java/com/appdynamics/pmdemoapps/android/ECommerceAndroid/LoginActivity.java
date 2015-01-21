package com.appdynamics.pmdemoapps.android.ECommerceAndroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appdynamics.eumagent.runtime.Instrumentation;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.AsyncTaskListener;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.Constants;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.GlobalDataProvider;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.UserLoginTask;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.UserPrefActivity;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity implements AsyncTaskListener {

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mUser;
	private String mPassword;

	// UI references.
	private EditText mUserView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	private boolean exceptionOccurred;
	//private String exceptionMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		mUserView = (EditText) findViewById(R.id.username);
		mUserView.setText(mUser);

		mPasswordView = (EditText) findViewById(R.id.password);
		
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
				
		
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

        Instrumentation.startTimer("Login");

		// Reset errors.
		mUserView.setError(null);
		mPasswordView.setError(null);
		// Store values at the time of the login attempt.
		mUser = mUserView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} 

		// Check for a valid user name.
		if (TextUtils.isEmpty(mUser)) {
			mUserView.setError(getString(R.string.error_field_required));
			cancel = true;
		} 

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask(this);
			mAuthTask.execute(mUser,mPassword);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	private void navigateToHomePage(){
		displayToast("Login successful!");		
		Intent detailIntent = new Intent(this, ItemListActivity.class);
		startActivity(detailIntent);
	}
	
	private void displayToast(CharSequence text){
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            openSettingsPage();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void openSettingsPage() {
        Instrumentation.leaveBreadcrumb("Open Settings");
		Intent detailIntent = new Intent(this, UserPrefActivity.class);
		startActivity(detailIntent);
	}

	
	public void cancelled() {
		mAuthTask = null;
		showProgress(false);
	}
	
	public String getEndpoint(){
		return GlobalDataProvider.getInstance().getRestServiceUrl();  
		
	}

	@Override
	public void onPostExecute(boolean success, boolean error,
			String exceptionMessage) {
		mAuthTask = null;
		showProgress(false);
        Instrumentation.stopTimer("Login");
		if (success) {
            Instrumentation.leaveBreadcrumb("Login successful");
			//Save the username/password in settings
			SharedPreferences settings = getSharedPreferences(Constants.COMMON_PREFS_FILE_NAME, 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putString(Constants.COMMON_PREFS_USERNAME,mUser);
		    editor.putString(Constants.COMMON_PREFS_PASSWORD,mPassword);
		    editor.commit();
			
		    finish();
			navigateToHomePage();
			return;
		} else {
            Instrumentation.leaveBreadcrumb("Login failed");
			if(exceptionOccurred){
				displayToast(exceptionMessage);
			}else{
				mUserView
						.setError(exceptionMessage);
				mUserView.requestFocus();
			}
		}
		
	}
	
}
