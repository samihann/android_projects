// MovieCentralAIDL.aidl
package com.samihann.MovieCentral;

// Declare any non-default types here with import statements

interface MovieCentralAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    String[] allMovies();
    String aMovie(int number);
    String[] allMovieName();

}