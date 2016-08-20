package com.dmi3coder.fourplaces.backend.type;


public enum MealType {
    FIRST, SECOND, DRINK, SNACKS, BAKERY, CANDY, SEA, ETC;
    private static final String[] backendJsonTypes = {"first", "second", "drink", "snacks", "bread", "confectionery", "seaproduct", "etc"};
    public static final String[] backendRuTypes = {"Первое", "Второе", "Напитки", "Закуски", "Хлебные изделия", "Кондитерские изделия", "Морские продукты"};

    @Override
    public String toString() {
        for (int i = 0; i < MealType.values().length; i++) {
            if (this == MealType.values()[i]) {
                return MealType.backendRuTypes[i];
            }
        }
        return null;
    }

    public String toJson() {
        for (int i = 0; i < MealType.values().length; i++) {
            if (this == MealType.values()[i]) {
                return MealType.backendJsonTypes[i];
            }
        }
        return null;
    }
}
