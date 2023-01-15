package com.example.mabaya.service;

import com.example.mabaya.dao.ifc.ProductDao;
import com.example.mabaya.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao mProductDao;

    public List<Product> getProducts(List<Integer> serialNumbers) {
        return mProductDao.getProducts(serialNumbers);
    }

    public Product serveAd(String category) {
        Product product = mProductDao.getHighestBidProductFromCategoryInActiveCampaign(category);
        if(product != null) {
            return product;
        }
        return mProductDao.getHighestBidProductInActiveCampaign();
    }
}
