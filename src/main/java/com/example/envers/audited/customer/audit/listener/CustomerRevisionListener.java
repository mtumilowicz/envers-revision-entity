package com.example.envers.audited.customer.audit.listener;

import com.example.envers.audited.customer.audit.domain.CustomerRevision;
import com.example.envers.user.UserInfoMock;
import com.google.common.base.Preconditions;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

/**
 * Created by mtumilowicz on 2018-07-15.
 */
@Component
public class CustomerRevisionListener implements RevisionListener {
    
    @Override
    public void newRevision(Object revisionEntity) {
        Preconditions.checkArgument(revisionEntity instanceof CustomerRevision);
        CustomerRevision rev = (CustomerRevision) revisionEntity;
        rev.setUserName(UserInfoMock.login);
    }
}
