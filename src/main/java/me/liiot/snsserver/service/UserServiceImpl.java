package me.liiot.snsserver.service;

import me.liiot.snsserver.exception.NotUniqueIdException;
import me.liiot.snsserver.mapper.UserMapper;
import me.liiot.snsserver.model.User;
import me.liiot.snsserver.tool.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
@Service
: "모델에 독립된 인터페이스로 제공되는 작업"으로 정의된 서비스임을 명시
  비즈니스 로직을 처리할 클래스
*/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Override
    public void signUpUser(User user) {

        user.setPassword(passwordEncryptor.encrypt(user.getPassword()));
        userMapper.insertUser(user);
    }

    @Override
    public void checkIdDupe(String userId) throws NotUniqueIdException {
        boolean isDuplicated = userMapper.checkIdDupe(userId);

        if (isDuplicated) {
            throw new NotUniqueIdException("중복된 아이디입니다.");
        }
    }
}
