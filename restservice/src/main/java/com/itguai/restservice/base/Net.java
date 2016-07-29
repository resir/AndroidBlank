package com.itguai.restservice.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by jianyuncheng on 15/5/4.
 */
public class Net {

    public static final String SERVER_HOST_TEST = "http://114.215.178.133:8089";//测试
    public static final String OPENID_HOST_TEST = "http://115.29.245.103:8080";//测试
    public static final String SERVER_HOST_PRODUCT = "http://crm.tqmall.com";//正式
    public static final String OPENID_HOST_PRODUCT = "http://zs.tqmall.com";//正式

    public static String serverHost = "http://crm.tqmall.com";//正式
    public static String openIdHost = "http://zs.tqmall.com";//正式

    private static CustomerClient client;
    private static OkHttpClient okHttpClient;


    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
             //如果仅仅是用来判断网络连接
               //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    public static <T> T getApi(Class<T> cls) {
        if (null == client) {
            client = new CustomerClient();
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(serverHost)
                .setConverter(new MyGsonConverter(new Gson()))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                    }
                })
                .setClient(client)
                .build();
        return restAdapter.create(cls);
    }

    public static void get(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        if (null == okHttpClient) {
            createOkClient();
        }
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void post(String url, RequestBody formBody, Callback callback) {
        Request request = new Request.Builder().url(url).post(formBody).build();
        if (null == okHttpClient) {
            createOkClient();
        }
        okHttpClient.newCall(request).enqueue(callback);
    }

    private static void createOkClient() {
        okHttpClient = new OkHttpClient();
    }

}
