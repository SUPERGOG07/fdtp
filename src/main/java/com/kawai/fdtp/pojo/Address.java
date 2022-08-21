package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("address")
public class Address {

    private String id;
    //目标类型
    /**
     * 0:store  1:food  2:attraction 3:posts 4:comment
     */
    private Integer type;
    //目标id
    private String target;
    //是否农村
    private Integer isCountry;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //省、自治区
    private String province;
    //市、直辖市
    private String city;
    //区、县
    private String area;
    //街道
    private String street;
    //路
    private String road;
    //镇、乡
    private String town;
    //村
    private String village;
    //详细地址
    private String detail;

    public static Address defaultConstruct(){
        return new Address("1",1,"1",1,"1","1",
                "1","1","1","1","1","1","1","1");
    }

    public static String  pickup(String address,LambdaQueryWrapper<Address> wrapper){
        boolean flag = false;

        address = address.substring(address.indexOf("省")+1);

        //市
        String city = address.substring(0,address.indexOf("市")+1);
        if (!city.isEmpty()){
            flag = true;
            wrapper.eq(Address::getCity,city);
        }
        address = address.substring(city.length());

        //区、县
        String area = address.substring(0,address.indexOf("区")+1);
        if (!area.isEmpty()){
            flag = true;
            wrapper.eq(Address::getArea,area);
        }else {
            area = address.substring(0,address.indexOf("县")+1);
            if (!area.isEmpty()){
                flag = true;
                wrapper.eq(Address::getArea,area);
            }
        }
        address = address.substring(area.length());

        //街道
        String street = address.substring(0,address.indexOf("街道")+1);
        if(!street.isEmpty()){
            flag = true;
            wrapper.eq(Address::getStreet,street);
        }
        address = address.substring(street.length());

        //镇
        String town = address.substring(0,address.indexOf("镇")+1);
        if(town.isEmpty()){
            town = address.substring(0,address.indexOf("乡")+1);
        }
        if (!town.isEmpty()){
            flag = true;
            wrapper.eq(Address::getTown,town);
        }
        address = address.substring(town.length());

        String village = address.substring(0,address.indexOf("村")+1);
        if (!village.isEmpty()){
            flag = true;
            wrapper.eq(Address::getVillage,village);
        }
        address = address.substring(village.length());

        //路
        String road = address.substring(0,address.indexOf("路")+1);
        if (road.isEmpty()){
            road = address.substring(0,address.indexOf("街")+1);
            if (road.isEmpty()){
                road = address.substring(0,address.indexOf("道")+1);
            }
        }
        if (!road.isEmpty()){
            flag = true;
            wrapper.eq(Address::getRoad,road);
        }
        address = address.substring(road.length());

        if (!flag){
            wrapper.eq(Address::getDetail,"unknown detail and can't be check");
        }

        return address;
    }

    public static void check(Address address){
        String unknown = "unknown";
        if (address.getLongitude()==null){
            address.setLongitude(unknown);
        }
        if (address.getLatitude()==null){
            address.setLatitude(unknown);
        }
        if (address.getProvince()==null){
            address.setProvince(unknown);
        }
        if (address.getCity()==null){
            address.setCity(unknown);
        }
        if (address.getArea()==null){
            address.setArea(unknown);
        }
        if (address.getStreet()==null){
            address.setStreet(unknown);
        }
        if (address.getRoad()==null){
            address.setRoad(unknown);
        }
        if (address.getTown()==null){
            address.setTown(unknown);
        }
        if (address.getVillage()==null){
            address.setVillage(unknown);
        }
        if (address.getDetail()==null){
            address.setDetail(unknown);
        }
    }

}
