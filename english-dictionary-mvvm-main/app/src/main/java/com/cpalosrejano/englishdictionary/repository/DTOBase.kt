package com.cpalosrejano.englishdictionary.repository

/**
 * Helper class with methods which helps to user easily convert from DTO to Domain Objects.
 */
abstract class DTOBase<T> {

    /**
     * Function which convert a DTO Object into a Domain Object.
     * User is responsible to implements it if want to use List<DTOBase>.asDomain()
     */
    abstract fun asDomain() : T

}

/**
 * Method which convert a list of DTO Object into a list of Domain Object
 * This inline function uses DTOBase.asDomain()
 */
fun <T> List<DTOBase<T>>.asDomain() : List<T> {
    return map {
        it.asDomain()
    }
}