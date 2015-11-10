package com.rtve.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rtve.R;
import com.rtve.core.storage.CameraConfigLoader;

import java.util.HashMap;
import java.util.List;

public class LoadConfigurationActivity extends AppCompatActivity
{
   public final static String SELECTED_CONFIG = "com.rtve.ui.SELECTED_CONFIG";

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_load_configuration);

      final ListView listview = (ListView) findViewById(R.id.load_config_listview);

      List<String> availableConfigNames = CameraConfigLoader.getAvailableConfigNames(this);

      final StableArrayAdapter adapter = new StableArrayAdapter(this,
                                                                android.R.layout.simple_list_item_1,
                                                                availableConfigNames);
      listview.setAdapter(adapter);

      listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {

         @Override
         public void onItemClick(AdapterView<?> parent, final View view,
                                 int position, long id)
         {
            final String item = (String) parent.getItemAtPosition(position);

            new AlertDialog.Builder(LoadConfigurationActivity.this)
                    .setTitle("Confirm Selection")
                    .setMessage("Load the \"" + item + "\" configuration?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                       public void onClick(DialogInterface dialog, int whichButton)
                       {
                          loadConfiguration(item);
                       }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
         }

      });
   }

   private void loadConfiguration(String configName)
   {
      Intent intent = new Intent(this, MainActivity.class);
      intent.putExtra(SELECTED_CONFIG, configName);
      startActivity(intent);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_load_configuration, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();

      //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings)
      {
         return true;
      }

      return super.onOptionsItemSelected(item);
   }

   private class StableArrayAdapter extends ArrayAdapter<String>
   {

      HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

      public StableArrayAdapter(Context context, int textViewResourceId,
                                List<String> objects)
      {
         super(context, textViewResourceId, objects);
         for (int i = 0; i < objects.size(); ++i)
         {
            mIdMap.put(objects.get(i), i);
         }
      }

      @Override
      public long getItemId(int position)
      {
         String item = getItem(position);
         return mIdMap.get(item);
      }

      @Override
      public boolean hasStableIds()
      {
         return true;
      }

   }
}
