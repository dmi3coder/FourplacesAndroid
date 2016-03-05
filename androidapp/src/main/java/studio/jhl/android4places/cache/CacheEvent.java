package studio.jhl.android4places.cache;

/**
 * Created by dmi3coder on 3/5/16 9:58 PM.
 */
public class CacheEvent {
    public final String RESULT;
    public static final String RESULT_DONE="studio.jhl.android4places.cache.DONE";
    public static final String RESULT_ERROR="studio.jhl.android4places.cache.ERROR";

    public CacheEvent(String RESULT) {
        this.RESULT = RESULT;
    }
}
