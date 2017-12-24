package collections;

import java.util.List;

/** A map interface that allows to insert (put) a (key, value) pair into the map,
 *  look up a value based on the key, delete a (key, value) pair based on the key, check the number
 *  of entries currently in the map, get a list of all keys, and a list of all values.
 *
 *  You may NOT modify anything in this file. Your MyHashMap class should implement MyMap
 *  interface that I provided.
 */
public interface MyMap {
    /** Insert a (key, data) into the map, overwriting the previous value associated with the given key.
     * @param key If the key is null, throw IllegalArgumentException.
     * @param data
     * @return the previous value associated with this key or null if the key did not map to any value.
     */
    Object put(String key, Object data);

    /** Return the value associated with the given key or null if the key does not map to any value
     *  @param key If null, throw IllegalArgumentException.
     *  @return the value associated with the key.
     */
    Object get(String key);

    /** Delete the (key, data) entry from the map or do nothing if this key is not in the map.
     *  @param key
     *  @return The value associated with this key before deletion
     */
    Object delete(String key);

    /** Return the number of entries in the map
     * @return the number of (key, value) entries in the map.
     */
    int size();

    /** Return map keys
     * @return a list of all keys in the map
     * */
    List<String> keys();

    /** Return map values
     * @return a list of all values in the map
     * */
    List<Object> values();
}
