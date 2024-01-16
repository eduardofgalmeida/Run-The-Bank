//package com.backend.core.config;
//
//import org.apache.camel.builder.RouteBuilder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PaymentNotificationRoute extends RouteBuilder {
//
//    @Override
//    public void configure() throws Exception {
//        from("direct:paymentNotification")
//                .setHeader("Content-Type", constant("application/json"))
//                .setBody(constant("{\"paymentStatus\": \"success\"}"))
//                .to("http://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3");
//    }
//}
