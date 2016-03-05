package studio.jhl.android4places.backend.type;

/**
 * Created by dmi3coder on 3/5/16 2:49 PM.
 */
public enum CafeType{
    ALL,CAFE,NIGHT_CLUB,FUN,RESTAURANT,FASTFOOD,SUSHI,ETC;
    String[] backendRuTypes = {"","Кофейня", "Ночной клуб", "Развлечения", "Ресторан", "Фаст фуд", "Суши бар", "Что то другое"};

    @Override
    public String toString() {
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
}
