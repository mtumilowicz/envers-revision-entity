package com.example.envers.audited.infrastructure.revision.listener

import com.example.envers.audited.domain.customer.model.CustomRevisionEntity
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-07-30.
 */
class CustomRevisionEntityListenerTest extends Specification {
    def "test newRevision"() {
        given:
        def listener = new CustomRevisionEntityListener()

        and:
        def entity = new CustomRevisionEntity()

        when:
        listener.newRevision(entity)
        
        then:
        entity.login == 'mtumilowicz'
    }
}
