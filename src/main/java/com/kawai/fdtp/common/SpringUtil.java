package com.kawai.fdtp.common;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.*;



@Component
@Slf4j
public class SpringUtil {
    public static Map<String, Set<String>> roles = new HashMap<>();

    public static void setRoles(ApplicationContext applicationContext){
        //获得所有controller
        Map<String,Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);
        for (Map.Entry<String,Object> entry : controllers.entrySet()){  //遍历所有controller
            Class<?> theClass = AopUtils.getTargetClass(entry.getValue());

            String parentPath = theClass.getDeclaredAnnotation(RequestMapping.class).value()[0];    // 获得controller母路径
            Set<String> parentRoles = new HashSet<>();
            if(theClass.isAnnotationPresent(HasRole.class)){    //加入母路径所需权限
                parentRoles.addAll(Arrays.asList(theClass.getDeclaredAnnotation(HasRole.class).value()));
                roles.put(parentPath,parentRoles);
            }

            List<Method> methods = Arrays.asList(theClass.getMethods());
            for (Method method : methods) {
                Set<String> childRoles = new HashSet<>();
                String path = null;
                if(method.isAnnotationPresent(HasRole.class)){  //加入子路径所需角色
                    childRoles.addAll(Arrays.asList(method.getDeclaredAnnotation(HasRole.class).value()));

                    if (!method.getDeclaredAnnotation(HasRole.class).path().isEmpty()){
                        path = parentPath+method.getDeclaredAnnotation(HasRole.class).path();
                    }else {
                        if(method.isAnnotationPresent(GetMapping.class)){
                            path = parentPath+method.getDeclaredAnnotation(GetMapping.class).value()[0];
                        }
                        else if(method.isAnnotationPresent(PostMapping.class)){
                            path = parentPath+method.getDeclaredAnnotation(PostMapping.class).value()[0];
                        }
                        else if(method.isAnnotationPresent(PutMapping.class)){
                            path = parentPath+method.getDeclaredAnnotation(PutMapping.class).value()[0];
                        }
                        else if(method.isAnnotationPresent(DeleteMapping.class)){
                            path = parentPath+method.getDeclaredAnnotation(DeleteMapping.class).value()[0];
                        }
                    }

                }

                childRoles.addAll(parentRoles);

                if(path!=null){
                    log.info(path);
                    roles.put(path,childRoles);
                }

            }
        }
    }

    public static boolean checkUrl(String url,String role){
        if(roles.get(url)!=null){
            if (roles.get(url).contains(role)){
                return true;
            }
        } else if (roles.get("/"+url.split("/")[1])!=null) {
            if(roles.get("/"+url.split("/")[1]).contains(role)){
                return true;
            }
        }
        return false;
    }

    @Bean
    public static String getSMToken(){

        String username = "superdog07";
        String password = "zxc.12345";

        String token = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try{
            HttpPost httpPost = new HttpPost("https://smms.app/api/v2/token");

            httpPost.addHeader("accept","*/*");
            httpPost.addHeader("connection","Keep-Alive");
            httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.66 Safari/537.36 Edg/103.0.1264.44");



            httpPost.setEntity(MultipartEntityBuilder.create()
                    .addTextBody("username",username)
                    .addTextBody("password",password)
                    .build());

            CloseableHttpResponse response = httpClient.execute(httpPost);


            if (response.getStatusLine().getStatusCode()==200){
                HttpEntity entity = response.getEntity();
                if (entity!=null){
                    String entityJson = EntityUtils.toString(entity, Charset.defaultCharset());
                    token = JSONObject.parseObject(entityJson).getObject("data", JSONObject.class).getString("token");
                    log.info("token-->{}",token);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return token;
    }


}
