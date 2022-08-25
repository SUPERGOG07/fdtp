package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.AddressMapper;
import com.kawai.fdtp.mapper.AttractionMapper;
import com.kawai.fdtp.pojo.Address;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.pojo.Store;
import com.kawai.fdtp.service.AttractionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AttractionServiceImpl extends ServiceImpl<AttractionMapper, Attraction> implements AttractionService {

    @Resource
    AttractionMapper attractionMapper;

    @Resource
    AddressMapper addressMapper;

    @Override
    public Boolean grade(String id, int num) {
        Attraction attraction = attractionMapper.selectById(id);
        if((attraction.getGrade()+num )>=0){
            attraction.setGrade(attraction.getGrade()+num);
            attractionMapper.updateById(attraction);
            return true;
        }

        return false;
    }

    @Override
    public List<Attraction> getAttractionByAddress(String address, Integer page, Integer size) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getType,2);
        String detail = Address.pickup(address,wrapper);

        Page<Address> attractionPage = new Page<>(page,size);
        addressMapper.selectPage(attractionPage,wrapper);
        if (attractionPage.getRecords().isEmpty()){
            wrapper.clear();
            wrapper.eq(Address::getType,2);
            wrapper.like(Address::getCity,detail).or().like(Address::getArea,detail).or()
                    .like(Address::getTown,detail).or().like(Address::getVillage,detail).or()
                    .like(Address::getStreet,detail).or().like(Address::getRoad,detail).or()
                    .like(Address::getDetail,detail);
            addressMapper.selectPage(attractionPage,wrapper);
        }
        List<Address> addressList = new ArrayList<>(attractionPage.getRecords());

        List<Attraction> attractions = new ArrayList<>();

        addressList.forEach(address1 -> {
            Attraction attraction = attractionMapper.selectOne(new LambdaQueryWrapper<Attraction>().eq(Attraction::getAddress,address1.getId()));
            if (attraction!=null){
                attractions.add(attraction);
            }

        });
        attractions.sort(Comparator.comparing(Attraction::getGrade).reversed());
        Attraction.check(attractions);

        return attractions;
    }
}
