package org.ifolks.commons.model.patterns;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static <T> T getBean(Class<T> beanClass) {
		if (context == null) {
			throw new IllegalStateException("ApplicationContext is not initialized yet in SpringContextUtils.");
		}
		return context.getBean(beanClass);
	}

	public static Object getBean(String name) {
		if (context == null) {
			throw new IllegalStateException("ApplicationContext is not initialized yet in SpringContextUtils.");
		}
		return context.getBean(name);
	}
}
