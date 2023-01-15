package com.example.mabaya.api;

import com.example.mabaya.api.request.CreateCampaignRequest;
import com.example.mabaya.domain.Campaign;
import com.example.mabaya.domain.Product;
import com.example.mabaya.service.CampaignService;
import com.example.mabaya.service.ProductService;
import com.google.gson.Gson;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
public class CampaignApi {

    @Autowired
    private CampaignService mCampaignService;

    @Autowired
    private ProductService mProductService;

    Gson gson = new Gson();

    @PostMapping(value = "/createCampaign")
    public ResponseEntity<String> createCampaign(@RequestBody CreateCampaignRequest createCampaignRequest) {
        String name = createCampaignRequest.getName();
        Date startDate = Date.from(Instant.ofEpochMilli(createCampaignRequest.getStartDate()));
        List<Integer> productsToPromote = createCampaignRequest.getProductsToPromote();
        float bid = createCampaignRequest.getBid();
        if(!validateCampaign(name, startDate, productsToPromote, bid)) {
            return new ResponseEntity<>("bad args", HttpStatus.BAD_REQUEST);
        }

        Campaign campaign = mCampaignService.createCampaign(name, startDate, productsToPromote, bid);
        if(campaign == null) {
            return new ResponseEntity<>("wrong product id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gson.toJson(campaign), HttpStatus.OK);
    }

    private boolean validateCampaign(String name, Date startDate, List<Integer> productsToPromote, float bid){
        if(Strings.isEmpty(name) || startDate == null || productsToPromote == null || productsToPromote.isEmpty() || bid <= 0) {
            return false;
        }
        return true;
    }

    @GetMapping(value = "/serveAd")
    public ResponseEntity<String> serveAd(@RequestParam String category) {
        Product product = mProductService.serveAd(category);
        if(product == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(product), HttpStatus.OK);
    }
}
