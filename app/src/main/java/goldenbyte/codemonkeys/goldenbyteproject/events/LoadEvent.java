package goldenbyte.codemonkeys.goldenbyteproject.events;

import java.util.ArrayList;

import goldenbyte.codemonkeys.goldenbyteproject.bean.Cafe;

/**
 * Created by dmi3coder on 03.01.2016 14:23.
 */
public class LoadEvent {
    public final ArrayList<Cafe> cafes;

    public LoadEvent(ArrayList<Cafe> cafes) {
        this.cafes = cafes;
    }
}
