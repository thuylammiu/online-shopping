package com.finalexam.webapi.service;

import com.finalexam.webapi.dto.PaymentInfo;
import com.finalexam.webapi.dto.Purchase;
import com.finalexam.webapi.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;

}
