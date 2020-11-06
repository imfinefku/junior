/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 非Controller调用Service
 *
 * @author yutao
 */
public class SpringBeanFactoryUtils implements ApplicationContextAware {

    private static ApplicationContext appCtx;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        appCtx = ac;
    }

    public static ApplicationContext getApplicationContext() {
        return appCtx;
    }

    public static Object getBean(String beanName) {
        return appCtx.getBean(beanName);
    }
}
