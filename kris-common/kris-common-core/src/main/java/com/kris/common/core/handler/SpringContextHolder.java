package com.kris.common.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/** @Author: kris @Date: 2021/7/12 @Description: @Since: JDK11 */
@Slf4j
@Service
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

  private static ApplicationContext applicationContext = null;

  @Override
  public void setApplicationContext(@Nullable ApplicationContext applicationContext)
      throws BeansException {
    if (SpringContextHolder.applicationContext != null) {
      log.warn(
          "SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
              + SpringContextHolder.applicationContext);
    }
    setSpringContext(applicationContext);
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void destroy() {
    if (log.isDebugEnabled()) {
      log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
    }
    this.setSpringContext(null);
  }

  private synchronized void setSpringContext(ApplicationContext applicationContext) {
    SpringContextHolder.applicationContext = applicationContext;
  }

  /**
   * Spring事件发布
   *
   * @param event 事件
   */
  public static void publishEvent(ApplicationEvent event) {
    applicationContext.publishEvent(event);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) {
    return (T) applicationContext.getBean(name);
  }

  public static <T> T getBean(Class<T> typeClass) {
    return applicationContext.getBean(typeClass);
  }

  public static <T> T getBean(String name, Class<T> typeClass) {
    return applicationContext.getBean(name, typeClass);
  }

}
