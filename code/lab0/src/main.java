package collections;

import java.util.*;

public class main {

    public static void main(String[] args) {
        MyHashMap hmap =  new MyHashMap(7);
        //   insert a  bunch of   keys, some with the same hash code
        //   Strings that hash to   the same hash code were taken from the following site:
        //   https://stackoverflow.com/questions/12925988/how-to-generate-strings-that-share-the-same-hashcode-in-java


        hmap.put("BB", 100);

        System.out.println(hmap.keys());
        System.out.println(hmap.values());

        //System.out.println(hmap.keys() + " BB is inserted");
        //System.out.println(hmap.values() + " BB's value is inserted");
        //System.out.println("BB's value is " + hmap.get("BB"));

        hmap.put("BB", 1);
        System.out.println(hmap.keys());
        System.out.println(hmap.values());

        hmap.put("aa", 1);
        System.out.println(hmap.keys());
        System.out.println(hmap.values());

        hmap.put("cc", 2);
        System.out.println(hmap.keys());
        System.out.println(hmap.values());


        hmap.put("Aa", 2);
        System.out.println(hmap.keys());
        System.out.println(hmap.values());

        hmap.put("AaAa", 7);
        System.out.println(hmap.keys());
        System.out.println(hmap.values());


        hmap.put("BBBB", 5);
        System.out.println(hmap.keys());
        System.out.println(hmap.values());


        hmap.put("BBAb", 10);
        System.out.println(hmap.keys());
        System.out.println(hmap.values());


        hmap.put("Aa", 19);
        System.out.println(hmap.keys());
        System.out.println(hmap.values());


        hmap.delete("BB");
        System.out.println(hmap.keys());
        System.out.println(hmap.values());



        hmap.put("BB", 4);
        System.out.println(hmap.keys());
        System.out.println(hmap.values());

        //   check if   the correct (key, value) entry was stored in the hash table
        if   ((Integer)hmap.get("BB") !=   4)   {
            System.out.println("Wrong value for the key 'BB'");
        }


        hmap.put("Aa", 2);
        if   ((Integer)hmap.get("Aa") !=   2)   {
            System.out.println("Wrong value for the key 'Aa'");
        }


        hmap.put("AaAa", 7);
        if   ((Integer)hmap.get("AaAa") != 7) {
            System.out.println("Wrong value for the key 'AaAa'");
        }

        hmap.put("BBAb", 10);
        if   ((Integer)hmap.get("BBAb") != 10) {
            System.out.println("Wrong value for the key 'BBAb'");
        }

        //   check "Replacing a  value"
        hmap.put("Aa", 2);

        System.out.println("keys are " + hmap.keys());
        System.out.println("values are " + hmap.values());



        if   (((Integer)hmap.put("Aa", 19) != 2) || ((Integer)hmap.get("Aa") != 19)) {
            System.out.println("Overwriting the key Aa   did not work correctly. ");
        }

        //   Check Deletion
        if   ((Integer)hmap.delete("BB") !=   4)   {
            System.out.println( "Deleting 'BB' did not work correctly. ");
        }

        if   (hmap.get("BB") !=   null) {
            System.out.println( "After we   deleted 'BB', value for it should be   null, but it is not ");
        }
        System.out.println(hmap.keys());
        System.out.println(hmap.values());

        //   check "Replacing a  value"
        hmap.put("BB", 25);

        if   (((Integer)hmap.get("BB") !=   25)) {
            System.out.println("Deletion followed by   inserting the key BB again did not work correctly. ");
        }
    }
}