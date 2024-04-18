package org.example.rentalofproperty.Controllers;

import org.example.rentalofproperty.Models.City;
import org.example.rentalofproperty.Models.Country;
import org.example.rentalofproperty.Models.UserModel;
import org.example.rentalofproperty.Repo.ICityRepository;
import org.example.rentalofproperty.Repo.ICountryRepository;
import org.example.rentalofproperty.Repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
public class MainController {
 @Autowired
 private IUserRepository userRepository;
 @Autowired
private ICountryRepository countryRepository;
 @Autowired
private ICityRepository cityRepository;
 @GetMapping("/")
  public String home(Model model){
//   Iterable<Country> countries = countryRepository.findAll();
//   Iterable<City> cities= cityRepository.findAll();
  ArrayList<String> countries= new ArrayList<>();
//  HashMap<String, ArrayList<String> cities= new HashMap<>();
//  Collections.addAll(countries,"Польща","Чехія","Італія","Україна");
//  cities.put("Україна","Львів","Київ"]);
//  cities.put("Україна","Київ");
//  cities.put("Польща","Варшава");
//
  for (String countryName : countries){
    Country oldCountry= countryRepository.findByName(countryName);
    if(oldCountry==null) {
     countryRepository.save(new Country(countryName));
    }
  }



  return "home";
 }

 @PostMapping("/")
 public String registration(@RequestParam(required = false) String name, @RequestParam String mail, @RequestParam String password,
                            @RequestParam (required = false) String password2,@RequestParam(required = false) String registration,
                            @RequestParam(required = false) String authorization, @RequestParam(required = false) LocalDate dateOfBirth,Model model) {

  if (registration != null) {

   if (!password.equals(password2)) {
    model.addAttribute("message", "Ви не правильно ввели пароль, повторіть введення знову");
    return "home";
   }

   if (userRepository.findByMail(mail) != null) {
     model.addAttribute("message", "Користувач з вказаною електронною адресою вже зареэстрований");
    return "home";
   }
   // 3. реєструємо користувача та відпраляємо його на сторінку для орендодавця або на сторінку орендаря
   UserModel user = new UserModel(name, mail, password, 1, 2, dateOfBirth);
   userRepository.save(user);
   model.addAttribute("message", "Ви зареэструвались");
   return "home";
  }


  // логіка для авторизації нового користувача

  else if (authorization != null) {
   UserModel user = userRepository.findByMailAndPassword(mail, password);
   if (user != null) {
    //model.addAttribute("message", "Ви авторизувались");
    return "newPage";
   } else {
    model.addAttribute("message", "Користувача з введеним логіном і паролем не існує");
    return "home";
   }

  }

  return "home";
 }


 @GetMapping("/about")
 public String about(Model model){
  return "about";
 }
 @GetMapping("/contacts")
 public String contact(Model model){
  return "contacts";
 }


@GetMapping("/offers")
public String offer(Model model){
 return "offer";
}

}


