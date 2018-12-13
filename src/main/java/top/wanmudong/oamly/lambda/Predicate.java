package top.wanmudong.oamly.lambda;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanmudong
 * @date 20:53 2018/12/11
 */
@FunctionalInterface
public interface Predicate<T> {
    public boolean test(T s);

    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<T>();
        for(T s : list){
            if(p.test(s)){
                result.add(s);
            }
        }
        return result;
    }

}
