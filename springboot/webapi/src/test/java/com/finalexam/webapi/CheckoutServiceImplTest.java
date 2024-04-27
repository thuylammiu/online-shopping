package com.finalexam.webapi;
import com.finalexam.webapi.dao.CustomerRepository;
//import com.finalexam.webapi.dto.BillingAddress;
import com.finalexam.webapi.dto.Purchase;
import com.finalexam.webapi.dto.PurchaseResponse;
import com.finalexam.webapi.entity.Customer;
import com.finalexam.webapi.entity.Order;
import com.finalexam.webapi.entity.OrderItem;
import com.finalexam.webapi.service.CheckoutServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class CheckoutServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPlaceOrder() {
        // Prepare test data
        Purchase purchase = new Purchase();
        Order order = new Order();
        order.setOrderTrackingNumber("34343434");
        order.setId(1L);
        Set<OrderItem> orderItems = new HashSet<>();
        var orderItem = new OrderItem();
       // orderItem.setId(1L);
        orderItem.setQuantity(2);
        orderItems.add(orderItem);
       // orderItems.add(new OrderItem( ));
        order.setOrderItems(orderItems);
        //BillingAddress billingAddress = new BillingAddress();
       // billingAddress.setCity("TestCity");
       // order.setBillingAddress(billingAddress);
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        purchase.setCustomer(customer);
        purchase.setOrder(order);

        // Mock behavior
        when(customerRepository.findByEmail("test@example.com")).thenReturn(null);

        // Call the method
        PurchaseResponse response = checkoutService.placeOrder(purchase);

        // Verify
        assertEquals(36, response.getOrderTrackingNumber().length()); // Assuming UUID format

        // Verify interactions
        verify(customerRepository, times(1)).findByEmail("test@example.com");
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
