package com.hanium.showerendorphins.showerTest;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.AromaDto;
import com.hanium.showerendorphins.dto.ShowerDto;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.enums.FeelingStatus;
import com.hanium.showerendorphins.enums.Gender;
import com.hanium.showerendorphins.enums.Note;
import com.hanium.showerendorphins.repository.AromaRepository;
import com.hanium.showerendorphins.repository.ShowerRepository;
import com.hanium.showerendorphins.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class ShowerDomainTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowerRepository showerRepository;

    @Autowired
    AromaRepository aromaRepository;


    Integer usercode = 0;

    @Before
    public void setup(){
        //given userdata
        UserDto user = UserDto.builder()
                .userId("testuser")
                .username("테스트")
                .gender(Gender.MALE)
                .age(20)
                .build();

        //given aromaData
        AromaDto aromaData = AromaDto.builder()
                .koName("라벤더")
                .enName("Lavender")
                .note(Note.MIDDLE)
                .feeling(FeelingStatus.HAPPY)
                .scent("우아함")
                .summary("아로마테라피에서 가장 폭넓게 쓰이는 오일. 피부에 직접 바를 수 있음")
                .caution("광과민성 반응(강한 햇빛에 노출될 시에 피부 자극 유발)에 주의")
                .imgURL("https://cdn.imweb.me/thumbnail/20170923/59c560f0b0783.jpg")
                .build();

        //save
        User savedUser = userRepository.save(user.toEntity());
        usercode = savedUser.getCode();
        Aroma savedData = aromaRepository.save(aromaData.toEntity());
    }

    @Test
    public void 샤워정보_저장() {
        //given
        User user = userRepository.findById(usercode).get();
        Aroma aroma = aromaRepository.findByKoName("라벤더").get();

        ShowerDto userShowerData1 = ShowerDto.builder()
                .user(user)
                .height(165.5)
                .bodyTemperature(35.6)
                .feeling(FeelingStatus.HAPPY)
                .aroma(aroma)
                .rating(3.0)
                .build();
        ShowerDto userShowerData2 = ShowerDto.builder()
                .user(user)
                .height(165.5)
                .bodyTemperature(35.6)
                .feeling(FeelingStatus.HAPPY)
                .aroma(aroma)
                .rating(4.0)
                .build();
        ShowerDto userShowerData3 = ShowerDto.builder()
                .user(user)
                .height(165.5)
                .bodyTemperature(35.6)
                .feeling(FeelingStatus.HAPPY)
                .aroma(aroma)
                .rating(5.0)
                .build();
        //when
        showerRepository.save(userShowerData1.toEntity());
        showerRepository.save(userShowerData2.toEntity());
        showerRepository.save(userShowerData3.toEntity());
        List<Shower> showerList = showerRepository.findAll();

        //then
        Assertions.assertEquals(showerList.size(), 3);

    }
}
