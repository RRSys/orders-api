package com.rrsys.ordersapi.clients;

import com.rrsys.ordersapi.clients.response.product.ProductResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(url = "http://localhost:8081/products", name = "product-client")
public interface ProductsClients {

    @GetMapping("/{id}")
    ResponseEntity<ProductResponseClient> findById(@PathVariable UUID id);
}
