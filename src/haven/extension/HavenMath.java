package haven.extension;

public class HavenMath {
   public static int expAverage(int[] values) 
   {
        int product = 1;
        for(int value : values)
            product *= value;

        return (int) Math.floor(Math.pow(product, 1.0 / values.length));
   }
}
