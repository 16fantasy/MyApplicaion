package com.fantasy.common.util;

import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * @author jingc
 * @description BeanUtil  bean工具类
 * @since 2022/2/23
 */
public class BeanUtil {

    public static <T> T beanCopy(Object origin, Class<T> classZ) throws IllegalAccessException, InstantiationException {
        if (Objects.isNull(origin)) {
            return null;
        }
        T target = classZ.newInstance();
        BeanUtils.copyProperties(origin, target);
        return target;
    }
}
