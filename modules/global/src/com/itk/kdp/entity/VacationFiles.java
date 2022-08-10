package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.itk.kdp.base.itk.StandardEntityITK;

import javax.persistence.*;

@Table(name = "KDP_VACATION_FILES")
@Entity(name = "kdp_VacationFiles")
@NamePattern("|vacation, document")
public class VacationFiles extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = 434079015524068039L;

    @Composition
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_ID")
    private FileDescriptor document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VACATION_ID")
    private VacationRequest vacation;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "TASK")
    private String task;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID")
    private Employees author;

    public FileDescriptor getDocument() {
        return document;
    }

    public void setDocument(FileDescriptor document) {
        this.document = document;
    }

    public VacationRequest getVacation() {
        return vacation;
    }

    public void setVacation(VacationRequest vacation) {
        this.vacation = vacation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Employees getAuthor() {
        return author;
    }

    public void setAuthor(Employees author) {
        this.author = author;
    }

    @Override
    public String getCaption() {
        return vacation.getCaption() + "(" +document.getName() + ")";
    }
}