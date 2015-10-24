package com.rtve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rtve.R;

public class LandingScreenActivity extends AppCompatActivity
{

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_landing_screen);

//      if (savedInstanceState == null)
//      {
//         getSupportFragmentManager().beginTransaction()
//                                    .add(R.id.container, new PlaceholderFragment())
//                                    .commit();
//      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_landing_screen, menu);
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

   public void newConfig(View view)
   {
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
   }

   public void loadConfig(View view)
   {
      Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
   }

//   /**
//    * A placeholder fragment containing a simple view.
//    */
//   public static class PlaceholderFragment extends Fragment
//   {
//
//      public PlaceholderFragment() { }
//
//      @Override
//      public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                               Bundle savedInstanceState) {
//         View rootView = inflater.inflate(R.layout.fragment_display_message,
//                                          container, false);
//         return rootView;
//      }
//   }
}
