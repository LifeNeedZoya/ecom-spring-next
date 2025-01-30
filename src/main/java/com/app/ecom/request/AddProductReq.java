package com.app.ecom.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductReq {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private int price;

    @NotNull
    private int discountPercent;

    @NotNull
    private int quantity;

    @NotNull
    private List<String> colors;

    @NotNull
    private String image;

    @NotNull
    private Long categoryId;

    @NotNull
    private List<String> sizes;

}
