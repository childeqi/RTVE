package com.rtve.core.storage;

import com.rtve.common.CameraConfig;
import com.rtve.common.CameraConfigList;
import com.rtve.common.CameraFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/24/2015.
 */
class XmlConfigListFactory
{
   static CameraConfigList getConfigListFromConfiguration(Configuration config)
   {
      if (config == null)
      {
         throw new NullPointerException();
      }

      CameraConfigList toReturn = new CameraConfigList();

      for (Camera cam : config.getCameras())
      {
         toReturn.add(CameraFactory.loadCamera(cam.getNumber(), cam.getName(), cam.getLensType()));
      }

      return toReturn;
   }

   static Configuration getConfigurationFromConfigList(CameraConfigList list)
   {
      if (list == null)
      {
         throw new NullPointerException();
      }

      List<Camera> cameras = new ArrayList<>(list.size());

      for (CameraConfig config : list.getBackingList())
      {
         cameras.add(new Camera(config.getNumber(), config.getName(), config.getLensType()));
      }

      return new Configuration(cameras);
   }
}
