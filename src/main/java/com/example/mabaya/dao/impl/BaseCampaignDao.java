package com.example.mabaya.dao.impl;

import com.example.mabaya.dao.ifc.CampaignDao;
import com.example.mabaya.domain.Campaign;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BaseCampaignDao extends MySqlDao implements CampaignDao {

    private static final String INSERT_CAMPAIGN = "INSERT INTO campaign(name, bid, start_time) values(?,?,?)";
    private static final String INSERT_PRODUCT_CAMPAIGN = "INSERT INTO product_campaign(product_serial_number, campaign_id) values(?,?)";

    @Override
    public Campaign createCampaign(Campaign campaign) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_CAMPAIGN, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, campaign.getName());
                ps.setFloat(2, campaign.getBid());
                ps.setTimestamp(3, Timestamp.from(campaign.getStartDate().toInstant()));

                return ps;
            }
        }, holder);

        campaign.setId(holder.getKey().intValue());
        createProductCampaignConnection(campaign);

        return campaign;
    }

    private void createProductCampaignConnection(Campaign campaign) {
        List<Integer> serialNumberList = campaign.getProductList().stream().map(product -> product.getSerialNumber()).collect(Collectors.toList());
        for(int serialNumber : serialNumberList) {
            jdbcTemplate.update(INSERT_PRODUCT_CAMPAIGN, serialNumber, campaign.getId());
        }
    }
}
