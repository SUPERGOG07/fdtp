package com.kawai.fdtp.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import java.lang.reflect.Method;
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

}
