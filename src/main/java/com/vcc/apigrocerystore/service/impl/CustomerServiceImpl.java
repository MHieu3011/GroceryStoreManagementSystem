package com.vcc.apigrocerystore.service.impl;

import com.vcc.apigrocerystore.builder.Response;
import com.vcc.apigrocerystore.dao.CustomerDAO;
import com.vcc.apigrocerystore.entities.CustomerEntity;
import com.vcc.apigrocerystore.exception.CommonException;
import com.vcc.apigrocerystore.global.ErrorCode;
import com.vcc.apigrocerystore.model.request.CustomerFormRequest;
import com.vcc.apigrocerystore.model.response.InfoCustomerResponse;
import com.vcc.apigrocerystore.service.CustomerService;
import com.vcc.apigrocerystore.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends AbstractService implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public Response create(CustomerFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        String fullName = form.getFullName();
        int sex = form.getSex();
        String phoneNumber = form.getPhoneNumber();
        if (CommonUtils.checkEmpty(fullName)) {
            throw new CommonException(ErrorCode.FULL_NAME_MUST_NOT_EMPTY, "full name must not empty");
        }
        if (CommonUtils.checkEmpty(phoneNumber)) {
            throw new CommonException(ErrorCode.PHONE_NUMBER_MUST_NOT_EMPTY, "phone number must not empty");
        }
        if (phoneNumber.matches("[0-9]")) {
            throw new CommonException(ErrorCode.PHONE_NUMBER_MUST_NUMBER, "phone number must number");
        }
        if (sex != 0 && sex != 1) {
            throw new CommonException(ErrorCode.SEX_INVALID, "sex invalid");
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setFullName(fullName);
        entity.setSex(sex);
        entity.setPhoneNumber(phoneNumber);
        InfoCustomerResponse result = customerDAO.create(entity);

        if (result.getFullName() != null) {
            return new Response.Builder(1, HttpStatus.OK.value())
                    .buildData(result)
                    .buildMessage("Create customer successfully")
                    .build();
        } else {
            return new Response.Builder(0, HttpStatus.OK.value())
                    .buildMessage("Create customer error")
                    .build();
        }
    }
}
