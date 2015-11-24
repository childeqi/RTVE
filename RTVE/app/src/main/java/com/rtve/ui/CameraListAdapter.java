package com.rtve.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.rtve.R;
import com.rtve.common.CameraConfig;
import com.rtve.common.CameraConfigList;
import com.rtve.common.CameraTimeRecorder;

/**
 * Created by Matt on 10/4/2015.
 */
public class CameraListAdapter
        extends BaseAdapter
        implements CameraView.CameraViewDeletionListener,
                   CameraViewGroup.SelectedCameraListener,
                   CameraConfigList.CameraListChangeListener
{
   private final CameraViewGroup    cvGroup;
   private final CameraConfigList   configList;
   private       CameraTimeRecorder recorder;
   private       Context            mContext;

   public CameraListAdapter(Context c,
                            CameraConfigList configList,
                            CameraTimeRecorder recorder)
   {
      mContext = c;
      this.recorder = recorder;

      this.configList = configList;
      configList.addChangeListener(this);

      cvGroup = new CameraViewGroup();
      cvGroup.addSelectedCameraListener(this);

   }

   public void resetTiming(CameraTimeRecorder newRecorder)
   {
      cvGroup.resetCameraViews();
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
      CameraView camView;
      if (convertView == null)
      {
         // if it's not recycled, initialize some attributes
         camView = (CameraView) LayoutInflater.from(mContext).inflate(R.layout.camera_view,
                                                          null, false);
//         camView = new CameraView(mContext);
         camView.addDeletionListener(this);
         cvGroup.addCameraView(camView);
      }
      else
      {
         camView = (CameraView) convertView;
      }

      camView.setCameraConfig((CameraConfig) getItem(position));
      return camView;
   }

   @Override
   public void cameraListChanged(CameraConfigList list)
   {
      this.notifyDataSetChanged();
   }

   @Override
   public void deletePressed(CameraView view)
   {
      configList.remove(view.getCameraConfig());
      cvGroup.removeCameraView(view);
      Toast.makeText(view.getContext(),
                     "\"" + view.getCameraConfig().getName() +
                             "\" camera deleted.",
                     Toast.LENGTH_SHORT).show();
   }

   @Override
   public void selectedCameraChanged(CameraView selected)
   {
      if (selected != null)
      {
         recorder.cameraSelected(selected.getCameraConfig());
      }
   }
}
