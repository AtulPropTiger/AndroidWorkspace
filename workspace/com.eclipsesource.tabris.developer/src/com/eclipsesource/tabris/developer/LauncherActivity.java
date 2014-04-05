
package com.eclipsesource.tabris.developer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.eclipsesource.tabris.android.TabrisActivity;

public class LauncherActivity extends SherlockActivity {

  private static final String INDEX_JSON = "rwt-resources/index.json";

  private static final String PREFS_APP_END_POINT_URL = "appEndPointUrl";
  private static final String PREFS_USER_END_POINT_URL = "userEndPointUrl";
  private static final String PREFS_THEME = "theme";

  private static final String DEFAULT_URL = "http://";
  private static final int DEFAULT_THEME = 0;

  private TextView textEndPointUrl;
  private Spinner spinnerTheme;
  private Button buttonReconnect;

  @Override
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
      getWindow().requestFeature( Window.FEATURE_ACTION_BAR );
    }
    setContentView( R.layout.main );
    initContent();
    populateUi();
    start();
  }

  private void initContent() {
    initTextEndpoint();
    initSpinnerTheme();
    initButtonConnect();
    initButtonReconnect();
    initFeedback();
  }

  private void initTextEndpoint() {
    textEndPointUrl = ( TextView )findViewById( R.id.text_url );
    textEndPointUrl.setText( DEFAULT_URL );
    textEndPointUrl.setOnEditorActionListener( new TextView.OnEditorActionListener() {

      public boolean onEditorAction( TextView v, int actionId, KeyEvent event ) {
        if( actionId == EditorInfo.IME_ACTION_DONE ) {
          start();
          return true;
        }
        return false;
      }

    } );
  }

  private void initSpinnerTheme() {
    List<String> themes = Arrays.asList( "Manifest Theme",
                                         "Light Theme",
                                         "Light Theme (Dark ActionBar)",
                                         "Dark Theme" );
    ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,
                                                             android.R.layout.simple_spinner_item,
                                                             themes );
    adapter.setDropDownViewResource( com.eclipsesource.tabris.android.R.layout.spinner_dropdown_item );
    spinnerTheme = ( Spinner )findViewById( R.id.spinner_theme );
    spinnerTheme.setAdapter( adapter );
  }

  private void initButtonConnect() {
    View buttonConnect = findViewById( R.id.button_connect );
    buttonConnect.setOnClickListener( new OnClickListener() {

      public void onClick( View v ) {
        start();
      }

    } );
  }

  private void initButtonReconnect() {
    buttonReconnect = ( Button )findViewById( R.id.button_reconnect );
    buttonReconnect.setOnClickListener( new OnClickListener() {

      public void onClick( View v ) {
        SharedPreferences launcherPrefs = getPreferences( MODE_PRIVATE );
        String urlInPrefs = launcherPrefs.getString( PREFS_USER_END_POINT_URL, DEFAULT_URL );
        startTabrisActivity( urlInPrefs );
      }

    } );
  }

  private void initFeedback() {
    TextView textProvideFeedback = ( TextView )findViewById( R.id.provide_feedback_text );
    textProvideFeedback.setMovementMethod( LinkMovementMethod.getInstance() );
  }

  private void populateUi() {
    textEndPointUrl.setText( getUrl() );
    spinnerTheme.setSelection( getCurrentTheme() );
  }

  private String getUrl() {
    SharedPreferences prefs = getPreferences( MODE_PRIVATE );
    String userPrefUrl = prefs.getString( PREFS_USER_END_POINT_URL, null );
    String appPrefUrl = prefs.getString( PREFS_APP_END_POINT_URL, null );
    String propertiesUrl = getUrlFromPropertiesFile();
    if( validUrl( propertiesUrl ) && !propertiesUrl.equals( appPrefUrl ) ) {
      return propertiesUrl;
    } else if( validUrl( userPrefUrl ) ) {
      return userPrefUrl;
    } else if( validUrl( propertiesUrl ) ) {
      return propertiesUrl;
    }
    return DEFAULT_URL;
  }

  private boolean validUrl( String url ) {
    return url != null && url.length() > 0;
  }

  private String getUrlFromPropertiesFile() {
    Properties props = new Properties();
    try {
      props.load( getAssets().open( "launcher.properties" ) );
      return props.getProperty( "url", DEFAULT_URL );
    } catch( IOException e ) {
      // if we can not read the props file we assume there is none
      return null;
    }
  }

  private int getCurrentTheme() {
    return getPreferences( MODE_PRIVATE ).getInt( PREFS_THEME, DEFAULT_THEME );
  }

  private void start() {
    CharSequence text = textEndPointUrl.getText();
    if( text != null ) {
      String url = String.valueOf( text ).trim();
      discoverOrStart( url );
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    SharedPreferences launcherPrefs = getPreferences( MODE_PRIVATE );
    String url = launcherPrefs.getString( PREFS_USER_END_POINT_URL, DEFAULT_URL );
    if( DEFAULT_URL.equals( url ) ) {
      buttonReconnect.setVisibility( View.GONE );
    } else {
      buttonReconnect.setVisibility( View.VISIBLE );
    }
    if( url != null && url.length() > 0 ) {
      String entryPoint = url.substring( url.lastIndexOf( '/' ), url.length() );
      buttonReconnect.setText( "Connect to \"" + entryPoint + "\"" );
    }
  }

  private void discoverOrStart( String url ) {
    if( url.endsWith( "/" ) ) {
      url += INDEX_JSON;
    }
    if( url.endsWith( INDEX_JSON ) ) {
      loadDiscovery( url );
    } else {
      startTabrisActivity( url );
    }
  }

  private void loadDiscovery( String url ) {
    new GetJsonIndexAsyncTask( this ).execute( url );
  }

  void startTabrisActivity( String url ) {
    int theme = spinnerTheme.getSelectedItemPosition();
    persistDetails( url, theme );
    Intent intent = new Intent( this, TabrisActivity.class );
    intent.putExtra( TabrisActivity.EXTRA_END_POINT, url );
    intent.putExtra( TabrisActivity.EXTRA_THEME, theme );
    intent.putExtra( TabrisActivity.EXTRA_NETWORK_PROGRESS_INDICATOR_DELAY, 1500 );
    intent.putExtra( TabrisActivity.EXTRA_NETWORK_CONNECTION_TIMEOUT, 5000 );
    intent.putExtra( TabrisActivity.EXTRA_NETWORK_READ_TIMEOUT, 10000 );
    startActivityForResult( intent, 0 );
  }

  private void persistDetails( String url, int theme ) {
    SharedPreferences launcherPrefs = getPreferences( MODE_PRIVATE );
    Editor editor = launcherPrefs.edit();
    editor.putString( PREFS_USER_END_POINT_URL, url );
    editor.putString( PREFS_APP_END_POINT_URL, getUrlFromPropertiesFile() );
    editor.putInt( PREFS_THEME, theme );
    editor.commit();
  }

}
