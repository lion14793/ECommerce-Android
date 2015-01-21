/**
 * 
 */
package com.appdynamics.pmdemoapps.android.ECommerceAndroid.model;

/**
 * @author harsha.hegde
 *
 */
public class Book extends Item {

	public Book(Long id, String title, String desc, String imagePath,Double price) {
		super(id, title, desc, imagePath,price);
	}
	
	public Book() {
		super();
	}

}
