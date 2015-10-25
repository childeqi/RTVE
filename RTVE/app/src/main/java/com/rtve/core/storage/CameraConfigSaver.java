package com.rtve.core.storage;

import android.content.Context;

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

      if (configFile.exists() && (!configFile.isFile() || !configFile.canWrite()))
      {
         throw new IllegalArgumentException("Cannot save configuration to given filename");
      }
      else
      {
         try
         {
            configFile.createNewFile();
         }
         catch (IOException e)
         {
            throw new IllegalArgumentException("Cannot save configuration to given filename", e);
         }
      }

      return writeConfigListToFile(configFile, list);
   }

   private static boolean writeConfigListToFile(java.io.File outputFile, CameraConfigList configList)
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
         e.printStackTrace();
         return false;
      }
   }
}
