package com.ra.ss9.service;

import com.ra.ss9.model.dto.request.AccountRequestDTO;
import com.ra.ss9.model.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts();
    Account getAccount(Integer accountId);
    Account insertAccount(AccountRequestDTO accountRequestDTO);
    Account updateAccount(Account account, Integer accountId);
    void deleteAccount(Integer accountId);
}
