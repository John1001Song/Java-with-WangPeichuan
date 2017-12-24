import java.util.*;
/** A class that contains several simple examples from the lecture
 *  on Data Structures. 
 */

public class DataStructuresExample {
	
	public static void runArrayListExample() {

		// ArrayList example
		System.out.println("----------An ArrayList Example ----------");
		List<String> countries = new ArrayList<String>();
		countries.add("USA");
		countries.add("China");
		countries.add("Italy");

		for (int i = 0; i < countries.size();  i++)  {
			System.out.print(countries.get(i) + " ");
		}
		System.out.println();
		System.out.println();
	}

	public static void runHashSetExample() {

		// HashSet example
		System.out.println("------------A HashSet Example ----------");
		Set<String> words = new HashSet<String>();

		words.add("cat");
		words.add("mail");
		words.add("dog");
		words.add("cat"); // won't add it again
		System.out.println("The set has no duplicates: ");
		System.out.println(words); //don't know the order
		System.out.println();


	}

	public static void runTreeSetExample() {

		// TreeSet example
		System.out.println("----------A TreeSet Example ----------");
		TreeSet<String> wordsTree = new TreeSet<String>();
		wordsTree.add("duck");
		wordsTree.add("cat");
		wordsTree.add("mail");
		wordsTree.add("dog");
		String firstElement = wordsTree.first();
		System.out.println("The first element is " + firstElement);
		String demoLower = wordsTree.lower("mail"); //returns the greatest word less than mail
		System.out.println("The greatest value lower than mail is " + demoLower);
		String demoHigher = wordsTree.higher("cat");  // returns the smallest word higher than cat
		System.out.println("The lowest value higher than cat is " + demoHigher);
		System.out.println();

	}

	public static void runHashMapExample() {

		// create a dictionary
		System.out.println("----------A HashMap example -------------");

 		Map<String, String>  dict = new HashMap<>();

		dict.put("smart", "Having a quick-witted intelligence");

		dict.put("university", "A school that offers courses leading to a degree");
		dict.put("diploma", "A certificate issued by an educational institution, that testifies that the recipient has successfully completed a particular course of study");
		
		// Iterate over the keys and print keys and values:
		for (String key : dict.keySet()) {
 				 System.out.println("Key:" + key + ", Value: " + dict.get(key));
		}
		System.out.println();


	}

	public static void runTreeMapExample() {

		// create a dictionary
		System.out.println("----------A TreeMap example -------------");

 		SortedMap<String, String>  dict = new TreeMap<>();

		dict.put("hello", "hola");
		dict.put("bye", "adios");
        dict.put("one", "uno");
        dict.put("two", "dos");
        dict.put("three", "tres");
        dict.put("four", "las cuatro");
        dict.put("three", "tres!");
		
		System.out.println(dict);

		SortedMap<String, String> submap = dict.subMap("hello", "three");
		System.out.println("Submap from hello to three, not including three: ");
		System.out.println(submap);
		System.out.println();

	}


	public static void runNestedMapExample() {
		System.out.println("----------A nested HashMap example -------------");

		// Nested map
		Map<String, Map<String, List<Integer>>> invertedIndex = new HashMap<>();
		// For each word, we can store it in the map as the key, and the value is another HashMap,
		// where each key is the name of the file, and the value is the list of positions where a given word
		// occurs in the file 

		Map<String, List<Integer>> map1 = new HashMap<>(); // Value for the key="cat"

		List<Integer> list1 = new ArrayList<>(); // values for the key="file1.txt"
		list1.add(0); // cat occurs in positions 0, 3 and 5 in file1.txt
		list1.add(3);
		list1.add(5);
		map1.put("file1.txt", list1); // positions of the word cat in file1.txt

		List<Integer> list2 = new ArrayList<>(); // value for the key="file2.txt"
		list2.add(4);
		list2.add(6);
		map1.put("file2.txt", list2); // positions of the word cat in file2.txt

		invertedIndex.put("cat", map1); // map1 is the value for key = "cat"

		// Now let's do it for another word - "mouse"
		Map<String, List<Integer>> map2 = new HashMap<>(); // Value for the key="mouse"

		list1 = new ArrayList<>(); // values for the key="file1.txt"
		list1.add(2); // mouse occurs in position 2 in file1.txt
		map2.put("file1.txt", list1); // positions of the word mouse in file1.txt

		list2 = new ArrayList<>(); // value for the key="file2.txt"
		list2.add(1); // mouse occurs in position 1 in file2.txt
		map2.put("file2.txt", list2); // positions of the word mouse in file2.txt

		invertedIndex.put("mouse", map2); // map2 is the value for key = "mouse"
		// ... 
		// We can do the same for other words - except you would not do it manually like in this example!
		// You would write code that reads each file, reads each word from that file, and creates or updates 
		// a nested map as it goes
		System.out.println(invertedIndex);


		// How can you use such data structure?
		// Now, let's say you want to find all occurences of "cat". Here is how you can do it:
		System.out.println("We can use such nested map for efficient search: ");
		Map<String, List<Integer>> resMap = invertedIndex.get("cat");
		for (String fileName: resMap.keySet()) {
			List<Integer> list = resMap.get(fileName);
			System.out.println(list);
		}
	}


	public static void main(String[] args) {

		// comment and uncomment the examples below as needed
		// An ArrayList Example
		runArrayListExample();

		// A HashSet Example
		runHashSetExample();

		// A TreeSet Example
		runTreeSetExample();

		// A HashMap Example
		runHashMapExample();

		// A TreeMap Example
		runTreeMapExample();

		// A nested map example
		runNestedMapExample();
		
	}

}