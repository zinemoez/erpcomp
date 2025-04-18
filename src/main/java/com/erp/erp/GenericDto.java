package com.erp.erp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GenericDto implements Serializable {

    protected Integer id;
    protected Integer version;
    protected String createdBy;
    protected String modifiedBy;
    protected Date creationDate;
    protected Date modificationDate;

}
