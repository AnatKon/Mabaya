package com.example.mabaya.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateCampaignRequest {

    private String name;
    private long startDate;
    private List<Integer> productsToPromote;
    private float bid;
}
