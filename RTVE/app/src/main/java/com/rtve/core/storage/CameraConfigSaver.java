package com.rtve.core.storage;

import com.rtve.common.CameraConfigList;

/**
 * Created by Matt on 10/24/2015.
 */
public class CameraConfigSaver
{
   public static final String CONFIG_STORAGE_PREFIX = "config_";

   private CameraConfigSaver()
   {
      // a private constructor makes this class uninstantiable
   }

   public static boolean save(CameraConfigList list, String configName)
   {
      return true;
   }
}
