package com.luv2code.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	//autowire the customerService
	@Autowired
	private CustomerService customerService;
	
	//add mapping for GET/customers
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		return customerService.getCustomers();
	}
	
	//add mapping for GET/customer/{customerid}
	@GetMapping("/customer/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		Customer theCustomer=customerService.getCustomer(customerId);
		
		if(theCustomer==null) {
			throw new CustomerNotFoundException("Customer id not found -"+customerId);
		}
		
		return theCustomer;
	}
	
	//add mapping for POST/customers -add new customers
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		//also just in case the pass an id in json...set id to 0
		//this is force a save of new item...instead of update
		theCustomer.setId(0);
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
		
	}
	
	//add mapping for PUT/customers- update exsititng customer
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	
	//add mapping to DELETE/customers ->delete exsisting customer
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		
		//check customer exist or not
		Customer tempCustomer=customerService.getCustomer(customerId);
		
		if(tempCustomer==null) {
			throw new CustomerNotFoundException("Customer id not found -"+customerId);
		}
		
		customerService.deleteCustomer(customerId);
		return "Deleted customer id :"+customerId;
	}
	
}
