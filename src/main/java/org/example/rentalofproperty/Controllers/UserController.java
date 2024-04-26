package org.example.rentalofproperty.Controllers;

import org.example.rentalofproperty.Models.*;
import org.example.rentalofproperty.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

@Controller
public class UserController {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICityRepository cityRepository;
    @Autowired
    private ICountryRepository countryRepository;
    @Autowired
    private IAdvertisementRepository advertisementRepository;
    @Autowired
    private IHousingRepository housingRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IStatusRepository statusRepository;
    @Autowired
    private IMessageRepository messageRepository;
    @Autowired
    private IMessageTypeRepository messageTypeRepository;
    @Autowired
    private IRoleRepository roleRepository;
    private UserModel user;
    private Iterable<Country> countries;
    private Iterable<City> cities;
    private Iterable<Advertisement> myAdvertisements;
    private Iterable<HousingType> housingTypes;
    private Iterable<Advertisement> advertisements;
    private ArrayList<Advertisement> filtredAavertisements=new ArrayList<>();

    @GetMapping("/user")
    public String admin(@RequestParam(required = false) Long id, Model model){
        if(user==null)
            user=userRepository.findById(id).get();
        if(countries==null)
            countries=countryRepository.findAll();
        if(cities==null)
            cities=cityRepository.findAll();
        if(advertisements==null)
            advertisements=advertisementRepository.findAll();
        if(housingTypes==null)
            housingTypes=housingRepository.findAll();

        MessageType messageType1= messageTypeRepository.findByName("щодо рейтинга");
        MessageType messageType2= messageTypeRepository.findByName("щодо ордера");
        MessageType messageType= messageTypeRepository.findByName("опрацьовано");
        //знаходимо всі повідомлення з визначенням рейтингів
        Iterable<Message> messages=messageRepository.findAllByMessageType_IdAndRecipient_Id(messageType1.getId(),user.getId());
        Iterator<Message> iterator= messages.iterator();
       if(iterator.hasNext()){
           model.addAttribute("flag",1);
           model.addAttribute("flag2",0);
           model.addAttribute("flag3",0);
           Message mess= iterator.next();
           model.addAttribute("orderId",mess.getOrder_id());
           model.addAttribute("messageId",mess.getId());
           model.addAttribute("description",mess.getDescription());
       }
       else{
           model.addAttribute("flag",0);
           //знаходимо всі повідомлення щодо ордера
           Iterable<Message> messages2=messageRepository.findAllByMessageType_IdAndRecipient_Id(messageType2.getId(),user.getId());
           iterator=messages2.iterator();
           if(iterator.hasNext()){
               Message mess= iterator.next();
               if(user.getRole().getId()==roleRepository.findByName("орендар").getId()){
                   model.addAttribute("flag2",1);
                   model.addAttribute("flag3",0);
                   if(mess.getSender().isLocked()){
                       OrderModel order= orderRepository.findById(mess.getOrder_id()).get();
                       Status status= statusRepository.findByName("відхилено");
                       order.setStatus(status);
                       orderRepository.save(order);
                       String txt = "Аккаунт орендодавця заблокований. Ви не можете орендувати приміщення";
                       model.addAttribute("description2",txt);
                       mess.setMessageType(messageType);
                       messageRepository.save(mess);
                   }
                   else{
                       mess.setMessageType(messageType);
                       messageRepository.save(mess);
                       model.addAttribute("description2",mess.getDescription());
                   }


               } else if(user.getRole().getId()==roleRepository.findByName("орендодавець").getId()) {

                       Role role = roleRepository.findByName("орендар");
                       if(mess.getSender().getRole().getId()==role.getId()){
                           model.addAttribute("flag2",0);
                           model.addAttribute("flag3",1);

                           model.addAttribute("order3Id",mess.getOrder_id());
                           model.addAttribute("message3Id",mess.getId());
                           model.addAttribute("description3",mess.getDescription());

                   }
                       else{
                           model.addAttribute("flag2",1);
                           model.addAttribute("flag3",0);
                           model.addAttribute("description2",mess.getDescription());
                       }
               }

           }
           else{
               model.addAttribute("flag2",0);
               model.addAttribute("flag3",0);
           }
       }

        // знаходимо всі повідомлення зі створення ордера

         model.addAttribute("user",user);
        return "userPage";
    }

    @PostMapping("/user")
    public String addVoiceToRating(@RequestParam(required = false) Integer rating, @RequestParam (required = false) Long orderId, @RequestParam(required = false) Long messageId,
                                   @RequestParam(required = false) String action, Model model){
        OrderModel order= orderRepository.findById(orderId).get();
        Message message= messageRepository.findById(messageId).get();
        MessageType messageType= messageTypeRepository.findByName("опрацьовано");
        if(rating!=null){
            UserModel userRecipient=null;
            if(user.getRole().getName().equals("орендар")){
                userRecipient= order.getAdvertisement().getLandLord();
            } else if(user.getRole().getName().equals("орендодавець")) {
                userRecipient= order.getRenter();
            }
            userRecipient.addVoice(rating);
            userRepository.save(userRecipient);

        } else if (action !=null) {
            String txt="";
            if(action.equals("confirm")){
                Status status= statusRepository.findByName("схвалено");
                order.setStatus(status);
                order.setDate(LocalDate.now());
                orderRepository.save(order);
                txt= "Ваша запит на оренду житла схвалено";
            } else if(action.equals("reject")) {
                Status status= statusRepository.findByName("відхилено");
                order.setStatus(status);
                order.setDate(LocalDate.now());
                orderRepository.save(order);
                txt="Ваш запит на оренду житла відхилено";
            }
            UserModel userRecipient= order.getRenter();
            MessageType messageType1= messageTypeRepository.findByName("щодо ордера");
            Message newMessage= new Message(txt,user,userRecipient,orderId,messageType1);
            messageRepository.save(newMessage);
        }
        message.setMessageType(messageType);
        messageRepository.save(message);
        return "redirect:/user";
    }

    @GetMapping("user/about")
    public String about(Model model){
        model.addAttribute("user",user);
        return "userAboutPage";
    }

    @GetMapping("user/contacts")
    public String contacts(Model model){
        model.addAttribute("user",user);
        return "userContactsPage";
    }

    @GetMapping("user/offers")
    public String offer(Model model){
        filtredAavertisements.clear();
        model.addAttribute("user",user);
        model.addAttribute("advertisements",filtredAavertisements);
        model.addAttribute("cities",cities);
        model.addAttribute("countries",countries);
        model.addAttribute("housingTypes",housingTypes);
        return "userOffersPage";
    }

    @PostMapping("user/offers/filter")
    public String filter(@RequestParam String action,@RequestParam Long housingTypeId,@RequestParam Long countryId,@RequestParam Long cityId,
                         @RequestParam Integer rentalDate,@RequestParam Integer rating,@RequestParam Integer price,@RequestParam (required = false) String countryCheckbox,
                         @RequestParam(required = false) String rentalDatesCheckbox,@RequestParam(required = false) String ratingCheckbox, @RequestParam (required = false)String cityCheckbox,
                         @RequestParam(required = false) String priceCheckbox, @RequestParam (required = false) String housingTypeCheckbox, Model model) {

        if (action.equals("filter")) {
            if(countryCheckbox==null && rentalDatesCheckbox==null && ratingCheckbox==null && cityCheckbox==null && priceCheckbox==null && housingTypeCheckbox==null ){
                model.addAttribute("advertisements",advertisements);
            }else{
                Iterator<Advertisement> iterator= advertisements.iterator();
                while(iterator.hasNext()){
                    boolean flag=true;
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
        model.addAttribute("user",user);
        model.addAttribute("cities",cities);
        model.addAttribute("countries",countries);
        model.addAttribute("housingTypes",housingTypes);
        return "userOffersPage";
    }


    @PostMapping("user/offers/order")
    public String createOrder(@RequestParam Long advertisementId, Model model){
        Advertisement adv= advertisementRepository.findById(advertisementId).get();
        if(adv==null){
            model.addAttribute("message","Ордер не знайдено");
        }
        else{
            Status status= statusRepository.findByName("створено");
            if(orderRepository.existsByAdvertisementIdAndStatusId(adv.getId(),status.getId())){
                model.addAttribute("message","Заявку була створена раніше, чекайте на відповідь");
            }
            else{
                OrderModel newOrder= new OrderModel(adv,user,status);
                orderRepository.save(newOrder);
                String mess= "Створено заявку на оренду вашого  житла, id оголошення: "+adv.getId()+" строком на: "+adv.getRentalDate()+" днів, вартістю: "+adv.getPrice();
                MessageType messageType= messageTypeRepository.findByName("щодо ордера");
                Message newmessage= new Message(mess,user,adv.getLandLord(), newOrder.getId(),messageType);
                messageRepository.save(newmessage);
                model.addAttribute("message","Заявку на оренду створено, чекайте на підтвердження");
            }


        }

        model.addAttribute("user",user);
        model.addAttribute("advertisements",filtredAavertisements);
        model.addAttribute("cities",cities);
        model.addAttribute("countries",countries);
        model.addAttribute("housingTypes",housingTypes);
        return "userOffersPage";
    }



    @GetMapping("user/personal")
    public String personalInfo(Model model){
        model.addAttribute("user",user);
        if(cities==null)
           cities=cityRepository.findAll();
        if(countries==null)
           countries=countryRepository.findAll();
        model.addAttribute("cities",cities);
        model.addAttribute("countries",countries);
        return "userPersonalPage";
    }

    @PostMapping("/user/personal")
    public String changePersonalInfo(@RequestParam Long id, @RequestParam String name, @RequestParam String mail, @RequestParam String password,
                                     @RequestParam Long cityId, @RequestParam LocalDate dateOfBirth,Model model){

        UserModel newUser= userRepository.findById(id).get();
        if(newUser==null){
            model.addAttribute("message","Користувача в базі данних не знайдено");
        }
        else{
            UserModel oldUser= userRepository.findByMail(mail);
            if(oldUser!=null && oldUser.getId()!= newUser.getId()){
                model.addAttribute("message","Електронна адреса- вже використовуэться іншим користувачем");
            }
            else{
                City city=cityRepository.findById(cityId).get();
                Role role= newUser.getRole();
                newUser.setAllParameters(name,city,role,mail,password,dateOfBirth);
                userRepository.save(newUser);
                user=userRepository.findById(id).get();
                model.addAttribute("message","Ваші персональні дані змінено");
            }

        }
        model.addAttribute("user",user);
        model.addAttribute("countries",countries);
        model.addAttribute("cities",cities);
        return "userPersonalPage";
    }

    @GetMapping("user/myOffers")
    public String addOffer(Model model){
        model.addAttribute("user",user);
        if(myAdvertisements==null)
            myAdvertisements=advertisementRepository.findByLandLordIdAndIsDeletedFalseOrderByDateDesc(user.getId());
        if(housingTypes==null)
            housingTypes=housingRepository.findAll();
        if(cities==null)
            cities=cityRepository.findAll();
        if(countries==null)
            countries=countryRepository.findAll();

        model.addAttribute("advertisements",myAdvertisements);
        model.addAttribute("housingTypes",housingTypes);
        model.addAttribute("cities",cities);
        model.addAttribute("countries",countries);
        return "userMyOffersPage";
    }

    @PostMapping("user/myOffers/add")
    public String addAdvertisement(@RequestParam Long housingTypeId,@RequestParam Long cityId,@RequestParam String description,
                                   @RequestParam Integer price,@RequestParam Integer rentalDate , @RequestParam(required = false) MultipartFile photo1,
                                   @RequestParam(required = false) MultipartFile photo2,@RequestParam(required = false) MultipartFile photo3,  Model model){

        HousingType houseType= housingRepository.findById(housingTypeId).get();
        City city= cityRepository.findById(cityId).get();
        if(houseType!=null && city!=null){
            Advertisement newAdvertisement= new Advertisement(user,houseType,city,price,rentalDate,description);
            //далее работа с файлами
            if(photo1!=null){



            }
            if(photo2!=null){

            }
            if(photo3!=null){

            }

            //если все хорошо - то выводим сообщение
            //advertisementRepository.save(newAdvertisement);
            myAdvertisements=advertisementRepository.findByLandLordIdAndIsDeletedFalseOrderByDateDesc(user.getId());
            model.addAttribute("message","Повідомлення створене- чекайте на модерацію");
        }
        else{
            model.addAttribute("message","Не можливо створити замовлення");
        }

        model.addAttribute("user",user);
        model.addAttribute("advertisements",myAdvertisements);
        model.addAttribute("housingTypes",housingTypes);
        model.addAttribute("cities",cities);
        model.addAttribute("countries",countries);
        return "userMyOffersPage";
    }

    @PostMapping("user/myOffers/deleteOrEdit")
    public String deleteOrEdit(@RequestParam String action,@RequestParam Long id,@RequestParam Long cityId, @RequestParam Long housingTypeId,@RequestParam String description,
                               @RequestParam Integer price,@RequestParam Integer rentalDate,Model model){

        if(action.equals("delete")){
            Advertisement adv= advertisementRepository.findById(id).get();
            if(adv==null){
                model.addAttribute("message","Оголошеня не знайдене");
            }else{
                adv.setDeleted(true);
                advertisementRepository.save(adv);
                myAdvertisements=advertisementRepository.findByLandLordIdAndIsDeletedFalseOrderByDateDesc(user.getId());
                model.addAttribute("message","Оголошення видалене");
            }


        } else if (action.equals("edit")) {
            Advertisement adv= advertisementRepository.findById(id).get();
            if(adv==null){
                model.addAttribute("message","Оголошеня не знайдене");
            }else{
                City city= cityRepository.findById(cityId).get();
                HousingType type= housingRepository.findById(housingTypeId).get();
                adv.setSomeFields(type,city,price,rentalDate,description);
                advertisementRepository.save(adv);
                myAdvertisements=advertisementRepository.findByLandLordIdAndIsDeletedFalseOrderByDateDesc(user.getId());
                model.addAttribute("message","Оголошення оновлено");
            }
        }else{
            model.addAttribute("message","Жодних дій не виконано");
        }

        model.addAttribute("user",user);
        model.addAttribute("advertisements",myAdvertisements);
        model.addAttribute("housingTypes",housingTypes);
        model.addAttribute("cities",cities);
        model.addAttribute("countries",countries);
        return "userMyOffersPage";
    }

}