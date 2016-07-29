package com.itguai.biz.util;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.itguai.biz.App;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Util {
    private static final String NOTIFICATIONID ="NOTIFICATIONID_01" ;

    public static String[] getJsonObjectStringArray(JSONArray jsonArray, String key) {
        String res[];
        try {
            res = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = new JSONObject(jsonArray.getString(i));
                res[i] = getJsonStringValue(jsonObject2, key);
            }
        } catch (Exception e) {
            return new String[]{};
        }
        return res;
    }

    public static int[] getJsonObjectIntegerArray(JSONArray jsonArray, String key) {
        int res[];
        try {
            res = new int[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = new JSONObject(jsonArray.getString(i));
                res[i] = getJsonIntegerValue(jsonObject2, key);
            }
        } catch (Exception e) {
            return new int[]{};
        }
        return res;
    }

    public static JSONObject getJsonObject(JSONArray jsonArray, int index) {
        try {
            if (jsonArray != null && index >= 0 && index < jsonArray.length()) {
                return jsonArray.getJSONObject(index);
            }
        } catch (JSONException e) {
            return null;
        }
        return null;
    }

    public static String getArrayValue(String[] array, int index) {
        if (array != null && index >= 0 && index < array.length) {
            return array[index];
        }
        return "";
    }

    public static int getArrayValue(int[] array, int index) {
        if (array != null && index >= 0 && index < array.length) {
            return array[index];
        }
        return 0;
    }

    public static String getJsonStringValue(JSONObject jsonObject, String key) {
        return getJsonStringValue(jsonObject, key, "");
    }

    public static String getJsonStringValue(JSONObject jsonObject, String key, String defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                String value = jsonObject.getString(key).trim();
                if (value.equals("null")) {
                    value = "";
                }
                return value;
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static int getJsonIntegerValue(JSONObject json, String key) {
        return getJsonIntegerValue(json, key, 0);
    }

    public static int getJsonIntegerValue(JSONObject jsonObject, String key, int defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getInt(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static Long getJsonLongValue(JSONObject json, String key) {
        return getJsonLongValue(json, key, 0L);
    }

    public static Long getJsonLongValue(JSONObject jsonObject, String key, Long defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getLong(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static float getJsonFloatValue(JSONObject jsonObject, String key, float defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return Float.valueOf(jsonObject.getString(key));
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static boolean getJsonBooleanValue(JSONObject jsonObject, String key, boolean defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getBoolean(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static JSONObject getJsonObject(JSONObject jsonObject, String key) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getJSONObject(key);
            }
        } catch (Exception e) {
            return new JSONObject();
        }
        return new JSONObject();
    }

    public static JSONArray getJsonArray(JSONObject jsonObject, String key) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getJSONArray(key);
            }
        } catch (Exception e) {
            return new JSONArray();
        }
        return new JSONArray();
    }

    public static void removeDuplicate(ArrayList arrayList) {
        HashSet h = new HashSet(arrayList);
        arrayList.clear();
        arrayList.addAll(h);
    }

    public static void removeDuplicateWithOrder(ArrayList arrayList) {
        HashSet set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = arrayList.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (!set.contains(element)) {
                set.add(element);
                newList.add(element);
            }
        }
        arrayList.clear();
        arrayList.addAll(newList);
    }

    public static JSONArray removeDuplicate(JSONArray jsonArray) {
        HashSet set = new HashSet();
        JSONArray newArray = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Object element = jsonArray.get(i);
                if (!set.contains(element)) {
                    set.add(element);
                    newArray.put(element);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return newArray;
    }

    public static boolean checkEmail(String email) {
        try {
            String check = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            boolean isMatched = matcher.matches();
            if (isMatched) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isMobilePhoneNumber(String number) {
        String regx = "^(13[0-9]|15[0-9]|18[0-9]|14[5|7]|17[6-8])\\d{8}$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }

    public static boolean isVinNumber(String str) {
        String regx = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static void showLongMessage(Context mContext, CharSequence text) {
        if (text != null && text.length() > 0) {
            Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
        }
    }

    public static void showShortMessage(Context mContext, CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShortMessage(Context mContext, int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param filePath
     * @return String
     */
    public static String getFileContent(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sbContent = new StringBuffer();
        String sLine = "";
        while ((sLine = br.readLine()) != null) {
            String s = sLine.toString() + "\n";
            sbContent = sbContent.append(s);
        }
        fis.close();
        isr.close();
        br.close();
        return sbContent.toString();
    }

    public static String TimeStampToString(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
        String date = sdf.format(new Date(timeStamp));
        return date;
    }

    public static String escapeByTags(String s, String[] tags) {
        for (String re : tags) {
            Pattern p = Pattern
                    .compile("<[\\s]*?" + re + "[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?" + re + "[\\s]*?>", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(s);
            s = m.replaceAll("");
        }
        return s;
    }

    public static String fliterBR(String body) {
        if (body != null) {
            body = body.replaceAll("\n{2,}", "\n");
            body = body.replace("<br />\n<br />", "<br />");
            body = body.replace("<br /><br />", "<br />");
            body = body.replace("<br />\n\r<br />", "<br />");
            body = body.replace("<br />\r<br />", "<br />");
            body = body.trim();
            if (body.endsWith("<br />")) {
                body = body.substring(0, body.lastIndexOf("<br />"));
            }
        } else {
            body = "";
        }
        return body;
    }

    public static void dialPhone(Activity activity, String telephone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + telephone));
        activity.startActivity(callIntent);
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        Writer writer = new StringWriter();
        char[] buffer = new char[2048];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        String text = writer.toString();
        return text;
    }

    public static String getHtmlTemplate(Context context, String assetsFileName) {
        String html = "";
        try {
            html = convertStreamToString(context.getAssets().open(assetsFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    public static Long paraseLong(String str, Long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return defaultValue;
    }

    public static void cancelTask(AsyncTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel(true);
            task = null;
        }
    }


    public static String getChannelStr() {
        String qudao = "";
        ApplicationInfo appInfo = null;
        try {
            appInfo = App.getIns().getPackageManager().getApplicationInfo(App.getIns().getPackageName(),
                    PackageManager.GET_META_DATA);
            qudao = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                qudao = String.valueOf(appInfo.metaData.getInt("UMENG_CHANNEL"));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return qudao;
    }

    public static boolean isAfterHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static void setWallpaper(Context context, Bitmap bitmap) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        try {
            /**
             * Change the current system wallpaper to a bitmap. The given bitmap
             * is converted to a PNG and stored as the wallpaper. On success,
             * the intent {@link android.content.Intent#ACTION_WALLPAPER_CHANGED} is broadcast.
             *
             * <p>
             * This method requires the caller to hold the permission
             * {@link android.Manifest.permission#SET_WALLPAPER}.
             *
             * @param bitmap
             *            The bitmap to save.
             *
             * @throws IOException
             *             If an error occurs reverting to the default
             *             wallpaper.
             */
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Util.showShortMessage(context, "设置失败");
        }
    }    /*
     * 让Gallery上能马上看到图片
     */

    public static void scanPhoto(Context context, String imgFileName) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static boolean isValidateUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        if (TextUtils.isEmpty(uri.getHost())) {
            return false;
        }
        return true;
    }

    public static String encodeUTF8Str(String param) {
        if (!TextUtils.isEmpty(param)) {
            try {
                param = URLEncoder.encode(param, "utf8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return param;
    }

    private static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static Map<String, String> getRequestParamsMap(String url) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            URL urlObj = new URL(url);
            String queryMapStr = urlObj.getQuery();
            String[] params = queryMapStr.split("&");
            for (String param : params) {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 不做处理
        }
        return map;
    }

    public static float getFloatValueFromString(String priceValue) {
        float floatValue = 0;
        if (!TextUtils.isEmpty(priceValue)) {
            try {
                floatValue = Float.valueOf(priceValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return floatValue;
    }

    public static void hideIME(Context c, EditText edit){
        InputMethodManager imm = (InputMethodManager)c.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }

    public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
}
