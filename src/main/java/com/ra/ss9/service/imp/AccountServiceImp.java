package com.ra.ss9.service.imp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ra.ss9.model.dto.request.AccountRequestDTO;
import com.ra.ss9.model.entity.Account;
import com.ra.ss9.repository.AccountRepository;
import com.ra.ss9.service.AccountService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    static final Logger log = LoggerFactory.getLogger(AccountServiceImp.class);

    @Override
    public Account getAccount(Integer accountId) {
        log.info("{} - getAccount {}", LocalDateTime.now().toString(),accountId);
        log.warn("Test log warning for this system!");
        return accountRepository.findById(accountId).orElseThrow(()-> new NoSuchElementException("Khong ton tai account co id: "+accountId));
    }

    @Override
    public Account insertAccount(AccountRequestDTO accountRequestDTO) {
        //upload anh len cloudiary
        MultipartFile imageFile = accountRequestDTO.getImageFile();
        String url = "";
        if(imageFile!=null && !imageFile.isEmpty()){
            try {
                Map upload = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
                url = upload.get("secure_url").toString();
                if(url==null || url.isEmpty()){
                    throw new RuntimeException("Url of image file is empty!");
                }
            } catch (IOException e) {
                throw new RuntimeException("Upload file error!");
            }
        }
        Account acc = Account.builder()
                .username(accountRequestDTO.getUsername())
                .password(BCrypt.hashpw(accountRequestDTO.getPassword(), BCrypt.gensalt()))
                .fullName(accountRequestDTO.getFullName())
                .address(accountRequestDTO.getAddress())
                .email(accountRequestDTO.getEmail())
                .phone(accountRequestDTO.getPhone())
                .gender(accountRequestDTO.getGender())
                .imageUrl(url)
                .build();
        return accountRepository.save(acc);
    }

    @Override
    public Account updateAccount(Account account, Integer accountId) {
        accountRepository.findById(accountId).orElseThrow(()-> new NoSuchElementException("Khong ton tai account co id: "+accountId));
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountRepository.findById(accountId).orElseThrow(()-> new NoSuchElementException("Khong ton tai account co id: "+accountId));
        accountRepository.deleteById(accountId);
    }
}