package com.rtve.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/12/2015.
 */
public class CameraTimeRecorder
{
   public interface TimingStartedCallback
   {
      void timingStarted();
   }

   private final List<TimeSlot> timeList;
   private final TimingStartedCallback callback;

   private boolean      first;
   private CameraConfig currentCamera;
   private TimeSlot     currentTimeSlot;

   public CameraTimeRecorder(TimingStartedCallback callback)
   {
      this.callback = callback;
      timeList = new ArrayList<>();
      first = true;
   }

   public void cameraSelected(CameraConfig camera)
   {
      if (first)
      {
         first = false; // never again
         currentCamera = camera;
         currentTimeSlot = new TimeSlot(camera, new CameraTime());
         callback.timingStarted();
      }
      else if (!camera.equals(currentCamera))
      {
         CameraTime now = new CameraTime();
         timeList.add(currentTimeSlot.stopAt(now));
         currentCamera = camera;
         currentTimeSlot = new TimeSlot(camera, now);
      }
   }

   public List<TimeSlot> stopTiming()
   {
      timeList.add(currentTimeSlot.stopAt(new CameraTime()));
      return timeList;
   }

}
