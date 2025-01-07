package com.hhu.myhjycommunity;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Jiemi {
    @Test
    public void jiemi() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //admin
        System.out.println(encoder.matches("admin123"
                ,"$2a$10$49YTPwiCdAYU2nP3Yuv1e.TyIQ1/J15jzMJ65rRQJiMGiML24GP.2"));
        //jt
        System.out.println(encoder.matches("jt123"
                ,"$2a$10$Vi0UXFNcOvTZIoxjtLZy6e..YlmoygjIJIQ7UJrphBtiPHZqoWA3S"));
        //test
        System.out.println(encoder.matches("123456"
                ,"$2a$10$oATgmRttyTCF2ZjBKh.4de7ii/OeEmGvWgmFSJIKgMZ3em5Iwxl7G"));

        //laoli
        System.out.println(encoder.matches("laoli123"
                ,"$2a$10$yCLCc3570FzImTiF9ezOEeB93/vKb9gkXpEtreSVUb3Ivy/h3ptQC"));

        //xxx
        System.out.println(encoder.matches("xxx123"
                ,"$2a$10$u0.ErQhwgFKrVVD1ZAbZb.9SnHE/wzBavmoOHC62gMY.WW9cd3XXi"));
    }

    @Test
    public void jiami(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("laoli123"));
    }

}
