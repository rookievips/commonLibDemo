package com.chen.api.helper;

import android.app.Activity;

import java.util.Stack;

/**
 * Author: Chen
 * Date: 2018/6/6
 * Desc: activity 管理类
 */
public class ActManager {
    private static Stack<Activity> activityStack;
    private static ActManager instance;

    private ActManager() {}

    /**
     * 单一实例
     * @return
     */
    public static ActManager getInstance() {
        if (instance == null) {
            synchronized (ActManager.class) {
                if (instance == null) {
                    instance = new ActManager();
                    activityStack = new Stack<>();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到栈
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity(栈中最后一个被压入的)
     * @return
     */
    public Activity getCurrentActivity() {
        try {
            return activityStack.lastElement();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前Activity的前一个Activity
     * @return
     */
    public Activity getPreActivity() {
        int index = activityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        return activityStack.get(index);
    }

    /**
     * 结束当前的Activity(栈中最后一个压入的)
     */
    public void finishCurActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除指定的Activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束除当前以外的所有Activity
     */
    public void finishAllActivityWithoutCur() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                if (activity != activityStack.lastElement()) {
                    activityStack.remove(activity);
                    activity.finish();
                }
            }
        }
    }

    /**
     * 返回指定的Activity
     * @param cls
     */
    public void returnToActivity(Class<?> cls) {
        while (activityStack.size() != 0)
            if (activityStack.peek().getClass() == cls) {
                break;
            } else {
                finishActivity(activityStack.peek());
            }
    }

    /**
     * 是否已经打开了指定的Activity
     * @param cls
     * @return
     */
    public boolean isOpenActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (cls == activity.getClass()) {
                    return true;
                }
            }
        }
        return false;
    }
}
