package com.example.restapipostgres.controller;

import com.example.restapipostgres.model.Customer;
import com.example.restapipostgres.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @GetMapping
    public Flux<Customer> getCustomers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Customer> getCustomer(@PathVariable Integer id) {
        return repository.findById(id);
    }

    @PostMapping("/create")
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @PutMapping("/update/{id}")
    public Mono<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Integer id) {
        return repository.findById(id).map((c) -> {
            c.setName(customer.getName());
            return c;
        }).flatMap( c -> repository.save(c));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return repository.deleteById(id);
    }

}
