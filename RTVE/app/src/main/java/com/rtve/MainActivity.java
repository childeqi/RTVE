package com.rtve;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;


public class MainActivity extends AppCompatActivity

{
   private ToggleButtonAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      GridView gridview = (GridView) findViewById(R.id.gridview);
      adapter = new ToggleButtonAdapter(this);
      gridview.setAdapter(adapter);

//      gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
//      {
//         public void onItemClick(AdapterView<?> parent, View v, int position, long id)
//         {
//            Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//         }
//      });

      // Execute some code after 2 seconds have passed
      Handler handler = new Handler();
      handler.postDelayed(new Runnable()
      {
         public void run()
         {
            adapter.addCamera(CameraFactory.createCamera("First Narrow", LensType.NARROW));
            adapter.addCamera(CameraFactory.createCamera("2nd Narrow", LensType.NARROW));
            adapter.addCamera(CameraFactory.createCamera("3rd Narrow", LensType.NARROW));
            adapter.addCamera(CameraFactory.createCamera("First Wide", LensType.WIDE));
            adapter.addCamera(CameraFactory.createCamera("Long Long Long Long Long",
                                                         LensType.WIDE));
         }
      }, 2000);
   }

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
//         case R.id.action_settings:
//            openSettings();
//            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }

   public void openAddCameraDialog()
   {

   }

   public void stopPressed()
   {

   }
}
