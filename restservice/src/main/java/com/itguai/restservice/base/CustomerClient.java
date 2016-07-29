package com.itguai.restservice.base;

import com.squareup.okhttp.OkHttpClient;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianyuncheng on 15/4/30.
 */
public class CustomerClient extends OkClient {

    private OkHttpClient client;


    public CustomerClient() {
    }

    public CustomerClient(OkHttpClient client) {
        super(client);
        this.client = client;
    }

    @Override
    public Response execute(Request request) throws IOException {
        List<Header> headers = new ArrayList<>();
        headers.addAll(request.getHeaders());
        Request updated;

        String newUrl = request.getUrl();
        if ("GET".equals(request.getMethod())) {
            newUrl = addGetParam(request.getUrl());
            updated = new Request(request.getMethod(), newUrl, headers, request.getBody());
        } else {
            updated = new Request(request.getMethod(), request.getUrl(), headers, request.getBody());
        }
        return super.execute(updated);
    }

    private String addGetParam(String url) {
//        if (url.contains("?")) {
//            if (!url.contains("warehouseId") && wareHouseId != null) {
//                url += "&warehouseId=" + wareHouseId;
//            }
//            if (!url.contains("userId") && userId != null) {
//                url += "&userId=" + userId;
//            }
//        } else {
//            url += "?" +
//                    (wareHouseId == null ? "" : "warehouseId=" + wareHouseId) +
//                    (userId == null ? "" : "&userId=" + userId);
//        }
        return url;
    }

}
