import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.junit.*;

import hotelapp.*;

/**
 * A Test class for HotelDataBuilder
 * Modified by okarpenko from the code of srollins.
 *
 */
public class HotelDataBuilderTest {

	private static final int RUNS = 10;
	private static final int THREADS = 4;

	@Test(timeout = TestUtils.TIMEOUT)
	public void testSimpleAddHotel() {
		// Add a hotel to the hotel data, check if it was added correctly
		String testName = "testSimpleAddHotel";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		hdata.addHotel("25622", "Hilton San Francisco Union Square", "San Francisco", "CA", "55 Cyril Magnin St", 37.78,
				-122.4);
		String expected = "Hilton San Francisco Union Square: 25622" + System.lineSeparator() + "55 Cyril Magnin St" + System.lineSeparator() + "San Francisco, CA";
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected,
				hdata.toString("25622").trim());
	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testSimpleAddHotelReview() {
		// Adds a hotel and a review, checks that they've been added correctly
		String testName = "testSimpleAddHotelReview";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
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

	@Test
	public void testAddInvalidReview() {
		String testName = "testAddInvalidReview";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		// adding a review for non-existent hotel id
		hdata.addReview("0007", "x78741", 4, "Bad review", "Hotel was not clean", false, "2016-06-29T17:50:37", "Jason");

		// Making sure hdata.toString("0007") returns an empty string since a hotel with this id was never added to the hotel data
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), "", hdata.toString("0007").trim());

	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testThreeReviewsSameHotel() {
		// Adds one hotel and three reviews for it. Makes sure the reviews are
		// sorted correctly
		// (by date, and if the dates are equal, by username
		String testName = "testThreeReviewsSameHotel";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
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

	@Test(timeout = TestUtils.TIMEOUT)
	public void testInvalidRating() {
		// Tries to add a review where the rating is out of range
		// addReview should return false
		// This review should not be added to the map
		String testName = "testInvalidRating";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		hdata.addHotel("68432", "Best Hotel", "San Francisco", "CA", "762 Market st", 38.0, -120.0);
		boolean isAdded = hdata.addReview("68432", "57b717a44751ca0b791823b2", 7, "Awesome stay",
				"Central location. Free sodas!", true, "2017-09-01T13:00:00", "Phil");
		String expected = "Best Hotel: 68432" + System.lineSeparator() + "762 Market st" +   System.lineSeparator() + "San Francisco, CA" +  System.lineSeparator() ;
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), false, isAdded);
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected, hdata.toString("68432"));
	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testInvalidDate() {
		// tries to add a review with invalid date "2014-24"
		// addReview should return false
		String testName = "testInvalidDate";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		boolean isAdded = hdata.addReview("68432", "57b717a44751ca0b791823b2", 5, "Awesome stay",
				"Central location. Free sodas!", true, "2014-24", "Phil"); // the date is in the wrong format
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), false, isAdded);

	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testGetHotels() {
		String testName = "testGetHotels";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		HotelDataBuilder builder = new HotelDataBuilder(hdata);
		builder.loadHotelInfo("input/hotels.json");
		List<String> hList = hdata.getHotels();
		Collections.sort(hList);

		String[] ids = {"1003", "10323", "1047", "1110785", "1139289", "1145267", "1146383",
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

	@Test(timeout = TestUtils.TIMEOUT)
	public void testConcurrentBuildSmallSet() {
		String testName = "testConcurrentBuildSmallSet";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		HotelDataBuilder builder = new HotelDataBuilder(hdata, THREADS);

		String inputHotelFile = TestUtils.INPUT_DIR + File.separator + "hotels.json";
		builder.loadHotelInfo(inputHotelFile);
		builder.loadReviews(Paths.get(TestUtils.INPUT_DIR + File.separator + "reviews"));
		Path actual = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "studentOutput");  // your output
		try {
			Files.deleteIfExists(actual);
		}
		catch (IOException e) {
			System.out.println("Could not delete old output file: " + e);
		}
		builder.printToFile(actual);

		Path expected = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "expectedOutput"); // instructor's
																								// output
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
	
	@Test(timeout = TestUtils.TIMEOUT)
	public void testConcurrentBuildLargerSetSeveralThreads() {
		String testName = "testConcurrentBuildLargerSetSeveralThreads";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		HotelDataBuilder builder = new HotelDataBuilder(hdata, THREADS);

		String inputHotelFile = TestUtils.INPUT_DIR + File.separator + "hotels.json";
		builder.loadHotelInfo(inputHotelFile);
		
		builder.loadReviews(Paths.get(TestUtils.INPUT_DIR + File.separator + "reviewsLargeSet"));
		Path actual = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "studentOutputLargeSet");  // your output
		try {
			Files.deleteIfExists(actual);
		}
		catch (IOException e) {
			System.out.println("Could not delete old output file: " + e);
		}

		builder.printToFile(actual);

		Path expected = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "expectedOutputLargeSet"); // instructor's
																								// output
																							
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
	
	@Test(timeout = TestUtils.TIMEOUT)
	public void testConcurrentBuildLargerSetOneThread() {
		String testName = "testConcurrentBuildLargerSetOneThread";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		HotelDataBuilder builder = new HotelDataBuilder(hdata, 1);

		String inputHotelFile = TestUtils.INPUT_DIR + File.separator + "hotels.json";
		builder.loadHotelInfo(inputHotelFile);
		
		builder.loadReviews(Paths.get(TestUtils.INPUT_DIR + File.separator + "reviewsLargeSet"));
		Path actual = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "studentOutputLargeSet");  // your output
		try {
			Files.deleteIfExists(actual);
		}
		catch (IOException e) {
			System.out.println("Could not delete old output file: " + e);
		}
		builder.printToFile(actual);

		Path expected = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "expectedOutputLargeSet"); // instructor's
																								// output
																							
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
	
	/**
	 * Tests the hotel builder output multiple times, to make sure the
	 * results are always consistent.
	 */
	@Test(timeout = TestUtils.TIMEOUT * RUNS)
	public void testHotelDataConsistency() {
		
		for (int i = 0; i < RUNS; i++) {
			testConcurrentBuildLargerSetSeveralThreads();
		}
	}

	
	
	

}