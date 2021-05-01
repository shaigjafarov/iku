package az.etaskify.mapper;

import az.etaskify.dto.UserDto;
import az.etaskify.model.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void toDto() {
        User user= new User();
        user.setEmail("@mail");
        user.setName("ilku");
        user.setId(21l);
        user.setPassword("123124");
        user.setUserName("ikuas");
        List<User> singleton = Collections.singletonList(user);

        //UserMapper.INSTANCE.toDtoList(singleton).forEach(System.out::println);

    }
}