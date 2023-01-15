package com.example.mabaya.dao.ifc;

import com.example.mabaya.domain.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(List<Integer> serialNumbers);

    Product getHighestBidProductFromCategoryInActiveCampaign(String category);

    Product getHighestBidProductInActiveCampaign();
}
