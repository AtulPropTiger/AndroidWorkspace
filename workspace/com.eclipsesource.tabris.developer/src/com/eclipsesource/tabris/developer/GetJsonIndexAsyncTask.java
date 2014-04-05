
package com.eclipsesource.tabris.developer;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;

import com.eclipsesource.tabris.android.internal.transport.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class GetJsonIndexAsyncTask extends AsyncTask<String, Integer, String> {

  private final LauncherActivity activity;
  private Exception error;
  private ProgressDialog pd;
  private String indexJsonUrl;

  public GetJsonIndexAsyncTask( LauncherActivity activity ) {
    this.activity = activity;
  }

  @Override
  protected String doInBackground( String... params ) {
    String result = null;
    try {
      indexJsonUrl = params[ 0 ];
      HttpRequest httpRequest = createHttpRequest();
      if( httpRequest.ok() ) {
        result = httpRequest.body();
      } else {
        error = new Exception( "Could not fetch rwt-resources/index.json. Response code: "
                               + httpRequest.code() );
      }
    } catch( Exception e ) {
      error = e;
    }
    return result;
  }

  private HttpRequest createHttpRequest() {
    HttpRequest httpRequest = HttpRequest.get( indexJsonUrl )
      .trustAllCerts()
      .trustAllHosts()
      .uncompress( true )
      .acceptGzipEncoding();
    return httpRequest;
  }

  @Override
  protected void onPreExecute() {
    pd = new ProgressDialog( activity );
    pd.setProgressStyle( ProgressDialog.STYLE_SPINNER );
    pd.setMessage( "Discovering entry points..." );
    pd.setIndeterminate( true );
    pd.show();
  }

  @Override
  protected void onPostExecute( String result ) {
    if( pd.isShowing() ) {
      pd.cancel();
    } else {
      return;
    }
    if( error != null ) {
      displayError( activity, error );
      return;
    }
    try {
      String[] entrypoints = createEntriesArray( result );
      showSelectionDialog( entrypoints );
    } catch( Exception e ) {
      displayError( activity, e );
    }
  }

  private void showSelectionDialog( final String[] entrypoints ) {
    AlertDialog.Builder builder = new AlertDialog.Builder( activity );
    builder.setTitle( "Choose Entry Point" );
    String[] strippedEntrypoints = stripEntrypoints( entrypoints );
    builder.setItems( strippedEntrypoints, new DialogInterface.OnClickListener() {

      public void onClick( DialogInterface dialog, int item ) {
        String host = indexJsonUrl.substring( 0, indexJsonUrl.lastIndexOf( '/' ) );
        String url = host + "/" + entrypoints[ item ];
        try {
          activity.startTabrisActivity( new URL( url ).toExternalForm() );
        } catch( MalformedURLException e ) {
        }
      }
    } );
    builder.show();
  }

  private String[] stripEntrypoints( String[] entrypoints ) {
    String stripped[] = new String[ entrypoints.length ];
    for( int i = 0; i < entrypoints.length; i++ ) {
      String entrypoint = entrypoints[ i ];
      stripped[ i ] = entrypoint.substring( 3, entrypoint.length() );
    }
    return stripped;
  }

  private String[] createEntriesArray( String result ) {
    Gson gson = new GsonBuilder().create();
    JsonReader reader = new JsonReader( new StringReader( result ) );
    reader.setLenient( true );
    Map<String, Object> json = gson.fromJson( reader, Map.class );
    List<Map<String, Object>> entryPointList = ( List<Map<String, Object>> )json.get( "entrypoints" );
    final String[] entrypoints = new String[ entryPointList.size() ];
    for( int i = 0; i < entrypoints.length; i++ ) {
      entrypoints[ i ] = ( String )entryPointList.get( i ).get( "path" );
    }
    return entrypoints;
  }

  private static void displayError( Activity activity, Exception error ) {
    AlertDialog.Builder builder = new AlertDialog.Builder( activity );
    builder.setTitle( "Discovery Failed" );
    builder.setMessage( error.getMessage() );
    builder.setNeutralButton( R.string.ok, new OnClickListener() {

      public void onClick( DialogInterface dialog, int which ) {
        dialog.cancel();
      }
    } );
    builder.show();
  }

}
