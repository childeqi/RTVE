package com.rtve.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.rtve.common.CameraConfig;
import com.rtve.common.CameraConfigList;
import com.rtve.common.CameraTimeRecorder;

/**
 * Created by Matt on 10/4/2015.
 */
public class CameraListAdapter
        extends BaseAdapter
        implements View.OnClickListener,
                   RadioGroup.OnCheckedChangeListener,
                   CameraConfigList.CameraListChangeListener
{
   private final RadioGroup         rgp;
   private final CameraConfigList   configList;
   private       CameraTimeRecorder recorder;
   private       Context            mContext;

   public CameraListAdapter(Context c,
                            CameraConfigList configList,
                            CameraTimeRecorder recorder)
   {
      mContext = c;
      rgp = new RadioGroup(c);
      rgp.setOnCheckedChangeListener(this);
      this.configList = configList;
      configList.addChangeListener(this);
      this.recorder = recorder;
   }

   public void resetTiming(CameraTimeRecorder newRecorder)
   {
      rgp.clearCheck();
      this.recorder = newRecorder;
   }

   public CameraConfigList getCameraConfigList()
   {
      return configList;
   }

   @Override
   public int getCount()
   {
      return configList.size();
   }

   @Override
   public Object getItem(int position)
   {
      return configList.get(position);
   }

   @Override
   public long getItemId(int position)
   {
      return configList.get(position).getNumber();
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent)
   {
      ToggleButton button;
      if (convertView == null)
      {
         // if it's not recycled, initialize some attributes
         button = new ToggleButton(mContext);
         button.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,
                                                          GridView.LayoutParams.MATCH_PARENT));
         button.setTextIsSelectable(false);
         button.setMaxLines(2);
         button.setHorizontallyScrolling(false);
         button.setPadding(20, 20, 20, 20);
         button.setTextSize(20);
         button.setGravity(Gravity.CENTER);
         button.setAllCaps(false);
         button.setOnClickListener(this);
         rgp.addView(button);
      }
      else
      {
         button = (ToggleButton) convertView;
      }

      String text = configList.get(position).toString();
      button.setId(configList.get(position).getNumber());
      button.setText(text);
      button.setTextOn(text);
      button.setTextOff(text);
      return button;
   }

   @Override
   public void onClick(View v)
   {
      rgp.clearCheck();
      rgp.check(v.getId());
      recorder.cameraSelected(getCameraWithId(v.getId()));
   }

   @Override
   public void onCheckedChanged(final RadioGroup radioGroup, final int i)
   {
      for (int j = 0; j < radioGroup.getChildCount(); j++)
      {
         final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
         view.setChecked(view.getId() == i);
      }
   }

   @Override
   public void cameraListChanged(CameraConfigList list)
   {
      this.notifyDataSetChanged();
   }

   private CameraConfig getCameraWithId(int id)
   {
      for (CameraConfig config : configList.getBackingList())
      {
         if (config.getNumber() == id)
         {
            return config;
         }
      }
      return null;
   }
}
