package org.nightingaale.paymentservice.client;

import org.nightingaale.paymentservice.config.FeignClientConfig;
import org.nightingaale.paymentservice.event.CreatePaymentTransactionRequest;
import org.nightingaale.paymentservice.event.feign.CreateUserIdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-service", url = "http://user-service:8086", configuration = FeignClientConfig.class)
public interface UserServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/users/send")
    CreateUserIdRequest sendId(@RequestBody CreatePaymentTransactionRequest request);
}
