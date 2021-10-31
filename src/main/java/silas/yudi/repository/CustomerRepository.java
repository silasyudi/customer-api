package silas.yudi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import silas.yudi.entity.model.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, QueryByExampleExecutor<Customer> {

}
