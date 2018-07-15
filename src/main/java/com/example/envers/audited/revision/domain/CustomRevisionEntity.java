package com.example.envers.audited.revision.domain;

import com.example.envers.audited.revision.listener.CustomRevisionEntityListener;
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
@RevisionEntity(CustomRevisionEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomRevisionEntity extends DefaultRevisionEntity {
    private String login;
}
