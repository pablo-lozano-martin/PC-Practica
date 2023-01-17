package servidor;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
 
class Pair
{
    public static <T, U> Map.Entry<T, U> of(T first, U second) {
        return new AbstractMap.SimpleEntry<>(first, second);
    }
}