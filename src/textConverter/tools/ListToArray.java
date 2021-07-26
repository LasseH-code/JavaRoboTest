package textConverter.tools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListToArray<T> {
    public T[] listToArray (Class<T> c, List<T> in) {
        T[] out = (T[]) Array.newInstance(c, in.size());
        for (int i = 0; i < out.length; i++) {
            out[i] = in.get(i);
        }
        return out;
    }
    public List<T> arrayToList (T[] in) {
        List<T> out = new ArrayList<T>();
        for (int i = 0; i < out.size(); i++) {
            out.add(in[i]);
        }
        return out;
    }
}
