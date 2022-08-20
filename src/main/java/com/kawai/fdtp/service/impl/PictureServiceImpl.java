package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.PictureMapper;
import com.kawai.fdtp.pojo.Picture;
import com.kawai.fdtp.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    @Resource
    PictureMapper pictureMapper;

    @Override
    public List<Picture> getPictures(Integer type,String target,Integer page,Integer size) {

        LambdaQueryWrapper<Picture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Picture::getType,type).eq(Picture::getTarget,target).orderByDesc(Picture::getTime);

        Page<Picture> pageInfo = new Page<>(page,size);

        pictureMapper.selectPage(pageInfo,wrapper);

        return pageInfo.getRecords();
    }
}
