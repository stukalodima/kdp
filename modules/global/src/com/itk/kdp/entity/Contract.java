package com.itk.kdp.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "KDP_CONTRACT")
@Entity(name = "kdp_Contract")
public class Contract extends StandardEntity {
    private static final long serialVersionUID = -5218496934155734949L;

    @NotNull
    @Column(name = "NUMBER")
    private String number;

    @NotNull
    @Column(name = "STATE", nullable = false)
    private String state;

    @NotNull
    @Column(name = "DATE", nullable = false)
    private Date date;

    public Contract(String number, String state, Date date) {
        this.number = number;
        this.state = state;
        this.date = date;
    }

    public Contract() {
        this.number = "";
        this.state = "new";
        this.date = new Date();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}