package com.kawai.fdtp.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    public static final String SECRET = "kawa!fdtp";

    /**
     * 解析jwt
     * @param token
     * @return
     */
    public static Map<String ,String > parseAccessToken(String token) {

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);

        Map<String,String> map = new HashMap<>();

        map.put("user",decodedJWT.getClaim("user").asString());
        map.put("role",decodedJWT.getClaim("role").asString());

        return map;
    }

}
