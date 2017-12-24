
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import org.junit.*;
import hotelapp.*;

/**
 * Author: okarpenko.
 *
 */
public class Lab4Test {

	public static final String INPUT_DIR = "input";
	public static final String OUTPUT_DIR = "test";
	public static final int TIMEOUT = 60000;

	/**
	 * A helper method that reads the student output file and checks if it
	 * contains expected attractions
	 */
	public void testIfContainsExpectedAttractions(String testName, String filename,
			Map<String, String[]> allExpectedAttractions) {

		Charset charset = java.nio.charset.StandardCharsets.UTF_8;

		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename), charset)) {
			String line = null;
			line = br.readLine();
			if (line == null) { // nothing in the file
				System.out.println("Output file is empty");
				Assert.fail();
			}
			while (line != null) {
				// line: the hotel name + id
				String[] parts = line.split(", ");
				if (parts.length != 2) {
					System.out.println("line = " + line);
					System.out.println("Wrong format of the output file in test " + testName);
					Assert.fail();
				}
				String hotelId = parts[1];
				System.out.println("hotelId = " + hotelId);
				String[] expectedAttractionsForHotelId = allExpectedAttractions.get(hotelId);
				if (expectedAttractionsForHotelId == null) {
					System.out.println("No attractions for the hotel id = " + hotelId);
					Assert.fail();
				}
				StringBuffer sb = new StringBuffer();
				boolean doneWithThisHotel = false;
				while (!doneWithThisHotel && ((line = br.readLine()) != null)) {
					if (line.equals("++++++++++++++++++++"))
						doneWithThisHotel = true;
					else
						sb.append(line + System.lineSeparator());

				}
				String result = sb.toString();
				for (String attractionInfo : expectedAttractionsForHotelId) {
					// System.out.println("Testing attraction " +
					// attractionInfo);
					Assert.assertEquals(
							String.format("%n" + "Test Case: %s%n + Attraction not found: %s%n HotelId = %s%n",
									testName, attractionInfo, hotelId),
							result.contains(attractionInfo), true);
				}
				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println(
					"IOException in the test can not read from the file  " + filename + System.lineSeparator() + e);
			Assert.fail();
		}
	}

	@Test(timeout = TIMEOUT)
	public void testFetchAttractionsHotels1Radius2() {
		// fetch attractions in the radius of 2 miles from the hotel in
		// hotels1.json
		String testName = "testFetchAttractionsHotels1Radius2";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		HotelDataBuilder builder = new HotelDataBuilder(hdata);
		String inputHotelFile = INPUT_DIR + File.separator + "hotels1.json";
		builder.loadHotelInfo(inputHotelFile);
		TouristAttractionFinder finder = new TouristAttractionFinder(hdata);

		finder.fetchAttractions(2);

		// your output
		Path actual = Paths.get(OUTPUT_DIR + File.separator + "studentOutput1Rad2");
		// delete previous output file if it exists
		try {
			Files.deleteIfExists(actual);
		} catch (IOException e) {
			System.out.println("IOException when trying to delete the previous output file: " + e);
		}
		finder.printAttractions(actual);

		String[] expectedAttractions = { 
				"Emeryville Marina & Park; 3310 Powell St, Emeryville, CA 94608, United States",
				"Pottery & Beyond; 4055 Hubbard St, Emeryville, CA 94608, United States",
				"Shorebird Park Emeryville; W Frontage Rd & Access Rd, Emeryville, CA 94608, United States",
				"Emeryville Recreation Department; 4300 San Pablo Ave, Emeryville, CA 94608, United States" };
		Map<String, String[]> allAttractions = new TreeMap<>();
		allAttractions.put("10323", expectedAttractions);
		testIfContainsExpectedAttractions(testName, actual.toString(), allAttractions);
	}

	@Test(timeout = 4 * TIMEOUT)
	public void testFetchAttractionsHotels4Radius2() {
		// fetch attractions close to hotels in hotels4.json
		String testName = "testFetchAttractionsHotels4Radius2";
		ThreadSafeHotelData hdata = new ThreadSafeHotelData();
		HotelDataBuilder builder = new HotelDataBuilder(hdata);
		String inputHotelFile = INPUT_DIR + File.separator + "hotels4.json";
		builder.loadHotelInfo(inputHotelFile);
		TouristAttractionFinder finder = new TouristAttractionFinder(hdata);
		finder.fetchAttractions(2);

		// your output
		Path actual = Paths.get(OUTPUT_DIR + File.separator + "studentOutput4Rad2");
		// delete previous output file if it exists
		try {
			Files.deleteIfExists(actual);
		} catch (IOException e) {
			System.out.println("IOException when trying to delete the previous output file: " + e);
		} 
	
		finder.printAttractions(actual);
		Map<String, String[]> allAttractions = new TreeMap<>();
		String[] expectedAttractions10323 = { 
				"Emeryville Marina & Park; 3310 Powell St, Emeryville, CA 94608, United States",
				"Pottery & Beyond; 4055 Hubbard St, Emeryville, CA 94608, United States",
				"Shorebird Park Emeryville; W Frontage Rd & Access Rd, Emeryville, CA 94608, United States",
				"Emeryville Recreation Department; 4300 San Pablo Ave, Emeryville, CA 94608, United States" };
		allAttractions.put("10323", expectedAttractions10323);

		String[] expectedAttractions12539 = {
				"San Francisco CityPASS; 900 Market St, San Francisco, CA 94102, United States",
				"San Francisco Museum of Modern Art; 151 3rd St, San Francisco, CA 94103, United States",
				"PIER 39; Beach St & The Embarcadero, San Francisco, CA 94133, United States",
				"Coit Tower; 1 Telegraph Hill Blvd, San Francisco, CA 94133, United States",
				"California Academy of Sciences; 55 Music Concourse Dr, San Francisco, CA 94118, United States",
				"Golden Gate Park; San Francisco, CA, United States",
				"USS Pampanito; Fisherman's Wharf, San Francisco, CA 94133, United States",
				"Legion of Honor; 100 34th Ave, San Francisco, CA 94121, United States",
				"Japanese Tea Garden; 75 Hagiwara Tea Garden Dr, San Francisco, CA 94102, United States",
				//"Musée Mécanique; Pier 45, A, San Francisco, CA 94133, United States",
				"Chinatown San Francisco; Stockton St Tunnel, San Francisco, CA 94108, United States",
				};

		allAttractions.put("12539", expectedAttractions12539);

		String[] expectedAttractions16955 = {
				"Eaglerider San Francisco BMW - Ducati - Honda Motorcycle Rental; 136 South Linden Avenue H, South San Francisco, CA 94080, United States",
				"Treasure Island RV Park; 1700 El Camino Real, South San Francisco, CA 94080, United States",
				"South San Francisco Parks and Recreation Department; 33 Arroyo Dr, South San Francisco, CA 94080, United States",
				"California Academy of Sciences; 55 Music Concourse Dr, San Francisco, CA 94118, United States",
				"Buri Buri Park; South San Francisco, CA 94080, United States",
				"Brentwood Park; Rosewood & Briarwood, South San Francisco, CA 94080, United States",
				};
		allAttractions.put("16955", expectedAttractions16955);

		String[] expectedAttractions1047 = {
				"San Francisco CityPASS; 900 Market St, San Francisco, CA 94102, United States",

				"San Francisco Museum of Modern Art; 151 3rd St, San Francisco, CA 94103, United States",

				"PIER 39; Beach St & The Embarcadero, San Francisco, CA 94133, United States",

				"Coit Tower; 1 Telegraph Hill Blvd, San Francisco, CA 94133, United States",

				"California Academy of Sciences; 55 Music Concourse Dr, San Francisco, CA 94118, United States",

				"Golden Gate Park; San Francisco, CA, United States",

				"San Francisco Deluxe Sightseeing Tours; 2737 Taylor St, San Francisco, CA 94133, United States",

				"USS Pampanito; Fisherman's Wharf, San Francisco, CA 94133, United States",

				"Legion of Honor; 100 34th Ave, San Francisco, CA 94121, United States",

				"Japanese Tea Garden; 75 Hagiwara Tea Garden Dr, San Francisco, CA 94102, United States",
				
				"Chinatown San Francisco; Stockton St Tunnel, San Francisco, CA 94108, United States",

		};

		allAttractions.put("1047", expectedAttractions1047);
		testIfContainsExpectedAttractions(testName, actual.toString(), allAttractions);

	}

}