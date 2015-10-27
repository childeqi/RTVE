package com.rtve.core.storage;

import android.content.Context;
import android.util.Log;

import com.rtve.common.CameraConfigList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;

/**
 * Created by Matt on 10/24/2015.
 */
public class CameraConfigSaver
{
   // package protected
   static final String CONFIG_STORAGE_PREFIX = "config_";

   private CameraConfigSaver()
   {
      // a private constructor makes this class uninstantiable
   }

   public static boolean save(Context c, CameraConfigList list, String configName)
   {
      if (c == null || list == null || configName == null)
      {
         throw new NullPointerException();
      }
      else if (configName.isEmpty() || list.size() == 0)
      {
         throw new IllegalArgumentException();
      }

      java.io.File configFile = new java.io.File(c.getFilesDir(),
                                                 CameraConfigSaver.CONFIG_STORAGE_PREFIX + configName);

      boolean isNewFile;
      if (configFile.exists())
      {
         isNewFile = false;
         if ((!configFile.isFile() || !configFile.canWrite()))
         {
            throw new IllegalArgumentException("Cannot save configuration to given filename");
         }
      }
      else
      {
         isNewFile = true;
         try
         {
            configFile.createNewFile();
         }
         catch (IOException e)
         {
            throw new IllegalArgumentException("Cannot save configuration to given filename", e);
         }
      }

      return writeConfigListToFile(configFile, isNewFile, list);
   }

   private static boolean writeConfigListToFile(java.io.File outputFile, boolean isNewFile,
                                                CameraConfigList configList)
   {
      Configuration config = XmlConfigListFactory.getConfigurationFromConfigList(configList);

      Serializer serializer = new Persister();

      try
      {
         serializer.write(config, outputFile);
         return true;
      }
      catch(Exception e)
      {
         Log.e(CameraConfigSaver.class.getSimpleName(), "Failed to write XML", e);
         if (isNewFile)
         {
            if (outputFile.exists() && !outputFile.delete())
            {
               Log.e(CameraConfigSaver.class.getSimpleName(), "Failed to delete new config file", e);
            }
         }
         return false;
      }
   }
}
