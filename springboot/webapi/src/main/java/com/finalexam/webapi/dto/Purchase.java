package com.finalexam.webapi.dto;

import com.finalexam.webapi.entity.Address;
import com.finalexam.webapi.entity.Customer;
import com.finalexam.webapi.entity.Order;
import com.finalexam.webapi.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
