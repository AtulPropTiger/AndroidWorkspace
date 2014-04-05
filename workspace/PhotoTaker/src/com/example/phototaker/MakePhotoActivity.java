package com.example.phototaker;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


public class MakePhotoActivity extends Activity {
  final static String DEBUG_TAG = "MakePhotoActivity";
  private Camera camera;
  private int cameraId = 0;
  private CameraPreview mPreview;
  public static Button captureButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
	  
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    Log.i("oncreate was just called", "on created was caleld uo");
    /*
    // do we have a camera?
    if (!getPackageManager()
        .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
      Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
          .show();
    } else {
      cameraId = findFrontFacingCamera();
    	
      if (cameraId < 0) {
        Toast.makeText(this, "No front facing camera found.",
            Toast.LENGTH_LONG).show();
      } else {
        camera = Camera.open(cameraId);
      }
    }
  
	  if (camera != null) {
		    Log.d("camera state","camera is NOT null");  
		  }else{
		    Log.d("camera state","camera is null");
		    camera = android.hardware.Camera.open(cameraId);
		  }
	  
  
  
  		*/
	  
    final PictureCallback mPicture = new PictureCallback() {

	    @Override//
	    public void onPictureTaken(byte[] data, Camera camera) {
	    	Log.i("GGGGGGGGOOOOOOOD ! PICTURE TAKEN", "YES YES YES YES");
	        File pictureFile = getDir();//getOutputMediaFile(FileColumns.MEDIA_TYPE_IMAGE);
	        if (pictureFile == null){
	            Log.d("TAG", "Error creating media file, check storage permissions: ");
	            return;
	        }

	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	        } catch (FileNotFoundException e) {
	            Log.d("TAG", "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d("TAG", "Error accessing file: " + e.getMessage());
	        }
	       
	        
	        
	    }
	    
	    

	    
	};


    
    
      camera = getCameraInstance();

      // Create our Preview view and set it as the content of our activity.
      
      mPreview = new CameraPreview(this, camera);
  
      FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
      preview.addView(mPreview);
	  
      
      //camera.takePicture(null, null, mPicture);
      
      
      
	  captureButton = (Button) findViewById(R.id.captureFront);
	  captureButton.setOnClickListener(
			    new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			            // get an image from the camera
			            //camera.takePicture(null, null, mPicture);
			            camera.takePicture(null, null, mPicture);
			            Log.i("yepyepyepype", "yepeypeyeyepyep");
			              
			        }
			    }
			);		  
			
	  //captureButton.callOnClick();
	  //captureButton.performClick();

  }
  
  
  public static void sendClick(){
	  captureButton.performClick();
	  
  }
			  
		  

  /*
  @Override
  public void onStart(){
	  	super.onStart();
	  	setContentView(R.layout.main);
	  	
	  	
	  	Log.i("is this called? on start", "on start called?");

	    

	      // Create our Preview view and set it as the content of our activity.
	      

	      

	 // camera.takePicture(null, null, micture);
  }
	  
	*/
  


  private int findFrontFacingCamera() {
    int cameraId = -1;
    // Search for the front facing camera
    int numberOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numberOfCameras; i++) {
      CameraInfo info = new CameraInfo();
      Camera.getCameraInfo(i, info);
      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
        Log.d(DEBUG_TAG, "Camera found");
        cameraId = i;
        break;
      }
    }
    return cameraId;
  }

  @Override
  protected void onPause() {
    if (camera != null) {
      camera.release();
      camera = null;
    }
    super.onPause();
  }
  
  
  public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}

  

   File getDir() {
    //File sdDir = Environment
     // .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
      Log.d(MakePhotoActivity.DEBUG_TAG, "get dir was called");
    File sdDir =  Environment
    	      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    //Environment.getDataDirectory();
   //File sdDir = new File(Environment.getExternalStoragePublicDirectory(
	//	   Environment.DIRECTORY_PICTURES), "Path");
   //sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, 
   //Uri.parse("file://"+ sdDir)));
    return new File(sdDir, "CameraAPIDemo");
  }
  
} 
