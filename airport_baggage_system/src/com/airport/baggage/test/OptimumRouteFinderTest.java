package com.airport.baggage.test;

/**
 * 
 * @author Santosh Kumar Sahoo
 *
 */
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.airport.baggage.OptimumRouteFinder;

public class OptimumRouteFinderTest {

	public static final String DEPARTURES = "# Section: Departures";
	public static final String BAGS = "# Section: Bags";
	public static final String LINE_SPLITOR = " ";
	public static final String LINE_STARTER = "#";

	public static final int FIRST_POSITION = 1;
	public static final int SECOND_POSITION = 2;
	public static final int ZERO_POSITION = 0;

	public static HashMap<String, String> flightMap = new HashMap<String, String>();
	public static HashMap<String, String[]> bagMap = new LinkedHashMap<String, String[]>();

	@Before
	public void setup() {

		Scanner scanner = new Scanner(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream("input.txt"));

		String line = "";
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			if (DEPARTURES.equals(line)) {
				line = scanner.nextLine();
				while (!line.startsWith(LINE_STARTER)) {
					String[] line_array = line.split(LINE_SPLITOR);
					flightMap.put(line_array[ZERO_POSITION],
							line_array[FIRST_POSITION]);
					if (scanner.hasNext()) {
						line = scanner.nextLine();
					} else {
						break;
					}
				}

			}
			if (BAGS.equals(line)) {
				line = scanner.nextLine();
				while (!line.startsWith(LINE_STARTER)) {
					String[] line_array = line.split(LINE_SPLITOR);
					if (line_array[SECOND_POSITION].equals("ARRIVAL"))
						bagMap.put(line_array[ZERO_POSITION], new String[] {
								line_array[FIRST_POSITION], "BaggageClaim" });
					else
						bagMap.put(
								line_array[ZERO_POSITION],
								new String[] {
										line_array[FIRST_POSITION],
										flightMap
												.get(line_array[SECOND_POSITION]) });
					if (scanner.hasNext())
						line = scanner.nextLine();
					else
						break;
				}

			}

		}
		scanner.close();
	}

	@Test
	public void getOptimumuRoutesForAllInputsTest() {
		for (String bagId : bagMap.keySet()) {
			OptimumRouteFinder optRoute = new OptimumRouteFinder();
			try {
				String shortestPath = optRoute.getRoute(bagId,
						bagMap.get(bagId)[0], bagMap.get(bagId)[1]);
				System.out.println(shortestPath);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	public void getOptimumuRouteFromA2ToA1Test() {
		OptimumRouteFinder optRoute = new OptimumRouteFinder();
		try {
			String shortestPath = optRoute.getRoute("0003", "A2", "A1");

			assertEquals("Shortest path from A2 to A1", "1",
					shortestPath.split(":")[FIRST_POSITION].trim());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getOptimumuRouteFromA8ToA5Test() {
		OptimumRouteFinder optRoute = new OptimumRouteFinder();
		try {
			String shortestPath = optRoute.getRoute("0004", "A8", "A5");

			assertEquals("Shortest path from A8 to A5", "6",
					shortestPath.split(":")[FIRST_POSITION].trim());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getOptimumuRouteFromA7ToBaggageClaimTest() {
		OptimumRouteFinder optRoute = new OptimumRouteFinder();
		try {
			String shortestPath = optRoute.getRoute("0005", "A7",
					"BaggageClaim");

			assertEquals("Shortest path from A7 to BaggageClaim", "12",
					shortestPath.split(":")[FIRST_POSITION].trim());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getOptimumuRouteFromA5ToA4Test() {
		OptimumRouteFinder optRoute = new OptimumRouteFinder();
		try {
			String shortestPath = optRoute.getRoute("0002", "A5", "A4");

			assertEquals("Shortest path from A5 to A4", "9",
					shortestPath.split(":")[FIRST_POSITION].trim());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
