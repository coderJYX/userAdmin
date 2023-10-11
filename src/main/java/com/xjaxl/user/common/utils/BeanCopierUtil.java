package com.xjaxl.user.common.utils;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对Cglib BeanCopier进行封装 方便进行bean复制

 * @date 2023-02-28
 */
public abstract class BeanCopierUtil {
    private static final Map<String, BeanCopier> beanCopierCache = new ConcurrentHashMap<>();
    private static final Map<String, ConstructorAccess<?>> constructorAccessCache = new ConcurrentHashMap<>();

    /**
     * 拷贝泛型对象
     *
     * <pre>
     * Page&lt;ProductLimitPrice&gt; src = new Page<>();
     * Page&lt;ProductLimitPriceDTO&gt; dist = WrappedBeanCopier.copyProperties(src, new TypeReference&lt;Page&lt;ProductLimitPriceDTO&gt;&gt;(){});
     * </pre>
     */
    public static <T> T copyProperties(Object source, TypeReference<T> targetType) {
        if (source == null) {
            return null;
        }
        return TypeUtils.cast(source, targetType.getType(), ParserConfig.getGlobalInstance());
    }

    /**
     * 拷贝普通对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        ConstructorAccess<?> constructorAccess = getConstructorAccess(targetClass);
        T t = (T) constructorAccess.newInstance();
        copyProperties(source, t);
        return t;
    }

    /**
     * 拷贝List
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        ConstructorAccess<?> constructorAccess = getConstructorAccess(targetClass);
        List<T> resultList = new ArrayList<>(sourceList.size());
        for (Object o : sourceList) {
            T t = null;
            try {
                t = (T) constructorAccess.newInstance();
                copyProperties(o, t);
                resultList.add(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

    private static void copyProperties(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier = null;
        if (!beanCopierCache.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            beanCopierCache.put(beanKey, copier);
        } else {
            copier = beanCopierCache.get(beanKey);
        }
        return copier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    private static ConstructorAccess<?> getConstructorAccess(Class<?> targetClass) {
        ConstructorAccess<?> constructorAccess = constructorAccessCache.get(targetClass.toString());
        if(constructorAccess != null) {
            return constructorAccess;
        }
        try {
            constructorAccess = ConstructorAccess.get(targetClass);
//            constructorAccess.newInstance();
            constructorAccessCache.put(targetClass.toString(),constructorAccess);
        } catch (Exception e) {
            throw new RuntimeException("Create new instance of "+targetClass+" failed: "+e.getMessage());
        }
        return constructorAccess;
    }

}