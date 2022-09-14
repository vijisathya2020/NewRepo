package com.hcl.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.Purchase;
import com.hcl.entity.Address;
import com.hcl.entity.Order;
import com.hcl.entity.OrderItem;
import com.hcl.entity.PaymentInfo;
import com.hcl.entity.Product;
//import com.hcl.entity.User;
import com.hcl.model.cartItem;
import com.hcl.repo.AddressRepository;
import com.hcl.repo.OrderRepository;
import com.hcl.repo.PaymentRepository;
import com.hcl.repo.ProductRepository;
//import com.hcl.repo.UserRepository;
import com.hcl.service.AddressService;
import com.hcl.service.OrderService;
import com.hcl.service.SendEmail;
//import com.hcl.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags= "Order")
public class OrderController {
	@Autowired
	private OrderService orderService;

//	@Autowired
//	UserService userService;

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private AddressService addressService;

//	@Autowired
//	private UserRepository userRepo;

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/checkout/{email}/{name}")
	@PreAuthorize("hasAuthority('Customer') and !hasAuthority('Admin')")
	@ApiOperation(value = "Checkout for Order")
	public Purchase checkout(@RequestBody Purchase p, @PathVariable String email, @PathVariable String name, Principal principal) {
		String oktaId = principal.getName();
		List<cartItem> items = p.getItems();
		if (items == null)
			System.out.println("null");

		// Creates a list of products from the cart items.
		// Principal principal
		List<OrderItem> orderItems = new ArrayList<OrderItem>();

		Order order = new Order();

		double totalPrice = 0.00;
		// Loops through the items list to create orderItem entries from it,
		// decrease product stock, and calculate total price.
		for (int i = 0; i < items.size(); i++) {
			Product product = items.get(i).getProduct();
			int amt = items.get(i).getAmt();
			// Adds an OrderItem entry connected to this order, which holds a product and
			// amount.

			orderItems.add(new OrderItem(order, product, amt));

			totalPrice += product.getProductPrice() * amt;

			// save stock changes after checkout
			product.setProductStock(product.getProductStock() - amt);
			productRepository.save(product);
		}

		//User u = userRepo.findByOktaId(oktaId).get();

		Address s = p.getPayment().getShippingAddressId();
		Address b = p.getPayment().getBillingAddressId();

		addressService.addAddress(oktaId, s);
		addressService.addAddress(oktaId, b);

		order.setOrderStatus("Processing");
		order.setOrderTime(new Timestamp(System.currentTimeMillis()));
		order.setItems(orderItems);
		order.setTotalPrice(totalPrice);
		order.setOktaId(oktaId);
		order.setShippingAddress(p.getPayment().getShippingAddressId());
		String number = generateTrackingNumber();
		order.setTrackingNumber(number);
		orderRepo.save(order);

		PaymentInfo payment = new PaymentInfo();

		payment.setBillingAddressId(p.getPayment().getBillingAddressId());
		payment.setShippingAddressId(p.getPayment().getShippingAddressId());
		payment.setCardHolderFirstName(p.getPayment().getCardHolderFirstName());
		payment.setCardHolderLastName(p.getPayment().getCardHolderLastName());
		payment.setCardNumber(p.getPayment().getCardNumber());
		payment.setCvv(p.getPayment().getCvv());
		payment.setOrder(order);
		paymentRepo.save(payment);

		// Creates an order based on the list of products and amounts.

	    SendEmail.sendOrderConfirmation(email,name,order);

		String message = p.getMessage();
		return new Purchase(payment, items, message);

	}

	public String generateTrackingNumber() {
		return UUID.randomUUID().toString();
	}

	@GetMapping("/order")
	@ApiOperation(value = "Gets All Orders")
	@PreAuthorize("hasAuthority('Customer')")
	public List<Order> getAllOrders() {
		return orderService.findAll();
	}

	@GetMapping("/myOrders")
	@PreAuthorize("hasAuthority('Customer')")
	@ApiOperation(value = "Gets all Orders by Username")
	public List<Order> getMyOrders(Principal principal) {
		return orderService.findByOktaId(principal.getName());
		
	}

	@GetMapping("/orderItems/{trackingNumber}")
	@ApiOperation(value = "Find Order by Tracking Number")
	@PreAuthorize("hasAuthority('Customer')")
	public List<OrderItem> getOrderItemsByTrackingNumber(@PathVariable String trackingNumber) {
		System.out.println(trackingNumber);
		Optional<Order> order = orderService.findByTrackingNumber(trackingNumber);
		if (order.isPresent()) {
			return order.get().getItems();
		}
		return null;
	}
	
	@GetMapping("/order/{trackingNumber}")
	@ApiOperation(value = "Find Order by Tracking Number")
	@PreAuthorize("hasAuthority('Customer') or hasAuthority('Admin')")
	public Order trackOrder(@PathVariable String trackingNumber) {
		System.out.println(trackingNumber);
		Optional<Order> order = orderService.findByTrackingNumber(trackingNumber);
		if (order.isPresent()) {
			return order.get();
		}
		return null;
	}
}
