package com.osj.stockinfomation.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/* =========================================================*
@project: LG ONE ID*
@Source: PreferencesUtil*
@Desc: 데이터 관리
Copyright ⓒ2020 LGCNS All right reserved
============================================================*
변경사항*
============================================================*
Ver   DateAuthor    Descrition*
============================================================*
1.0   2020.5.15.    오승진 최초프로그램작성*
============================================================*/

public class PreferencesUtil {
  public static class PreferenceKey {

    public static final String PREF_TOKEN = "token";                                        // 푸시 토큰

    public static final String PUSH_MESSAGE_AGREE = "push_message_agree";                   // 푸시 수신 여부
    public static final String PUSH_MESSAGE_DIALOG = "push_message_dialog";                 // 푸시 다이얼로그 유무
    public static final String MKT_MESSAGE_AGREE = "mkt_message_agree";                     // 마케팅 수신 여부
    public static final String MKT_MESSAGE_DIALOG = "mkt_message_dialog";                   // 마케팃 다이얼로그 유무

    public static final String PREF_PERMISSION_INFO = "PREF_PERMISSION_INFO";


    public static final String LINK = "link";
  }

  /**
   * <pre> DefaultSharedPreferences key(String)에  value(String)을 저장한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @param value Value
   */
  public static synchronized void putString(Context ctx, String key, String value) {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
    Editor editor = pref.edit();
    editor.putString(key, value);
    editor.commit();
  }

  /**
   * <pre> DefaultSharedPreferences key(String)에  value(Boolean)을 저장한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @param value Value
   */
  public static synchronized void putBoolean(Context ctx, String key, boolean value) {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
    Editor editor = pref.edit();
    editor.putBoolean(key, value);
    editor.commit();
  }

  /**
   * <pre> DefaultSharedPreferences key(String)에  value(Integer)을 저장한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @param value Value
   */
  public static synchronized void putInteger(Context ctx, String key, int value) {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
    Editor editor = pref.edit();
    editor.putInt(key, value);
    editor.commit();
  }

  /**
   * <pre> DefaultSharedPreferences key(String)에  value(Float)을 저장한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @param value Value
   */
  public static synchronized void putFloat(Context ctx, String key, float value) {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
    Editor editor = pref.edit();
    editor.putFloat(key, value);
    editor.commit();
  }

    /**
     * <pre> DefaultSharedPreferences key(String)에  value(Float)을 저장한다</pre>
     *
     * @param ctx Context
     * @param key Key
     * @param value Value
     */
    public static synchronized void putLong(Context ctx, String key, long value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor editor = pref.edit();
        editor.putLong(key, value);
        editor.commit();
    }

  /**
   * <pre>해당하는 key에 String값을 반환한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @return Value String
   */
  public static synchronized String getString(Context ctx, String key) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, "");
  }

  /**
   * <pre>해당하는 key에 Booolean 값을 반환한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @return Value String
   */
  public static synchronized boolean getBoolean(Context ctx, String key) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(key, false);
  }


  /**
   * <pre>해당하는 key에 Booolean 값을 반환한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @param defaultValue defaultValue
   * @return Value String
   */
  public static synchronized boolean getBoolean(Context ctx, String key , boolean defaultValue) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(key, defaultValue);
  }




  /**
   * <pre>해당하는 key에 float 값을 반환한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @return Value String
   */
  public static synchronized float getFloat(Context ctx, String key) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getFloat(key, 0.0f);
  }

    /**
     * <pre>해당하는 key에 float 값을 반환한다</pre>
     *
     * @param ctx Context
     * @param key Key
     * @return Value String
     */
    public static synchronized long getLong(Context ctx, String key) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getLong(key, 0);
    }

  /**
   * <pre>해당하는 key에 Integer값을 반환한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @return Value String
   */
  public static synchronized int getInteger(Context ctx, String key) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(key, 0);
  }

  /**
   * <pre>해당하는 key에 String값을 반환한다</pre>
   *
   * @param ctx Context
   * @param key Key
   * @param def default value
   */
  public static synchronized String getString(Context ctx, String key, String def) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, def);
  }

  /**
   * <pre> DefaultSharedPreferences key(String)에  value(String)을 삭제한다.</pre>
   *
   * @param ctx Context
   * @param key Key
   */
  public static synchronized void remove(Context ctx, String key) {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
    Editor editor = pref.edit();
    editor.remove(key);
    editor.commit();
  }
}