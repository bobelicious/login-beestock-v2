package br.com.serratec.beestock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.serratec.beestock.model.Profile;
import br.com.serratec.beestock.service.ProfileService;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @PostMapping
    public Profile add (@RequestBody Profile profile){
        return profileService.add(profile);
    }
}
