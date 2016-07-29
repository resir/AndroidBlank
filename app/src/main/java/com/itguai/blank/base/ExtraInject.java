package com.itguai.blank.base;//package com.tqmall.pda.base;

import android.app.Activity;
import java.io.Serializable;
import java.lang.reflect.Field;
public class ExtraInject {
    public static void inject(Activity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Extra.class)) {
                Extra extra = f.getAnnotation(Extra.class);
                if (null != extra) {
                    f.setAccessible(true);
                    String key;
                    if (!"".equals(extra.value())) {
                        key = extra.value();
                    } else {
                        key = f.getName();
                    }

                    try {
                        if ("String".equals(f.getType().getSimpleName())) {
                            f.set(activity, activity.getIntent().getStringExtra(key));
                        } else if ("int".equals(f.getType().getSimpleName())
                                || "Integer".equals(f.getType().getSimpleName())) {
                            f.set(activity, activity.getIntent().getIntExtra(key, 0));
                        } else if ("boolean".equals(f.getType().getSimpleName())
                                || "Boolean".equals(f.getType().getSimpleName())) {
                            f.set(activity, activity.getIntent().getBooleanExtra(key, false));
                        } else if ("byte".equals(f.getType().getSimpleName())
                                || "Byte".equals(f.getType().getSimpleName())) {
                            f.set(activity, activity.getIntent().getByteExtra(key, (byte) 0));
                        } else if ("long".equals(f.getType().getSimpleName())
                                || "Long".equals(f.getType().getSimpleName())) {
                            f.set(activity, activity.getIntent().getLongExtra(key, 0));
                        } else if ("double".equals(f.getType().getSimpleName())
                                || "Double".equals(f.getType().getSimpleName())) {
                            f.set(activity, activity.getIntent().getDoubleExtra(key, 0));
                        } else if (f.getGenericType() instanceof Serializable ||
                                f.getGenericType().toString().contains( "java.util.ArrayList")  ||
                                f.getGenericType().toString().contains( "java.util.List")  ){
                            Object obj = activity.getIntent().getExtras().get(key);
                            f.set(activity, obj);
                        } else {
                            Object obj = activity.getIntent().getExtras().get(key);
                            f.set(activity, obj);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}