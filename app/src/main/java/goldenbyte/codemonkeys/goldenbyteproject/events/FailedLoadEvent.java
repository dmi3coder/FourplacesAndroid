package goldenbyte.codemonkeys.goldenbyteproject.events;

/**
 * Created by dmi3coder on 03.01.2016 14:29.
 */
public class FailedLoadEvent {
    public final String message;

    public FailedLoadEvent(String message) {
        this.message = message;
    }
}
