package com.decathlon.cndp_interface_dev.mapper;


import com.decathlon.cndp_interface_dev.pojo.OrderDetail;
import com.decathlon.cndp_interface_dev.pojo.Personne;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SportMapper {

    @Select(" select\n" +
            "a.union_id, \n" +
            "d.numero_carte as card_number,\n" +
            "timezone('UTC',d.date_creation_carte) as registration_time, \n" +
            "c.person_id as person_id,\n" +
            "d.num_tiers_createur as registration_store\n" +
            "from \n" +
            "(select channel_person_id as union_id, id from \n" +
            "public.ods_sb_channel_person\n" +
            "where channel_person_id in (  #{channel_person_id}   )) a\n" +
            "left join public.ods_sb_social_connection b \n" +
            "on a.id=b.social_id\n" +
            "left join public.ods_sb_dkt_person c\n" +
            "on b.dkt_person_id=c.id\n" +
            "left join customeras1.personne d \n" +
            "on c.person_id::int8=d.id\n" +
            "group by 1,2,3,4,5")
    Personne findByChannelPersonId(String channel_person_id);


    @Select({
            "<script>",
            " select \n" +
                    "transaction_id,\n" +
                    "item_code,\n" +
                    "transaction_date,\n" +
                    "membercard,\n" +
                    "item_qty,\n" +
                    "unit_price,\n" +
                    "'offline' as channel_type\n" +
                    "from public.f_offline_transaction_v5\n" +
                    "where transaction_date between to_date(#{start_date},'YYYY-MM-DD HH24:MI:SS') and to_date(#{end_date},'YYYY-MM-DD HH24:MI:SS') \n" +
                    "and \n" +
                    "membercard in  ",
            "<foreach collection='list' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    }

    )
    List<OrderDetail> findOrderDetails(@Param("list") List<String> card_numbers, @Param("start_date") String start_date, @Param("end_date") String end_date);


    @Select({
            "<script>",
            " select \n" +
                    "a.order_id as transaction_id,\n" +
                    "a.product_id as item_code,\n" +
                    "b.paytime as transaction_date,\n" +
                    "case when b.name='迪卡侬CUBE' then b.uname  else b.card_number end  as membercard,\n" +
                    "a.nums as item_qty,\n" +
                    "a.sale_price_per_item as unit_price,\n" +
                    "'online' as channel_type\n" +
                    "from public.f_online_product as a \n" +
                    "inner join  f_online_order  b  on  a.order_bn = b.order_bn and b.paytime between to_date(#{start_date},'YYYY-MM-DD HH24:MI:SS') and to_date(#{end_date},'YYYY-MM-DD HH24:MI:SS') and  \n " +
                    "case when b.name='迪卡侬CUBE' then b.uname  else b.card_number end  in\t" +
                    "<foreach collection='list' item='id' open='(' separator=',' close=')'>",
                    "#{id}",
                    "</foreach>",
                    " " +
                    "left join f_online_order c on a.order_bn = c.order_bn",
            "</script>"
    }

    )
    List<OrderDetail> find_online_order_detail(@Param("list") List<String> card_numbers, @Param("start_date") String start_date, @Param("end_date") String end_date);

}
