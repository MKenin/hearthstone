package haven.extension;

import haven.Text;

public class QualityHelper {

    private static Integer _radius = null;

    public static int BackgroundRadius() {
        if(_radius == null)
        {
           _radius = (int)Math.round(Text.std.strsize("000").x * 1.2);
        }
        return _radius;
    }
}
