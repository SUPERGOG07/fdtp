package com.kawai.fdtp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kawai.fdtp.common.FileUtil;
import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Picture;
import com.kawai.fdtp.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/picture")
@HasRole(value = {"customer","admin"})
public class PictureController {

    @Resource
    PictureService pictureService;

    @PostMapping("/add")
    @ApiOperation("增加图片")
    public R<Picture> add(Integer type,String target,String url){
        log.info("图片增加-->{}",target);

        Picture picture = new Picture();
        picture.setType(type);
        picture.setTarget(target);
        picture.setUrl(url);
        picture.setTime(System.currentTimeMillis());

        if(pictureService.getOne( new LambdaQueryWrapper<Picture>()
                        .eq(Picture::getType,type)
                .eq(Picture::getTarget,target)
                .eq(Picture::getUrl,url)
        )==null){
            if (pictureService.save(picture)){
                return R.success(picture);
            }
        }

        return R.error("上传失败",Picture.defaultConstruct());

    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除图片")
    public R<Picture> delete(@PathVariable String id){
        log.info("图片删除-->{}",id);

        if(pictureService.removeById(id)){
            return R.success(Picture.defaultConstruct());
        }
        return R.error(Picture.defaultConstruct());
    }

    @DeleteMapping("/delete/batch")
    @ApiOperation("批量删除图片")
    public R<Picture> deleteByBatch(@RequestParam(value = "ids",required = false) List<String> ids){
        log.info("图片批量删除");
        if(ids.isEmpty()){
            return R.error("参数错误",Picture.defaultConstruct());
        }

        if(pictureService.removeBatchByIds(ids)){
            return R.success(Picture.defaultConstruct());
        }
        return R.error(Picture.defaultConstruct());
    }

    @GetMapping("/page")
    @ApiOperation("批量获取图片")
    public R<List<Picture>> getPictures(Integer type,String target,Integer page,Integer size){
        log.info("批量获取图片");

        List<Picture> pictures = new ArrayList<>();
        pictures.addAll(pictureService.getPictures(type, target, page, size));

        if (!pictures.isEmpty()){
            return R.success(pictures);
        }

        pictures.add(Picture.defaultConstruct());
        return R.error(pictures);
    }


    @PostMapping("/upload")
    @ApiOperation("上传图片到SM图库")
    public R<Picture> uploadFileToSM(Integer type,String target,@RequestParam(value = "file",required = false)MultipartFile file) throws IOException {
        if (file!=null){
            Map<String,String > result1 = FileUtil.uploadFile(file);
            if(result1.get("state").equals("false")){
                return R.error(result1.get("msg"),Picture.defaultConstruct());
            }else {
                Map<String,String> result2 = FileUtil.uploadFIleToSM(result1.get("msg"));
                if(result2.get("state").equals("false")){
                    return R.error(result2.get("msg"),Picture.defaultConstruct());
                }else {
                    Picture picture = new Picture();
                    picture.setTime(System.currentTimeMillis());
                    picture.setUrl(result2.get("msg"));
                    picture.setType(type);
                    picture.setTarget(target);
                    if (pictureService.save(picture)){
                        return R.success(picture);
                    }return R.error("上传失败",Picture.defaultConstruct());
                }
            }
        }
        return R.error("上传失败",Picture.defaultConstruct());
    }
}
