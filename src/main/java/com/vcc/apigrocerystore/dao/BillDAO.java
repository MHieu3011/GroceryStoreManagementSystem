package com.vcc.apigrocerystore.dao;

import com.vcc.apigrocerystore.entities.BillEntity;
import com.vcc.apigrocerystore.model.request.BillDetailRegistrationFormRequest;
import com.vcc.apigrocerystore.model.response.InfoBillResponse;
import com.vcc.apigrocerystore.model.response.InfoTotalRevenueByBrand;

import java.util.List;

public interface BillDAO {

    void createByParam(BillEntity entity) throws Exception;

    InfoBillResponse create(long idCustomer, long idUser, long date, List<BillDetailRegistrationFormRequest> billDetails) throws Exception;

    InfoTotalRevenueByBrand getTotalRevenueByBrand(long fromDate, long toDate, String brand) throws Exception;
}
