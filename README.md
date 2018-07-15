# envers-revision-entity
_Reference_: [Revision log jboss doc](https://docs.jboss.org/hibernate/envers/3.6/reference/en-US/html/revisionlog.html)  
_Reference_: [RevisionEntity tutorial](https://www.thoughts-on-java.org/hibernate-envers-extend-standard-revision/)

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

# project details
* `RevisionEntity`: `com.example.envers.audited.revision.domain.RevisionEntity`
* `RevisionListener`: `com.example.envers.audited.revision.listener.RevisionEntityListener`