package com.rtve.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.rtve.R;
import com.rtve.common.CameraConfig;
import com.rtve.common.CameraConfigList;


public class MainActivity
        extends AppCompatActivity
        implements AddCameraDialogFragment.AddCameraDialogListener
{
   private CameraListAdapter cameraListAdapter;
   private CameraConfigList  cameraConfigList;

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      switch (item.getItemId())
      {
         case R.id.action_addCam:
            openAddCameraDialog();
            return true;

         case R.id.action_stop:
            stopPressed();
            return true;

         // this is where how the settings menu will be opened, when implemented
//         case R.id.action_settings:
//            openSettings();
//            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }

   public void openAddCameraDialog()
   {
      DialogFragment dialog = AddCameraDialogFragment.createFragment(cameraConfigList);
      dialog.show(getFragmentManager(), "AddCameraDialogFragment");
   }

   public void stopPressed()
   {

   }

   @Override
   public void cameraAdded(AddCameraDialogFragment dialog, CameraConfig newConfig)
   {
      cameraConfigList.add(newConfig);
   }

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      GridView gridview = (GridView) findViewById(R.id.gridview);
      cameraConfigList = new CameraConfigList();
      cameraListAdapter = new CameraListAdapter(this, cameraConfigList);
      gridview.setAdapter(cameraListAdapter);
   }
}
