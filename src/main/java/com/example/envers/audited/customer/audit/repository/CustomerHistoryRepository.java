package com.example.envers.audited.customer.audit.repository;

import com.example.envers.audited.customer.audit.CustomerAuditReader;
import com.example.envers.audited.customer.domain.Customer;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mtumilowicz on 2018-07-14.
 */
@Repository
@Transactional(readOnly = true)
@AllArgsConstructor
public class CustomerHistoryRepository {
    
    private final CustomerAuditReader customerAuditReader;

    public List<Customer> getHistory(@NotNull Long id) {
        AuditReader auditReader = customerAuditReader.get();
        List<Number> revisions = ListUtils.emptyIfNull(auditReader.getRevisions(Customer.class, id));

        return revisions.stream()
                .map(rev -> auditReader.find(Customer.class, id, rev))
                .collect(Collectors.toList());
    }
    
    public boolean wasEntityDeletedBy(@NotNull Long id, @NotNull String login) {
        return CollectionUtils.isNotEmpty(customerAuditReader.get()
                .createQuery()
                .forRevisionsOfEntity(Customer.class, true)
                .add(AuditEntity.id().eq(id))
                .add(AuditEntity.revisionType().eq(RevisionType.DEL))
                .add(AuditEntity.revisionProperty("login").eq(login))
                .getResultList());


    }

    @SuppressWarnings("unchecked")
    public List<Number> allEntitiesCreatedBy(@NotNull String login) {
        AuditReader auditReader = customerAuditReader.get();

        return ListUtils.emptyIfNull(auditReader
                .createQuery()
                .forRevisionsOfEntity(Customer.class, false)
                .addProjection(AuditEntity.id())
                .add(AuditEntity.revisionType().eq(RevisionType.ADD))
                .add(AuditEntity.revisionProperty("login").eq(login))
                .getResultList());


    }
}
