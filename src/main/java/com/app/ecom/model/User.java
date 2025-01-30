package com.app.ecom.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.app.ecom.enums.USER_ROLE;
import com.app.ecom.enums.USER_STATUS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String fullName;

    private String mobile;

    @Enumerated(EnumType.STRING)
    private USER_ROLE role;

    @Builder.Default
    @OneToMany
    private Set<Address> addresses = new HashSet<>();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private USER_STATUS status = USER_STATUS.ACTIVE;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = USER_STATUS.ACTIVE;
    }

    @Builder.Default
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "user_used_coupons",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private Set<Coupon> usedCoupons = new HashSet<>();

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getMobile() {
        return this.mobile;
    }


    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public Set<Coupon> getUsedCoupons() {
        return this.usedCoupons;
    }

    public void setId(Long Id) {
        this.id = Id;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @JsonIgnore
    public void setUsedCoupons(Set<Coupon> usedCoupons) {
        this.usedCoupons = usedCoupons;
    }

    public String toString() {
        return "User(Id=" + this.getId() + ", password=" + this.getPassword() + ", email=" + this.getEmail() + ", fullName=" + this.getFullName() + ", mobile=" + this.getMobile() + ", addresses=" + this.getAddresses() + ", usedCoupons=" + this.getUsedCoupons() + ")";
    }
}
