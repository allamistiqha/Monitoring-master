package unhas.informatics.monitoringapp.Preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefManager {

    static final String KEY_USER_TEREGISTER ="user", KEY_PASS_TEREGISTER ="pass", KEY_NAME_REGISTERED = "name", KEY_STATUS_REGISTERED = "status";
    static final String KEY_USERNAME_SEDANG_LOGIN = "Username_logged_in";
    static final String KEY_PASSWORD_SEDANG_LOGIN = "Password_logged_in";
    static final String KEY_STATUS_SEDANG_LOGIN = "Status_logged_in";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setRegisteredUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_TEREGISTER, username);
        editor.apply();
    }

    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString(KEY_USER_TEREGISTER,"");
    }

    public static void setRegisteredPass(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASS_TEREGISTER, password);
        editor.apply();
    }

    public static String getRegisteredPass(Context context){
        return getSharedPreference(context).getString(KEY_PASS_TEREGISTER,"");
    }
    public static void setRegisteredName(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NAME_REGISTERED, username);
        editor.apply();
    }

    public static String getRegisteredName(Context context){
        return getSharedPreference(context).getString(KEY_NAME_REGISTERED,"");
    }

    public static void setRegisteredStatus(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_STATUS_REGISTERED, password);
        editor.apply();
    }

    public static String getRegisteredStatus(Context context){
        return getSharedPreference(context).getString(KEY_STATUS_REGISTERED,"");
    }

    public static void setLoggedInUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USERNAME_SEDANG_LOGIN, username);
        editor.apply();
    }

    public static String getLoggedInUser(Context context){
        return getSharedPreference(context).getString(KEY_USERNAME_SEDANG_LOGIN,"");
    }

    public static void setLoggedInPass(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASSWORD_SEDANG_LOGIN, password);
        editor.apply();
    }

    public static String getLoggedInPass(Context context){
        return getSharedPreference(context).getString(KEY_PASSWORD_SEDANG_LOGIN,"");
    }

    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_SEDANG_LOGIN,status);
        editor.apply();
    }

    public static Boolean getTnggl(Context context){
        return getSharedPreference(context).getBoolean("TGL",false);
    }

    public static void setTnggl(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean("TGL",status);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS_SEDANG_LOGIN,false);
    }

    public static void setNamePelanggan(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("NamaPelanggan", username);
        editor.apply();
    }
    public static String getNamepelanggan(Context context){
        return getSharedPreference(context).getString("NamaPelanggan","");
    }

    public static String getTngglBerakhir(Context context){
        return getSharedPreference(context).getString("Berakhir","");
    }
    public static void setTngglBerakhir(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("Berakhir", username);
        editor.apply();
    }

    public static void setStatusPelanggan(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("Pemabayaran", username);
        editor.apply();
    }
    public static String getStatusPelanggan(Context context){
        return getSharedPreference(context).getString("Pemabayaran","");
    }

    public static void setTngglPemakaian(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("Pemakaian", username);
        editor.apply();
    }
    public static String getTngglPemakaian(Context context){
        return getSharedPreference(context).getString("Pemakaian","");
    }

    public static void setNamaBarang(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("Barang", username);
        editor.apply();
    }
    public static String getNamaBarang(Context context){
        return getSharedPreference(context).getString("Barang","");
    }

    public static void setDaya(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("Daya", username);
        editor.apply();
    }
    public static String getDaya(Context context){
        return getSharedPreference(context).getString("Daya","");
    }

    public static void setUlp(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("Daya", username);
        editor.apply();
    }
    public static String getUlp(Context context){
        return getSharedPreference(context).getString("Daya","");
    }

    public static void setStatusUlp(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("UlpStatus", username);
        editor.apply();
    }
    public static String getStatusUlp(Context context){
        return getSharedPreference(context).getString("UlpStatus","");
    }

    public static void setId(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("MyId", username);
        editor.apply();
    }
    public static String getId(Context context){
        return getSharedPreference(context).getString("MyId","");
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USERNAME_SEDANG_LOGIN);
        editor.remove(KEY_PASSWORD_SEDANG_LOGIN);
        editor.remove(KEY_STATUS_SEDANG_LOGIN);
        editor.remove(KEY_NAME_REGISTERED);
        editor.remove(KEY_STATUS_REGISTERED);
        editor.apply();
    }

    public static void clearDataUlp (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove("NamaPelanggan");
        editor.remove("Berakhir");
        editor.remove("Pemabayaran");
        editor.remove("Pemakaian");
        editor.remove("Barang");
        editor.remove("Daya");
        editor.remove("UlpStatus");
        editor.remove("MyId");
        editor.apply();
    }

    public static void clearTanggl(Context context){
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove("TGL");
        editor.apply();
    }
}
