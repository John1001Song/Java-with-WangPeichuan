package collections;

import java.util.*;

/** An implementation of MyMap interface.
 *  A hash table that uses linear probing to resolve collisions.
 *  Fill in the code in this class and test it thoroughly.
 */
public class MyHashMap implements MyMap {

     private HashEntry[] table; // the actual hashtable - an array of Hash Entries
     private int maxSize; // the maximum number of entries in the table
     private int currentSize; // how many entries the table contains

    /** A constructor for MyHashMap class
     *  @param maxSize - the maximum number of elements in the hash table.
     */
    public MyHashMap(int maxSize) {
        // FILL IN CODE
        // Initialize instance variables maxSize and table
        this.maxSize = maxSize;
        table = new HashEntry[maxSize];

        for (int i = 0; i < maxSize; i++) {
            table[i] = null;
        }

        this.currentSize = 0;
    }

    /**find method is used to check if the key is in the table.
     * if the key is in the table and no matter deleted or not, return its index.
     *      It means this position is available for the key to put a value.
     *
     * if the hashCode position is occupied by another key, index++, until one position is available.
     *      If all checked and no position available, return -1, which represents not found.
     * */
    public int find(String key){

        int currentIndex;
        int newIndex;

        int returnPosition;

        newIndex = currentIndex = hash(key);

        //if this position is null, which means the position is never occupied. Then, return -3.
        if (table[currentIndex] == null){
            returnPosition = -3;
            return returnPosition;
        }

        //newIndex++;//pointer moves to the next position

        //start from the current index, check if the following positions have the key
        //if on the position, a key's value was deleted and isDeleted == true,
        // it is ok, since the String key is left in this position.
        //we can still check table[index].getKey().equals()
        while (table[newIndex] != null){

            // if the key is found, return its index
            // even the key is deleted, it is ok to return the index.
            // It could convenient put function to insert a previously deleted key
            // in the put function, table[index].getState will check if it is deleted
            if (table[newIndex].getKey().equals(key)){
                return newIndex;
            }

            //the key has not been found yet, pointer moves to the next position
            newIndex++;

            while (newIndex >= maxSize){
                newIndex = newIndex - maxSize;
            }

            // when the newIndex has checked all the positions and is back to the starting point,
            // it means the table is full
            if (newIndex == currentIndex) {
                return -1;
            }
        }

        // after the while loop, newIndex stops and return not found, because of there is a null cell.
        // it means there is no more entries after the newIndex and it is not necessary to check.
        // -2 means the table is not full and the key is not found
            return -2;
    }


    /** Insert a (key, data) into the map, overwriting the previous value associated with the given key.
     * @param key If the key is null, throw IllegalArgumentException.
     * @param data
     * @return the previous value associated with this key or null if the key did not map to any value.
     */
    @Override
    public Object put(String key, Object data) {
        // FILL IN CODE

        if (key == null) throw new IllegalArgumentException();

        int position = find(key);

        // when the table is full and not find the key, no more key can be inserted
        if (position == -1){
            return null;
        }

        // -2 means the key is not in the table and the table is not full, it can be inserted
        if (position == -2) {

            int keyIndex = hash(key);
            //when current position is occupied by other keys and not deleted, pointer moves
            while (table[keyIndex] == null || !table[keyIndex].getKey().equals(key)){
                if (table[keyIndex] == null){
                    table[keyIndex] = new HashEntry(key, data);
                    return null;
                }

                if (table[keyIndex].getState() == true){
                    table[keyIndex].setKey(key);
                    table[keyIndex].setData(data);
                    table[keyIndex].setDeletedFalse();
                    return null;
                }

                keyIndex++;
                if (keyIndex >= maxSize){
                    keyIndex = keyIndex - maxSize;
                }
            }
            //after the while loop, if there is no return, it means there is no key in deleted state.
            // current key should be inserted to the tail of the chunk

            if (keyIndex >= maxSize){
                keyIndex = keyIndex - maxSize;
            }

            if (table[keyIndex] == null) {
                table[keyIndex] = new HashEntry(key, data);
                return null;
            }

            return null;
        }

        // if there is a null position for the hashCode position, directly insert key and value
        // return null
        if (position == -3){
            int keyIndex = hash(key);

            table[keyIndex] = new HashEntry(key, data);
            return null;
        }

            // if the key was deleted, then insert the new value in the old position
            // since the key is marked as deleted, the key's value is not available and return null

        if (table[position].getState() == true){
            table[position].setData(data);
            table[position].setDeletedFalse();
            return null;
        }
        else {
            Object previousValue = table[position].getData();
            table[position].setData(data);
            return previousValue;
        }

        // when the key is found and not deleted, update the key's value
        // return previous key's value




    }

    /** Return the value associated with the given key or null if the key does not map to any value
     *  @param key If null, throw IllegalArgumentException.
     *  @return the value associated with the key.
     */
    @Override
    public Object get(String key) {
        // FILL IN CODE

        if (key == null) throw new IllegalArgumentException();

        /* Check the array from index 0 to maxSize-1;
        *   if there is a key same as the wanted key, check the value;
        *       if the value is available, return it;
        *       else return null
        *   if there is no key same as the wanted key, return null*/

        for (HashEntry entry: table){
            if (entry != null && entry.getKey().equals(key) && entry.getState() == false)
                return entry.getData();
        }

        return null; // change this

    }

    /** Delete the (key, data) entry from the map or do nothing if this key is not in the map.
     *  @param key
     *  @return The value associated with this key before deletion
     */
    @Override
    public Object delete(String key) {
        // FILL IN CODE

        for (HashEntry entry:table){
            if (entry.getKey().equals(key)){
                if (entry.getState() == false){
                    entry.setDeletedTrue();
                    return entry.getData();
                }else return null;
            }
        }
        return null;


    }

    /** Return the number of entries in the map
     * @return the number of (key, value) entries in the map.
     */
    @Override
    public int size() {

        return currentSize;
    }

    /** Return map keys
     * @return a list of all keys in the map
     */
    public List<String> keys() {
        // FILL IN CODE

       List<String> keys = new ArrayList<String>();

       for (HashEntry entry: table){
           if (entry != null && entry.getKey() != null && entry.getState() == false){
               keys.add(entry.getKey());
           }
       }

       return keys;

    }

    /** Return map values
     * @return a list of all values in the map
     */
    @Override
    public List<Object> values() {
       // FILL IN CODE

        List<Object> values = new ArrayList<Object>();

        for (HashEntry entry: table){
            if (entry != null && entry.getKey() != null && entry.getState() == false){
                values.add(entry.getData());
            }
        }

        return values;
    }

    /** Returns the index in the hash table that this key hashes to.
     * @param key
     * @return the index in the table
     * */
    private int hash(String key) {
        // FILL IN CODE
        // Call the hashCode() method in class String and return this value modulus the table size
        int keyIndex = key.hashCode();
        while (keyIndex < 0){
            keyIndex = keyIndex + maxSize;
            if (keyIndex > 0) break;
        }
        keyIndex = keyIndex % maxSize;
        return keyIndex; // change this
    }
}
