package nestedClasses;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/** The example demonstrates how to use an inner class, StringWorker, to process a string */
public class StringsExample {
    private Map<Character, Integer> mapCharacters; // for each character, stores its # of occurrences in the strings
    // Array of strings:
    private String[] strings = {
            "abcsfjfws;kf;wks;gbnlensljegkq'r'bwgjqkf'kb';ked'gkw'g'wkg'wkg'kw'gk'wkgh'kw'gk'qgkq'gk'q",
            "vqwjf1jf[mvqmcvqm'm'wgk[32989v;fkfqkf'q;fqpjg[jq[fk[q;fqf[qkf]l]   nedobvonwr;vqp",
            "lisasfk[skv[mbnwogb;fcqanfpqa;qnpfnpnqapfq;npqanpgy[akcq; qm[qmfq  qfl;",
            "dvjp;[qfv[s[vkb jdpqaf af[qkd[ka]vl]slv]lsafj[wvb,,vsvk[sk[kv[sk[vk[sfqwojrf"};
    private ExecutorService executor = null;

    public StringsExample() {
        mapCharacters = new HashMap<>();
        executor = Executors.newCachedThreadPool();
    }

    /** Inner class, StringWorker
     *  Processes one string, adds info to the map
     */
    private class StringWorker implements Runnable {
        String str;
        StringWorker(String s) {
            str = s;
        }

        @Override
        public void run() {
              for (int i = 0; i < str.length(); i++) {
                    Character ch = str.charAt(i);
                    modifyMap(ch);

              } // for

        } // run
    }

    /** Increment the value corresponding to the given character in the map */
    public synchronized void modifyMap(Character ch) {
        if (mapCharacters.get(ch) == null)
            mapCharacters.put(ch, 1);
        else {
            mapCharacters.put(ch, mapCharacters.get(ch) + 1);
        }
    }


    /** Print all the values from the map of characters */
    public synchronized void print() {
        for (Character ch: mapCharacters.keySet()) {
            System.out.println(ch + ": " + mapCharacters.get(ch));
        }
    }

    /** Creates a new StringWorker for each string in the array, and submits it to the executor service */
    public void processStrings() {
        for (String s: strings) {
            executor.submit(new StringWorker(s));
        }
        executor.shutdown(); // do not accept any more jobs
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS); // wait for all threads to finish
        }
        catch(InterruptedException e) {
            System.out.println("Interrupted : " + e);
        }
    }

    public static void main(String[] args) {
        StringsExample ex = new StringsExample();
        ex.processStrings();
        ex.print();
    }
}
