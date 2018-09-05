[![Build Status](https://travis-ci.com/mtumilowicz/envers-revision-entity.svg?branch=master)](https://travis-ci.com/mtumilowicz/envers-revision-entity)

# envers-revision-entity
_Reference_: [Revision log jboss doc](https://docs.jboss.org/hibernate/envers/3.6/reference/en-US/html/revisionlog.html)  
_Reference_: [RevisionEntity tutorial](https://www.thoughts-on-java.org/hibernate-envers-extend-standard-revision/)  
_Reference_: [Advanced querying envers](https://www.thoughts-on-java.org/hibernate-envers-query-data-audit-log/)  
_Reference_: [AuditQueryCreator doc](http://docs.jboss.org/hibernate/orm/5.3/javadocs/org/hibernate/envers/query/AuditQueryCreator.html)  
_Reference_: [AuditQuery doc](http://docs.jboss.org/hibernate/orm/5.3/javadocs/org/hibernate/envers/query/AuditQuery.html)

# preface
[Take a look at pt.1](https://github.com/mtumilowicz/envers-audited)

Envers provides an easy way to log additional data for each revision. You simply need to annotate one entity with 
`@RevisionEntity`, and a new instance of this entity will be persisted when a new revision is created (that is, whenever 
an audited entity is modified). **As revisions are global, you can have at most one revisions entity**.

_Remark_: The revision entity must be a mapped Hibernate entity.  
_Remark_: In case there is no entity annotated with `@RevisionEntity`, 
a default table will be generated, with the name `REVINFO`.

# details
This entity must have at least two properties:
* an `integer`/`long`-valued property, annotated with `@RevisionNumber`.
* `long`/`j.u.Date`- valued property, annotated with `@RevisionTimestamp`. 
Value of this property will be automatically set by Envers.

_Remark_: You can either add these properties to your entity, or extend `org.hibernate.envers.DefaultRevisionEntity`, 
which already has those two properties.

To fill the entity with additional data, you'll need to implement the `org.jboss.envers.RevisionListener` interface. 
Its `newRevision` method will be called when a new revision is created, before persisting the revision entity. 
The implementation should be stateless and thread-safe. The listener then has to be attached to the revisions 
entity by specifying it as a parameter to the `@RevisionEntity` annotation.

* RevisionEntity

    |ID   |TIMESTAMP   |ADDITIONAL FIELD (ex. LOGIN)   |
    |---|---|---|
    |...   |...   |...   |

* Audit table

    |ID   |REV   |REVTYPE   | CUSTOM FIELDS   |
    |---|---|---|---|
    |...   |...   |...   |...   |

# manual
We give two examples of creating queries (`com.example.envers.audited.domain.customer.history.repository.CustomerHistoryRepository`):
* `wasEntityDeletedBy`
    ```
    customerAuditReader.get()
                    .createQuery()
                    .forRevisionsOfEntity(Customer.class, true) // include delete
                    .addProjection(AuditEntity.id()) // maps to ids
                    .add(AuditEntity.id().eq(id)) // take revisions of entity with id = id
                    .add(AuditEntity.revisionType().eq(RevisionType.DEL)) // only DEL
                    .add(AuditEntity.revisionProperty("login").eq(login)) // only by login
                    .getResultList()
    ```
* `allIdsOfCustomersCreatedBy`
    ```
    auditReader
                    .createQuery()
                    .forRevisionsOfEntity(Customer.class, true, false) // full entities, not include deleted
                    .add(AuditEntity.revisionType().eq(RevisionType.ADD)) // only ADD
                    .add(AuditEntity.revisionProperty("login").eq(login)) // only by login
                    .getResultList()
    ```

# project details
* `RevisionEntity`: `com.example.envers.audited.domain.customer.model.CustomRevisionEntity`
* `RevisionListener`: `com.example.envers.audited.infrastructure.revision.listener.CustomRevisionEntityListener`