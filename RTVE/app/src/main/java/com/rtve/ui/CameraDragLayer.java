package com.rtve.ui;

import android.content.ClipDescription;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.RelativeLayout;

import com.rtve.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 11/21/2015.
 */
public class CameraDragLayer extends RelativeLayout implements View.OnDragListener
{
   private Adapter adapter;
   private       SparseArray<List<View>> typedViewsCache = new SparseArray<List<View>>();
   private final DataSetObserver         observer        = new DataSetObserver()
   {

      @Override
      public void onChanged()
      {
         refreshViewsFromAdapter();
      }

      @Override
      public void onInvalidated()
      {
         removeAllViews();
      }
   };

   public CameraDragLayer(Context context)
   {
      super(context);
   }

   public CameraDragLayer(Context context, AttributeSet attrs)
   {
      super(context, attrs);
   }

   public CameraDragLayer(Context context, AttributeSet attrs, int defStyleAttr)
   {
      super(context, attrs, defStyleAttr);
   }

   private static void addToTypesMap(int type, View view, SparseArray<List<View>> typedViewsCache)
   {
      List<View> singleTypeViews = typedViewsCache.get(type);
      if (singleTypeViews == null)
      {
         singleTypeViews = new ArrayList<View>();
         typedViewsCache.put(type, singleTypeViews);
      }
      singleTypeViews.add(view);
   }

   private static View shiftCachedViewOfType(int type, SparseArray<List<View>> typedViewsCache)
   {
      List<View> singleTypeViews = typedViewsCache.get(type);
      if (singleTypeViews != null)
      {
         if (singleTypeViews.size() > 0)
         {
            return singleTypeViews.remove(0);
         }
      }
      return null;
   }

   @Override
   protected void onFinishInflate()
   {
      super.onFinishInflate();

      findViewById(R.id.mainview).setOnDragListener(this);
   }

   public Adapter getAdapter()
   {
      return adapter;
   }

   public void setAdapter(Adapter adapter)
   {
      if (this.adapter != null)
      {
         this.adapter.unregisterDataSetObserver(observer);
      }
      this.adapter = adapter;
      if (this.adapter != null)
      {
         this.adapter.registerDataSetObserver(observer);
      }
      initViewsFromAdapter();
   }

   protected void initViewsFromAdapter()
   {
      typedViewsCache.clear();
      removeAllViews();
      View view;
      if (adapter != null)
      {
         for (int i = 0; i < adapter.getCount(); i++)
         {
            view = adapter.getView(i, null, this);
            addToTypesMap(adapter.getItemViewType(i), view, typedViewsCache);
            addView(view, i);
         }
      }
   }

   protected void refreshViewsFromAdapter()
   {
      Log.d(getClass().getSimpleName(), "In refreshViewsFromAdapter");
      SparseArray<List<View>> typedViewsCacheCopy = typedViewsCache;
      typedViewsCache = new SparseArray<List<View>>();
      removeAllViews();
      View convertView;
      int  type;
      for (int i = 0; i < adapter.getCount(); i++)
      {
         type = adapter.getItemViewType(i);
         convertView = shiftCachedViewOfType(type, typedViewsCacheCopy);
         convertView = adapter.getView(i, convertView, this);
         addToTypesMap(type, convertView, typedViewsCache);
         addView(convertView, i);
      }
   }

   @Override
   public boolean onDrag(View v, DragEvent event)
   {
      // Defines a variable to store the action type for the incoming event
      final int action = event.getAction();

      // gets the view that is being dragged
      CameraView draggedView = (CameraView) event.getLocalState();
      Point      touchPoint  = draggedView.getDragTouchPoint();

      // Handles each of the expected events
      switch (action)
      {
         case DragEvent.ACTION_DRAG_STARTED:
            Log.i(getClass().getSimpleName(), "drag action started");

            // Determines if this view can accept the dragged data
            if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
            {
               Log.i(getClass().getSimpleName(), "Can accept this data");
               return true;
            }
            else
            {
               Log.i(getClass().getSimpleName(), "Cannot accept this data");
               return false;
            }

         case DragEvent.ACTION_DROP:
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) draggedView
                    .getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

            /* need to account for touch point in drop shadow */
            // these are the location of the touch point on the drag shadow (relative to this
            // CameraDragLayer)
            int dropX = (int) event.getX();
            int dropY = (int) event.getY();

            // by subtracting the touch point location (which is relative to the CameraView) we get
            // the top left corner of the CameraView
            Point topLeft = new Point(dropX - touchPoint.x, dropY - touchPoint.y);

            /* need to ensure that view can fully fit within the parent view's bounds */
            Point bottomRight = new Point(topLeft.x + draggedView.getWidth(),
                                          topLeft.y + draggedView.getHeight());

            int xMargin, yMargin;

            // adjust the x coordinate to fit in parent view
            if (topLeft.x < 0)
            {
               xMargin = 0;
            }
            else if (bottomRight.x > v.getWidth())
            {
               xMargin = v.getWidth() - draggedView.getWidth();
            }
            else
            {
               xMargin = topLeft.x;
            }

            // adjust the y coordinate to fit in parent view
            if (topLeft.y < 0)
            {
               yMargin = 0;
            }
            else if (bottomRight.y > v.getHeight())
            {
               yMargin = v.getHeight() - draggedView.getHeight();
            }
            else
            {
               yMargin = topLeft.y;
            }

            // finally, assign new location to dragged view and set visible again
            params.setMargins(xMargin, yMargin, 0, 0);
            draggedView.setLayoutParams(params);
            draggedView.setVisibility(View.VISIBLE);
            return true;

         case DragEvent.ACTION_DRAG_ENDED:
            Log.i(getClass().getSimpleName(), "Drag action ended, result = " + event.getResult());

            // if the drop was unsuccessful, reset the original view to visible
            if (!event.getResult())
            {
               Log.i(getClass().getSimpleName(), "Setting original view visible again");
               draggedView.setVisibility(View.VISIBLE);
            }
            return true;

         case DragEvent.ACTION_DRAG_ENTERED:
         case DragEvent.ACTION_DRAG_LOCATION:
         case DragEvent.ACTION_DRAG_EXITED:
            // ignore
            break;

         // An unknown action type was received.
         default:
            Log.e(AbstractDragEventListener.class.getName(),
                  "Unknown action type received by OnDragListener.");
            break;
      }

      return false;
   }
}
