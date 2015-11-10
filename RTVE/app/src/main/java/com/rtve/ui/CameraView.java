package com.rtve.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rtve.R;
import com.rtve.common.CameraConfig;

/**
 * Created by Matt on 11/9/2015.
 */
public class CameraView extends RelativeLayout
{
   private boolean           recording;
   private boolean           selected;
   private CameraConfig      config;
   private CountDownTextView cdText;
   private Button            recordButton;


   public CameraView(Context context)
   {
      super(context);
      init(null);
   }

   public CameraView(Context context, AttributeSet attrs)
   {
      this(context, attrs, 0);
      init(attrs);
   }

   public CameraView(Context context, AttributeSet attrs, int default_style)
   {
      super(context, attrs, default_style);
      init(attrs);
   }

   private void init(AttributeSet attrs)
   {
      Context c = getContext();

      this.setBackgroundColor(Color.TRANSPARENT);
      this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

      if (isInEditMode())
      {

      }
      else if (attrs != null)
      {
         TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.CameraView, 0, 0);

         String text = array.getString(R.styleable.CameraView_camName);
         boolean timeLimited = array.getBoolean(R.styleable.CameraView_timeLimited, false);
         boolean tightLens = array.getBoolean(R.styleable.CameraView_tightLens, false);
         boolean wideLens = array.getBoolean(R.styleable.CameraView_wideLens, false);

         if (text == null)
         {
            throw new IllegalArgumentException("No camera name included in attributes");
         }
         else if ((tightLens && wideLens) || (!tightLens && !wideLens))
         {
            throw new IllegalArgumentException(
                    "Camera must be either tight lens or wide lens (but not both)");
         }

         // set Name

         //
         if (timeLimited)
         {
         }
         else // not time limited
         {

         }

         if (tightLens)
         {

         }
         else // guaranteed to be wideLens
         {

         }

         this.selected = false;
         this.recording = false;

         array.recycle();
      }
   }

   @Override
   protected void onFinishInflate()
   {
      super.onFinishInflate();

      // get references to subcomponents here
   }

   public void setCameraConfig(CameraConfig config)
   {
      this.config = config;
   }

   public boolean isRecording()
   {
      return this.recording;
   }   public void setSelected(boolean selected)
   {
      if (this.selected != selected)
      {
         this.selected = selected;
      }
   }

   public void setRecording(boolean recording)
   {
      if (this.recording != recording)
      {
         this.recording = recording;
      }
   }   public boolean isSelected()
   {
      return this.selected;
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

      private CountDownTimer cdTimer;
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

      private void init()
      {
         millisRemaining = COUNTDOWN_START_TIME_MS;
         updateTextTime();
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
            cdTimer.cancel();
            isPaused = true;
         }
      }

      public boolean isPaused()
      {
         return isPaused;
      }
   }





}
