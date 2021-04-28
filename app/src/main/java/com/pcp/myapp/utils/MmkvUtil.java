package com.pcp.myapp.utils;

import com.tencent.mmkv.MMKV;

/**
 * author : huazi
 * time   : 2021/4/28
 * desc   :
 */
public class MmkvUtil {

    public final static String LOGIN_ROLE = "login_role";
    public final static String USER_NAME = "user_name";
    public final static String STUDENT = "学生";
    public final static String TEACHER = "老师";
    public final static String ADMIN = "管理员";

    public static void saveUserName(String userName) {
        MMKV.defaultMMKV().encode(USER_NAME, userName);
    }

    public static String getUserName() {
        return MMKV.defaultMMKV().decodeString(USER_NAME);
    }

    public static void saveRole(String role) {
        MMKV.defaultMMKV().encode(LOGIN_ROLE, role);
    }

    public static String getRole() {
        return MMKV.defaultMMKV().decodeString(LOGIN_ROLE);
    }

    public static boolean isStudent() {
        return getRole().equalsIgnoreCase(STUDENT);
    }

    public static boolean isTeacher() {
        return getRole().equalsIgnoreCase(TEACHER);
    }

    public static boolean isAdmin() {
        return getRole().equalsIgnoreCase(ADMIN);
    }

    public void showRoleView() {
        if (MmkvUtil.isStudent()) {

        } else if (MmkvUtil.isTeacher()) {

        } else if (MmkvUtil.isAdmin()) {

        }
    }
}
