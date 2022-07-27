package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.*;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@PublishEntityChangedEvents
@Table(name = "KDP_ORGANIZATIONS")
@Entity(name = "kdp_Organizations")
@NamePattern("%s|shortName")
public class Organizations extends StandardEntity {
    private static final long serialVersionUID = -7946626287646661870L;

    @NotNull
    @Column(name = "CODE", length = 9)
    private String code;

    @NotNull
    @Column(name = "PREFIX", length = 3)
    private String prefix;

    @NotNull
    @Column(name = "SHORT_NAME", nullable = false)
    private String shortName;

    @NotNull
    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "ORGANIZATIONS_EN")
    private String organizationsEn;

    @NotNull
    @Column(name = "ORGANIZATIONS_UA", nullable = false)
    private String organizationsUa;

    @NotNull
    @Column(name = "ORGANIZATIONS_RU", nullable = false)
    private String organizationsRu;

    @NotNull
    @Column(name = "CODE_OCPO", length = 12)
    private String codeRegistration;

    @Column(name = "DATE_REGISTRATION")
    @Temporal(TemporalType.DATE)
    private Date dateRegistration;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.DENY)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_REGISTRATION_ID")
    private Countries countryRegistration;

    @Column(name = "ORGANIZATIONS1C_ID")
    private String organizations1cId;

    @Column(name = "ENTITY")
    private Boolean entity;

    @Column(name = "ACTIVE")
    private Boolean active;

    public Countries getCountryRegistration() {
        return countryRegistration;
    }

    public void setCountryRegistration(Countries countryRegistration) {
        this.countryRegistration = countryRegistration;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getOrganizations1cId() {
        return organizations1cId;
    }

    public void setOrganizations1cId(String organizations1cId) {
        this.organizations1cId = organizations1cId;
    }

    public String getOrganizationsRu() {
        return organizationsRu;
    }

    public void setOrganizationsRu(String organizationsRu) {
        this.organizationsRu = organizationsRu;
    }

    public String getOrganizationsUa() {
        return organizationsUa;
    }

    public void setOrganizationsUa(String organizationsUa) {
        this.organizationsUa = organizationsUa;
    }

    public String getOrganizationsEn() {
        return organizationsEn;
    }

    public void setOrganizationsEn(String organizationsEn) {
        this.organizationsEn = organizationsEn;
    }

    public String getCodeRegistration() {
        return codeRegistration;
    }

    public void setCodeRegistration(String codeRegistration) {
        this.codeRegistration = codeRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public Boolean getEntity() {
        return entity;
    }

    public void setEntity(Boolean entity) {
        this.entity = entity;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @PostConstruct
    private void initVisitDate() {

        if (code == null) {
            setCode(generateNewCode().toString());
        }

        setActive(true);

        DataManager dataManager = AppBeans.get(DataManager.class);

        List<Countries> listCountry = dataManager.load(Countries.class)
                .query("select e from kdp_Countries e where e.code = :codeCountry")
                .parameter("codeCountry",804)
                .view("_minimal")
                .list();

        if (listCountry.size() == 1) {
            setCountryRegistration(listCountry.get(0));
        }

    }

    private Long generateNewCode() {
        UniqueNumbersService numbersService = AppBeans.get(UniqueNumbersService.class);
        return numbersService.getNextNumber("organizationCode");
    }

}