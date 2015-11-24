package com.rtve.ui;

import android.util.Log;

import com.rtve.BuildConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Forces one CameraView in this group to be selected at any given time, like a RadioGroup for
 * CameraViews instead of RadioButtons.
 * <p/>
 * Created by Matt on 11/23/2015.
 */
public class CameraViewGroup implements CameraView.CameraViewSelectionListener
{
   private final Set<CameraView>              viewSet;
   private final List<SelectedCameraListener> selectedCameraListenerList;
   private       CameraView                   selectedView;

   public CameraViewGroup()
   {
      viewSet = new HashSet<>();
      selectedCameraListenerList = new ArrayList<>();
   }

   public boolean addCameraView(CameraView cv)
   {
      cv.addSelectionListener(this);
      return viewSet.add(cv);
   }

   public boolean removeCameraView(CameraView cv)
   {
      cv.removeSelectionListener(this);
      boolean contained = viewSet.remove(cv);

      if (contained)
      {
         // will do nothing if cv is not the currently selected view
         changeSelectionToAnythingElse(cv);
      }

      return contained;
   }

   public void clearGroup()
   {
      for (CameraView cv : viewSet)
      {
         cv.removeSelectionListener(this);
      }
      viewSet.clear();
   }

   public void changeSelectionToAnythingElse(CameraView cv)
   {
      if (selectedView == null || !selectedView.equals(cv))
      {
         return; // either nothing was previously selected or something else is already selected
      }
      // after this point selectedView is guaranteed to be non-null

      CameraView newSelectedView = null;
      if (!viewSet.isEmpty())
      {
         Iterator<CameraView> it = viewSet.iterator();
         while (it.hasNext())
         {
            CameraView next = viewSet.iterator().next();
            if (next.canBeSelected())
            {
               newSelectedView = next;
               break;
            }
         }
      }

      // at this point newSelectedView is either newly null or a newly selected valid CameraView,
      // so alert listeners of change in selected CameraView
      selectView(newSelectedView);

      // at this point selectedView is either newly null or a newly selected valid CameraView,
      // so alert listeners of change in selected CameraView
      fireSelectedCameraChanged(selectedView);
   }

   private void selectView(CameraView cv)
   {
      clearSelection();

      if (cv != null)
      {
         // for debug... not present in release builds
         if (BuildConfig.DEBUG && !viewSet.contains(cv))
         {
            throw new AssertionError("viewSet does not contain given CameraView");
         }

         if (cv.canBeSelected())
         {
            cv.setSelected(true);
            selectedView = cv;
         }
      }

      fireSelectedCameraChanged(cv);
   }

   public void clearSelection()
   {
      if (selectedView != null)
      {
         selectedView.setSelected(false);
         selectedView = null;
      }
   }

   public void resetCameraViews()
   {
      clearSelection();

      for (CameraView cv : viewSet)
      {
         cv.reset();
      }
   }

   @Override
   public void clickedForSelection(CameraView view)
   {
      Log.d(getClass().getSimpleName(), "CameraView selected: " + view.getCameraConfig().toString());
      selectView(view);
   }

   @Override
   public void cameraTimeEndedWhileSelected(CameraView view)
   {
      changeSelectionToAnythingElse(view);
   }

   private void fireSelectedCameraChanged(CameraView newSelection)
   {
      for (SelectedCameraListener listener : selectedCameraListenerList)
      {
         listener.selectedCameraChanged(newSelection);
      }
   }

   public void addSelectedCameraListener(SelectedCameraListener listener)
   {
      selectedCameraListenerList.add(listener);
   }

   /**
    * Returns an unmodifiable list of the instances of {@code SelectedCameraListener} currently
    * attached to this list.
    *
    * @return an unmodifiable list of the currently attached listeners
    */
   public List<SelectedCameraListener> getSelectedCameraListeners()
   {
      return Collections.unmodifiableList(selectedCameraListenerList);
   }

   public void removeSelectedCameraListener(SelectedCameraListener listener)
   {
      selectedCameraListenerList.remove(listener);
   }

   public interface SelectedCameraListener
   {
      void selectedCameraChanged(CameraView selected);
   }
}
