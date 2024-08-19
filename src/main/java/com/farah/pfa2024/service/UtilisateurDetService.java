package com.farah.pfa2024.service;

import com.farah.pfa2024.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurDetService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            Long id_user = Long.parseLong(id);
            return utilisateurRepository.findById(id_user).orElseThrow(()-> new UsernameNotFoundException("Pas d'utilisateur avec cet identificateur: "+id_user));

        }catch (NumberFormatException e) {
            return utilisateurRepository.findBymail(id).orElseThrow(()-> new UsernameNotFoundException("Pas d'utilisateur avec ce mail: "+id));
        }
        /*.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new UtilisateurDet(utilisateur);*/
    }
}
