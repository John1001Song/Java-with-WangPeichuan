package collections;

/** A class that represents an entry in the hash table: a (key, data) pair */
public class HashEntry {
    private String key;
    private Object data;
    private boolean isDeleted; // whether this entry was deleted

    /** Constructor for HashEntry
     * @param key
     * @param data
     */
    public HashEntry(String key, Object data) {
        // FILL IN CODE
        this.key = key;
        this.data = data;
        this.isDeleted = false;
    }

   // FILL IN CODE: Add other methods as needed (getters, setters)

    //getter
    public String getKey() {
        return key;
    }

    public Object getData() {
        return data;
    }

    public boolean getState(){
        return isDeleted;
    }

    //setter
    public void setKey(String key){
        this.key = key;
    }

    public void setData(Object data){
        this.data = data;
    }

    public void setDeletedTrue(){
        this.isDeleted = true;
    }

    public void setDeletedFalse(){
        this.isDeleted = false;
    }
}
