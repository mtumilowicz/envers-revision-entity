package com.example.envers.audited.customer.service;

import com.example.envers.audited.customer.domain.Customer;
import com.example.envers.audited.customer.domain.CustomerAssembler;
import com.example.envers.audited.customer.domain.CustomerDto;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mtumilowicz on 2018-07-13.
 */
@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final EntityManager entityManager;

    public void save(@NotNull CustomerDto dto) {
        repository.save(CustomerAssembler.toEntity(dto));
    }
    
    public Optional<Customer> findById(@NotNull Long i) {
        return repository.findById(i);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }
    
    public void update(@NotNull CustomerDto dto, @NotNull  Long id) {
        Customer one = Objects.requireNonNull(repository.getOne(id));
        CustomerAssembler.merge(one, dto);
        repository.save(one);
    }
    
    public void deleteById(@NotNull Long id) {
        repository.deleteById(id);
    }
    
    public List<Customer> getHistory(@NotNull Long id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<Number> revisions = ListUtils.emptyIfNull(auditReader.getRevisions(Customer.class, id));

        return revisions.stream()
                .map(rev -> auditReader.find(Customer.class, id, rev))
                .collect(Collectors.toList());
    }
}
