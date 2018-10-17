package biz.distance;

import java.text.DecimalFormat;

/**
 *  经纬度计算距离(米)
 */
public class DistanceTest {

    public static void main(String[] args) {

        System.out.println(getDistance(120.260594,30.317601,120.225265,30.312713));
    }

    public static double getDistance(double longt1, double lat1, double longt2,double lat2) {

        if (longt1 == 0 || lat1 == 0 || longt2 == 0 || lat2 == 0){
            return 0.00;
        }

        double x, y;
        x = (longt2 - longt1) * Math.PI * 6371229 * Math.cos(((lat1 + lat2) / 2) * Math.PI / 180) / 180;
        y = (lat2 - lat1) * Math.PI * 6371229 / 180;

        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(Math.hypot(x, y)));
    }
}
