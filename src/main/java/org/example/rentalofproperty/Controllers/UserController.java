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
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    private UserModel user;

    @GetMapping("/user")
    public String admin(@RequestParam(required = false) Long id, Model model){
        if(user==null)
            user=userRepository.findById(id).get();
        model.addAttribute("user",user);
        return "userPage";
    }

    @GetMapping("user/about")
    public String about(Model model){
        return "userAboutPage";
    }

    @GetMapping("user/contacts")
    public String contacts(Model model){
        return "userContactsPage";
    }

    @GetMapping("user/offers")
    public String offer(Model model){
        return "userOffersPage";
    }

    @GetMapping("user/personalPage")
    public String personalInfo(Model model){
        model.addAttribute("user",user);
        return "userPersonalPage";
    }

    @GetMapping("user/addOffer")
    public String addOffer(Model model){
        model.addAttribute("user",user);
        return "userNewOfferPage";
    }



}