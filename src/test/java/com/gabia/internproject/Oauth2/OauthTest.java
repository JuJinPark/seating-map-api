package com.gabia.internproject.Oauth2;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OauthTest {


//        @Before
//        public void setup() {
//            RestAssured.baseURI = "http://localhost";
//            RestAssured.port = 8080;
//        }
//
//        @Test
//        public void hiworks로그인_시도하면_OAuth인증창_등장한다 () throws Exception {
//            given()
//                    .when()
//                    .redirects().follow(false) // 리다이렉트 방지
//                    .get("/login/hiworks")
//                    .then()
//                    .statusCode(302)
//                    .header("Location", containsString("https://api.hiworks.com/open/auth/authform"));
//        }

}
