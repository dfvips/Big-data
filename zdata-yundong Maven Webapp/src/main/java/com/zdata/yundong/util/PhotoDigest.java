package com.zdata.yundong.util;

import javax.imageio.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
 
public class PhotoDigest {
 
    public static int[] getData(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            BufferedImage slt = new BufferedImage(100, 100,
                    BufferedImage.TYPE_INT_RGB);
            slt.getGraphics().drawImage(img, 0, 0, 100, 100, null);
            // ImageIO.write(slt,"jpeg",new File("slt.jpg"));
            int[] data = new int[256];
            for (int x = 0; x < slt.getWidth(); x++) {
                for (int y = 0; y < slt.getHeight(); y++) {
                    int rgb = slt.getRGB(x, y);
                    Color myColor = new Color(rgb);
                    int r = myColor.getRed();
                    int g = myColor.getGreen();
                    int b = myColor.getBlue();
                    data[(r + g + b) / 3]++;
                }
            }
            // data 锟斤拷锟斤拷锟斤拷谓图锟斤拷学锟斤拷锟叫碉拷直锟斤拷图锟侥革拷锟斤拷
            return data;
        } catch (Exception exception) {
            return null;
        }
    }
 
    public static float compare(int[] s, int[] t) {
    	if(s != null && t != null ) {
		    float result = 0F;
		    for (int i = 0; i < 256; i++) {
		        int abs = Math.abs(s[i] - t[i]);
		        int max = Math.max(s[i], t[i]);
		        result += (1 - ((float) abs / (max == 0 ? 1 : max)));
		    }
		    return result / 256;
    	}else {
    		return 0;
    	}
        
    }
}
