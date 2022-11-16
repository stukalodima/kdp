package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "KDP_IDEA")
@Entity(name = "kdp_Idea")
@NamePattern("%s|name")
public class Idea extends StandardEntity {
    private static final long serialVersionUID = 4063973761325125870L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID")
    @OnDeleteInverse(DeletePolicy.DENY)
    @NotNull
    private Employees author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    @OnDeleteInverse(DeletePolicy.DENY)
    @NotNull
    private Employees manager;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    @NotNull
    private String description;

    @Column(name = "PROBLEM_SOLVE")
    @Lob
    @NotNull
    private String problemSolve;

    @Column(name = "BENEFITS")
    @Lob
    @NotNull
    private String benefits;

    @Column(name = "AUTOMATION")
    private Boolean automation;

    @Column(name = "RESOURCES")
    @Lob
    @NotNull
    private String resources;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXECUTOR_ID")
    @OnDeleteInverse(DeletePolicy.DENY)
    private Employees executor;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idea")
    private List<IdeaAttachments> attachments;

    public List<IdeaAttachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<IdeaAttachments> attachments) {
        this.attachments = attachments;
    }

    public Employees getAuthor() {
        return author;
    }

    public void setAuthor(Employees author) {
        this.author = author;
    }

    public Employees getManager() {
        return manager;
    }

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProblemSolve() {
        return problemSolve;
    }

    public void setProblemSolve(String problemSolve) {
        this.problemSolve = problemSolve;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Boolean getAutomation() {
        return automation;
    }

    public void setAutomation(Boolean automation) {
        this.automation = automation;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public Employees getExecutor() {
        return executor;
    }

    public void setExecutor(Employees executor) {
        this.executor = executor;
    }
}