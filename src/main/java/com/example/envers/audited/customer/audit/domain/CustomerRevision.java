package com.example.envers.audited.customer.audit.domain;

import com.example.envers.audited.customer.audit.listener.CustomerRevisionListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

/**
 * Created by mtumilowicz on 2018-07-15.
 */
@Entity
@RevisionEntity(CustomerRevisionListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerRevision extends DefaultRevisionEntity {
    private String userName;
}
