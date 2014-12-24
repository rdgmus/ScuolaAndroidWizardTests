package it.keyorchestra.registroandroid.admin.options.test;

import it.keyorchestra.registroandroid.admin.options.R;
import it.keyorchestra.registroandroid.admin.options.ScuolaWizard;
import it.keyorchestra.registroandroid.admin.options.dbMatthed.DatabaseOps;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class ScuolaWizardTest extends
		ActivityInstrumentationTestCase2<ScuolaWizard> {

	private ScuolaWizard mFirstTestActivity;
	private TextView tvHash;
	private SharedPreferences getPrefs;
	private final String _hash = "1419364991.2343$2y$10$hP/rRuAIQfTjT6CFb1yZ0uYg22CVvasz67ID1a76OEkguT7E3KdiS";
	private final long _id_utente = 46;
	private final String _ruoloScelto = "AMMINISTRATORE";

	public ScuolaWizardTest() {
		super(ScuolaWizard.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.test.ActivityInstrumentationTestCase2#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();

		mFirstTestActivity = getActivity();
		tvHash = (TextView) mFirstTestActivity.findViewById(R.id.tvHash);

		getPrefs = mFirstTestActivity.getPreferences(Activity.MODE_PRIVATE);

		assertNotNull("getPrefs is NULL", getPrefs);
		
		mFirstTestActivity.getIntent().putExtra(Intent.EXTRA_TEXT,
				"Utente: [" + _id_utente + "] Ruolo: " + _ruoloScelto);

		// Editor editor = getPrefs.edit();
		// editor.putLong("id_utente", _id_utente);
		// editor.putString("hash", _hash);
		// editor.putString("databaseList", "MySQL");
		//
		// editor.putString("ipMySQL", "192.168.0.215");
		// editor.putString("userNameMySQL", "root");
		// editor.putString("userPasswdMySQL", "myzconun");
		// editor.putString("portMySQL", "3307");
		// editor.putString("schemaMySQL", "scuola");
		//
		// editor.apply();

		Bundle bundle = packPreferences();
		mFirstTestActivity.getIntent().putExtras(bundle);
		unPackPreferences(bundle);
	}

	public void testPreconditions() {
		assertNotNull("mFirstTestActivity is null", mFirstTestActivity);
		assertNotNull("tvHash is null", tvHash);
		assertTrue("id_utente non valorizzato",
				getPrefs.getLong("id_utente", -1) == _id_utente);
		assertTrue("hash non presente",
				getPrefs.getString("hash", "").equals(_hash));

		assertTrue("databaseList non valorizzato",
				getPrefs.getString("databaseList", "").equals("MySQL"));
		assertTrue("ipMySQL non valorizzato", getPrefs.getString("ipMySQL", "")
				.equals("192.168.0.215"));
		assertTrue("userNameMySQL non valorizzato",
				getPrefs.getString("userNameMySQL", "").equals("root"));
		assertTrue("userPasswdMySQL non valorizzato",
				getPrefs.getString("userPasswdMySQL", "").equals("myzconun"));
		assertTrue("portMySQL non valorizzato",
				getPrefs.getString("portMySQL", "").equals("3307"));
		assertTrue("schemaMySQL non valorizzato",
				getPrefs.getString("schemaMySQL", "").equals("scuola"));
	}

	public void testMyFirstTestTextView_labelText() {
		final String expected = mFirstTestActivity
				.getString(R.string.hashValue);
		final String actual = tvHash.getText().toString();
		assertEquals(expected, actual);
	}

	public void testAuthorization() {
		String sharedText = mFirstTestActivity.getIntent().getStringExtra(
				Intent.EXTRA_TEXT);
		assertNotNull("sharedText is null", sharedText);

		assertTrue("id_utente non valorizzato",
				getPrefs.getLong("id_utente", -1) == _id_utente);
		assertTrue("hash non presente",
				getPrefs.getString("hash", "").equals(_hash));

		try {
			Class<?> driver = Class.forName("com.mysql.jdbc.Driver");
			assertNotNull("com.mysql.jdbc.Driver is NULL", driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		DatabaseOps databaseOps = new DatabaseOps(mFirstTestActivity);
		boolean result = databaseOps.testUserHasHash(mFirstTestActivity,
				getPrefs.getLong("id_utente", -1),
				getPrefs.getString("hash", ""));

		assertTrue("databaseOps.testUserHasHash() is FALSE", result);
	}

	private Context getApplicationContext() {
		// TODO Auto-generated method stub
//		Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
//		return context;
		return mFirstTestActivity.getApplicationContext();
	
	}

	protected Bundle packPreferences() {
		// TODO Auto-generated method stub
		Bundle basket = new Bundle();
		long id_utente = getPrefs.getLong("id_utente", -1);
		basket.putLong("id_utente", id_utente);
		basket.putString("hash", _hash);

		// DATABASE
		basket.putString("databaseList", getPrefs.getString("databaseList", ""));

		// PostgreSQL
		basket.putString("ipPostgreSQL", getPrefs.getString("ipPostgreSQL", ""));
		basket.putString("userNamePostgreSQL",
				getPrefs.getString("userNamePostgreSQL", ""));
		basket.putString("userPasswdPostgreSQL",
				getPrefs.getString("userPasswdPostgreSQL", ""));
		basket.putString("portPostgreSQL",
				getPrefs.getString("portPostgreSQL", ""));
		basket.putString("schemaPostgreSQL",
				getPrefs.getString("schemaPostgreSQL", ""));

		// MySQL
		basket.putString("ipMySQL", getPrefs.getString("ipMySQL", ""));
		basket.putString("userNameMySQL",
				getPrefs.getString("userNameMySQL", ""));
		basket.putString("userPasswdMySQL",
				getPrefs.getString("userPasswdMySQL", ""));
		basket.putString("portMySQL", getPrefs.getString("portMySQL", ""));
		basket.putString("schemaMySQL", getPrefs.getString("schemaMySQL", ""));

		// PhpMySqlAndroid - SERVER SIDE SCRIPTINGs
		basket.putString("phpencoder", getPrefs.getString("phpencoder", ""));
		basket.putString("retrieveTableData",
				getPrefs.getString("retrieveTableData", ""));
		basket.putString("GraphViewInterface",
				getPrefs.getString("GraphViewInterface", ""));
		basket.putString("LogEventsRegisterInterface",
				getPrefs.getString("LogEventsRegisterInterface", ""));
		basket.putString("schemaMySQL", getPrefs.getString("schemaMySQL", ""));

		return basket;
	}

	@SuppressLint("NewApi")
	public void unPackPreferences(Bundle basket) {
		Editor editor = getPrefs.edit();
		editor.putLong("id_utente", basket.getLong("id_utente", -1l));
		editor.putString("hash", basket.getString("hash", ""));

		// DATABASE
		editor.putString("databaseList", basket.getString("databaseList", ""));

		// PostgreSQL
		editor.putString("ipPostgreSQL", basket.getString("ipPostgreSQL", ""));
		editor.putString("userNamePostgreSQL",
				basket.getString("userNamePostgreSQL", ""));
		editor.putString("userPasswdPostgreSQL",
				basket.getString("userPasswdPostgreSQL", ""));
		editor.putString("portPostgreSQL",
				basket.getString("portPostgreSQL", ""));
		editor.putString("schemaPostgreSQL",
				basket.getString("schemaPostgreSQL", ""));

		// MySQL
		editor.putString("ipMySQL", basket.getString("ipMySQL", ""));
		editor.putString("userNameMySQL", basket.getString("userNameMySQL", ""));
		editor.putString("userPasswdMySQL",
				basket.getString("userPasswdMySQL", ""));
		editor.putString("portMySQL", basket.getString("portMySQL", ""));
		editor.putString("schemaMySQL", basket.getString("schemaMySQL", ""));

		// PhpMySqlAndroid - SERVER SIDE SCRIPTINGs
		editor.putString("phpencoder", basket.getString("phpencoder", ""));
		editor.putString("retrieveTableData",
				basket.getString("retrieveTableData", ""));
		editor.putString("GraphViewInterface",
				basket.getString("GraphViewInterface", ""));
		editor.putString("LogEventsRegisterInterface",
				basket.getString("LogEventsRegisterInterface", ""));

		editor.apply();
	}


}
