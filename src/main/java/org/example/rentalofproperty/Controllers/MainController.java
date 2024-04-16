package org.example.rentalofproperty.Controllers;

import org.example.rentalofproperty.Models.UserModel;
import org.example.rentalofproperty.Repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class MainController {
 @Autowired
 private IUserRepository userRepository;

 @GetMapping("/")
  public String home(Model model){
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



}
