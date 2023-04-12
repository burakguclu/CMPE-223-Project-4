//-----------------------------------------------------
// Title: Programming Assignment 4 - Customer.java
// Author: Burak Güçlü
// Description: This class is used for creating Customer
// objects.
//-----------------------------------------------------

public class Customer {

	int id, year, order_time, deliver_time, waiting_time;

	public Customer(int id, int year, int order_time, int deliver_time) {
		// --------------------------------------------------------
		// Summary: This method is constructor method which is used
		// for creating a Customer object.
		// Precondition: id is an integer value that is used for assigning
		// it to the Customer as id. year is an integer value that is used 
		// for assigning it to the Customer as year that he/she has been a
		// member of the company. order_time is an integer value that is 
		// used for assigning it to the Customer as his/her ordering time.
		// deliver_time is an integer value that is used for assigning
		// it to the Customer as his/her order's deliver time.
		// Postcondition: It creates a Customer object with given values.
		// --------------------------------------------------------

		this.id = id;
		this.year = year;
		this.order_time = order_time;
		this.deliver_time = deliver_time;
		this.waiting_time = 0;
	}

}
