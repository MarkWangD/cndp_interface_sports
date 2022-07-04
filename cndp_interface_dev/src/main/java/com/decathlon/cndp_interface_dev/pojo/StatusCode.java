package com.decathlon.cndp_interface_dev.pojo;


import lombok.Data;

import java.util.List;

@Data
public class StatusCode {


    private Object data ;
    private String status ;
    private Integer code ;

    public StatusCode() {
    }

    public StatusCode(Object data, String status, Integer code) {
        this.data = data;
        this.status = status;
        this.code = code;
    }



    public static StatusCode success() {
        return new StatusCode(null , "success" , 200 );
    }

    public static StatusCode success(List<Personne> personneList) {
        return new StatusCode(personneList , "success" , 200 );
    }

    public static StatusCode success1(List<OrderDetail> OrderDetailList) {
        return new StatusCode(OrderDetailList , "success" , 200 );
    }




    public static StatusCode fail() {
        return new StatusCode(null , "fail" , 404 );
    }

//    public static StatusCode success(List<OrderDetail> orderDetails){
//        return new StatusCode(orderDetails , "success" , 200 );
//    }



}
