package org.example.rentalofproperty.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rentalofproperty.Models.*;
import org.example.rentalofproperty.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
 @Autowired
 private IHousingRepository housingRepository;
 @Autowired
 private IRoleRepository roleRepository;
 @Autowired
 private IStatusRepository statusRepository;

 private Iterable<City> cities;
 private Iterable<Country> countries;
 private Iterable<Role> roles;
 @GetMapping("/")
  public String home(Model model){
  //якщо БД не має жодної країни, то додаємо в БД
  if(countryRepository.count()<1)
    addCountriesAndCities();
  //якщо БД не має жодного типа житла, то додаємо в БД
  if(housingRepository.count()<1)
    addHousingTypes();
  //якщо БД не має жодної ролі, то додаємо в БД
  if(roleRepository.count()<1)
    addRoles();
  //якщо БД не має жодного статусу, то додаємо в БД
  if(statusRepository.count()<1)
   addStatuses();
  //якщо БД не має жодного user, то додаємо в БД
  if(userRepository.count()<1)
     addUser();

  // завантаження даних з бд
  cities= cityRepository.findAll();
  countries=countryRepository.findAll();
  roles=roleRepository.findByNameNot("адміністратор");
  model.addAttribute("cities",cities);
  model.addAttribute("countries",countries);
  model.addAttribute("roles",roles);
  return "home";
 }




 @PostMapping("/")
 public String registration(RedirectAttributes attr, @RequestParam(required = false) String name, @RequestParam String mail, @RequestParam String password,
                            @RequestParam (required = false) String password2, @RequestParam(required = false) String registration,
                            @RequestParam(required = false) String authorization, @RequestParam(required = false) LocalDate dateOfBirth,
                            @RequestParam(required = false) Long cityId, @RequestParam(required = false) Long roleId, Model model) {

  model.addAttribute("cities",cities);
  model.addAttribute("countries",countries);
  model.addAttribute("roles",roles);
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
   City city= cityRepository.findById(cityId).get();
   Role role=roleRepository.findById(roleId).get();
   if(city ==null || role==null){
    model.addAttribute("message", "Зареєструватись не вдалось");

    return "home";
   }
   UserModel user = new UserModel(name, mail, password, city, role, dateOfBirth);
   userRepository.save(user);
   model.addAttribute("message", "Ви зареэструвались");
   return "home";
  }


  // логіка для авторизації користувача

  else if (authorization != null) {
   UserModel user = userRepository.findByMailAndPassword(mail, password);
   if (user != null) {
    attr.addAttribute("id",user.getId());
    if(user.getRole().getName().equals("адміністратор")){
     return "redirect:/admin";
    }else if(user.getRole().getName().equals("орендар")){
     return "redirect:/renter";
    }else{
     return "redirect:/landlord";
    }

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

 private void addCountriesAndCities(){

  // створюємо та додаємо до БД країни
  ArrayList<String> countryNames= new ArrayList<>();
  Collections.addAll(countryNames,"Польща","Україна");
  for (String countryName:countryNames){
   countryRepository.save(new Country(countryName));
  }
  // створюємо та додаємо до БД міста
  HashMap<String, ArrayList<String>> cities= new HashMap<>();

// Добавляем первое значение в карту
  String country1 = "Польща";
  ArrayList<String> citiesInCountry1 = new ArrayList<>();
  citiesInCountry1.add("Краків");
  citiesInCountry1.add("Варшава");
  cities.put(country1, citiesInCountry1);

// Добавляем второе значение в карту
  String country2 = "Україна";
  ArrayList<String> citiesInCountry2 = new ArrayList<>();
  citiesInCountry2.add("Львів");
  citiesInCountry2.add("Київ");
  cities.put(country2, citiesInCountry2);

  for (Map.Entry<String,ArrayList<String>> entry:cities.entrySet()){
   String countryName = entry.getKey();
   List<String> cityNames = entry.getValue();
   Country country=countryRepository.findByName(countryName);
   for (String cityName: cityNames){
    City city=new City(cityName,country);
    cityRepository.save(city);
   }
  }
 }

 private void addHousingTypes(){
  ArrayList<String> housingTypes= new ArrayList<>();
  Collections.addAll(housingTypes,"квартира","будинок");
  for (String housingType:housingTypes){
   housingRepository.save(new HousingType(housingType));
  }
 }
 private void addRoles(){
  ArrayList<String> roles= new ArrayList<>();
  Collections.addAll(roles,"адміністратор","орендар","орендодавець");
  for (String roleName:roles){
   roleRepository.save(new Role(roleName));
  }
 }

 private void addStatuses(){
  ArrayList<String> statuses= new ArrayList<>();
  Collections.addAll(statuses,"створено","відхилено","схвалено","виконано");
  for (String statusName:statuses){
   statusRepository.save(new Status(statusName));
  }
 }
 private void addUser(){
  City city= cityRepository.findByName("Киів");
  Role role= roleRepository.findByName("адміністратор");
  if(city !=null && role!=null){
   UserModel user= new UserModel("admin","admin@gmail.com","admin",city,role,LocalDate.now());
   userRepository.save(user);
  }
 }





}


