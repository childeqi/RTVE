package com.rtve.ui;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;

/**
 * Created by Matt on 11/21/2015.
 */
public abstract class AbstractDragEventListener implements View.OnDragListener
{
   // This is the method that the system calls when it dispatches a drag event to the
   // listener.
   public boolean onDrag(View v, DragEvent event)
   {
      // Defines a variable to store the action type for the incoming event
      final int action = event.getAction();

      // Handles each of the expected events
      switch (action)
      {
         case DragEvent.ACTION_DRAG_STARTED: return onDragStarted(v, event);
         case DragEvent.ACTION_DRAG_ENTERED: return onDragEntered(v, event);
         case DragEvent.ACTION_DRAG_LOCATION: return onDragLocation(v, event);
         case DragEvent.ACTION_DRAG_EXITED: return onDragExited(v, event);
         case DragEvent.ACTION_DROP: return onDrop(v, event);
         case DragEvent.ACTION_DRAG_ENDED: return onDragEnded(v, event);
         // An unknown action type was received.
         default:
            Log.e(AbstractDragEventListener.class.getName(), "Unknown action type received by OnDragListener.");
            break;
      }

      return false;
   }

   protected abstract boolean onDragStarted(View v, DragEvent event);

   protected abstract boolean onDragEntered(View v, DragEvent event);

   protected abstract boolean onDragLocation(View v, DragEvent event);

   protected abstract boolean onDragExited(View v, DragEvent event);

   protected abstract boolean onDrop(View v, DragEvent event);

   protected abstract boolean onDragEnded(View v, DragEvent event);
}
