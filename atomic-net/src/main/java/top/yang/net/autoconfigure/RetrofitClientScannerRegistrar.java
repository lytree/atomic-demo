package top.yang.net.autoconfigure;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.yang.net.annotation.RetrofitClient;
import top.yang.net.spring.RetrofitClientFactoryBean;

public class RetrofitClientScannerRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware {
    private static final Logger logger = LoggerFactory.getLogger(RetrofitClientScannerRegistrar.class);


    private BeanFactory beanFactory;
    private ResourceLoader resourceLoader;
    private ClassLoader classLoader;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata metadata, @NotNull BeanDefinitionRegistry registry) {
        if (!AutoConfigurationPackages.has(this.beanFactory)) {
            logger.debug("Could not determine auto-configuration package, automatic retrofit scanning disabled.");
            return;
        }
        List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
        ClassPathRetrofitClientScanner scanner = new ClassPathRetrofitClientScanner(registry,classLoader);
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }
        scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(packages));
    }

    @Override
    public void setResourceLoader(@NotNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static class ClassPathRetrofitClientScanner extends ClassPathBeanDefinitionScanner {

        private final ClassLoader classLoader;

        public ClassPathRetrofitClientScanner(BeanDefinitionRegistry registry, ClassLoader classLoader) {
            super(registry, false);
            this.classLoader = classLoader;
        }

        public void registerFilters() {
            AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(RetrofitClient.class);
            this.addIncludeFilter(annotationTypeFilter);
        }

        @NotNull
        @Override
        protected Set<BeanDefinitionHolder> doScan(@NotNull String... basePackages) {
            Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
            if (beanDefinitionHolders.isEmpty()) {
                logger.warn("No RetrofitClient was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
            } else {
                processBeanDefinitions(beanDefinitionHolders);
            }
            return beanDefinitionHolders;
        }
        @Override
        protected boolean isCandidateComponent(
                AnnotatedBeanDefinition beanDefinition) {
            if (beanDefinition.getMetadata().isInterface()) {
                try {
                    Class<?> target = ClassUtils.forName(
                            beanDefinition.getMetadata().getClassName(),
                            classLoader);
                    return !target.isAnnotation();
                } catch (Exception ex) {
                    logger.error("load class exception:", ex);
                }
            }
            return false;
        }

        private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
            GenericBeanDefinition definition;
            for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
                definition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
                if (logger.isDebugEnabled()) {
                    logger.debug("Creating RetrofitClientBean with name '" + beanDefinitionHolder.getBeanName()
                            + "' and '" + definition.getBeanClassName() + "' Interface");
                }
                String beanClassName = definition.getBeanClassName();
                definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
                // beanClass全部设置为RetrofitFactoryBean
                definition.setBeanClass(RetrofitClientFactoryBean.class);
            }
        }
    }


}