//-----------------------------------------------------
// Title: Programming Assignment 4 - HeapTree.java
// Author: Burak Güçlü
// Description: This class is used for representing the
// heap-based priority queue as a tree.
//-----------------------------------------------------

public class HeapTree {

	private Customer[] pq;
	// Storing Customers objects in the array.

	private int N;
	// Length of the array.

	public int size;
	// Size of the tree.

	public HeapTree(int capacity) {
		// --------------------------------------------------------
		// Summary: This method is constructor method which is used
		// for creating a HeapTree object.
		// Precondition: capacity is an integer value that is used
		// for determining the length of the array.
		// Postcondition: It creates a HeapTree object.
		// --------------------------------------------------------

		pq = new Customer[capacity];
		size = 0;
	}

	public Customer getElement(int x) {
		// --------------------------------------------------------
		// Summary: This method is reaching the Customer object.
		// Precondition: x is an integer value that is used for
		// reaching that particular element in the tree.
		// Postcondition: It returns expected Customer object.
		// --------------------------------------------------------

		return pq[x];
	}

	private boolean less(int i, int j) {
		// --------------------------------------------------------
		// Summary: This method is comparing the Customer objects.
		// Precondition: i and j are integer values that are used for
		// comparing the elements.
		// Postcondition: It returns a boolean value respected to
		// comparison of these objects.
		// --------------------------------------------------------

		// Customers' priorities are their years. If they are equal their waiting times
		// are checked.
		if (pq[i].year == pq[j].year)
			return pq[i].waiting_time < pq[j].waiting_time;
		return pq[i].year > pq[j].year;
	}

	private void exch(int i, int j) {
		// --------------------------------------------------------
		// Summary: This method is changing the places of Customer objects.
		// Precondition: i and j are integer values that are used for
		// changing the elements.
		// Postcondition: It changes the places of two Custome objects.
		// --------------------------------------------------------

		Customer t = pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}

	private void swim(int k) {
		// --------------------------------------------------------
		// Summary: This method is taking Customer object to its place
		// by looking its priority. It is used for inserting a Customer object.
		// Precondition: k is an integer value that determining the
		// Customer object which will be moving.
		// Postcondition: It takes the Customer object to the
		// appropriate place.
		// --------------------------------------------------------

		while (k > 1 && less(k / 2, k)) {
			// If the taken Customer object has more priority, take it upper.

			exch(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k) {
		// --------------------------------------------------------
		// Summary: This method is taking Customer object to its place
		// by looking its priority. It is used for deleting the Customer
		// with the most priority.
		// Precondition: k is an integer value that determining the
		// Customer object which will be moving.
		// Postcondition: It takes the Customer object to the
		// appropriate place.
		// --------------------------------------------------------

		while (2 * k <= N) {
			// If taken Customer object has less priority, take it lower.

			int j = 2 * k;
			if (j < N && less(j, j + 1))
				j++;
			if (!less(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}

	public void insert(Customer x) {
		// --------------------------------------------------------
		// Summary: This method is adding a new Customer object to
		// the heap-based priority queue.
		// Precondition: x is a Customer object that will be added
		// to the heap-based priority queue.
		// Postcondition: Customer object will be in the tree now.
		// --------------------------------------------------------

		pq[++N] = x; // Add it into the last place.
		swim(N); // Change its place until it is appropriate for it.
		size++; // Tree size increased.
	}

	public Customer delMax() {
		// --------------------------------------------------------
		// Summary: This method is deleting the Customer object
		// which has the most priority in the tree.
		// Postcondition: Customer object will be deleted from tree.
		// --------------------------------------------------------

		Customer max = pq[1]; // Take the first object.
		exch(1, N--); // Change it with the last item.
		sink(1); // Find a place for the last item.
		pq[N + 1] = null; // Delete the item
		size--; // Tree size decreased.
		return max; // Return the deleted item.
	}
}