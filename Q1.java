//-----------------------------------------------------
// Title: Programming Assignment 4 - Q1.java
// Author: Burak Güçlü
// Description: This class is the main class of our program
// that has three different methods. main is used for calling
// other methods. simulation calculates and finds the total
// average waiting time. PrintOutput prints the results.
//-----------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Q1 {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner input = new Scanner(System.in);
		// We created a Scanner object for taking input filename and expected waiting
		// time.

		System.out.println("Enter input filename:");
		String filename = input.next();
		// Taking the file name as an input from the user.

		System.out.println("Enter the maximum average waiting time:");
		int expected_waiting_time = input.nextInt();
		// Taking the expected maximum average waiting time as an input from the user.

		File f = new File(filename);
		Scanner s = new Scanner(f);
		// In order to reach data in the txt file, we used this objects.

		int number_of_customers = s.nextInt();
		// Taking customer number from txt file's first input.

		if (number_of_customers > 200)
			System.exit(0);
		// We are expected to have at most 200 customers in the data file.

		int count = 0; // This integer will be used to put Customer objects to array.
		Customer[] myArray = new Customer[number_of_customers];
		// Creating a temporary array for storing all customers.

		while (s.hasNextLine()) {
			int id = s.nextInt(); // Taking id from input file
			int year = s.nextInt(); // Taking year from input file
			int order_time = s.nextInt(); // Taking order time from input file
			int deliver_time = s.nextInt(); // Taking deliver time from input file
			Customer customer = new Customer(id, year, order_time, deliver_time);
			// Creating new Customer object with the taken integer values.

			myArray[count] = customer; // Adding each customer to array.
			count++; // Next space for Customer object.
		}

		int num_couriers = 2;
		// We started the simulation with 2 couriers.

		double temp_waiting_time = Integer.MAX_VALUE;
		// Since we are looking for a value that is less than the expected one, we used
		// Integer.MAX_VALUE to use it in the loop.

		String[] messages = new String[number_of_customers];
		// We created a String array for storing messages.
		// It will be used to print the messages in the end.

		double[] results;
		// Since the return value of simulation method is double array,
		// we created this double array.

		results = simulation(temp_waiting_time, expected_waiting_time, number_of_customers, num_couriers, myArray,
				messages);
		// Simulation method is called.
		// We have taken the results of the simulation into the array.

		num_couriers = (int) results[0];
		// The first element of the array is number of couriers, so it is assigned.

		temp_waiting_time = results[1];
		// The second elemtns of the array is total average waiting time, it is
		// assigned.

		System.out.println();

		PrintingOutput(num_couriers, messages, temp_waiting_time);
		// In order to print all necessary messages, PrintOuput method is called.

	}

	public static double[] simulation(double temp_waiting_time, int expected_waiting_time, int number_of_customers,
			int num_couriers, Customer[] myArray, String[] messages) {
		// --------------------------------------------------------
		// Summary: This simulation method is used for calculating the
		// average waiting by changing the number of couriers.
		// Precondition: temp_waiting_time is a double value which is used for
		// checking if the simulation is done by comparing it with expected
		// waiting time. expected_waiting_time is an integer value which is
		// used for comparing it with found waiting time. number_of_customers
		// is an integer value which is used for determining the border of
		// operations. num_couriers is an integer value which is the representation
		// of couriers and used for operations' count. myArray is a Customer
		// array which is used for storing the Customer objects and making some
		// changes on them. messages is a String array which is used for storing
		// output messages in order to print them later.
		// Postcondition: This method returns a double array which contains
		// number of couriers and the average waiting time.
		// --------------------------------------------------------

		while (temp_waiting_time > expected_waiting_time && num_couriers < 10) {
			// This while loop will be executed until the expected result is found
			// and number of couriers is less than 10.

			int message_num = 0; // This integer will be used to put messages to array.
			int[] couriers = new int[num_couriers];
			// Creating new array with the given size. It will assign all element as 0.

			HeapTree tree = new HeapTree(number_of_customers + 1);
			// Creating a heap based tree to store Customer objects.

			for (int realtime = 1; realtime < 500; realtime++) {
				// This for loop is used for having a time constant that represent time value.

				for (int a = 0; a < number_of_customers; a++) {
					// Traverse the customer array.

					if (realtime == myArray[a].order_time) {
						// If the order time is equal to customer's order time, add it into the tree.
						tree.insert(myArray[a]);
					}
				}

				for (int b = 0; b < couriers.length; b++) {
					// Traverse the courier array.

					if (couriers[b] == 0 && tree.size > 0) {
						// If any courier is available, assign a customer to him/her.

						Customer temp = tree.delMax();
						// Take the customer who has the most priority.

						couriers[b] = temp.deliver_time - 1;
						// Make the courier busy until the delivery time is finished.

						String message = "Courier " + b + " takes customer " + temp.id + " at minute " + realtime
								+ " (wait: " + temp.waiting_time + " mins)";
						// Create a message for each customer done.

						messages[message_num] = message;
						// Put the message into the array.

						message_num++;
						// Go to the next place for message.

					} else if (couriers[b] != 0) {
						// If the courier is busy with a order, decrease the busy time by one.

						couriers[b]--;
					}
				}

				for (int x = 1; x < tree.size + 1; x++) {
					// If there is a customer in the tree that is waiting for his/her order to be
					// delivered, increase his/her waiting time by one.

					tree.getElement(x).waiting_time++;
				}
			}

			int total = 0;
			// In order to calculate average waiting time, we create this integer.

			for (int k = 0; k < number_of_customers; k++) {
				// Take every customers waiting time and add it to the total value.

				total += myArray[k].waiting_time;
			}

			temp_waiting_time = (double) total / 12;
			// Calculation of average waiting time.

			num_couriers++;
			// In the next step, try this simulation with more couriers.

			for (int z = 0; z < number_of_customers; z++)
				myArray[z].waiting_time = 0;
			// Changing every customers' waiting time with 0 for the next step of
			// simulation.

		}

		double[] results = new double[2];
		// Storing necessary information to give them to main method.

		results[0] = num_couriers; // First necessary data.
		results[1] = temp_waiting_time; // Second necessary data.

		return results;
		// Returning a double array.
	}

	private static void PrintingOutput(int num_couriers, String[] messages, double temp_waiting_time) {
		// --------------------------------------------------------
		// Summary: This method is used for printing the output as expected
		// in the assignment document.
		// Precondition: num_couriers is an integer value that is used for
		// printing the minimum number of couriers required. messages is
		// String array which is used for printing each message in it.
		// temp_waiting_time is a double value which is used for printing
		// the average waiting time.
		// Postcondition: It prints the results as an expected output.
		// --------------------------------------------------------

		int last_couriers = num_couriers - 1;
		// Since we updated the number of couriers in the last step, we need to
		// decrease it by one.

		System.out.println("Minimum number of couriers required: " + last_couriers);
		// Printing minimum number of couriers required.

		System.out.println();

		System.out.println("Simulation with " + last_couriers + " Couriers:");
		// Printing number of couriers is used to reach the expected average waiting
		// time.

		System.out.println();

		for (int p = 0; p < messages.length; p++)
			System.out.println(messages[p]);
		// Printing the necessary information for each customer.

		System.out.println();

		if (Math.floor(temp_waiting_time) == temp_waiting_time) {
			// In the VPL, it is expected to print the average waiting time without any
			// decimal digits if it is an integer value.

			int lastresult = (int) temp_waiting_time;
			System.out.println("Average waiting time: " + lastresult + " minutes");
		} else {
			// In the VPL, it is expected to print the average waiting time with the decimal
			// digits if it is a double value.

			String abc = String.format("%.5f", temp_waiting_time);
			String average_waiting_time = abc.replace(',', '.');
			System.out.println("Average waiting time: " + average_waiting_time + " minutes");
		}
	}

}