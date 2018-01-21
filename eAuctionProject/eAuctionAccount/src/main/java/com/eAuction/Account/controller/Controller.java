package com.eAuction.Account.controller;


import java.util.List;


import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eAuction.Account.domain.Address;
import com.eAuction.Account.domain.Payment;
import com.eAuction.Account.domain.Response;
import com.eAuction.Account.domain.ShoppingCart;
import com.eAuction.Account.domain.User;
import com.eAuction.Account.objectInterface.ResponseInterface;
import com.eAuction.Account.objectInterface.UserInterface;
import com.eAuction.util.PasswordEncode;
import com.eAuction.Account.Impl.AddressServiceImpl;
import com.eAuction.Account.Impl.PaymentServiceImpl;
import com.eAuction.Account.Impl.UserServiceImpl;
import com.eAuction.Account.Service.ShoppingCartService;


@RestController
public class Controller {
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	PaymentServiceImpl paymentService;
	
	@Autowired
	AddressServiceImpl addressService;
	
	@Autowired 
	ShoppingCartService shoppingCartService;
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public UserInterface login(@RequestParam("userName") String username, @RequestParam("password") String password,HttpServletResponse response) {
		User user = null;
         user = userService.findByName(username);
		if(user == null) {
			return new Response(false, "UserName or Password is wrong");
		}
		else if(!(PasswordEncode.getMD5(password, username)).equals(user.getPassword())) {
			return new Response(false, "UserName or Password is wrong");
		}
		else {
			return user;
		}
	}	
	
	@RequestMapping(value = "/signup",method=RequestMethod.POST)
	public UserInterface signup(@RequestParam("userName") String userName, @RequestParam("password") String password
			,@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email,
			@RequestParam("role") String role) {
		User user = userService.findByName(userName);
		if(user != null) {
			return new Response(false, "UserName has already existed");
		}
		else {
			String newPassword = PasswordEncode.getMD5(password, userName);
			User newuser = new User(userName, newPassword, firstName, lastName, role, email);
			userService.addUser(newuser);
			return userService.findByName(userName);
		}
	}
	
	@RequestMapping(value="/profile",method=RequestMethod.POST)
	public User profile(@RequestParam("userName") String userName) {
		User user = userService.findByName(userName);
		return user;
	}
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	@Transactional
	public UserInterface deleteUser(@RequestParam("userName") String userName) throws Exception {
		userService.deleteUser(userName);
		addressService.deleteByUserName(userName);
		paymentService.deleteByUserName(userName);
		shoppingCartService.delete(userName);
		return new Response(true, "Delete successfully");
	}
	
	@RequestMapping(value="/blockUser", method=RequestMethod.POST)
	public UserInterface blockUser(@RequestParam("userName") String userName, @RequestParam("role") String role) {
		User user = userService.findByName(userName);
		if(user == null) { 
			return new Response(false, "UserName does not existed");
		}
		else {
			userService.updateUser(userName,role);
			return new Response(true, "Update successfully");
		}
	}
	
	@RequestMapping(value="/addPayment",method=RequestMethod.POST)
	public ResponseInterface addPayment(@RequestParam("holderName") String holderName, @RequestParam("cardNumber") String cardNumber,
			@RequestParam("expiryMonth") String expiryMonth, @RequestParam("expiryYear") String expiryYear,@RequestParam("cvs") String cvs,
			@RequestParam("userName") String userName) {
		Payment payment = new Payment(cardNumber, holderName, cvs, expiryMonth, expiryYear, userName);
		paymentService.insert(payment);
		return new Response(true,"Add successfully");
	}
	
	@RequestMapping(value="/payments",method=RequestMethod.POST)
	public List<Payment> listPayments(@RequestParam("userName") String userName) {
		List<Payment> payments = paymentService.findByUserName(userName);
		return payments;
	}
	
	@RequestMapping(value="/addAddress", method=RequestMethod.POST)
	public Address addAddress(@RequestParam("country") String country, @RequestParam("state") String state,
			@RequestParam("addressStreet1") String addressStreet1, @RequestParam("addressStreet2") String addressStreet2,
			@RequestParam("city") String city, @RequestParam("userName") String userName,@RequestParam("zip") String zip) {
		Address address = new Address(country, state, addressStreet1, addressStreet2, city, Integer.parseInt(zip),userName);
		addressService.insert(address);
		return address;
	}
	
	@RequestMapping(value="/addresses",method=RequestMethod.POST)
	public List<Address> listAddresses(@RequestParam("userName") String userName){
		List<Address> addresses = addressService.findByUserName(userName);
		return addresses;
	}
	
	@RequestMapping(value="/shoppingCarts", method=RequestMethod.POST)
	public List<ShoppingCart> listShoppingCarts(@RequestParam("userName") String userName){
		List<ShoppingCart> shoppingCarts = shoppingCartService.findByUserName(userName);
		return shoppingCarts;
	}
	
	@RequestMapping(value="/addToShoppingCart",method=RequestMethod.POST)
	public ShoppingCart addToShoppingCart(@RequestParam("userName") String userName, 
			@RequestParam("postingId") String postingId, @RequestParam("quantity") String quantity,@RequestParam("price") String price,
			@RequestParam("imageUrl") String imageUrl, @RequestParam("itemName") String itemName,
			@RequestParam("typeId") String typeId) {
		ShoppingCart shoppingCart = new ShoppingCart(userName, Integer.parseInt(postingId), Integer.parseInt(quantity),Double.parseDouble(price),
				imageUrl,itemName,Integer.parseInt(typeId));
		shoppingCartService.addToShoppingCart(shoppingCart);
		return shoppingCart;
	}
	
	@RequestMapping(value="/deleteShoppingCart",method=RequestMethod.POST)
	public Response deleteShoppingCart(@RequestParam("id") String id) {
		shoppingCartService.deleteById(Integer.parseInt(id));
		return new Response(true, "no error");
	}
	
	@RequestMapping(value="/deleteAddress",method=RequestMethod.POST)
	public Response deleteAddress(@RequestParam("id") String id) {
		addressService.delete(Integer.parseInt(id));
		return new Response(true, "");
	}
	
	@RequestMapping(value="/deletePayment",method=RequestMethod.POST)
	public Response deletePayment(@RequestParam("id") String id,@RequestParam("userName") String userName) {
		boolean res = paymentService.delete(Integer.parseInt(id),userName);
		return new Response(res, "");
	}

	
	@RequestMapping(value="/addToShoppingCartById",method=RequestMethod.POST)
	public ShoppingCart addToShoppingCartById(@RequestParam("userId") String userId, 
			@RequestParam("postingId") String postingId, @RequestParam("quantity") String quantity,@RequestParam("price") String price,
			@RequestParam("imageUrl") String imageUrl, @RequestParam("itemName") String itemName,
			@RequestParam("typeId") String typeId) {
		String userName = userService.findById(Integer.parseInt(userId)).getUserName();
		ShoppingCart shoppingCart = new ShoppingCart(userName, Integer.parseInt(postingId), Integer.parseInt(quantity),Double.parseDouble(price),
				imageUrl,itemName,Integer.parseInt(typeId));
		shoppingCartService.addToShoppingCart(shoppingCart);
		return shoppingCart;
	}
	
	@RequestMapping(value="/updateInformation",method=RequestMethod.POST)
	public User updateInformation(@RequestParam("userId") String userId, 
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("email") String email) {
		User user = userService.updateInformation(firstName, lastName, email, Integer.parseInt(userId));
		return user;
	}
	
	
}
