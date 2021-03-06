package com.vcc.apigrocerystore.adapter;

import com.vcc.apigrocerystore.entities.ItemEntity;
import com.vcc.apigrocerystore.model.response.InfoItemResponse;
import com.vcc.apigrocerystore.utils.DateTimeUtils;
import org.springframework.stereotype.Component;

@Component("ItemAdapter")
public class ItemAdapter implements EntityAdapter<ItemEntity, InfoItemResponse> {
    @Override
    public InfoItemResponse transform(ItemEntity entity) {
        InfoItemResponse result = new InfoItemResponse();
        result.setCode(entity.getCode());
        result.setName(entity.getName());
        result.setFromDate(DateTimeUtils.formatTimeInSec(entity.getFromDate(), DateTimeUtils.DEFAULT_DATE_FORMAT));
        result.setToDate(DateTimeUtils.formatTimeInSec(entity.getToDate(), DateTimeUtils.DEFAULT_DATE_FORMAT));
        result.setPrice(entity.getPrice());
        result.setBrand(entity.getBrand());
        return result;
    }
}
