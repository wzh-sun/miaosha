package com.wzhsun.miaosha;

import com.wzhsun.miaosha.dao.UserDOMapper;
import com.wzhsun.miaosha.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication(scanBasePackages = {"com.wzhsun.miaosha"})
@RestController
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
@MapperScan("com.wzhsun.miaosha.dao")
public class MiaoshaApplication {

    @Autowired
    private UserDOMapper userDOMapper;

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaApplication.class, args);
    }

}
