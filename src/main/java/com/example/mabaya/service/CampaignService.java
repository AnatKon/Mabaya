package com.example.mabaya.service;

import com.example.mabaya.dao.ifc.CampaignDao;
import com.example.mabaya.domain.Campaign;
import com.example.mabaya.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    @Autowired
    private CampaignDao mCampaignDao;

    @Autowired
    private ProductService mProductService;

    public Campaign createCampaign(String name, Date startDate, List<Integer> productsToPromote, float bid){
        List<Product> productList = mProductService.getProducts(productsToPromote);
        if(productList == null || productList.isEmpty() || productList.size() < productsToPromote.size()) {
            return null;
        }
        Campaign campaign = new Campaign(productList, name, bid, startDate);
        return mCampaignDao.createCampaign(campaign);
    }
}
