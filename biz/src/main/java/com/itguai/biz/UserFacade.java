package  com.itguai.biz;


import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.itguai.models.result.UserBO;

/**
 * Created by jianyuncheng on 15/3/8.
 */
public class UserFacade {
    SharedPreferences preference;
    static UserFacade instance = new UserFacade();
    private String sessionId;

    private UserFacade() {
        preference = App.getIns().getSharedPreferences("userInfo", Context.MODE_PRIVATE);;
    }

    public static UserFacade getIns() {
        return instance;
    }

    public void loadUserInfo(final Runnable callback) {
    }

    private void saveUser(UserBO user) {
        String userStr = null;
        if (null != user) {
            userStr = new Gson().toJson(user);
        }
        preference.edit().putString("user", userStr).apply();
    }

    public UserBO getUser() {
        String userStr = preference.getString("user", null);
        if (null != userStr) {
            return new Gson().fromJson(userStr, UserBO.class);
        }
        return null;
    }


    public Long getUserId() {
        UserBO user = getUser();
        if (null != user) {
            return user.userId;
        } else {
            return null;
        }
    }

    public void clearUser() {
        saveUser(null);
    }



}
