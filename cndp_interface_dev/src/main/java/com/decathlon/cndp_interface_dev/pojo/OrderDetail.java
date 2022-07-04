package com.decathlon.cndp_interface_dev.pojo;

import lombok.Data;

@Data
public class OrderDetail {
    private String transaction_id ;
    private String item_code ;
    private String transaction_date ;
    private String membercard ;
    private String item_qty;
    private String unit_price;
    private String channel_type;
}
