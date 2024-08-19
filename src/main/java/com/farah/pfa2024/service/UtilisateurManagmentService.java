package com.farah.pfa2024.service;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.model.*;
import com.farah.pfa2024.repository.AdminRepository;
import com.farah.pfa2024.repository.ClientRepository;
import com.farah.pfa2024.repository.PrestataireRepository;
import com.farah.pfa2024.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UtilisateurManagmentService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PrestataireRepository prestataireRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ReqResponse registerAdmin(ReqResponse registerRequest) {
        ReqResponse registerResponse = new ReqResponse();

        try {
            Admin admin = new Admin();
            admin.setNom(registerRequest.getNom());
            admin.setPrenom(registerRequest.getPrenom());
            admin.setMail(registerRequest.getMail());
            admin.setMdp(passwordEncoder.encode(registerRequest.getMdp()));   //hashed password
            admin.setRole(Role.admin);

            Admin adminResult = adminRepository.save(admin);
            if (adminResult.getId_user()>0) {  //Checks if the user was saved successfully.
                registerResponse.setAdmins(List.of(adminResult));
                registerResponse.setMessage("Admin saved Successfully");
                registerResponse.setStatusCode(200);
            }
        }catch (Exception e) {
            registerResponse.setStatusCode(500);
            registerResponse.setError(e.getMessage());
        }
        return registerResponse;
    }

    public ReqResponse registerClient(ReqResponse registerRequest) {
        ReqResponse registerResponse = new ReqResponse();

        try {
            Client client = new Client();
            client.setNom(registerRequest.getNom());
            client.setPrenom(registerRequest.getPrenom());
            client.setMail(registerRequest.getMail());
            client.setMdp(passwordEncoder.encode(registerRequest.getMdp()));   //hashed password
            //client.setMdp(registerRequest.getMdp());
            client.setRole(Role.client);
            client.setNum_tel(registerRequest.getNum_tel());
            client.setGouvernorat(registerRequest.getGouvernorat());

            Client clientResult = clientRepository.save(client);
            if (clientResult.getId_user()>0) {  //Checks if the user was saved successfully.
                registerResponse.setClients(List.of(clientResult));
                registerResponse.setMessage("Client saved Successfully");
                registerResponse.setStatusCode(200);
            }
        }catch (Exception e) {
            registerResponse.setStatusCode(500);
            registerResponse.setError(e.getMessage());
        }
        return registerResponse;
    }

    public ReqResponse registerPrestataire(ReqResponse registerRequest) {
        ReqResponse registerResponse = new ReqResponse();

        try {
            Prestataire prestataire = new Prestataire();
            prestataire.setNom(registerRequest.getNom());
            prestataire.setPrenom(registerRequest.getPrenom());
            prestataire.setMail(registerRequest.getMail());
            prestataire.setMdp(passwordEncoder.encode(registerRequest.getMdp()));   //hashed password
            //prestataire.setMdp(registerRequest.getMdp());
            prestataire.setRole(Role.prestataire);
            prestataire.setNum_tel(registerRequest.getNum_tel());
            prestataire.setGouvernorat(registerRequest.getGouvernorat());

            prestataire.setServicesP(registerRequest.getServicesP());
            for (ServiceP serviceP  : prestataire.getServicesP()) {
                serviceP.setPrestataire(prestataire);
            }

            prestataire.setImage(registerRequest.getImage());
            prestataire.setDate_de_naissance(registerRequest.getDate_de_naissance());

            Prestataire prestataireResult = prestataireRepository.save(prestataire);
            if (prestataireResult.getId_user()>0) {  //Checks if the user was saved successfully.
                registerResponse.setPrestataires(List.of(prestataireResult));
                registerResponse.setMessage("Prestataire saved Successfully");
                registerResponse.setStatusCode(200);
            }
        }catch (Exception e) {
            registerResponse.setStatusCode(500);
            registerResponse.setError(e.getMessage());
        }
        return registerResponse;
    }

    public ReqResponse login(ReqResponse loginRequest) {
        ReqResponse loginResponse = new ReqResponse();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getMdp()));
            var utilisateur1 =  utilisateurRepository.findBymail(loginRequest.getMail()).orElseThrow();
            var jwt = jwtUtils.createToken(utilisateur1,utilisateur1.getId_user());
            //var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), utilisateur1);
            loginResponse.setStatusCode(200);
            loginResponse.setToken(jwt);
            //loginResponse.setRefreshToken(refreshToken);
            loginResponse.setRole(utilisateur1.getRole()); //to be able to know the role in the front end and manipulate routing
            loginRequest.setExp("24Hrs");
            loginResponse.setMessage("User logged in successfully");
        }catch (Exception e) {
            loginResponse.setStatusCode(500);
            loginResponse.setError(e.getMessage());
        }
        return loginResponse;
    }

    public ReqResponse refreshToken(ReqResponse refreshTokenReq){
        ReqResponse refreshTokenResponse = new ReqResponse();
        try {
            String UMail = jwtUtils.extractUsername(refreshTokenReq.getToken());
            Utilisateur utilisateur = utilisateurRepository.findBymail(UMail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReq.getToken(), utilisateur)) {
                var jwt = jwtUtils.createToken(utilisateur,utilisateur.getId_user());
                refreshTokenResponse.setStatusCode(200);
                refreshTokenResponse.setToken(jwt);
                refreshTokenResponse.setMessage("Token refreshed successfully");
                refreshTokenResponse.setExp("24Hrs");
                refreshTokenResponse.setRefreshToken(refreshTokenReq.getToken());
            }
        }catch (Exception e) {
            refreshTokenResponse.setStatusCode(500);
            refreshTokenResponse.setError(e.getMessage());

        }
        return refreshTokenResponse;
    }
}
