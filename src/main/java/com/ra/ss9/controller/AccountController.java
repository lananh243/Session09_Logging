package com.ra.ss9.controller;

import com.ra.ss9.model.dto.request.AccountRequestDTO;
import com.ra.ss9.model.dto.response.DataResponse;
import com.ra.ss9.model.entity.Account;
import com.ra.ss9.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<DataResponse<List<Account>>> getAllAccounts(){
        return new ResponseEntity<>(new DataResponse<>(accountService.getAccounts(), HttpStatus.OK),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<DataResponse<Account>> postAccount(@Valid @ModelAttribute AccountRequestDTO accountRequestDTO){
        return new ResponseEntity<>(new DataResponse<Account>(accountService.insertAccount(accountRequestDTO), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<DataResponse<Account>> getAccount(@PathVariable Integer accountId){
        return new ResponseEntity<>(new DataResponse<>(accountService.getAccount(accountId), HttpStatus.OK), HttpStatus.OK);
    }
}
