package com.vcc.apigrocerystore.service.impl;

import com.vcc.apigrocerystore.builder.Response;
import com.vcc.apigrocerystore.dao.StoreHouseDAO;
import com.vcc.apigrocerystore.entities.StoreHouseEntity;
import com.vcc.apigrocerystore.exception.CommonException;
import com.vcc.apigrocerystore.global.ErrorCode;
import com.vcc.apigrocerystore.model.request.StoreHouseFormRequest;
import com.vcc.apigrocerystore.service.StoreHouseService;
import com.vcc.apigrocerystore.utils.CommonUtils;
import com.vcc.apigrocerystore.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StoreHouseServiceImpl extends AbstractService implements StoreHouseService {

    @Autowired
    private StoreHouseDAO storeHouseDAO;

    @Override
    public Response create(StoreHouseFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        long idItem = form.getIdItem();
        String codeItem = form.getCodeItem();
        int number = form.getNumber();
        String strDate = form.getDate();
        if (CommonUtils.checkEmpty(codeItem)) {
            throw new CommonException(ErrorCode.CODE_ITEM_MUST_NOT_EMPTY, "code item must not empty");
        }
        if (CommonUtils.checkEmpty(strDate)) {
            throw new CommonException(ErrorCode.DATE_TIME_MUST_NOT_EMPTY, "date must not empty");
        }
        if (number < 0) {
            throw new CommonException(ErrorCode.NUMBER_MUST_SMALLER_0, "number must smaller 0");
        }

        StoreHouseEntity entity = new StoreHouseEntity();
        long date = DateTimeUtils.getTimeInSecs(strDate);
        entity.setIdItem(idItem);
        entity.setCodeItem(codeItem);
        entity.setNumber(number);
        entity.setDate(date);
        storeHouseDAO.create(entity);

        return new Response.Builder(1, HttpStatus.OK.value())
                .buildMessage("Create store house successfully")
                .build();
    }
}