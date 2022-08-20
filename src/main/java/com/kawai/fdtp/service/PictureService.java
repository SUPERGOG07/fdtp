package com.kawai.fdtp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kawai.fdtp.pojo.Picture;

import java.util.List;

public interface PictureService extends IService<Picture> {

    List<Picture> getPictures(Integer type,String target,Integer page,Integer size);

}
