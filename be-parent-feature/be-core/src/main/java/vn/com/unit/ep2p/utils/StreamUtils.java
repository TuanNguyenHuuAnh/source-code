package vn.com.unit.ep2p.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by NghiaPV on 7/31/2018.
 */
public class StreamUtils {
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(StreamUtils.class);

    /**
     *
     * @param collectionObject
     * @param searchPropertyAccessor
     * @param
     * @param <T>
     * @return
     */
    public static <T> List<T> searchElementDistinctByKey(Collection<T> collectionObject, Function<T, Object> searchPropertyAccessor) {

        List<T> result = new ArrayList<>();

        try {
            result = collectionObject.stream()
                    .filter(item -> searchPropertyAccessor.apply(item) != null)
                    .filter(distinctByKey(item -> searchPropertyAccessor.apply(item)))
                    .collect(Collectors.toList());

        }catch (Exception e){
            logger.error("##searchElementDistinctByKey##", e);
            throw e;
        }

        return result;
    }

    /**
     *
     * @param collectionObject
     * @param searchPropertyAccessor
     * @param searchText
     * @param <T>
     * @return
     */
//  use: List<User> foundUsers = search(users, User::getLastName, "Doe");
    public static <T> List<T> searchElementByKey(Collection<T> collectionObject, Function<T, String> searchPropertyAccessor, String searchText) {

        List<T> result = new ArrayList<>();

        try {
            result = collectionObject.stream()
                    .filter(item -> searchPropertyAccessor.apply(item) != null)
                    .filter(item -> Objects.equals(searchPropertyAccessor.apply(item), searchText))
                    .collect(Collectors.toList());

        }catch (Exception e){
            logger.error("##searchElementByKey##", e);
            throw e;
        }

        return result;
    }

    /**
     * @author NghiaPV
     * @since 17 Jan 2018
     * @param keyExtractor
     * @param <T>
     * @return
     */
    //https://www.concretepage.com/java/jdk-8/java-8-distinct-example
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
