/**************************************************************************
 * StickCreateActivity.java, giveastick Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Maxime Lebastard
 * Licence     : 
 * Last update : Feb 10, 2014
 *
 **************************************************************************/
package com.iia.giveastick.view.giveastick;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.iia.giveastick.R;
import com.iia.giveastick.entity.User;
import com.iia.giveastick.harmony.view.HarmonyFragmentActivity;
import com.iia.giveastick.util.AccelerometerListener;
import com.iia.giveastick.util.AccelerometerManager;
import com.iia.giveastick.util.Const;

/**
 * List of the sticks activity
 * Includes a menu inflater and a Listener on the shake
 * 
 * @see android.app.Activity
 */
public class StickListActivity extends HarmonyFragmentActivity implements AccelerometerListener {
	private final static String TAG = "StickListActivity";
	
	// Android Methods
	//////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_stick_list_giveastick);
		
		Intent intent = getIntent();
		
		User currentUser = (User) intent.getSerializableExtra(Const.BUNDLE_USER);
		
		try
		{
			String titleView = currentUser.getUserGroup().getTag();
			this.setTitle(titleView);
		}
		catch(Exception e)
		{
			Log.e(TAG, "Impossible to read the Bundle User object");
		}

		
		//Binder la vue
		final ListView myList = (ListView) findViewById(R.id.list);
		
		// Create Data
		final ArrayList<User> aUsers = new ArrayList<User>();
		
		// For demo reasons, we don't get the data from the Webservice
		// TODO: Get data from the webservice
		User monUser = (User) new User("alex", 3);
		User monUser2 = (User) new User("steve", 2);
		User monUser3 = (User) new User("boby", 4);
		User monUser4 = (User) new User("charly", 0);
		User monUser5 = (User) new User("emma", 3);
		User monUser6 = (User) new User("caissa", 2);
		User monUser7 = (User) new User("lolo", 4);
		User monUser8 = (User) new User("cliclic", 0);
		User monUser9 = (User) new User("zizou", 3);
		User monUser10 = (User) new User("momo", 2);
		User monUser11 = (User) new User("tony", 4);
		User monUser12 = (User) new User("guigui",  0);
		
		aUsers.add(monUser);
		aUsers.add(monUser2);
		aUsers.add(monUser3);
		aUsers.add(monUser4);
		aUsers.add(monUser5);
		aUsers.add(monUser6);
		aUsers.add(monUser7);
		aUsers.add(monUser8);
		aUsers.add(monUser9);
		aUsers.add(monUser10);
		aUsers.add(monUser11);
		aUsers.add(monUser12);
		
		myAdapter adapter = new myAdapter(this, R.layout.activity_row_stick_list, aUsers);
		
		myList.setAdapter(adapter);	
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				AlertDialog.Builder adb = new AlertDialog.Builder(
				StickListActivity.this);
				adb.setTitle("Vote !");
				adb.setMessage("Veux tu donner un baton à "
				+ aUsers.get(position).getNickname());
				adb.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    Toast.makeText(StickListActivity.this, R.string.sticklist_give_vote_dialog_click_ok, Toast.LENGTH_LONG).show();
	                }
				});
				adb.setNegativeButton("NON", null);
				adb.show(); 

			}
			
		});
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		// Accelerometer manager listener restart
		if(AccelerometerManager.isSupported(this)) {
			AccelerometerManager.startListening(this);
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		
		// AccelerometerManager listener stop
		if(AccelerometerManager.isListening()) {
			AccelerometerManager.stopListening();
		}
	}
	
	// ListAdapter
	////////////////////////
	private static class myAdapter extends ArrayAdapter<User> {

		private Context context;
		private int resource;
		private LayoutInflater monInflateur;
		
		public myAdapter(Context context, int resource, List<User> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.resource = resource;
			this.monInflateur = LayoutInflater.from(this.context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = monInflateur.inflate(this.resource, null);
			TextView nick = (TextView) view.findViewById(R.id.row_pseudo);
			TextView stick = (TextView) view.findViewById(R.id.stick);
			TextView nbStick = (TextView) view.findViewById(R.id.nomber_stick);
			
			User unUser = this.getItem(position);
			
			nick.setText(unUser.getNickname().toString());
			nbStick.setText(String.valueOf(unUser.getNbBatons()));
			stick.setText(unUser.getBatons().toString());
			
			return view;
		}
		
	}
	
	// Options of the Action Bar
	///////////////////////////////
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.stick_list_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		boolean result = false;
		switch (item.getItemId()){
			case R.id.action_logout:
				logout();
				result = true;
			break;
			
			case R.id.action_about:
				about();
				result = true;
			break;
			
			default:
				result = super.onOptionsItemSelected(item);
			break;
		}
		
		return result;
	}
	
	/**
	 * On touching the "A propos" item in the menu
	 * @author Alexandre Thiboult
	 */
	private void about(){
		Intent intentAbout = new Intent(StickListActivity.this, AboutActivity.class);
		startActivity(intentAbout);
	}
	
	/**
	 * On touching the "Deconnexion" item in the menu
	 * @author Alexandre Thiboult
	 */
	private void logout(){
		Intent intentLogout = new Intent(StickListActivity.this, UserLoginActivity.class);
		startActivity(intentLogout);
	}
	
	
	// Accelerometer Handler
	////////////////////////
	/**
	 * Detects if the phone is moving by the accelerometer
	 */
	public void onAccelerationChanged(float x, float y, float z) {
		// As useful as Paris Hilton
	}

	/**
	 * Triggers when the phone is shaked
	 * 
	 * @author Alexandre Thiboult
	 */
	public void onShake(float force) {
		AlertDialog.Builder adb = new AlertDialog.Builder(StickListActivity.this);
		adb.setTitle(R.string.your_pledge);
		// For demo reasons
		adb.setMessage("Vous devez dire ces 3 mots dans l'heure : \r \n" + "SMURF" + "\t" + "VACHE" + "\t" + "PRISE");
		adb.setPositiveButton("RETOUR", null);
		adb.show();
	}
}
