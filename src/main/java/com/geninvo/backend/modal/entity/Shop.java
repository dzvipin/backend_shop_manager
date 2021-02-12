package com.geninvo.backend.modal.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @Enumerated(STRING)
    private Category category;

    @NotBlank
    @NotNull
    private String ownerName;

    @NotNull
    @ManyToOne(optional = false, fetch = EAGER)
    @JoinColumn(name = "address", referencedColumnName = "id", nullable = false)
    private Address address;

    @NotNull
    private LocalDateTime dateCreated = LocalDateTime.now();

    @NotNull
    private LocalDateTime dateUpdated = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(final LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public enum Category {
        GENERALSTORE,
        MALL,
        MEDICALSTORE,
        SUPERMARKET
    }
}
