package com.company.customerinfo.repository.integration.test;

import com.company.customerinfo.model.Customer;
import com.company.customerinfo.model.ShippingAddress;
import com.company.customerinfo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void shouldSaveUser() {
        Customer customer = new Customer();
        customer.setName("name1");
        customer.setAge(1);

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setCountry("TR");
        shippingAddress.setCity("Ankara");
        shippingAddress.setStreetName("KaleSokak");
        customer.setShippingAddress(shippingAddress);
        Customer savedCustomer = customerRepository.save(customer);
        assertThat(savedCustomer).isEqualTo(customer);
    }

}