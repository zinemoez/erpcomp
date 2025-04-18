package com.erp.erp;


import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.SEQUENCE;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SEQ")
    protected Integer id = 0;
    @Version
    @Column(columnDefinition = "integer DEFAULT 0")
    protected Integer version;
    @Column(name = "CREATED_BY")
    protected String createdBy;
    @Column(name = "MODIFIED_BY")
    protected String modifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE")
    protected Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFICATION_DATE")
    protected Date modificationDate;
    @Column(name = "is_active", columnDefinition = "boolean DEFAULT true")
    protected Boolean active = true;
    public GenericEntity() {
    }

    public GenericEntity(String createdBy, String modifiedBy) {
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    @PrePersist
    public void onCreate() {
        this.creationDate = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version != null ? version : 0;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}
