package com.rtve.core;

import android.support.v7.app.AppCompatActivity;

import com.rtve.common.TimeSlot;

import java.util.List;

/**
 * Created by Matt on 10/11/2015.
 */
public interface CoreInterface
{
   void save(List<TimeSlot> timingList);

   void send(AppCompatActivity activity);
}
