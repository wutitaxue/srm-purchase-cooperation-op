package org.srm.purchasecooperation.cux.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: haidong.liang
 * @Date: 2021/3/9 2:53 下午
 */
@Component
public class AllowRawInjectDesWap implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ((DefaultListableBeanFactory)beanFactory).setAllowRawInjectionDespiteWrapping(true);
    }
}