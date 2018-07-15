package com.example.envers.audited.revision.domain;

import com.example.envers.audited.revision.listener.RevisionEntityListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.DefaultRevisionEntity;

import javax.persistence.Entity;

/**
 * Created by mtumilowicz on 2018-07-15.
 */
@Entity
@org.hibernate.envers.RevisionEntity(RevisionEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RevisionEntity extends DefaultRevisionEntity {
    private String userName;
}
