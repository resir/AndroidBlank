package com.itguai.biz.util;


import android.content.Context;

import java.util.UUID;

public class DeviceUUIDFactory {
    protected static UUID devUuid;
    private static final String BROKEN_ANDROID_ID = "9774d5556824444";
    private final String UUID_STRING = "uuid";

    public DeviceUUIDFactory(Context context) {
        if (devUuid == null) {
            synchronized (DeviceUUIDFactory.class) {
//                if (devUuid == null) {
//                    final String strID = App.config.getString(UUID_STRING, null);
//                    if (strID != null) {
//                        devUuid = UUID.fromString(strID);
//                    } else {
//                        final String strAndroidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
//                        try {
//                            if (!strAndroidID.equals(BROKEN_ANDROID_ID)) {
//                                devUuid = UUID.nameUUIDFromBytes(strAndroidID.getBytes("utf8"));
//                            } else {
//                                final String strDeviceID = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
//                                        .getDeviceId();
//                                devUuid = strDeviceID != null ? UUID.nameUUIDFromBytes(strDeviceID.getBytes("utf8")) : UUID.randomUUID();
//                            }
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    Editor edit = App.config.edit();
//                    edit.putString(UUID_STRING, devUuid.toString());
//                    edit.commit();
//                }
            }
        }
    }

    /**
     * 获取设备唯一设备号
     *
     * @return
     */
    public UUID getDeviceUUID() {
        return devUuid;
    }
}