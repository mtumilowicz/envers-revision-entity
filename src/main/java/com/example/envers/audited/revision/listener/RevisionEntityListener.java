package com.example.envers.audited.revision.listener;

import com.example.envers.audited.revision.domain.RevisionEntity;
import com.example.envers.user.UserInfoMock;
import com.google.common.base.Preconditions;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

/**
 * Created by mtumilowicz on 2018-07-15.
 */
@Component
public class RevisionEntityListener implements RevisionListener {
    
    @Override
    public void newRevision(Object revisionEntity) {
        Preconditions.checkArgument(revisionEntity instanceof RevisionEntity);
        RevisionEntity rev = (RevisionEntity) revisionEntity;
        rev.setUserName(UserInfoMock.login);
    }
}
