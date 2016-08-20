package com.dmi3coder.fourplaces.backend.type;


public enum CafeType{
    ALL,CAFE,NIGHT_CLUB,FUN,RESTAURANT,FASTFOOD,SUSHI,ETC;
    private static String[] backendRuTypes = {"","Кофейня", "Ночной клуб", "Развлечения", "Ресторан", "Фаст фуд", "Суши бар", "Что то другое"};


    public String toOldBackendString() {
        CafeType[] cafeTypes = CafeType.values();
        for(int i = 0 ;i<cafeTypes.length;i++) {
            if (this == cafeTypes[i]){
                if(i==0){
                    return "/api/getallcafe";
                }
                return "/api/getcafebytype/"+backendRuTypes[i];
            }
        }
        throw new NullPointerException("Cafe type not found");
    }

    public static CafeType toEnum(String jsonType) {
        for (int i = 0; i < backendRuTypes.length; i++) {
            if (backendRuTypes[i].equals(jsonType)) {
                return CafeType.values()[i];
            }
        }
        return ALL;
    }

    @Override
    public String toString() {
        CafeType[] cafeTypes = CafeType.values();
        for(int i = 0 ;i<cafeTypes.length;i++) {
            if (this == cafeTypes[i]){
                return backendRuTypes[i];
            }
        }
        return "";
    }
    }
