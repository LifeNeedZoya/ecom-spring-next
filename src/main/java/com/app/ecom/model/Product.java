package com.app.ecom.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.app.ecom.enums.PRODUCT_STATUS;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Builder
@EqualsAndHashCode(exclude = {"reviews", "category", "wishlists"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int price;

    @Builder.Default
    private int discountPercent = 0;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private PRODUCT_STATUS status;

    @Builder.Default
    @ElementCollection
    private List<String> colors = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    private List<String> images = new ArrayList<>();

    @Builder.Default
    private int numRatings = 0;

    @ManyToOne
    private Category category;

    private LocalDateTime createdAt;

    @ElementCollection
    private List<String> sizes;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    private Set<WishList> wishlists = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = PRODUCT_STATUS.ACTIVE;
    }
}


