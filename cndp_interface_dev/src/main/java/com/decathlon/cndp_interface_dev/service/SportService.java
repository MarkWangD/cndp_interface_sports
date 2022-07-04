package com.decathlon.cndp_interface_dev.service;

import com.decathlon.cndp_interface_dev.mapper.SportMapper;
import com.decathlon.cndp_interface_dev.pojo.OrderDetail;
import com.decathlon.cndp_interface_dev.pojo.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SportService {


    @Autowired
    SportMapper sportMapper;

    public Personne getPersonne(String channel_person_id) {
        return sportMapper.findByChannelPersonId(channel_person_id);
    }

    public List<Personne> getPersonnes(List<String> channel_person_ids) {

        List<Personne> personnes = new ArrayList<>();
        for (String channel_person_id : channel_person_ids) {

            Personne personne = sportMapper.findByChannelPersonId(channel_person_id);
            if (personne != null && !personnes.contains(personne)) {
                personnes.add(sportMapper.findByChannelPersonId(channel_person_id));
            }
        }
        return personnes;
    }


    public List<OrderDetail> getOrder(List<String> card_numbers, String start_date, String end_date) {

//        StringBuffer stringBuffer = new StringBuffer(" ");
//
//        stringBuffer.append(card_numbers.get(0)).append("'").append(",");
//        for (int i = 1; i < card_numbers.size() - 1; i++) {
//            stringBuffer.append("'").append(card_numbers.get(i)).append("'").append(",");
//        }
//        stringBuffer.append("'").append(card_numbers.get(card_numbers.size() - 1));
//
//        String card_number_List = stringBuffer.toString();
//        System.out.println("======123:" + card_number_List);


        List<OrderDetail> orderDetails = sportMapper.findOrderDetails(card_numbers, start_date, end_date);
        if (orderDetails != null) {
            return orderDetails;
        } else {
            List<OrderDetail> orderDetails1 = new ArrayList<OrderDetail>();
            return orderDetails1;
        }
    }

    public List<OrderDetail> get_online_order(List<String> card_numbers, String start_date, String end_date) {



        List<OrderDetail> orderDetails = sportMapper.find_online_order_detail(card_numbers, start_date, end_date);
        if (orderDetails != null) {
            return orderDetails;
        } else {
            List<OrderDetail> orderDetails1 = new ArrayList<OrderDetail>();
            return orderDetails1;
        }
    }
}