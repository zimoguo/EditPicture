package com.zimo.guo.util;

import android.hardware.Camera;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zimo on 15/8/26.
 */
public class CameraUtil {

    /**
     * 获取设备相机分辨率
     * @param parameters
     * @return
     */
    public static int getResolutionNum(Camera.Parameters parameters){
        return parameters.getSupportedPictureSizes().size();
    }

    public static int[] getPictureSize(Camera.Parameters parameters){
        int[] picSize;
        List picSizes = parameters.getSupportedPictureSizes();
        if (picSizes != null && picSizes.size()>0){
            int[] size = new int[picSizes.size()];
            if (picSizes.size() == 1){
                picSize = new int[2];
                Camera.Size s = (Camera.Size) picSizes.get(0);
                picSize[0] = s.width;
                picSize[1] = s.height;
                return picSize;
            }else {
                picSize = new int[4];
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                for (int i=0;i<picSizes.size();i++){
                    Camera.Size s = (Camera.Size) picSizes.get(i);
                    size[i] = s.width;
                    map.put(s.width,s.height);
                }
                Arrays.sort(size);
                picSize[0] = size[0];
                picSize[1] = map.get(size[0]);
                picSize[2] = size[picSizes.size()-1];
                picSize[3] = map.get(size[picSizes.size()-1]);
                return picSize;
            }

        }
        return null;
    }
}
