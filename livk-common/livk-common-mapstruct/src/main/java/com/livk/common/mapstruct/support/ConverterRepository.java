package com.livk.common.mapstruct.support;

import com.livk.common.mapstruct.converter.Converter;

import java.util.Map;

/**
 * <p>
 * AbstractFactory
 * </p>
 *
 * @author livk
 * @date 2022/5/11
 */
@SuppressWarnings("rawtypes")
public interface ConverterRepository {

	boolean contains(Class<?> sourceClass, Class<?> targetClass);

	Converter get(Class<?> sourceClass, Class<?> targetClass);

	Map<Class<?>, Map<Class<?>, Converter>> getConverterMap();

	void put(Converter t);

}
