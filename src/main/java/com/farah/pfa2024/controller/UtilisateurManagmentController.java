package com.farah.pfa2024.controller;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.service.UtilisateurManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//c'est testé
@RestController
@RequestMapping("/auth")
public class UtilisateurManagmentController {

    @Autowired
    private UtilisateurManagmentService utilisateurManagmentService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<ReqResponse> registerAdmin(@RequestBody ReqResponse req) {
        return ResponseEntity.ok(utilisateurManagmentService.registerAdmin(req));
    }


    @PostMapping("/registerClient")
    public ResponseEntity<ReqResponse> registerClient(@RequestBody ReqResponse req) {
        return ResponseEntity.ok(utilisateurManagmentService.registerClient(req));
    }

    @PostMapping("/registerPrestataire")
    public ResponseEntity<ReqResponse> registerPrestataire(@RequestBody ReqResponse req) {
        return ResponseEntity.ok(utilisateurManagmentService.registerPrestataire(req));
    }

    @PostMapping("/login")
    public ResponseEntity<ReqResponse> login(@RequestBody ReqResponse req) {
        return ResponseEntity.ok(utilisateurManagmentService.login(req));
    }

    //refresh token
}
