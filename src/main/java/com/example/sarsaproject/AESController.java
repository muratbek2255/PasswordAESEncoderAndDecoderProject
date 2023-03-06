package com.example.sarsaproject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


@RestController
@RequestMapping("/api/aes")
public class AESController {

    private final AESService aesService;

    @Autowired
    public AESController(AESService aesService) {
        this.aesService = aesService;
    }

    @PostMapping("/encrypt-decrypt")
    public ResponseEntity<HashMap<String, HashMap<String, String>>> encryptPassword(@RequestBody AESRequest aesRequest)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.status(201).body(aesService.encryptService(aesRequest));
    }
}
