package com.itguai.restservice.base;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.itguai.biz.util.ULog;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.io.*;
import java.lang.reflect.Type;

public class MyGsonConverter implements Converter {
    private boolean test = false;
    private final Gson gson;
    private String charset;

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public MyGsonConverter(Gson gson) {
        this(gson, "UTF-8");
    }

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use the specified charset.
     */
    public MyGsonConverter(Gson gson, String charset) {
        this.gson = gson;
        this.charset = charset;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {

        String charset = this.charset;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), charset);
        }
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(body.in(), charset);
            BufferedReader dr = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = dr.readLine();
            while (null != line) {
                sb.append(line).append("\n");
                line = dr.readLine();
            }
            String response = sb.toString();

            ULog.d("api response:" + response);
            return gson.fromJson(response, type);
        } catch (IOException e) {
            throw new ConversionException(e);
        } catch (JsonParseException e) {
            throw new ConversionException(e);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        try {
            String json = gson.toJson(object);
            ULog.d("api data:" + json);
            return new JsonTypedOutput(json.getBytes(charset), charset);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    private static class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        JsonTypedOutput(byte[] jsonBytes, String encode) {
            this.jsonBytes = jsonBytes;
            //            this.jsonBytes = signPost(jsonBytes, encode);

            this.mimeType = "application/json; charset=" + encode;
        }

        private byte[] signPost(byte[] jsonBytes, String encode) {
            try {
                String body = new String(jsonBytes, encode);
                JSONObject jo = new JSONObject(body);
                body = jo.toString();
                ULog.d("post body:" + body);
                return body.getBytes(encode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonBytes;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return mimeType;
        }

        @Override
        public long length() {
            return jsonBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }
}
