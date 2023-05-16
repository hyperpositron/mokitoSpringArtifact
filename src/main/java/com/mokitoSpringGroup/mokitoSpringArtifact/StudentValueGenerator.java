package com.mokitoSpringGroup.mokitoSpringArtifact;

public class StudentValueGenerator {
    private static int DEFAULT_MAX = 100;
    public static int DEFAULT_MIN = 1;

    public int generateAgeInRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int generateAge() {
        return generateAgeInRange(DEFAULT_MIN, DEFAULT_MAX);
    }
}
