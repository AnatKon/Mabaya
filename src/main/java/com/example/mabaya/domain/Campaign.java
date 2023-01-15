package com.example.mabaya.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Campaign {

    private int id;
    private List<Product> productList;
    private String name;
    private float bid;
    private Date startDate;

    public Campaign(List<Product> productList, String name, float bid, Date startDate) {
        this.productList = productList;
        this.name = name;
        this.bid = bid;
        this.startDate = startDate;
    }
}
