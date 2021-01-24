package com.prathap.weather.utils

/**
 * This class will be used by allOpen plugin to open all final class to Mock'able
 *  allows mocking for classes w/o directly opening them for Mocking
 * Note:  annotation 'com.prathap.weather.utils.OpenForTesting' needs to be added in build.gradle in allOpen{} tag
 */
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting
