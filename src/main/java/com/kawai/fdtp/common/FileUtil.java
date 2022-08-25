package com.kawai.fdtp.common;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class FileUtil {



    /**
     * 获得项目临时文件夹位置路径
     * @return
     * @throws IOException
     */
    public static String getFileBasePath() throws IOException {
        File file = new File("");

        String path = file.getCanonicalPath() + "\\src\\main\\resources\\templates";

        File dir = new File(path);
        if(!dir.exists()) dir.mkdir();

        return path;
    }

    /**
     * 将上传的文件存入临时文件夹里，然后用uuid随机命名文件
     * 返回操作结果map，如果成功存入，则返回成功状态和文件名，操作失败则返回失败状态和失败原因
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<String,String > uploadFile(MultipartFile file) throws IOException {
        Map<String,String> result = new HashMap<>();
        String basePath = null;
        try {
            basePath = getFileBasePath();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("获取文件路径出错");
            result.put("code","false");
            result.put("msg","获取文件路径出错");
        }

        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String newName = UUID.randomUUID().toString() + suffix;

        try {
            file.transferTo(new File(basePath + "\\" + newName));

            result.put("state","success");
            result.put("msg",newName);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("state","false");
            result.put("msg","上传失败");
            deleteFile(newName);
        }

        return result;
    }

    /**
     * 删除临时文件夹下的特定名称文件
     * @param fileName
     * @throws IOException
     */
    public static void deleteFile(String fileName) throws IOException{
        File file = new File(new File("").getCanonicalPath()
                +"\\src\\main\\resources\\templates\\"+fileName);
        if(!file.exists()){
            log.info("文件不存在-->"+fileName);
        }else {
            file.delete();
            log.info("删除成功-->"+fileName);
        }
    }


    /**
     * 上传临时文件夹的特定文件到SM图库里
     * 返回结果：成功返回成功状态以及文件url，失败则返回失败状态以及失败信息
     * @param fileName
     * @return
     */
    public static Map<String,String> uploadFIleToSM(String fileName){
        Map<String ,String> result = new HashMap<>();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try{
            File file = new File(new File("").getCanonicalPath()
                    +"\\src\\main\\resources\\templates\\"+fileName);

            HttpPost httpPost = new HttpPost("https://smms.app/api/v2/upload");

            httpPost.addHeader("Authorization",SpringUtil.getSMToken());
            httpPost.addHeader("accept","*/*");
            httpPost.addHeader("connection","Keep-Alive");
            httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.66 Safari/537.36 Edg/103.0.1264.44");

            FileBody fileBody = new FileBody(file);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("smfile",fileBody).build();
            httpPost.setEntity(reqEntity);
            log.info("executing request " + httpPost.getRequestLine());
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                log.info("----------------------------------------");
                log.info(response.getStatusLine().toString());
                if(response.getStatusLine().getStatusCode()!=200){
                    result.put("state","false");
                    result.put("msg","接入图床失败");
                }
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String entityJson = EntityUtils.toString(resEntity, Charset.defaultCharset());
                    log.info(entityJson);
                    String url = JSONObject.parseObject(entityJson)
                            .getObject("data", JSONObject.class).getString("url");
                    log.info(url);
                    result.put("state","success");
                    result.put("msg",url);
                }

                EntityUtils.consume(resEntity);
            }  finally {
                log.info("----------------------------------------");
                FileUtil.deleteFile(fileName);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result.put("state","false");
            result.put("msg","接入图床失败");
        } catch (IOException e) {
            e.printStackTrace();
            result.put("state","false");
            result.put("msg","接入图床失败");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
