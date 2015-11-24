package com.rtve.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.rtve.R;
import com.rtve.common.CameraConfig;
import com.rtve.common.CameraConfigList;
import com.rtve.common.CameraTimeRecorder;
import com.rtve.common.TimeSlot;
import com.rtve.core.CoreInterface;
import com.rtve.core.XMLExporter;
import com.rtve.core.storage.CameraConfigLoader;
import com.rtve.core.storage.CameraConfigSaver;

import java.util.List;


public class MainActivity
        extends AppCompatActivity
        implements AddCameraDialogFragment.AddCameraDialogListener,
                   CameraTimeRecorder.TimingStartedCallback,
                   CameraConfigList.CameraListChangeListener
{
   private CameraListAdapter  cameraListAdapter;
   private CameraConfigList   cameraConfigList;
   private CameraTimeRecorder camTimeRecorder;
   private boolean stopMenuItemEnabled   = false;
   private boolean addCamMenuItemEnabled = true;
   private boolean saveMenuItemEnabled   = false;

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

      // functionally enable/disable the save camera action bar item
      MenuItem save = menu.findItem(R.id.action_save);
      save.setEnabled(saveMenuItemEnabled);
      // make the add camera icon on the action bar look enabled/disabled
      save.getIcon().setAlpha(saveMenuItemEnabled ? 255 : 64);

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

         case R.id.action_save:
            saveConfig();
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

   public void setSaveMenuItemEnabled(boolean enabled, boolean invalidate)
   {
      if (saveMenuItemEnabled != enabled)
      {
         saveMenuItemEnabled = enabled;
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
      CoreInterface  core     = new XMLExporter();

      /* This proves that the timing works, and can be used for debugging
      System.out.println("******************* TIMES **************************");
      for (TimeSlot slot : timeList)
      {
         System.out.println(slot.toString());
      }
      System.out.println("****************************************************");
      */

      // *************************** CALL CORE HERE ***********************************
      core.save(timeList, this);
      core.send(this);
      // ******************************************************************************


      camTimeRecorder = new CameraTimeRecorder(this);
      cameraListAdapter.resetTiming(camTimeRecorder);
      setAddCamMenuItemEnabled(true, false);
      setStopMenuItemEnabled(false, true);
   }

   public void saveConfig()
   {
      // get prompts.xml view
      LayoutInflater li          = LayoutInflater.from(this);
      View           promptsView = li.inflate(R.layout.prompts, null);

      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

      // set prompts.xml to alertdialog builder
      alertDialogBuilder.setView(promptsView);

      final EditText userInput = (EditText) promptsView.findViewById(R.id.save_config_name_edittext);

      // set dialog message
      alertDialogBuilder
              .setCancelable(false)
              .setPositiveButton("OK",
                                 new DialogInterface.OnClickListener()
                                 {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                       //Do nothing here because we override this button later to
                                       // change the close behaviour.
                                       //However, we still need this because on older versions of
                                       // Android unless we
                                       //pass a handler the button doesn't get instantiated
                                    }
                                 })
              .setNegativeButton("Cancel",
                                 new DialogInterface.OnClickListener()
                                 {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                       dialog.cancel();
                                    }
                                 });

      // create alert dialog
      final AlertDialog alertDialog = alertDialogBuilder.create();

      // show it
      alertDialog.show();

      Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
      positiveButton.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            // get user input and use it as the configuration name
            String input = userInput.getText().toString();
            if (input.trim().isEmpty())
            {
               Toast.makeText(MainActivity.this,
                              "Invalid Configuration Name",
                              Toast.LENGTH_SHORT).show();

               // since we didn't save, don't do anything (so dialog doesn't close)
            }
            else
            {
               String toastText = null;
               try
               {
                  CameraConfigSaver.save(MainActivity.this,
                                         cameraConfigList,
                                         input.trim());
                  toastText = "Configuration Saved";
               }
               catch (Exception e)
               {
                  Log.e(getClass().getSimpleName(), "Failed to save configuration", e);
                  toastText = "Failed to save configuration";
               }
               finally
               {
                  alertDialog.cancel();
                  if (toastText != null)
                  {
                     Toast.makeText(MainActivity.this,
                                    toastText,
                                    Toast.LENGTH_SHORT).show();
                  }
               }

            }
         }
      });


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

      Intent intent     = getIntent();
      String configName = intent.getStringExtra(LoadConfigurationActivity.SELECTED_CONFIG);

      if (configName == null)
      {
         cameraConfigList = new CameraConfigList();
      }
      else
      {
         try
         {
            cameraConfigList = CameraConfigLoader.load(this, configName);
         }
         catch (Exception e)
         {
            Log.e(getClass().getSimpleName(), "Failed to load cameraConfigList", e);
            cameraConfigList = new CameraConfigList();
         }
      }

      GridView gridview = (GridView) findViewById(R.id.gridview);
      cameraConfigList.addChangeListener(this);
      camTimeRecorder = new CameraTimeRecorder(this);
      cameraListAdapter = new CameraListAdapter(this, cameraConfigList, camTimeRecorder);
      gridview.setAdapter(cameraListAdapter);
   }

   @Override
   public void cameraListChanged(CameraConfigList list)
   {
      if (cameraConfigList.size() > 0)
      {
         setSaveMenuItemEnabled(true, true);
      }
      else
      {
         setSaveMenuItemEnabled(false, true);
      }
   }
}
