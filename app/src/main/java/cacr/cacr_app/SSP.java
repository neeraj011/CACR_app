package cacr.cacr_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class SSP {
    static final String PREF_USER_NAME="uname";

    static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx,String username)
    {
        SharedPreferences.Editor editor=getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME,username);
        editor.commit();
    }
    public static String getusername(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME,"");
    }
    public static void clearun(Context ctx)
    {
        SharedPreferences.Editor editor=getSharedPreferences(ctx).edit();
        getSharedPreferences(ctx).edit().remove(PREF_USER_NAME).commit();
        //editor.clear();
        //editor.commit();
    }


}