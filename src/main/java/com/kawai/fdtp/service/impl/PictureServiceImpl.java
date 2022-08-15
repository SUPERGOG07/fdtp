package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.PictureMapper;
import com.kawai.fdtp.pojo.Picture;
import com.kawai.fdtp.service.PictureService;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {
}
