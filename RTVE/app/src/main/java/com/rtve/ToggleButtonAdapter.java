package com.rtve;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/4/2015.
 */
public class ToggleButtonAdapter
        extends BaseAdapter
        implements View.OnClickListener,
                   RadioGroup.OnCheckedChangeListener
{
   private final RadioGroup   rgp;
   private final List<Camera> cameras;
   private       Context      mContext;

   public ToggleButtonAdapter(Context c)
   {
      this(c, new ArrayList<Camera>());
   }

   public ToggleButtonAdapter(Context c, List<Camera> cameras)
   {
      mContext = c;
      rgp = new RadioGroup(c);
      rgp.setOnCheckedChangeListener(this);
      this.cameras = cameras;
   }


   public void addCamera(Camera c)
   {
      cameras.add(c);
      this.notifyDataSetChanged();
   }

   public void removeCamera(Camera c)
   {
      cameras.remove(c);
      this.notifyDataSetChanged();
   }

   public void clearCameras()
   {
      cameras.clear();
      this.notifyDataSetChanged();
   }

   @Override
   public int getCount()
   {
      return cameras.size();
   }

   @Override
   public Object getItem(int position)
   {
      return cameras.get(position);
   }

   @Override
   public long getItemId(int position)
   {
      return cameras.get(position).getNumber();
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
         button.setId(cameras.get(position).getNumber());
         rgp.addView(button);
      }
      else
      {
         button = (ToggleButton) convertView;
      }

      String text = cameras.get(position).toString();
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
//      ((ToggleButton) v).setChecked();
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
}
