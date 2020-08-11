package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.runner.RunWith;

import org.mockito.*;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

   /*@Test
    public void Should_add_User(){
        User user = new User(2,"name","lastname","pdname","ibi.game20@gmail.com","12A3456@8",false);
        Boolean isUserAdd = false;
        System.out.println(isUserAdd);
        isUserAdd = userService.addUser(user);
        System.out.println(userService.addUser(user));
        Mockito.verify(userRepository,Mockito.times(1)).save(Mockito.any(User.class));
    }
    */

    @Test
    public void Should_return_User() throws Exception {
        when(userRepository.findByEmailAndPassword("ibi.game20@gmail.com", "12A3456@8")).thenReturn(Optional.of(User.builder().build()));
        Assertions.assertThat(userService.authenticateUser("ibi.game20@gmail.com", "12A3456@8")).isNotEmpty();
    }

    @Test
    public void Should_return_UserByEmail(){
        when(userRepository.findByEmail("ibi.game20@gmail.com")).thenReturn(Optional.of(User.builder().build()));
        Assertions.assertThat(userService.findUserByEmail("ibi.game20@gmail.com")).isNotNull();
    }

    @Test
    public  void Should_VerifyEmail(){
        User user = new User(2,"name","lastname","pdname","ibi.game20@gmail.com","12A3456@8",false);
        userService.setEmailIsVerified(user);
        Mockito.verify(userRepository,Mockito.times(1)).save(Mockito.any(User.class));
    }

    private static Stream<Arguments> provideUserToAdd(){
        return Stream.of(
                Arguments.of(User.builder().build()),
                Arguments.of(User.builder().id(1).build()),
                Arguments.of(User.builder().id(2).name("name").build()),
                Arguments.of(User.builder().id(3).name("name").lastName("lastName").build()),
                Arguments.of(User.builder().id(3).name("name").lastName("lastName").pseudoName("pseudoName").build()),
                Arguments.of(User.builder().id(3).name("name").lastName("lastName").pseudoName("pseudoName").email("ibi.game20@gmail.com").build()),
                Arguments.of(User.builder().id(3).name("name").lastName("lastName").pseudoName("pseudoName").email("ibi.game20@gmail.com").password("12A3456@8").build()),
                Arguments.of(User.builder().id(3).name("name").lastName("lastName").pseudoName("pseudoName").email("ibi.game20@gmail.com").password("12A3456@8").isEmailVerified(false).build())

        );
    }
}
