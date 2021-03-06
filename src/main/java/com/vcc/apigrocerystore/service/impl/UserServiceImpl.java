package com.vcc.apigrocerystore.service.impl;

import com.vcc.apigrocerystore.builder.Response;
import com.vcc.apigrocerystore.cache.local.ResponseLocalCache;
import com.vcc.apigrocerystore.dao.UserDAO;
import com.vcc.apigrocerystore.entities.UserEntity;
import com.vcc.apigrocerystore.exception.CommonException;
import com.vcc.apigrocerystore.global.ErrorCode;
import com.vcc.apigrocerystore.model.request.UserFormRequest;
import com.vcc.apigrocerystore.model.request.UserRegistrationFormRequest;
import com.vcc.apigrocerystore.model.response.InfoUserResponse;
import com.vcc.apigrocerystore.service.UserService;
import com.vcc.apigrocerystore.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    @Qualifier("responseLocalCache")
    private ResponseLocalCache responseLocalCache;

    @Override
    public Response createByRequestBody(UserRegistrationFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        String userName = form.getUserName();
        String fullName = form.getFullName();
        int sex = form.getSex();
        String password = form.getPassword();
        String address = form.getAddress();
        if (CommonUtils.checkEmpty(userName)) {
            throw new CommonException(ErrorCode.USER_NAME_MUST_NOT_EMPTY, "username must not empty");
        }
        if (CommonUtils.checkEmpty(fullName)) {
            throw new CommonException(ErrorCode.FULL_NAME_MUST_NOT_EMPTY, "full name must not empty");
        }
        if (CommonUtils.checkEmpty(password)) {
            throw new CommonException(ErrorCode.PASS_WORD_MUST_NOT_EMPTY, "password must not empty");
        }
        if (sex != 0 && sex != 1) {
            throw new CommonException(ErrorCode.SEX_INVALID, "sex invalid");
        }

        UserEntity entity = new UserEntity();
        entity.setUserName(userName);
        entity.setFullName(fullName);
        entity.setSex(sex);
        entity.setPassword(password);
        entity.setAddress(address);
        entity.setRole(0);
        entity.setStatus(1);
        InfoUserResponse result = userDAO.create(entity);

        if (result.getUsername() != null) {
            return new Response.Builder(1, HttpStatus.OK.value())
                    .buildData(result)
                    .buildMessage("Create User successfully")
                    .build();
        } else {
            return new Response.Builder(0, HttpStatus.OK.value())
                    .buildMessage("Create User error")
                    .build();
        }
    }

    @Override
    public Response create(UserFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        String username = form.getUserName();
        String fullName = form.getFullName();
        int sex = form.getSex();
        String password = form.getPassword();
        if (CommonUtils.checkEmpty(username)) {
            throw new CommonException(ErrorCode.USER_NAME_MUST_NOT_EMPTY, "username must not empty");
        }
        if (CommonUtils.checkEmpty(fullName)) {
            throw new CommonException(ErrorCode.FULL_NAME_MUST_NOT_EMPTY, "full name must not empty");
        }
        if (CommonUtils.checkEmpty(password)) {
            throw new CommonException(ErrorCode.PASS_WORD_MUST_NOT_EMPTY, "password must not empty");
        }
        if (sex != 0 && sex != 1) {
            throw new CommonException(ErrorCode.SEX_INVALID, "sex invalid");
        }

        UserEntity entity = new UserEntity();
        entity.setUserName(username);
        entity.setFullName(fullName);
        entity.setSex(sex);
        entity.setPassword(password);
        entity.setAddress(form.getAddress());
        entity.setRole(0);
        entity.setStatus(1);
        InfoUserResponse result = userDAO.create(entity);

        if (result.getUsername() != null) {
            return new Response.Builder(1, HttpStatus.OK.value())
                    .buildData(result)
                    .buildMessage("Create User successfully")
                    .build();
        } else {
            return new Response.Builder(0, HttpStatus.OK.value())
                    .buildMessage("Create User error")
                    .build();
        }
    }

    @Override
    public Response login(UserFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        String username = form.getUserName();
        String password = form.getPassword();
        if (CommonUtils.checkEmpty(username)) {
            throw new CommonException(ErrorCode.USER_NAME_MUST_NOT_EMPTY, "username must not empty");
        }
        if (CommonUtils.checkEmpty(password)) {
            throw new CommonException(ErrorCode.PASS_WORD_MUST_NOT_EMPTY, "password must not empty");
        }

        //gọi cache, nếu cache có thì gửi dữ liệu về cho client
        String key = form.getRequestUri();
        InfoUserResponse result = (InfoUserResponse) responseLocalCache.get(key);
        if (result == null) {
            //nếu cache không có dữ liệu thì gọi dao vào put cache
            result = userDAO.login(username, password, 1);

            responseLocalCache.put(key, result);
        }
        if (result.getUsername() == null) {
            return new Response.Builder(0, HttpStatus.OK.value())
                    .buildMessage("Login Error")
                    .build();
        } else {
            return new Response.Builder(1, HttpStatus.OK.value())
                    .buildData(result)
                    .buildMessage("Login successfully")
                    .build();
        }
    }

    @Override
    public Response delete(UserFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        String username = form.getUserName();
        if (CommonUtils.checkEmpty(username)) {
            throw new CommonException(ErrorCode.USER_NAME_MUST_NOT_EMPTY, "username must not empty");
        }

        userDAO.delete(username);

        return new Response.Builder(1, HttpStatus.OK.value())
                .buildMessage("Delete user successfully")
                .build();
    }

    @Override
    public Response update(UserFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        String username = form.getUserName();
        String fullName = form.getFullName();
        int sex = form.getSex();
        String password = form.getPassword();
        if (CommonUtils.checkEmpty(username)) {
            throw new CommonException(ErrorCode.USER_NAME_MUST_NOT_EMPTY, "username must not empty");
        }
        if (CommonUtils.checkEmpty(fullName)) {
            throw new CommonException(ErrorCode.FULL_NAME_MUST_NOT_EMPTY, "full name must not empty");
        }
        if (CommonUtils.checkEmpty(password)) {
            throw new CommonException(ErrorCode.PASS_WORD_MUST_NOT_EMPTY, "password must not empty");
        }
        if (sex != 0 && sex != 1) {
            throw new CommonException(ErrorCode.SEX_INVALID, "sex invalid");
        }

        UserEntity entity = new UserEntity();
        entity.setId(form.getId());
        entity.setUserName(username);
        entity.setFullName(fullName);
        entity.setSex(sex);
        entity.setPassword(password);
        entity.setAddress(form.getAddress());
        InfoUserResponse result = userDAO.update(entity);

        return new Response.Builder(1, HttpStatus.OK.value())
                .buildData(result)
                .buildMessage("Update User successfully")
                .build();
    }

    @Override
    public Response findAll(UserFormRequest form) throws Exception {
        List<InfoUserResponse> resultList = userDAO.findAll();
        return new Response.Builder(1, HttpStatus.OK.value())
                .buildData(resultList)
                .buildMessage("findAll User successfully")
                .build();
    }

    @Override
    public Response searchByUserName(UserFormRequest form) throws Exception {
        List<InfoUserResponse> resultList = userDAO.searchByUsername(form.getUserName());
        return new Response.Builder(1, HttpStatus.OK.value())
                .buildData(resultList)
                .buildMessage("searchByUserName successfully")
                .build();
    }
}
