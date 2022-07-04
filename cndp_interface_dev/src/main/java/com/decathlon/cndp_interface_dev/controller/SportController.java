package com.decathlon.cndp_interface_dev.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.decathlon.cndp_interface_dev.mapper.SportMapper;
import com.decathlon.cndp_interface_dev.pojo.OrderDetail;
import com.decathlon.cndp_interface_dev.pojo.Personne;
import com.decathlon.cndp_interface_dev.pojo.StatusCode;
import com.decathlon.cndp_interface_dev.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class SportController {

    @Autowired
    SportService sportService;


//    // url = http://localhost:8080/sport    channel_person_id  = ?
//    @PostMapping("/sport")
//    public String getPersonne(@RequestParam( name = "channel_person_id") String channel_person_id) {
//
//        Personne personne = sportService.getPersonne(channel_person_id);
//
//        if( personne != null ) {
//            System.out.println(" numero_carte = " + personne.getNumero_carte() + " , date_creation_carte = " + personne.getDate_creation_carte());
//            return personne.toString() ;
//        }
//        return " mistake for channel_person_id , please check ! " ;
//    }
//
//    // url = http://localhost:8080/sports   channel_person_id  = ?
//    @PostMapping("/sports")
//    public String getPersonnes(@RequestParam( required = false, name = "channel_person_id") List<String> channel_person_ids) {
//
//        if(channel_person_ids != null) {
//            List<Personne> personneList = sportService.getPersonnes(channel_person_ids);
//
//            if( personneList.size() !=0 && !personneList.isEmpty()) {
//                return personneList.toString();
//            }
//            return " mistake for channel_person_id , please check !" ;
//        }
//        return null ;
////    }
//
//    //      url = http://localhost:8080/sportsJson   channel_person_id  = ?
//    @RequestMapping("/sportsJson1")
//    public String getPersonneJson1(@RequestParam(required = true, name = "channel_person_id") List<String> channel_person_ids) {
////requestBody
//        if (channel_person_ids != null) {
//            List<Personne> personneList = sportService.getPersonnes(channel_person_ids);
//
//            if (personneList.size() != 0 && !personneList.isEmpty()) {
//                String personneStr = JSONObject.toJSONString(personneList);
//                return personneStr;
//            }
//            return " no result found";
//        }
//        return null;
//    }
//
//    @RequestMapping("/sportsJson2")
//    public String getPersonneJson(@RequestBody JSONObject jsonStr) {
////requestBody
//        System.out.println(jsonStr);
//        String channel_person_id = (String) jsonStr.get("channel_person_id");
//
//        if (channel_person_id != null) {
//            Personne personne = sportService.getPersonne(channel_person_id);
//
//            return personne.toString();
//        }
//        return null;
//    }


    // 完整版
    // http://localhost:8080/sportsJson3    { "channel_person_ids":["o4zAjt3x_PeFJKpUK8teazQPZRK0"   , "oph8M446FNAYmTyd8kpgxjrOu9EI" ,"ob0m-4u4SOcZnJdjXlbmen9_XKtU"]}
    @RequestMapping("/person")
    public StatusCode getPersonneJsons(@RequestBody JSONObject jsonStr) {
//requestBody
        ArrayList<String> list = new ArrayList<>();

        JSONArray channel_person_ids = jsonStr.getJSONArray("channel_person_ids");

        for (int i = 0; i < channel_person_ids.size(); i++) {
            list.add(channel_person_ids.get(i).toString());
        }

        List<Personne> personnes = sportService.getPersonnes(list);

        if (!personnes.isEmpty() && personnes.size() != 0) {

            return StatusCode.success(personnes);
        }

        return StatusCode.fail();
    }

    @GetMapping("/test")
    public StatusCode test() {
//requestBody
        return StatusCode.success();
    }

    @RequestMapping("/transaction")
    public StatusCode getOrderJsons(@RequestBody JSONObject jsonStr) {
//requestBody
        ArrayList<String> cardNumberList = new ArrayList<>();//创建Arrarylist存放card number
        String start_date = jsonStr.getString("start_date");
        String end_date = jsonStr.getString("end_date");

        JSONArray card_numbers = jsonStr.getJSONArray("card_number");

        for (int i = 0; i < card_numbers.size(); i++) {
            cardNumberList.add(card_numbers.get(i).toString());
        }
        if (cardNumberList != null && start_date != null && end_date != null) {

            List<OrderDetail> orderDetails = sportService.getOrder(cardNumberList, start_date, end_date);
            if (orderDetails.size() != 0 && orderDetails != null) {
                return StatusCode.success1(orderDetails);
            }else {
                return StatusCode.success();
            }
        }
        return StatusCode.fail();


    }
    @RequestMapping("/orderdetail")
    public StatusCode getOrderDetail(@RequestBody JSONObject jsonStr) {
//requestBody
        ArrayList<String> cardNumberList = new ArrayList<>();//创建Arrarylist存放card number
        String start_date = jsonStr.getString("start_date");
        String end_date = jsonStr.getString("end_date");

        JSONArray card_numbers = jsonStr.getJSONArray("card_number");

        for (int i = 0; i < card_numbers.size(); i++) {
            cardNumberList.add(card_numbers.get(i).toString());
        }
        if (cardNumberList != null && start_date != null && end_date != null) {

            List<OrderDetail> orderDetails = sportService.get_online_order(cardNumberList, start_date, end_date);
            if (orderDetails.size() != 0 && orderDetails != null) {
                return StatusCode.success1(orderDetails);
            }else {
                return StatusCode.success();
            }
        }
        return StatusCode.fail();


    }

}
