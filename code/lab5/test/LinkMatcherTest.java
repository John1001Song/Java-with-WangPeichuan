import links.LinkMatcher;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** Test file for LinkMatcher */
public class LinkMatcherTest {

	@Test
	public void testFindLinks() {
		String testName = "testFindLinks";
		List<String> links = LinkMatcher.findLinks("TestHTML.html");
		if (links == null || links.size() == 0)
			fail("Test failed. Your code did not find any links in TestHTML.html");

		System.out.println("Your code returned the following links for TestHTML.html:" + System.lineSeparator());
		for (String l : links)
			System.out.println(l);

		String[] expectedLinks = { "http://www.cs.usfca.edu",
				"http://docs.python.org/library/string.html?highlight=string", "http://www.cnn.com/index.html",
				"https://www.github.com/index.html", "HTTP://WWW.USFCA.EDU?term=Fall&major=CS", "https://www.usfca.edu",
				"https://www.ucsf.edu" };
		if (links.size() != expectedLinks.length)
			fail("Test failed. You returned " + links.size() + " links. While the expected number of links is "
					+ expectedLinks.length);

		for (String link : expectedLinks) {
			assertEquals(String.format("%n" + "Test Case: %s%n + Does not contain the following link: %s%n ", testName,
					link), links.contains(link), true);
		}
		assertEquals(
				String.format("%n" + "Test Case: %s%n + Your result contains the link that should not returned: %s%n ",
						testName, "#news"),
				links.contains("#news"), false);
		assertEquals(String.format(
				"%n" + "Test Case: %s%n + Your result contains the link that should not returned. It's not in the a tag:  %s%n ",
				testName, "http://www.google.com"), links.contains("http://www.google.com"), false);
		assertEquals(String.format(
				"%n" + "Test Case: %s%n + Your result contains the link that should not returned:   %s%n ", testName,
				"http://www.weather.com"), links.contains("http://www.weather.com"), false);

	}

	@Test
	public void testFetchAndFindLinks() {
		String testName = "testFetchAndFindLinks";
		List<String> links = LinkMatcher.fetchAndFindLinks("http://tutoringcenter.cs.usfca.edu/resources/");
		System.out.println("Your code returned the following links for http://tutoringcenter.cs.usfca.edu/resources/:"
				+ System.lineSeparator());
		for (String l : links)
			System.out.println(l);
		if (links == null || links.size() == 0)
			fail("Test failed. Your code did not find any links in TestHTML.html");

		String[] expectedLinks = { "http://tutoringcenter.cs.usfca.edu", "http://tutoringcenter.cs.usfca.edu/tutors", "http://tutoringcenter.cs.usfca.edu/calendar", "http://tutoringcenter.cs.usfca.edu/resources", "http://tutoringcenter.cs.usfca.edu/about", "http://tutoringcenter.cs.usfca.edu/contact",
				"https://docs.google.com/presentation/d/1cEanHv4klTTpqGZeDMzBa3vTpNE7PCHlBZrvVd2w5So/edit?hl=en&pli=1",
				"/resources/advising-faq.html", "/resources/graduate-advising-faq.html",
				"/resources/about-cs-accounts.html", "/resources/email-forwarding.html", "/resources/getting-help.html",
				"http://www.cs.usfca.edu/basics.html", "http://cs.usfca.edu/support.html",
				"/resources/logging-in-remotely.html", "/resources/logging-in-without-a-password.html",
				"/resources/transferring-files.html", "/resources/using-linux-via-command-line.html", "/resources/windows_via_commandline.html",
				"/resources/using-java-via-command-line.html", "https://www.virtualbox.org",
				"/resources/why-use-revision-control.html", "/resources/subversion-svn-basics.html",
				"/resources/using-svn-via-command-line.html", "/resources/using-svn-via-eclipse-and-subclipse.html",
				"/resources/git-debugging.html", "https://github.com", "/resources/setting-up-java-and-eclipse.html",
				"/resources/configuring-eclipse.html", "/resources/enabling-assertions-in-eclipse.html",
				"/resources/adding-user-libraries-in-eclipse.html", "/resources/creating-a-cs-website.html",
				"/resources/creating-a-google-site.html", "http://www.linkedin.com", "http://www.usfca.edu/casa",
				"http://www.usfca.edu/lwc", "http://www.usfca.edu/sds", "http://usfca.edu/its/help/students",
				"http://stackoverflow.com/questions", "http://www.usfca.edu/catalog/policies/honor",
				"http://usfca.edu", "http://www.cs.usfca.edu", "http://twitter.github.io/bootstrap",
				"http://jekyllrb.com", "http://pages.github.com", "/calendar" };
		if (links.size() != expectedLinks.length)
			fail("Test failed. You returned " + links.size() + " links. While the expected number of links is "
				+ expectedLinks.length);

		for (String link : expectedLinks) {
			assertEquals(String.format("%n" + "Test Case: %s%n + Does not contain the following link: %s%n ", testName,
					link), links.contains(link), true);
		}

	}

}
