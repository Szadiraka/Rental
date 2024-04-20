package org.example.rentalofproperty.Controllers;
import org.example.rentalofproperty.Models.*;
import org.example.rentalofproperty.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;


@Controller
public class AdminController {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IHousingRepository housingRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IStatusRepository statusRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private ICityRepository cityRepository;
    @Autowired
    private IAdvertisementRepository advertisementRepository;
    @Autowired
    ICountryRepository countryRepository;
    private Iterable<Role> roles;
    private Iterable<City> cities;
    private Iterable<Country> countries;
    private Iterable<Advertisement> advertisements;
    private Iterable<Order> orders;
    private Iterable<UserModel> users;
    private UserModel user;

    @GetMapping("/admin")
    public String admin(@RequestParam Long id,Model model){
        roles=roleRepository.findAll();
        cities=cityRepository.findAll();
        countries=countryRepository.findAll();
        user=userRepository.findById(id).get();
        users=userRepository.findAll();
        advertisements=advertisementRepository.findAll();
        model.addAttribute("user",user);
        return "adminPage";
    }

    @GetMapping("/admin/cities")
    public String showCities(Model model){
        model.addAttribute("cities",cities);
        model.addAttribute("countries",countries);
        return "adminCitiesPage";
    }


    @GetMapping("/admin/countries")
    public String showCountries(Model model){
        model.addAttribute("countries",countries);
        return "adminCountriesPage";
    }

    @GetMapping("/admin/users")
    public String showUsers(Model model){
        model.addAttribute("users",users);
        return "adminCountriesPage";
    }

    @GetMapping("/admin/advertisements")
    public String showAdvertisements(Model model){
        model.addAttribute("advertisements",advertisements);
        return "adminAdvertisementsPage";
    }

    @PostMapping("/admin/countries/add")
    public String addCountry(@RequestParam String name, Model model){
         Country country= countryRepository.findByName(name);
         if(country!=null) {
             model.addAttribute("message", "Країна з такою назвою вже існує");
         }
         else{
             Country newCountry= new Country(name);
             countryRepository.save(newCountry);
             countries=countryRepository.findAll();
             model.addAttribute("message","Країна додана в БД");
         }
        model.addAttribute("countries",countries);
        return "adminCountriesPage";
    }
    @PostMapping("/admin/countries/deleteOrEdit")
    public String deleteOrEditCountry(@RequestParam String name, @RequestParam long id, @RequestParam String action, Model model){

         if(action.equals("delete")){
         Country country=countryRepository.findById(id).get();
         if(country!=null){
             Iterable<City> listCities= country.getCities();
             Iterator<City> iterator = listCities.iterator();
             if(!iterator.hasNext()){
                 countryRepository.delete(country);
                 countries=countryRepository.findAll();
                 model.addAttribute("message","Вказане місто  видалено");
             }
             else{
                 model.addAttribute("message","Ви не можете видалити вказану країну");
             }
             model.addAttribute("countries",countries);
             return "adminCountriesPage";
         }
         }else if(action.equals("edit")){
             Country country=countryRepository.findById(id).get();
             if(country!=null) {
                 country.setName(name);
                 countryRepository.save(country);
                 countries=countryRepository.findAll();
                 model.addAttribute("message", "Назву країни відкореговано");
                 model.addAttribute("countries",countries);
                 return "adminCountriesPage";
             }
         }
            model.addAttribute("message", "Вказаної країни не знайдено");
            model.addAttribute("countries",countries);
            return "adminCountriesPage";
    }


    @PostMapping("/admin/cities/add")
    public String addCity(@RequestParam String name,Long countryId, Model model){
        boolean flag=false;
        Iterable<City> listCities= cityRepository.findAllByName(name);
        Iterator<City> iterator=listCities.iterator();
        while(iterator.hasNext()){
            City city=iterator.next();
            if(city.getCountry().getId()==countryId){
                flag=true;
                break;
            }
        }
        if(flag){
            model.addAttribute("message","Місто з такою назвою у вказаній Вами країні вже існує");

        }
        else{
            Country country=countryRepository.findById(countryId).get();
            if(country!=null) {
                City newCity = new City(name,country);
                cityRepository.save(newCity);
                cities = cityRepository.findAll();
                model.addAttribute("message", "Країна додана в БД");
            }
            else{
                model.addAttribute("message", "Країна не  додана в БД тому що не знайдено країни");
            }

        }
        model.addAttribute("cities", cities);
        model.addAttribute("countries",countries);
        return "adminCitiesPage";
    }
    @PostMapping("/admin/cities/deleteOrEdit")
    public String deleteOrEditCity(@RequestParam String name, @RequestParam long id, @RequestParam String action, Model model){

        if(action.equals("delete")){
            City city=cityRepository.findById(id).get();
            if(city!=null){
                Iterable<UserModel> listUsers= city.getUsers();
                Iterable<Advertisement> listAdvertisement = city.getAdvertisements();
                Iterator<UserModel> iteratorUser = listUsers.iterator();
                Iterator<Advertisement> iterator= listAdvertisement.iterator();
                if(!iteratorUser.hasNext() && !iterator.hasNext()){
                    cityRepository.delete(city);
                    cities=cityRepository.findAll();
                    model.addAttribute("message","Вказану  видалено");

                }
                else{
                    model.addAttribute("message","Ви не можете видалити вказане місто");
                }
                model.addAttribute("countries",countries);
                model.addAttribute("cities",cities);
                return "adminCitiesPage";
            }
        }else if(action.equals("edit")){
            City city=cityRepository.findById(id).get();
            if(city!=null) {
                city.setName(name);
                cityRepository.save(city);
                cities=cityRepository.findAll();
                model.addAttribute("message", "Назву міста відкореговано");


            }
        }
        else{
            model.addAttribute("message", "Вказано міста не знайдено");
        }

        model.addAttribute("cities",cities);
        model.addAttribute("countries",countries);
        return "adminCitiesPage";
    }




}
