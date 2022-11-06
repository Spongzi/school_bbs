package com.spongzi.interceptor;

import com.spongzi.domain.User;

/**
 * 工具类, 实现向threadlocal存储方法
 *
 * @author spongzi
 */
public class UserHolder {
    private static ThreadLocal<User> t1 = new ThreadLocal<>();

    /**
     * 将用户对象存入threadlocal
     *
     * @param user 用户信息
     */
    public static void set(User user) {
        t1.set(user);
    }

    /**
     * 从当前线程获取用户对象
     *
     * @return 返回用户对象
     */
    public static User get() {
        return t1.get();
    }

    /**
     * 从当前线程, 获取用户Id
     *
     * @return 返回id
     */
    public static Long getUserId() {
        return t1.get().getId();
    }

    /**
     * 从当前线程,获取用户对象手机号码
     *
     * @return 获取到的手机号
     */
    public static String getPhone() {
        return t1.get().getPhone();
    }

    /**
     * 从当前线程,获取用户对象邮箱账号
     *
     * @return 获取到的邮箱
     */
    public static String getEmail() {
        return t1.get().getEmail();
    }

    /**
     * 清空线程
     */
    public static void remove() {
        t1.remove();
    }
}
