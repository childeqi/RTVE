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
import com.rtve.common.CameraTimeRecorder;
import com.rtve.common.TimeSlot;

import java.util.List;


public class MainActivity
        extends AppCompatActivity
        implements AddCameraDialogFragment.AddCameraDialogListener,
                   CameraTimeRecorder.TimingStartedCallback
{
   private CameraListAdapter cameraListAdapter;
   private CameraConfigList  cameraConfigList;
   private CameraTimeRecorder camTimeRecorder;
   private boolean stopMenuItemEnabled   = false;
   private boolean addCamMenuItemEnabled = true;

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);

      return true;
   }

   @Override
   public boolean onPrepareOptionsMenu(Menu menu)
   {
      // functionally enable/disable the stop action bar item
      MenuItem stop = menu.findItem(R.id.action_stop);
      stop.setEnabled(stopMenuItemEnabled);
      // make the stop icon on the action bar look enabled/disabled
      stop.getIcon().setAlpha(stopMenuItemEnabled ? 255 : 64);

      // functionally enable/disable the add camera action bar item
      MenuItem add = menu.findItem(R.id.action_addCam);
      add.setEnabled(addCamMenuItemEnabled);
      // make the add camera icon on the action bar look enabled/disabled
      add.getIcon().setAlpha(addCamMenuItemEnabled ? 255 : 64);

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

   public void setAddCamMenuItemEnabled(boolean enabled, boolean invalidate)
   {
      if (addCamMenuItemEnabled != enabled)
      {
         addCamMenuItemEnabled = enabled;
         if (invalidate) invalidateOptionsMenu();
      }
   }

   public void setStopMenuItemEnabled(boolean enabled, boolean invalidate)
   {
      if (stopMenuItemEnabled != enabled)
      {
         stopMenuItemEnabled = enabled;
         if (invalidate) invalidateOptionsMenu();
      }
   }

   public void openAddCameraDialog()
   {
      DialogFragment dialog = AddCameraDialogFragment.createFragment(cameraConfigList);
      dialog.show(getFragmentManager(), "AddCameraDialogFragment");
   }

   public void stopPressed()
   {
      List<TimeSlot> timeList = camTimeRecorder.stopTiming();

      System.out.println("******************* TIMES **************************");
      for (TimeSlot slot : timeList)
      {
         System.out.println(slot.toString());
      }
      System.out.println("****************************************************");

      // *************************** CALL CORE HERE ***********************************
      // core.save(timeList);
      // ******************************************************************************

      camTimeRecorder = new CameraTimeRecorder(this);
      cameraListAdapter.resetTiming(camTimeRecorder);
      setAddCamMenuItemEnabled(true, false);
      setStopMenuItemEnabled(false, true);


   }

   @Override
   public void cameraAdded(AddCameraDialogFragment dialog, CameraConfig newConfig)
   {
      cameraConfigList.add(newConfig);
   }

   @Override
   public void timingStarted()
   {
      setAddCamMenuItemEnabled(false, false);
      setStopMenuItemEnabled(true, true);
   }

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      GridView gridview = (GridView) findViewById(R.id.gridview);
      cameraConfigList = new CameraConfigList();
      camTimeRecorder = new CameraTimeRecorder(this);
      cameraListAdapter = new CameraListAdapter(this, cameraConfigList, camTimeRecorder);
      gridview.setAdapter(cameraListAdapter);
   }
}
