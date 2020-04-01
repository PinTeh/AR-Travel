package cn.imhtb.ar.travel;

import android.app.Application;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

import cn.imhtb.ar.travel.utils.LocSdkClient;
import map.baidu.ar.init.ArSdkManager;
import map.baidu.ar.init.MKGeneralListener;
import map.baidu.ar.model.ArLatLng;
import map.baidu.ar.model.ArPoiInfo;
import map.baidu.ar.model.PoiInfoImpl;
import map.baidu.ar.utils.ArBDLocation;

public class ArApplication extends Application {

    public static String defaultSelect="桂林电子科技大学";
    public static List<PoiInfoImpl> poiInfos = new ArrayList<>();
    public static ArLatLng[] latLngs = {new ArLatLng(40.082545, 116.376188), new ArLatLng(40.04326, 116.376781),
            new ArLatLng(40.043204, 116.300784), new ArLatLng(39.892352, 116.433015),
            new ArLatLng(39.970696, 116.267439), new ArLatLng(40.040553, 116.315732),
            new ArLatLng(40.032156, 116.316307), new ArLatLng(40.012707, 116.265714),
            new ArLatLng(40.010497, 116.335279), new ArLatLng(40.124643, 116.701359),
            new ArLatLng(40.042321, 116.15648), new ArLatLng(41.092678, 116.343903),
            new ArLatLng(40.083846, 116.234669), new ArLatLng(40.094444, 116.29216)};

    private static ArApplication mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // ArSDK模块初始化
        ArSdkManager.getInstance().initApplication(this, new MyGeneralListener());

        // 若用百度定位sdk,需要在此初始化定位SDK
        LocSdkClient.getInstance(this).getLocationStart();

        // 若用探索功能需要再这集成检索模块 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        // 检索模块 自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        // 包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        //20200401}
        initPoints(defaultSelect);

    }

    public static void setPoiInfos(List<PoiInfoImpl> p){
        poiInfos = p;
    }

    private void initPoints(String defaultSelect) {
        if ("桂林电子科技大学".equals(defaultSelect)){
            String[] tags = getResources().getStringArray(R.array.guet_tag);
            String[] latitudes = getResources().getStringArray(R.array.guet_latitude);
            String[] longitudes = getResources().getStringArray(R.array.guet_longitude);
            for (int i = 0; i < tags.length; i++) {
                ArPoiInfo pTest = new ArPoiInfo();
                pTest.name = tags[i];
                pTest.location = new ArLatLng(Double.parseDouble(longitudes[i]), Double.parseDouble(latitudes[i]));
                PoiInfoImpl poiImplT = new PoiInfoImpl();
                poiImplT.setPoiInfo(pTest);
                poiInfos.add(poiImplT);
            }
        }else if ("广西师范大学".equals(defaultSelect)){
            //TODO
        }
    }

    public static ArApplication getInstance() {
        return mInstance;
    }

    static class MyGeneralListener implements MKGeneralListener {
        // 1、事件监听，用来处理通常的网络错误，授权验证错误等
        @Override
        public void onGetPermissionState(int iError) {
            // 2、非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                Toast.makeText(ArApplication.getInstance().getApplicationContext(),
                        "arsdk 验证异常，请在AndoridManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: " + iError, Toast
                                .LENGTH_LONG).show();
            } else {
                Toast.makeText(ArApplication.getInstance().getApplicationContext(), "key认证成功", Toast.LENGTH_LONG)
                        .show();
            }
        }

        // 回调给ArSDK获取坐标（demo调用百度定位sdk）
        @Override
        public ArBDLocation onGetBDLocation() {
            // 3、用于传递给ArSdk经纬度信息
            // a、首先通过百度地图定位SDK获取经纬度信息
            // b、包装经纬度信息到ArSdk的ArBDLocation类中 return即可
            BDLocation location =
                    LocSdkClient.getInstance(ArSdkManager.getInstance().getAppContext()).getLocationStart()
                            .getLastKnownLocation();
            if (location == null) {
                return null;
            }
            ArBDLocation arBDLocation = new ArBDLocation();
            // 设置经纬度信息
            arBDLocation.setLongitude(location.getLongitude());
            arBDLocation.setLatitude(location.getLatitude());
            return arBDLocation;
        }
    }
}
