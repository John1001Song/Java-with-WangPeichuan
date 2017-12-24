import hotelapp.HotelData;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/** The test file for lab 1.
 * The tests were modified by okarpenko from the tests of Prof. Rollins.
 *
 */
public class HotelDataTest {

	/** Add a hotel to HotelData, make sure it was added correctly.
	 * Note: your toString(hotelId) method should return the string with the hotel info
	 * exactly in the same format as specified in the lab description. */
	@Test(timeout = TestUtils.TIMEOUT)
	public void testSimpleAddHotel() {
		String testName = "testSimpleAddHotel";
		HotelData hdata = new HotelData();
		hdata.addHotel("25622", "Hilton San Francisco Union Square", "San Francisco", "CA", "55 Cyril Magnin St", 37.78,
				-122.4);
		//System.out.println(hdata.toString("25622"));
		String expected = "Hilton San Francisco Union Square: 25622" + System.lineSeparator() + "55 Cyril Magnin St\nSan Francisco, CA";
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected,
				hdata.toString("25622").trim());
	}

	/** Add a hotel, then a review for this hotel. Checks that they were added correctly.
	 *  Your toString method in class HotelData should return a string in the exact format
	 *  specified in the lab description.
	 */
	@Test(timeout = TestUtils.TIMEOUT)
	public void testSimpleAddHotelReview() {
		// Adds a hotel and a review, checks that they've been added correctly
		String testName = "testSimpleAddHotelReview";
		HotelData hdata = new HotelData();
		hdata.addHotel("25622", "Hilton San Francisco Union Square", "San Francisco", "CA", "55 Cyril Magnin St", 37.78,
				-122.4);
		hdata.addReview("25622", "57b717a44751ca0b791823b2", 4, "Room too small",
				"Great location, but the room is too small", true, "2016-06-29T17:50:37", "Xiaofeng");
		String expected = "Hilton San Francisco Union Square: 25622" + System.lineSeparator() + "55 Cyril Magnin St" + System.lineSeparator() + "San Francisco, CA" + System.lineSeparator()
				+ "--------------------" + System.lineSeparator();
		expected += "Review by Xiaofeng on Wed Jun 29 17:50:37 PDT 2016" + System.lineSeparator() +
		"Rating: 4" + System.lineSeparator() + "Room too small" + System.lineSeparator() + "Great location, but the room is too small";

		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected,
				hdata.toString("25622").trim());
	}

	/** Tries to add a review for a hotel id that does not exist in HotelData.
	 *  In this case, the review should not be added. */
	@Test
	public void testAddInvalidReview() {
		String testName = "testAddInvalidReview";
		HotelData hdata = new HotelData();
		// adding a review for non-existent hotel id
		hdata.addReview("0007", "x78741", 4, "Bad review", "Hotel was not clean", false, "2016-06-29T17:50:37", "Jason");
		// Making sure hdata.toString("0007") returns an empty string since a hotel with this id was never added to the hotel data
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), "", hdata.toString("0007").trim());
	}


	/** Add three reviews for the same hotel. Check if they are sorted correctly. If not passing this test,
	 * check your compareTo method in class Review, and check toString method to make sure the format is correct. */
	@Test(timeout = TestUtils.TIMEOUT)
	public void testThreeReviewsSameHotel() {
		// Adds one hotel and three reviews for it. Makes sure the reviews are
		// sorted correctly (by date, and if the dates are equal, by username
		String testName = "testThreeReviewsSameHotel";
		HotelData hdata = new HotelData();
		hdata.addHotel("25622", "Hilton San Francisco Union Square", "San Francisco", "CA", "55 Cyril Magnin St", 37.78,
				-122.4);

		hdata.addReview("25622", "23d756a64672vr2gwegyhqw4", 5, "Great deal", "Loved the neighborhood, very lively",
				true, "2014-09-05T05:00:45", "Chris");
		hdata.addReview("25622", "92rlnlvnabuwbf256jsf20fj", 3, "Overpriced", "Good location, but very expensive", true,
				"2014-09-05T05:00:45", "Alicia");
		hdata.addReview("25622", "57b717a44751ca0b791823b2", 4, "Room too small",
				"Great location, but the room is too small", true, "2015-03-04T10:10:16", "Xiaofeng");

		StringBuilder sb = new StringBuilder();
		// most recent review first; if date is the same, should be sorted alphabetically
		sb.append("Hilton San Francisco Union Square: 25622" + System.lineSeparator() + "55 Cyril Magnin St" + System.lineSeparator() + "San Francisco, CA"+ System.lineSeparator());
		sb.append("--------------------" + System.lineSeparator() );

		sb.append("Review by Xiaofeng on Wed Mar 04 10:10:16 PST 2015" + System.lineSeparator() + "Rating: 4" + System.lineSeparator() + "Room too small" + System.lineSeparator() + "Great location, but the room is too small" + System.lineSeparator());
		sb.append("--------------------" + System.lineSeparator() );

		sb.append("Review by Alicia on Fri Sep 05 05:00:45 PDT 2014" + System.lineSeparator() + "Rating: 3" + System.lineSeparator() + "Overpriced" + System.lineSeparator() + "Good location, but very expensive" + System.lineSeparator());
		sb.append("--------------------" + System.lineSeparator());
		sb.append("Review by Chris on Fri Sep 05 05:00:45 PDT 2014" + System.lineSeparator() + "Rating: 5" + System.lineSeparator() + "Great deal"+ System.lineSeparator() + "Loved the neighborhood, very lively" + System.lineSeparator());
		String expected = sb.toString();

		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected.trim(), hdata.toString("25622").trim());
	}

	/** Try adding a review where the rating is not in the right range.
	 * addReview should return false.
	 * The review should not be added to HotelData.
	 */
	@Test(timeout = TestUtils.TIMEOUT)
	public void testInvalidRating() {

		String testName = "testInvalidRating";
		HotelData hdata = new HotelData();
		hdata.addHotel("68432", "Best Hotel", "San Francisco", "CA", "762 Market st", 38.0, -120.0);
		boolean isAdded = hdata.addReview("68432", "57b717a44751ca0b791823b2", 7, "Awesome stay",
				"Central location. Free sodas!", true, "2017-09-01T13:00:00", "Phil");
		String expected = "Best Hotel: 68432" + System.lineSeparator() + "762 Market st" +   System.lineSeparator() + "San Francisco, CA" +  System.lineSeparator() ;
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), false, isAdded);
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected, hdata.toString("68432"));
	}

	/** Tries adding a review where the date is in the incorrect format.
	 *  addReview should return false; the review should not be added.
	 */
	@Test(timeout = TestUtils.TIMEOUT)
	public void testInvalidDate() {
		String testName = "testInvalidDate";
		HotelData hdata = new HotelData();

		boolean isAdded = hdata.addReview("68432", "57b717a44751ca0b791823b2", 5, "Awesome stay",
				"Central location. Free sodas!", true, "2014-24", "Phil"); // the date is in the wrong format
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), false, isAdded);
	}

	/** Load hotels from hotels.json. Call getHotels() to get a list of hotel ids stored in the HotelData,
	 * make sure it is correct. */
	@Test(timeout = TestUtils.TIMEOUT)
	public void testGetHotels() {
		String testName = "testGetHotels";
		HotelData hdata = new HotelData();
		hdata.loadHotelInfo(TestUtils.INPUT_DIR + File.separator + "hotels.json");
		List<String> hList = hdata.getHotels();
		Collections.sort(hList);

		String[] ids = { "1003", "10323", "1047", "1110785", "1139289", "1145267", "1146383",
				"11509", "11523", "11771", "11793", "118232", "1208461", "12239224", "12239317", "12345",
				"12493", "12539", "1321986", "13950", "14451", "14553", "14742075", "14772741", "14781",
				"150946", "15135", "1515923", "15178126", "15254", "15674041", "15769", "1588312", "1606984",
				"16552", "1694", "16955", "17280", "17616", "1766605", "18200", "18204", "18320", "18437", "18962",
				"19035", "19842", "200649", "20191", "20488", "20547", "2063", "20701", "21073", "22148", "22500",
				"22510", "225819", "2336", "23383", "23395", "23406", "23581", "23774", "23838", "2406", "24468",
				"24540", "24620", "24625", "25271", "25622", "25860", "26500", "26760", "26945", "27274", "28200",
				"283107", "28502", "287112", "287665", "296720", "2981", "3308", "3552", "360", "4044", "40443", "40682",
				"41833", "422927", "42336", "4302", "437098", "438727", "444672", "455591", "4705", "476728", "487",
				"491", "5045", "50993", "51116", "519729", "522505", "524164", "5338", "5425", "547988", "564858",
				"57255", "578899", "5830", "5875", "5883", "5901", "5984", "599536", "6271901", "6505", "662368",
				"6666", "693658", "7635", "7655", "7713", "789395", "790530", "790579", "791769", "7942", "808403",
				"828", "855749", "856888", "8638", "8647196", "8737", "876", "876315", "881699", "883306", "890830",
				"891239", "894295", "897225", "897333", "904482", "910225", "912982", "915510", "918527", "919198",
				"9329", "9491356",
		};

		Assert.assertEquals(String.format("%n" + "Test Case: %s%n" + " Incorrect number of hotels %n", testName),
				ids.length, hList.size());

		for (int i = 0; i < ids.length; i++) {
			String hotelId = ids[i];

			if (!hotelId.equals(hList.get(i))) {
				System.out.println("getHotels() did not return the correct list of hotel ids.");
				System.out.println("The " + i + "th element is supposed to be " + ids[i]);
			}
			Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), hotelId, hList.get(i));
		}
	}

	/** The test loads all hotel info from "hotels.json" and all reviews from json files
	 * in the input folder and its subfolders into the HotelData data structures. Then outputs to the file
	 * and compares with expected output. The expected output is in file expectedOutput.
	 * The output of your program is in studentOutput file in the test folder.
	 *
	 */
	@Test(timeout = TestUtils.TIMEOUT)
	public void testPrintToFile() {
		String testName = "testPrintToFile";
		HotelData hdata = new HotelData();
		String inputHotelFile = TestUtils.INPUT_DIR + File.separator + "hotels.json";

		hdata.loadHotelInfo(inputHotelFile);
		hdata.loadReviews(Paths.get(TestUtils.INPUT_DIR + File.separator + "reviews"));
		Path actual = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "studentOutput"); // your
																							// result
																							// is
																							// in
																							// studentOutputFile
		if (Files.exists(actual)) {
			try {
				Files.delete(actual);
			} catch (IOException e) {
				System.out.println("Could not delete an old studentInfo file.");
			}
		}
		hdata.printToFile(actual);
		Path expected = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "expectedOutput"); // instructor's
																								// output
																								// file
		int count = 0;
		try {
			count = TestUtils.checkFiles(expected, actual);
		} catch (IOException e) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " File check failed: %s%n", testName, e.getMessage()));
		}

		if (count <= 0) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " Mismatched Line: %d%n", testName, -count));
		}

	}


}