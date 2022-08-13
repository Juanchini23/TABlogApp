package com.blog.Apiblog.utilerias;

import com.blog.Apiblog.ApiBlogApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEnconderGenerator {

    public static void main(String[] args) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("password"));
        //$2a$10$0JyZKUKCF55k6YoK6z82m.CgqMhlMsWfFC.bc5E9pLYcRz0R2iu.S
    }
}
