package com.example.mabaya.dao.impl;

import com.example.mabaya.dao.ifc.ProductDao;
import com.example.mabaya.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BaseProductDao extends MySqlDao implements ProductDao {

    private static final String SELECT_PRODUCT_BY_SERIAL_NUMBER = "SELECT * FROM product WHERE serial_number in ";
    private static final String SELECT_HIGHEST_BID_PRODUCT_FROM_CATEGORY_IN_ACTIVE_CAMPAIGN = "SELECT p.* " +
            "FROM product p " +
            "INNER JOIN product_campaign pc " +
            "ON pc.product_serial_number = p.serial_number " +
            "INNER JOIN campaign c " +
            "ON c.id = pc.campaign_id " +
            "WHERE TIMESTAMPDIFF(DAY, start_time, NOW()) <= 10 " +
            "AND p.category = ? " +
            "ORDER BY c.bid DESC " +
            "LIMIT 1";
    private static final String SELECT_HIGHEST_BID_PRODUCT_IN_ACTIVE_CAMPAIGN = "SELECT p.* " +
            "FROM product p " +
            "INNER JOIN product_campaign pc " +
            "ON pc.product_serial_number = p.serial_number " +
            "INNER JOIN campaign c " +
            "ON c.id = pc.campaign_id " +
            "WHERE TIMESTAMPDIFF(DAY, start_time, NOW()) <= 10 " +
            "ORDER BY c.bid DESC " +
            "LIMIT 1";

    private class ProductRowMapper  implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

            int serialNumber = rs.getInt("serial_number");
            String title = rs.getString("title");
            String category = rs.getString("category");
            float price = rs.getFloat("price");

            Product product = new Product(serialNumber, title, category, price);

            return product;
        }
    }

    @Override
    public List<Product> getProducts(List<Integer> serialNumbers){
        try {
            String temp = serialNumbers.toString();
            temp = temp.substring(1, temp.length() - 1);
            return jdbcTemplate.query(SELECT_PRODUCT_BY_SERIAL_NUMBER + "(" + temp + ")", new ProductRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Product getHighestBidProductFromCategoryInActiveCampaign(String category){
        try {
            return jdbcTemplate.queryForObject(SELECT_HIGHEST_BID_PRODUCT_FROM_CATEGORY_IN_ACTIVE_CAMPAIGN, new ProductRowMapper(), category);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Product getHighestBidProductInActiveCampaign(){
        try {
            return jdbcTemplate.queryForObject(SELECT_HIGHEST_BID_PRODUCT_IN_ACTIVE_CAMPAIGN, new ProductRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
