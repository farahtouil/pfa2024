package com.farah.pfa2024.service;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.model.Admin;
import com.farah.pfa2024.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ReqResponse getAllAdmins(){
        ReqResponse reqResponse = new ReqResponse();

        try {
            List<Admin> result = adminRepository.findAll();
            if (!result.isEmpty()){
                reqResponse.setAdmins(result);
                reqResponse.setMessage("Admin List");
                reqResponse.setStatusCode(200);
            }else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Pas d'admins");
            }
        }catch (Exception e){
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur: "+e.getMessage());
        }
        return reqResponse;
    }

    public ReqResponse getAdminById(Long id_admin){
        ReqResponse reqResponse = new ReqResponse();

        try {
            Optional<Admin> result = adminRepository.findById(id_admin);
            if (result.isPresent()) {
                reqResponse.setAdmin(result.get());
                reqResponse.setMessage("Admin trouvé avec id "+id_admin);
                reqResponse.setStatusCode(200);
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Admin n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur: " + e.getMessage());
        }

        return reqResponse;
    }

    public ReqResponse deleteAdmin(Long id_admin){
        ReqResponse reqResponse = new ReqResponse();
        try {
            Optional<Admin> result = adminRepository.findById(id_admin);
            if (result.isPresent()) {
                adminRepository.deleteById(id_admin);
                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Admin avec l'id"+id_admin+"est supprimé avec succés");
            }else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Admin n'existe pas");
            }
        }catch (Exception e){
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de la suppression de l'admin: "+e.getMessage());
        }
        return reqResponse;
    }

    public ReqResponse updateAdmin(Long id_admin,Admin adminDet) {
        ReqResponse reqResponse = new ReqResponse();
        Optional<Admin> admin = adminRepository.findById(id_admin);
        if (admin.isPresent()) {
            Admin updateAdmin = admin.get();
            updateAdmin.setNom(adminDet.getNom());
            updateAdmin.setPrenom(adminDet.getPrenom());
            updateAdmin.setMail(adminDet.getMail());
            updateAdmin.setMdp(adminDet.getMdp());

            /*// Check if password is present in the request
            if (adminDet.getPassword() != null && !adminDet.getPassword().isEmpty()) {
                // Encode the password and update it
                updateAdmin.setMdp(passwordEncoder.encode(updateAdmin.getMdp()));
            }*/  //peut etre pour changer le mdp

            adminRepository.save(updateAdmin);
            reqResponse.setStatusCode(200);
            reqResponse.setAdmin(updateAdmin);
            reqResponse.setMessage("Admin modifié avec succés");

        } else {
            reqResponse.setStatusCode(404);
            reqResponse.setMessage("Admin n'existe pas");

        }
        return reqResponse;
    }

   /* public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id_admin) {
        return adminRepository.findById(id_admin);
    }

    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void deleteAdmin(Long id_admin) {
        adminRepository.deleteById(id_admin);
    }*/
}
