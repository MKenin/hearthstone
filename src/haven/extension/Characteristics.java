package haven.extension;

import java.util.*;
import haven.*;

public class Characteristics {

    public static Integer getByName(String name)
    {
        return ((Glob.CAttr)Game.Global.cattr.get(name)).comp;
    }
}
