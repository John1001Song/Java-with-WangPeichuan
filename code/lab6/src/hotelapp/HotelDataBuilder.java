package hotelapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import concurrent.WorkQueue;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/** Class HotelDataBuilder. Loads hotel info from input files to ThreadSafeHotelData (using multithreading). */
public class HotelDataBuilder {
	// logger for debugging
	private final static Logger log = LogManager.getRootLogger();

	// the "global" ThreadSafeHotelData that will contain all hotels and reviews info
	private ThreadSafeHotelData hdata;
	// FILL IN CODE: add other instance variables as needed
	// ExecutorService can't be used in part2 and use the provided WorkQueue
	// ExecutorService executor;
	WorkQueue workQueue;

	// use number of Tasks to count
	int numTasks;

	/** Constructor for class HotelDataBuilder.
	 *  @param data
	 *  		- ThreadSafeHotelData type data contains a initialized hotelData
	 *  */
	public HotelDataBuilder(ThreadSafeHotelData data) {
		this.hdata = data;
		numTasks = 0;
		workQueue = new WorkQueue();
	}

	/** Constructor for class HotelDataBuilder that takes ThreadSafeHotelData and
	 * the number of threads to create as a parameter.
	 * @param data
	 * 			- ThreadSafeHotelData type data contains a initialized hotelData
	 * @param numThreads
	 * 			- number of threads will be used in the builder
	 */
	public HotelDataBuilder(ThreadSafeHotelData data, int numThreads) {
		this.hdata = data;
		numTasks = 0;
		workQueue = new WorkQueue(numThreads);
	}

	/**
	 * synchronized addition
	 * increase the number of tasks
	 * */
	synchronized void addTask(){
		numTasks++;
	}

	/**
	 * synchronized subtraction
	 * decrease the number of tasks
	 * */
	synchronized void subtractTask() {
		numTasks--;
		notifyAll();
	}

	/**
	 * Read the json file with information about the hotels and load it into the
	 * appropriate data structure(s).
	 * @param jsonFilename
	 * 					- json file name with its dir in type String
	 */
	public void loadHotelInfo(String jsonFilename) {
		hdata.loadHotelInfo(jsonFilename);
	}

	/**
	 * This class creates a new Runnable job for each json
	 * review file, that will parse the file, create Reviews and add them to
	 * ThreadSafeHotelData. Hint: you need to create an inner class in HotelDataBuilder
	 * that implements Runnable and that processes a single Json file in the run() method.
	 */
	private class Worker implements Runnable {
		Path currentDir;
		ThreadSafeHotelData localHotelData;
		String localHotelId;

		/**
		 * Worker method defines the job for one thread
		 *
		 * @param dir
		 * 			- the dir for one json file, which is already found in the folder*/
		public Worker(Path dir) {
			log.debug("Current Worker's dir: " + dir);
			this.currentDir = dir;
			addTask();
			localHotelData = new ThreadSafeHotelData();
		}

		/**
		 * run method defines what the detail the Worker has to do*/
		@Override
		public void run() {
			// each thread has its own local hotel data, and the global hotel data will merge locals in mergeHotels
			localHotelData.loadHotelInfo("input/hotels.json");
			localHotelId = localHotelData.handleAReviewFile(currentDir);
			hdata.mergeHotelData(localHotelId, localHotelData);
			// subtraction should be inside the run()
			// otherwise, there is a time gap between workQueue.execute and notifyAll, which is in the subtraction()
			// if it is outside of the run(), it can't concurrent
			subtractTask();
		}
	}

	/** Loads reviews from json files. Recursively processes subfolders.
	 *  Each json file with reviews should be processed concurrently (you need to create a new runnable job for each
	 *  json file that you encounter)
	 *  @param dir
	 *  		- it is a root path for all json files, which will be searched
	 */
	public void loadReviews(Path dir) {
		try {
			DirectoryStream<Path> filesList = Files.newDirectoryStream(dir);
			for (Path file : filesList) {
				if (file.toFile().isDirectory()) {
					loadReviews(file);
				} else {
					workQueue.execute(new Worker(file));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}






	/** Prints all hotel info to the file. Calls hdata's printToFile method.
	 * @param filename
	 * 				- the destination path where the output file will locate
	 * */
	public void printToFile(Path filename) {
		try {
			waitUntilFinished();
			hdata.printToFile(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The method waitUntilFinished() that waits
	 * if the number of tasks is greater than 0 (it means not all the tasks have been completed).
	 * */
	public synchronized void waitUntilFinished() {
		while (numTasks>0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
