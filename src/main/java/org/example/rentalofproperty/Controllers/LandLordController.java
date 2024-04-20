package org.example.rentalofproperty.Controllers;

import org.example.rentalofproperty.Models.City;
import org.example.rentalofproperty.Models.Country;
import org.example.rentalofproperty.Models.Role;
import org.example.rentalofproperty.Models.UserModel;
import org.example.rentalofproperty.Repo.IHousingRepository;
import org.example.rentalofproperty.Repo.IRoleRepository;
import org.example.rentalofproperty.Repo.IStatusRepository;
import org.example.rentalofproperty.Repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LandLordController {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IHousingRepository housingRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IStatusRepository statusRepository;
    private Iterable<Role> roles;
    private Iterable<City> cities2;
    private Iterable<Country> countries2;
    private UserModel user;

    @GetMapping("/landlord")
    public String admin(@RequestParam Long id, Model model){
        roles=roleRepository.findAll();
        user=userRepository.findById(id).get();
        model.addAttribute("user",user);
        return "landlordPage";
    }



}