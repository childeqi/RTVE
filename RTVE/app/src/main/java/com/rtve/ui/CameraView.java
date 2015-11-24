package com.rtve.ui;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rtve.R;
import com.rtve.common.CameraConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Matt on 11/9/2015.
 */
public class CameraView extends RelativeLayout
{
   private final List<CameraViewDeletionListener>  deletionListenerList  = new ArrayList<>();
   private final List<CameraViewSelectionListener> selectionListenerList = new ArrayList<>();

   private boolean      inflated;
   private boolean      recording;
   private boolean      selected;
   private boolean      outOfTime;
   private CameraConfig config;
   private int          recordingBgColorId;
   private Point        lastTouchPointBeforeDrag;

   private RelativeLayout    mainBg;
   private TextView          nameText;
   private CountDownTextView cdText;
   private TextView          lensText;
   private View              dragGrip;
   private ImageButton       recordButton;
   private ImageButton       deleteButton;
   private LinearLayout      center;

   public CameraView(Context context)
   {
      super(context);
      init();
   }

   public CameraView(Context context, AttributeSet attrs)
   {
      this(context, attrs, 0);
      init();
   }

   public CameraView(Context context, AttributeSet attrs, int default_style)
   {
      super(context, attrs, default_style);
      init();
   }

   public Point getDragTouchPoint()
   {
      return lastTouchPointBeforeDrag;
   }

   public void addDeletionListener(CameraViewDeletionListener listener)
   {
      deletionListenerList.add(listener);
   }

   /**
    * Returns an unmodifiable list of the instances of {@code CameraViewDeletionListener} currently
    * attached to this list.
    *
    * @return an unmodifiable list of the currently attached listeners
    */
   public List<CameraViewDeletionListener> getDeletionListeners()
   {
      return Collections.unmodifiableList(deletionListenerList);
   }

   public void removeDeletionListener(CameraViewDeletionListener listener)
   {
      deletionListenerList.remove(listener);
   }

   public void addSelectionListener(CameraViewSelectionListener listener)
   {
      selectionListenerList.add(listener);
   }

   /**
    * Returns an unmodifiable list of the instances of {@code CameraViewSelectionListener} currently
    * attached to this list.
    *
    * @return an unmodifiable list of the currently attached listeners
    */
   public List<CameraViewSelectionListener> getSelectionListeners()
   {
      return Collections.unmodifiableList(selectionListenerList);
   }

   public void removeSelectionListener(CameraViewSelectionListener listener)
   {
      selectionListenerList.remove(listener);
   }

   public void reset()
   {
      setSelected(false);
      setRecording(false);
      recordingBgColorId = getResources().getColor(R.color.camera_view_record_red);
      cdText.resetTime();
   }

   private void init()
   {
      setBackgroundColor(Color.TRANSPARENT);
      setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

      inflated = false;
      selected = false;
      recording = false;
      outOfTime = false;

      recordingBgColorId = getResources().getColor(R.color.camera_view_record_red);
   }

   public boolean canBeSelected()
   {
      return !outOfTime;
   }

   private void setRecordingBgColor(int colorId)
   {
      recordingBgColorId = colorId;
      if (recording && mainBg != null)
      {
         mainBg.setBackgroundColor(recordingBgColorId);
      }
   }

   private void setOutOfTime(boolean outOfTime)
   {
      this.outOfTime = outOfTime;

      if (this.outOfTime)
      {
         if (recording)
         {
            setRecording(false);
         }

         if (selected)
         {
            setSelected(false);
         }
      }
   }

   private void setupFromConfig()
   {
      if (config != null && inflated && !isInEditMode())
      {
         // set Name
         nameText.setText(config.getName());


         // set up countdown text time if this camera view is time limited (otherwise hide it)
         if (config.isTimeLimited())
         {
            // configure initial appearance
            cdText.setVisibility(View.VISIBLE);
            cdText.resetTime();

            // set up handling for when time hits warning stage
            cdText.setWarningTimeRunnable(new Runnable()
            {
               @Override
               public void run()
               {
                  CameraView.this.setRecordingBgColor(Color.YELLOW);
               }
            });

            // set up handling for when time runs out
            cdText.setEndTimeRunnable(new Runnable()
            {
               @Override
               public void run()
               {
                  CameraView.this.setOutOfTime(true);
               }
            });
         }
         else // not time limited
         {
            cdText.setVisibility(View.INVISIBLE);
         }


         // set lens type
         lensText.setText(config.getLensType().toString());


         // set up selection click listener
         center.setOnClickListener(new OnClickListener()
         {
            @Override
            public void onClick(View v)
            {
               // toggle selection state
               Log.d(CameraView.class.getSimpleName(),
                     CameraView.this.config.getName() + " was clicked for selection");
               CameraView.this.fireClickedForSelection();
            }
         });


         // set up recording click listener
         recordButton.setOnClickListener(new OnClickListener()
         {
            @Override
            public void onClick(View v)
            {
               // toggle recording state
               CameraView.this.setRecording(!CameraView.this.isRecording());
            }
         });

         // set up delete touch listener (for long click transition)
         TransitionDrawable bgDrawable = (TransitionDrawable) getContext().getResources()
                                                                          .getDrawable(
                                                                                  R.drawable
                                                                                          .camera_view_delete_transition);
         deleteButton.setOnTouchListener(new TouchTransitionListener(deleteButton, bgDrawable));

         // set up delete long click listener
         deleteButton.setOnLongClickListener(new View.OnLongClickListener()
         {

            @Override
            public boolean onLongClick(final View v)
            {
               new AlertDialog.Builder(v.getContext())
                       .setTitle("Confirm Camera Deletion")
                       .setMessage("Do you really want to delete the \"" + config.getName() +
                                           "\" camera?")
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setPositiveButton(android.R.string.yes,
                                          new DialogInterface.OnClickListener()
                                          {

                                             public void onClick(DialogInterface dialog,
                                                                 int whichButton)
                                             {
                                                CameraView.this.fireDeletePressed();
                                             }
                                          })
                       .setNegativeButton(android.R.string.no, null).show();

               // reset the background to the initial background (before the long click transition)
               Drawable bgDrawable = v.getContext().getResources().getDrawable(
                       R.drawable.camera_view_delete_normal);
               ((ImageButton) v).setImageDrawable(bgDrawable);

               return true;
            }
         });

         // set up delete touch listener (for long click transition)
         bgDrawable = (TransitionDrawable) getContext().getResources().getDrawable(
                 R.drawable.camera_view_drag_transition);
         final TouchTransitionListener dragTouchListener = new TouchTransitionListener(dragGrip,
                                                                                       bgDrawable);
         dragGrip.setOnTouchListener(dragTouchListener);

         // set up drag long click listener
         dragGrip.setOnLongClickListener(new OnLongClickListener()
         {
            @Override
            public boolean onLongClick(View v)
            {
               Log.d(CameraView.class.getSimpleName(),
                     CameraView.this.config.getName() + " was long clicked for dragging");

               // reset background to pre-transition bg
               Drawable bgDrawable = v.getContext().getResources().getDrawable(
                       R.drawable.camera_view_drag_normal);
               ((ImageButton) v).setImageDrawable(bgDrawable);

               // create the clipdata for this cameraView
               ClipData clipData = ClipData.newPlainText("CameraView", config.getName());

               // create the drag shadow for this camera view
               View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(CameraView.this)
               {
                  @Override
                  public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint)
                  {
                     super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
                     Point touchPoint = dragTouchListener.getLastTouchPoint();
                     shadowTouchPoint.set(touchPoint.x, touchPoint.y);

                  }
               };

               // save off touch point for future check by CameraDragLayer (yeah, I know, tight
               // coupling)
               CameraView.this.lastTouchPointBeforeDrag = dragTouchListener.getLastTouchPoint();

               // start the drag, dragging the whole CameraView, not just the dragGrip
               CameraView.this.startDrag(clipData, shadowBuilder, CameraView.this, 0);

               // we're dragging the shadow so make the original view invisible
               CameraView.this.setVisibility(View.INVISIBLE);

               return true;
            }
         });

         // reset status
         setSelected(false);
         setRecording(false);
         setOutOfTime(false);
      }
   }

   @Override
   protected void onFinishInflate()
   {
      super.onFinishInflate();

      // get references to subcomponents here
      mainBg = (RelativeLayout) findViewById(R.id.camera_view_background);
      nameText = (TextView) findViewById(R.id.camera_view_name);
      cdText = (CountDownTextView) findViewById(R.id.camera_view_time);
      lensText = (TextView) findViewById(R.id.camera_view_lens_type);
      dragGrip = findViewById(R.id.camera_view_drag_grip);
      recordButton = (ImageButton) findViewById(R.id.camera_view_record_button);
      deleteButton = (ImageButton) findViewById(R.id.camera_view_delete_button);
      center = (LinearLayout) findViewById(R.id.camera_view_center);

      inflated = true;

      // fill components with initial values
      setupFromConfig();
   }

   public CameraConfig getCameraConfig()
   {
      return config;
   }

   public void setCameraConfig(CameraConfig config)
   {
      if (this.config == null || !this.config.equals(config))
      {
         this.config = config;
         setupFromConfig();
      }
   }

   public boolean isRecording()
   {
      return this.recording;
   }

   public void setRecording(boolean record)
   {
      if (recording != record)
      {
         if (record)
         {
            if (!outOfTime)
            {
               recording = record;
               mainBg.setBackgroundColor(recordingBgColorId);

               if (config.isTimeLimited())
               {
                  cdText.start();
               }
            }
         }
         else
         {
            recording = record;
            mainBg.setBackgroundColor(Color.WHITE);

            if (config.isTimeLimited())
            {
               cdText.pause();
            }
         }
      }
   }

   private void fireDeletePressed()
   {
      for (CameraViewDeletionListener listener : deletionListenerList)
      {
         listener.deletePressed(this);
      }
   }

   private void fireClickedForSelection()
   {
      for (CameraViewSelectionListener listener : selectionListenerList)
      {
         listener.clickedForSelection(this);
      }
   }


   private void fireCameraTimeEndedWhileSelected()
   {
      for (CameraViewSelectionListener listener : selectionListenerList)
      {
         listener.cameraTimeEndedWhileSelected(this);
      }
   }

   public interface CameraViewDeletionListener
   {
      void deletePressed(CameraView view);
   }

   public interface CameraViewSelectionListener
   {
      void clickedForSelection(CameraView view);

      void cameraTimeEndedWhileSelected(CameraView view);
   }

   private static class CountDownTextView extends TextView
   {
      private static final long MILLIS_IN_SEC              = 1000;
      private static final long SECS_IN_MIN                = 60;
      private static final long MINS_IN_HOUR               = 60;
      // 30 minutes, in milliseconds
      private static final long COUNTDOWN_START_TIME_MS    = 30 * SECS_IN_MIN * MILLIS_IN_SEC;
      // 50 millisecond ticks on countdown timer
      private static final long COUNTDOWN_TICK_INTERVAL_MS = 50;
      // time left when the border of the CameraView should be made yellow (5 minutes)
      private static final long WARNING_TIME_MS            = 5 * SECS_IN_MIN * MILLIS_IN_SEC;


      private CountDownTimer cdTimer;
      private Runnable       warningTimeRunnable;
      private Runnable       endTimeRunnable;
      private long           millisRemaining;
      private long           millisRemainingAtLastUpdate;
      private boolean        isPaused;

      public CountDownTextView(Context context)
      {
         super(context);
         init();
      }

      public CountDownTextView(Context context, AttributeSet attrs)
      {
         super(context, attrs);
         init();
      }

      public CountDownTextView(Context context, AttributeSet attrs, int default_style)
      {
         super(context, attrs, default_style);
         init();
      }

      public void setWarningTimeRunnable(Runnable r)
      {
         this.warningTimeRunnable = r;
      }

      public void setEndTimeRunnable(Runnable r)
      {
         this.endTimeRunnable = r;
      }

      public void resetTime()
      {
         cancel();
         init();
      }

      private void init()
      {
         millisRemaining = COUNTDOWN_START_TIME_MS;
         updateTextTime();
         isPaused = true;
      }

      private void createCountDownTimer()
      {
         cdTimer = new CountDownTimer(millisRemaining, COUNTDOWN_TICK_INTERVAL_MS)
         {

            @Override
            public void onTick(long millisUntilFinished)
            {
               millisRemaining = millisUntilFinished;
               CountDownTextView.this.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish()
            {
               CountDownTextView.this.onFinish();
            }
         };
      }

      private void updateTextTime()
      {
         millisRemainingAtLastUpdate = millisRemaining;

         // get the number of seconds (total)
         long numSecs = millisRemaining / MILLIS_IN_SEC;
         if (millisRemaining % MILLIS_IN_SEC > 0)
         {
            numSecs++;
         }

         // get the number of minutes (total)
         long numMins = numSecs / SECS_IN_MIN;

         // get the number of hours
         // (this is the number to display in the hours column)
         long numHours = numMins / MINS_IN_HOUR;

         // get the number of seconds not included in the current minute
         // (this is the number to display in the seconds column)
         numSecs -= numMins * SECS_IN_MIN;

         // get the number of minutes not included in the current hour
         // (this is the number to display in the minutes column)
         numMins -= numHours * MINS_IN_HOUR;

         this.setText(String.format("%02d:%02d:%02d", numHours, numMins, numSecs));
      }

      /**
       * Callback fired on ticks.
       *
       * @param millisUntilFinished The amount of time until finished.
       */
      public void onTick(long millisUntilFinished)
      {
         millisRemaining = millisUntilFinished;

         if (millisRemainingAtLastUpdate > WARNING_TIME_MS
                 && millisRemaining <= WARNING_TIME_MS
                 && warningTimeRunnable != null)
         {
            warningTimeRunnable.run();
         }

         if (millisRemainingAtLastUpdate - millisUntilFinished >= MILLIS_IN_SEC)
         {
            updateTextTime();
         }
      }

      /**
       * Callback fired when the time is up.
       */
      public void onFinish()
      {
         if (endTimeRunnable != null)
         {
            endTimeRunnable.run();
         }
      }

      /**
       * Cancel the countdown.
       */
      public final void cancel()
      {
         if (cdTimer != null)
         {
            cdTimer.cancel();
         }
         this.millisRemaining = 0;
      }

      /**
       * Start or Resume the countdown.
       *
       * @return CountDownTextView current instance
       */
      public synchronized final CountDownTextView start()
      {
         if (isPaused)
         {
            createCountDownTimer();
            updateTextTime();
            cdTimer.start();
            isPaused = false;
         }
         return this;
      }

      /**
       * Pauses the CountDownTextView, so it could be resumed (via a call to start) later from the
       * same point where it was paused.
       */
      public void pause() throws IllegalStateException
      {
         if (isPaused == false)
         {
            if (cdTimer != null)
            {
               cdTimer.cancel();
            }
            isPaused = true;
         }
      }

      public boolean isPaused()
      {
         return isPaused;
      }
   }

   private static class TouchTransitionListener implements OnTouchListener
   {
      private static final int longPressDuration = ViewConfiguration.getLongPressTimeout();
      private final TransitionDrawable transition;
      private final View               containingView;
      private       boolean            transitioning;
      private       Point              lastTouchPoint;

      public TouchTransitionListener(View containingView, TransitionDrawable transition)
      {
         this.containingView = containingView;
         this.transition = transition;
      }

      private static boolean pointIsInsideView(float x, float y, View view)
      {
         int location[] = new int[2];
         view.getLocationOnScreen(location);
         int viewX = location[0];
         int viewY = location[1];

         //point is inside view bounds
         if ((x > viewX && x < (viewX + view.getWidth())) &&
                 (y > viewY && y < (viewY + view.getHeight())))
         {
            return true;
         }
         else
         {
            return false;
         }
      }

      public Point getLastTouchPoint()
      {
         return lastTouchPoint;
      }

      @Override
      public boolean onTouch(View v, MotionEvent me)
      {
         if (me.getAction() == MotionEvent.ACTION_DOWN)
         {
            //This means a finger has come down on top of our view
            //We are going to start the animation now.
            if (transition != null)
            {
               ((ImageButton) v).setImageDrawable(transition);
               transition.startTransition(longPressDuration);
               transitioning = true;
               lastTouchPoint = new Point((int) (containingView.getX() + me.getX()),
                                          (int) (containingView.getY() + me.getY()));
            }
         }
         else if (me.getAction() == MotionEvent.ACTION_UP
                 || me.getAction() == MotionEvent.ACTION_CANCEL
                 || ((me.getAction() == MotionEvent.ACTION_MOVE)
                             && !pointIsInsideView(me.getX(), me.getY(), containingView)))
         {
            //This means we didn't hold long enough to get a LongClick
            //Set the background back to the normal one.
            if (transition != null && transitioning)
            {
               transition.reverseTransition(longPressDuration);
               transitioning = false;
            }

         }

         return false;
      }
   }

   public void setSelected(boolean select)
   {
      if (selected != select)
      {
         if (select)
         {
            if (!outOfTime)
            {
               selected = select;
               center.setBackgroundColor(getResources().getColor(R.color.camera_view_selected_green));
            }
         }
         else
         {
            selected = select;
            center.setBackgroundColor(Color.BLACK);
         }
      }
   }


   public boolean isSelected()
   {
      return this.selected;
   }


}
