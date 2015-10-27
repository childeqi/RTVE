package com.rtve.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Matt on 10/11/2015.
 */
public class CameraConfigList implements Parcelable
{
   public interface CameraListChangeListener
   {
      void cameraListChanged(CameraConfigList list);
   }

   private final List<CameraConfig> camList;
   private final List<CameraListChangeListener> listenerList;

   public CameraConfigList()
   {
      this(new ArrayList<CameraConfig>());
   }

   public CameraConfigList(List<CameraConfig> initialList)
   {
      this.camList = initialList;
      this.listenerList = new ArrayList<>();
   }

   /**
    * The list of listeners is NOT retained.
    * @param in
    */
   private CameraConfigList(Parcel in)
   {
      camList = new ArrayList<CameraConfig>();
      in.readTypedList(camList, CameraConfig.CREATOR);
      this.listenerList = new ArrayList<>();
   }

   public void addChangeListener(CameraListChangeListener listener)
   {
      listenerList.add(listener);
   }

   /**
    * Returns an unmodifiable list of the instances of {@code CameraListChangeListener} currently
    * attached to this list.
    * @return an unmodifiable list of the currently attached listeners
    */
   public List<CameraListChangeListener> getChangeListeners()
   {
      return Collections.unmodifiableList(listenerList);
   }

   public void removeChangeListener(CameraListChangeListener listener)
   {
      listenerList.remove(listener);
   }

   private void fireListChanged()
   {
      for (CameraListChangeListener listener : listenerList)
      {
         listener.cameraListChanged(this);
      }
   }

   public void add(CameraConfig config)
   {
      camList.add(config);
      fireListChanged();
   }

   public void remove(CameraConfig config)
   {
      camList.remove(config);
      fireListChanged();
   }

   public void clear()
   {
      camList.clear();
      fireListChanged();
   }

   public CameraConfig get(int index)
   {
      return camList.get(index);
   }

   public int size()
   {
      return camList.size();
   }

   /**
    * Returns an unmodifiable view of the backing {@code List<CameraConfig>} used internally by this
    * class.
    */
   public List<CameraConfig> getBackingList()
   {
      return Collections.unmodifiableList(camList);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel out, int flags) {
      out.writeTypedList(camList);
   }

   public static final Parcelable.Creator<CameraConfigList> CREATOR
           = new Parcelable.Creator<CameraConfigList>() {
      public CameraConfigList createFromParcel(Parcel in) {
         return new CameraConfigList(in);
      }

      public CameraConfigList[] newArray(int size) {
         return new CameraConfigList[size];
      }
   };
}
