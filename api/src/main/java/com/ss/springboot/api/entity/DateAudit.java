package com.ss.springboot.api.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"sysCreationDate", "sysUpdateDate"},
        allowGetters = true
)
public abstract class DateAudit implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CreatedDate
    private Instant sysCreationDate;

    @LastModifiedDate
    private Instant sysUpdateDate;

    public Instant getSysCreationDate() {
        return sysCreationDate;
    }

    public void setSysCreationDate(Instant sysCreationDate) {
        this.sysCreationDate = sysCreationDate;
    }

    public Instant getSysUpdateDate() {
        return sysUpdateDate;
    }

    public void setSysUpdateDate(Instant sysUpdateDate) {
        this.sysUpdateDate = sysUpdateDate;
    }

}
