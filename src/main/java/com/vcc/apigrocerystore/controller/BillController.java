package com.vcc.apigrocerystore.controller;

import com.ecyrd.speed4j.StopWatch;
import com.vcc.apigrocerystore.builder.Response;
import com.vcc.apigrocerystore.model.request.BillFormRequest;
import com.vcc.apigrocerystore.model.request.BillRegistrationFormRequest;
import com.vcc.apigrocerystore.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/bill")
public class BillController extends BaseController {

    @Autowired
    private BillService billService;

    //    Thêm mới hóa đơn sử dụng param ( cách đơn sơ lúc đầu)
//    -> không hợp lý lắm do trường total_money đang gắn cứng
    @PostMapping(value = "/param", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createByParam(
            @RequestParam("id_customer") long idCustomer,
            @RequestParam("id_user") long idUser,
            @RequestParam("date") String date,
            @RequestParam("total_money") long totalMoney,
            HttpServletRequest request
    ) {
        StopWatch stopWatch = new StopWatch();
        String requestUri = request.getRequestURI() + "?" + getRequestParams(request);
        String strResponse;
        Response serverResponse;
        try {
            BillFormRequest form = new BillFormRequest();
            form.setRequestUri(requestUri);
            form.setIdCustomer(idCustomer);
            form.setIdUser(idUser);
            form.setDate(date);
            form.setTotalMoney(totalMoney);
            serverResponse = billService.createByParam(form);

            strResponse = gson.toJson(serverResponse, Response.class);
            requestLogger.info("Finish BillController.create: {} in {}", requestUri, stopWatch.stop());
        } catch (Exception e) {
            eLogger.error("BillController.createByParam error: {}", e.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_OCCURRED);
        }
        return new ResponseEntity<>(strResponse, HttpStatus.OK);
    }

    //    Thêm mới hóa đơn sử dụng body
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> create(
            @RequestBody @Valid BillRegistrationFormRequest billRegistrationFormRequest,
            HttpServletRequest request
    ) {
        StopWatch stopWatch = new StopWatch();
        String requestUri = request.getRequestURI() + "?" + getRequestParams(request);
        String strResponse;
        Response serverResponse;
        try {

            serverResponse = billService.create(billRegistrationFormRequest);
            strResponse = gson.toJson(serverResponse, Response.class);
            requestLogger.info("Finish BillController.create: {} in {}", requestUri, stopWatch.stop());
        } catch (Exception e) {
            eLogger.error("BillController.create error: {}", e.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_OCCURRED);
        }
        return new ResponseEntity<>(strResponse, HttpStatus.OK);
    }
}
