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
import java.time.format.DateTimeFormatter;
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
 @Autowired
 private IMessageTypeRepository messageTypeRepository;
 @Autowired
 private IMessageRepository messageRepository;
 @Autowired
 private IAdvertisementRepository advertisementRepository;
 @Autowired
 private IOrderRepository orderRepository;

 private Iterable<City> cities;
 private Iterable<Country> countries;
 private Iterable<Role> roles;
 private Iterable<HousingType> housingTypes;

 private Iterable<Advertisement> advertisements;
 private ArrayList<Advertisement> filtredAavertisements=new ArrayList<>();
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
  //якщо БД не має жодного messageType, то додаэмо в БД
  if(messageTypeRepository.count()<1)
    addMessageTypes();

  //перевірка ордерів зі статусом "схвалено", обробка їх
  orderProcessing();
  // перевіряємо повідомлення у користувачів, які блоковані
  messageProcessing();


  // завантаження даних з бд
  cities= cityRepository.findAll();
  countries=countryRepository.findAll();
  roles=roleRepository.findByNameNot("адміністратор");
  advertisements=advertisementRepository.findByIsModeratedTrueAndIsDeletedFalse();
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

  model.addAttribute("cities", cities);
  model.addAttribute("countries", countries);
  model.addAttribute("roles", roles);
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
   City city = cityRepository.findById(cityId).get();
   Role role = roleRepository.findById(roleId).get();
   if (city == null || role == null) {
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
    attr.addAttribute("id", user.getId());
    if (user.getRole().getName().equals("адміністратор")) {
     return "redirect:/admin";
    } else {
       // тут будемо перевірять чи не є користуваыч заблокованим
       if(user.isLocked()) {
           model.addAttribute("message", "Ваш аккаунт заблоковано");
           return "home";
       }

     return "redirect:/user";
    }

   } else {
    model.addAttribute("message", "Користувача з введеним логіном і паролем не існує");
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
//  if(advertisements==null)
//    advertisements=advertisementRepository.findByIsModeratedTrueAndIsDeletedFalse();
 if(cities==null)
   cities=cityRepository.findAll();
 if(countries==null)
   countries=countryRepository.findAll();
 if(housingTypes==null)
   housingTypes=housingRepository.findAll();

  model.addAttribute("advertisements",filtredAavertisements);
  model.addAttribute("cities",cities);
  model.addAttribute("countries",countries);
  model.addAttribute("housingTypes",housingTypes);
 return "offers";
}

@PostMapping("offers/filter")
public String filter(@RequestParam String action,@RequestParam Long housingTypeId,@RequestParam Long countryId,@RequestParam Long cityId,
                    @RequestParam Integer rentalDate,@RequestParam Integer rating,@RequestParam Integer price,@RequestParam (required = false) String countryCheckbox,
                    @RequestParam(required = false) String rentalDatesCheckbox,@RequestParam(required = false) String ratingCheckbox, @RequestParam (required = false)String cityCheckbox,
                     @RequestParam(required = false) String priceCheckbox, @RequestParam (required = false) String housingTypeCheckbox, Model model) {
   filtredAavertisements.clear();
  if (action.equals("filter")) {
    if(countryCheckbox==null && rentalDatesCheckbox==null && ratingCheckbox==null && cityCheckbox==null && priceCheckbox==null && housingTypeCheckbox==null ){
       model.addAttribute("advertisements",advertisements);
    }else{
         Iterator<Advertisement> iterator= advertisements.iterator();
         while(iterator.hasNext()){
           Advertisement  el= iterator.next();
            if(countryCheckbox!=null){
                if(el.getCity().getCountry().getId()!=countryId){
                 continue;
                }
            }
            if(cityCheckbox!=null){
             if(el.getCity().getId()!=cityId){
                 continue;
             }
            }
            if(rentalDatesCheckbox!=null){
              if(el.getRentalDate() < rentalDate){
                   continue;
              }
            }
            if(ratingCheckbox!=null){
              if(el.getLandLord().getRating() < rating){
                  continue;
              }
            }
            if(priceCheckbox!=null){
              if(el.getPrice()>price){
               continue;
              }
            }
            if(housingTypeCheckbox!=null){
             if(el.getHousingType().getId()!=housingTypeId){
              continue;
             }
            }

            filtredAavertisements.add(el);

         }
         model.addAttribute("advertisements",filtredAavertisements);
    }

 } else if(action.equals("without")){
    model.addAttribute("advertisements",advertisements);
 }
 model.addAttribute("cities",cities);
 model.addAttribute("countries",countries);
 model.addAttribute("housingTypes",housingTypes);
 return "offers";
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

private void addMessageTypes(){
    ArrayList<String> messageTypes= new ArrayList<>();
    Collections.addAll(messageTypes,"щодо ордера","щодо рейтинга","опрацьовано");
    for (String messageTypeName:messageTypes){
        messageTypeRepository.save(new MessageType(messageTypeName));
    }
}

private void orderProcessing(){
       Status status= statusRepository.findByName("схвалено");
        Iterable<OrderModel> orders= orderRepository.findByStatusId(status.getId());
    for (OrderModel targetOrder : orders) {
        LocalDate today = LocalDate.now();
        LocalDate startOfRent = targetOrder.getDate();
        int countOfRentalDays = targetOrder.getAdvertisement().getRentalDate();
        //перевіряємо, чи скінчився термін оренди
        if (today.isAfter(startOfRent.plusDays(countOfRentalDays))) {
            Status newStatus = statusRepository.findByName("виконано");
            targetOrder.setStatus(newStatus);
            orderRepository.save(targetOrder);
            //створюємо два повідомлення - для орендодавця та для орендаря
            MessageType messageType = messageTypeRepository.findByName("щодо рейтинга");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            //для орендодавця
            UserModel admin = userRepository.findByMail("admin@gmail.com");
            String txt = "Ви здавали житло у місті " + targetOrder.getAdvertisement().getCity().getName() + " з " + targetOrder.getDate().format(formatter) + " строком на " +
                    targetOrder.getAdvertisement().getRentalDate() + " день(днів). Виставте рейтинг для орендаря.";
            Message newMessage1 = new Message(txt, admin, targetOrder.getAdvertisement().getLandLord(), targetOrder.getId(), messageType);
            messageRepository.save(newMessage1);
            //для орендаря
            String txt2 = "Ви винаймали житло у місті " + targetOrder.getAdvertisement().getCity().getName() + " з " + targetOrder.getDate().format(formatter) + " строком на " +
                    targetOrder.getAdvertisement().getRentalDate() + " день(днів). Виставте рейтинг для орендодавця.";
            Message newMessage2 = new Message(txt2, admin, targetOrder.getRenter(), targetOrder.getId(), messageType);
            messageRepository.save(newMessage2);
        }
    }
    }

private void messageProcessing(){

     // потрібно перевірити якщо є повідомлення
  Iterable<Message> messages=messageRepository.findBySenderIsLockedOrRecipientIsLocked(true,true);
  for (Message message:messages){
      MessageType messageType1=messageTypeRepository.findByName("щодо ордера");
      MessageType messageType2=messageTypeRepository.findByName("щодо рейтинга");
      MessageType messageType3=messageTypeRepository.findByName("опрацьовано");
      // повідомлення про  виставлення рейтингу
      OrderModel order= orderRepository.findById(message.getOrder_id()).get();
      UserModel targetUser= message.getSender();
      if(message.getMessageType().getId()==messageType2.getId()){

          if(order!=null && targetUser!=null){
              if(targetUser.getRole().getName().equals("орендар")){
                  //виставляємо бал ==4 для орендодавця
                  order.getAdvertisement().getLandLord().addVoice(4);
              } else if(targetUser.getRole().getName().equals("орендодавець")) {
                  //виставляєто бал ==4 для орендаря
                  order.getRenter().addVoice(4);
              }
          }

      } else if (message.getMessageType().getId()==messageType1.getId()) {
             Status status= statusRepository.findByName("відхилено");
          // если получатель блокирован
          if(message.getRecipient().isLocked()){
              if(message.getRecipient().getRole().getName().equals("орендодавець")){
                  UserModel admin= userRepository.findByMail("admin@gmail.com");
                  Message newMessage= new Message("Ви не можете орендувати житло, тому що орендодавець - заблогований",admin,targetUser,message.getOrder_id(),messageType1);
                 messageRepository.save(newMessage);
                 //міняємо статус у ордера

                  order.setStatus(status);
                  orderRepository.save(order);
              }
              // если отправитель блокирован
          } else if(message.getSender().isLocked()) {
            if(message.getSender().getRole().getName().equals("орендар")){
                order.setStatus(status);
                orderRepository.save(order);
            }
          }

      }
      message.setMessageType(messageType3);
      messageRepository.save(message);
  }


}

}




