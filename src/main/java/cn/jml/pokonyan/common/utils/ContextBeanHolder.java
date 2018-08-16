package cn.jml.pokonyan.common.utils;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import lombok.NonNull;

public class ContextBeanHolder implements ApplicationContextAware {

    private ContextBeanHolder() {}

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == context) {
            synchronized (ContextBeanHolder.class) {
                if (null == context)
                    context = applicationContext;
            }
        }
    }

    /**
     * 获取指定name的Bean实例
     * 
     * @param <E>
     *            泛型
     * @param name
     *            Bean名称
     * @return E
     */
    @SuppressWarnings("unchecked")
    public static <E> E getBean(@NonNull String name) {
        return Objects.isNull(context) ? null : (E) context.getBean(name);
    }

    /**
     * 通过类型获取Bean
     * 
     * @param type
     *            Bean类型
     * @return E
     */
    public static <E> E getBean(@NonNull Class<E> type) {
        return Objects.isNull(context) ? null : context.getBean(type);
    }

    /**
     * 返回该类型的所有实现类
     * 
     * @param <E>
     *            泛型
     * @param clazz
     *            类型
     * @return 返回IOC容器中所有该类型实例对象
     */
    @SuppressWarnings("unchecked")
    public static <E> Map<String, E> getBeans(@NonNull Class<? extends E> clazz) {
        return Objects.isNull(context) ? null : (Map<String, E>) context.getBeansOfType(clazz);
    }

    public static <T> String[] getBeanNamesForType(@NonNull Class<T> clazz) {
        return Objects.isNull(context) ? null : context.getBeanNamesForType(clazz);
    }

    /**
     * 配置BeanHolder实例
     */
    @ConditionalOnMissingBean(ContextBeanHolder.class)
    public static class BeanHolderConfiguration {

        @Bean(autowire = Autowire.NO)
        public ContextBeanHolder createBeanHolder() {
            return new ContextBeanHolder();
        }
    }
}
