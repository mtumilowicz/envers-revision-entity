package com.example.envers.audited.revision.listener;

import com.example.envers.audited.revision.domain.CustomRevisionEntity;
import com.example.envers.user.UserInfoMock;
import com.google.common.base.Preconditions;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

/**
 * Created by mtumilowicz on 2018-07-15.
 */
@Component
public class CustomRevisionEntityListener implements RevisionListener {
    
    @Override
    public void newRevision(Object revisionEntity) {
        Preconditions.checkArgument(revisionEntity instanceof CustomRevisionEntity);
        CustomRevisionEntity rev = (CustomRevisionEntity) revisionEntity;
        rev.setLogin(UserInfoMock.login);
    }
}
