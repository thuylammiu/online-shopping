package com.finalexam.webapi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalexam.webapi.entity.*;
import com.finalexam.webapi.dto.Purchase;
import com.finalexam.webapi.dto.PurchaseResponse;
import com.finalexam.webapi.entity.Address;
import com.finalexam.webapi.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
public class CheckoutControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CheckoutService checkoutService;

    @Test
    public void testPlaceOrder() throws Exception {
        // Given
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        //product.setPrice(1000L);

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(product.getId());
        orderItem.setQuantity(1);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);

        Address billingAddress = new Address();
        billingAddress.setCity("TestCity");
        billingAddress.setCountry("TestCountry");

        Customer cus = new Customer();
        cus.setId(1L);
        cus.setEmail("lam@gmail.com");
        cus.setFirstName("Lam");
        cus.setLastName("Test");

        Purchase purchase = new Purchase();
        purchase.setOrder(new Order());
        purchase.setOrderItems(orderItems);
        purchase.setBillingAddress(billingAddress);
        purchase.setShippingAddress(billingAddress);
        purchase.setCustomer(cus);

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/checkout/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchase)));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderTrackingNumber").exists());
    }
}
