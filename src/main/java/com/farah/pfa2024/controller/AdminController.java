package com.farah.pfa2024.controller;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.model.Admin;
import com.farah.pfa2024.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<ReqResponse> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{id_admin}")
    public ResponseEntity<ReqResponse> getAdminById(@PathVariable Long id_admin) {
        return ResponseEntity.ok(adminService.getAdminById(id_admin));
    }

    @PutMapping("/{id_admin}")
    public ResponseEntity<ReqResponse> updateAdmin(@PathVariable Long id_admin, @RequestBody Admin adminDet) {
        return ResponseEntity.ok(adminService.updateAdmin(id_admin, adminDet));
    }

    @DeleteMapping("/{id_admin}")
    public ResponseEntity<ReqResponse> deleteAdmin(@PathVariable Long id_admin) {
        return ResponseEntity.ok(adminService.deleteAdmin(id_admin));
    }


    /*@GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();


    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    @PutMapping("/{id_admin}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id_admin, @RequestBody Admin adminDet) {
        Optional<Admin> admin = adminService.getAdminById(id_admin);
        if (admin.isPresent()) {
            Admin updateAdmin = admin.get();
            updateAdmin.setNom(adminDet.getNom());
            updateAdmin.setPrenom(adminDet.getPrenom());
            updateAdmin.setMail(adminDet.getMail());
            updateAdmin.setMdp(adminDet.getMdp());
            return ResponseEntity.ok(adminService.addAdmin(updateAdmin));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id_admin}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id_admin) {
        if (adminService.getAdminById(id_admin).isPresent()) {
            adminService.deleteAdmin(id_admin);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
