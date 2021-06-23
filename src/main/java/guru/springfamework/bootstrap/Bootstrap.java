package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepo;
    private final CustomerRepository customerRepo;

    public Bootstrap(CategoryRepository categoryRepo, CustomerRepository customerRepo) {
        this.categoryRepo = categoryRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();

    }

    private void loadCustomers() {
        // ----------- load Customers -----------
        Customer pierrot = new Customer();
        pierrot.setFirstName("Pierrot");
        pierrot.setLastName("Mongonnam");

        Customer craig = new Customer();
        craig.setFirstName("Craig");
        craig.setLastName("Walls");

        Customer kenkousen = new Customer();
        kenkousen.setFirstName("Ken");
        kenkousen.setLastName("Kousen");

        final List<Customer> savedCustomers = customerRepo.saveAll(Arrays.asList(pierrot, craig, kenkousen));
        log.info("Customers saved on Bootstrap: {}", savedCustomers);

    }

    private void loadCategories() {
        // ----------- load categories -----------
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        List<Category> categories = Arrays.asList(fruits,dried,fresh,exotic,nuts);

        categoryRepo.saveAll(categories);
        log.info("Product Categories saved on Bootstrap: {}",categories);

    }

}
