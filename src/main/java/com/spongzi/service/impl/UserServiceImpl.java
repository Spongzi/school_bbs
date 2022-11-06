package com.spongzi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongzi.domain.dto.UserRegister;
import com.spongzi.domain.vo.UserVo;
import com.spongzi.exception.BlogException;
import com.spongzi.exception.BlogExceptionEnum;
import com.spongzi.interceptor.UserHolder;
import com.spongzi.service.UserService;
import com.spongzi.domain.User;
import com.spongzi.mapper.UserMapper;
import com.spongzi.utlis.SendEmail;
import com.spongzi.utlis.TokenUtil;
import lombok.extern.java.Log;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.spongzi.constant.UserConstant.*;

/**
 * @author spongzi
 * @description 针对表【user(用户表信息)】的数据库操作Service实现
 * @createDate 2022-11-04 12:31:48
 */
@Log
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private SendEmail sendEmail;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String loginByPassword(String username, String email, String password) {
        if (StringUtils.isEmpty(password) || password.length() < PASSWORD_SHORT_LENGTH) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        log.info("test");
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        log.info(String.valueOf(StringUtils.isBlank(username)));
        userWrapper.eq(!StringUtils.isBlank(username), User::getUsername, username);
        userWrapper.eq(!StringUtils.isBlank(email), User::getEmail, email);
        User user = userMapper.selectOne(userWrapper);
        if (user == null) {
            throw new BlogException(BlogExceptionEnum.USER_NOT_EXIST);
        }
        log.info("user info" + user);

        String dbPassword = user.getPassword();

        // 对密码进行加密
        password = encryptionPassword(password, user.getUsername());

        // 判断密码是否和数据库查询出来的密码相同
        if (!dbPassword.equals(password)) {
            throw new BlogException(BlogExceptionEnum.USER_PASSWORD_ERROR);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", generateToken(user));
        map.put("user", getSafeUser(user));
        return generateToken(user);
    }

    @Override
    public String loginByCheckCode(String email, String password) {
        if (StringUtils.isEmpty(email)) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getEmail, email);
        User user = userMapper.selectOne(userWrapper);
        if (user == null) {
            throw new BlogException(BlogExceptionEnum.USER_NOT_EXIST);
        }
        if (USER_FREEZE.equals(user.getUserStatus())) {
            throw new BlogException(BlogExceptionEnum.USER_FREEZE);
        }
        String checkCode = (String) redisTemplate.opsForValue().get(CHECK_CODE + email);
        if (!Objects.equals(checkCode, password)) {
            throw new BlogException(BlogExceptionEnum.USER_PASSWORD_ERROR);
        }
        return generateToken(user);
    }

    @Override
    public String registerByPhoneCode(UserRegister userRegister) {
        String username = userRegister.getUsername();
        String password = userRegister.getPassword();
        String phone = userRegister.getPhone();
        String code = userRegister.getCode();

        // 校验是否包含特殊字符 m
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(username);
        // 校验数据合理
        if (StringUtils.isBlank(username) || username.length() < USERNAME_SHORT_LENGTH || m.find()) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        if (StringUtils.isBlank(password) || password.length() < PASSWORD_SHORT_LENGTH) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        if (StringUtils.isBlank(phone) || phone.length() != PHONE_LENGTH) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        if (StringUtils.isBlank(code) || code.length() != CHECK_CODE_LENGTH) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }

        // 从redis中获取对应的code
        String checkCode = (String) redisTemplate.opsForValue().get(CHECK_CODE + phone);
        if (!code.equals(checkCode)) {
            throw new BlogException(BlogExceptionEnum.USER_PHONE_CODE_ERROR);
        }

        // 检查数据库中是否存在该用户，如果存在就提示用户已存在
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(userWrapper);
        if (user != null) {
            throw new BlogException(BlogExceptionEnum.USER_EXIST_ERROR);
        }
        userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getPhone, phone);
        user = userMapper.selectOne(userWrapper);
        if (user != null) {
            throw new BlogException(BlogExceptionEnum.USER_EXIST_ERROR);
        }

        User dbUser = new User();
        dbUser.setUsername(username);
        dbUser.setPassword(encryptionPassword(password, username));
        dbUser.setPhone(phone);

        userMapper.insert(dbUser);
        return "注册成功";
    }

    @Override
    public String register(UserRegister userRegister) {
        String username = userRegister.getUsername();
        String password = userRegister.getPassword();
        String email = userRegister.getEmail();
        String code = userRegister.getCode();

        // 校验是否包含特殊字符 m
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(username);
        // 校验数据合理
        if (StringUtils.isBlank(username) || username.length() < USERNAME_SHORT_LENGTH || m.find()) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        if (StringUtils.isBlank(password) || password.length() < PASSWORD_SHORT_LENGTH) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        if (StringUtils.isBlank(code) || code.length() != CHECK_CODE_LENGTH) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }

        // 从redis中获取对应的code
        String checkCode = (String) redisTemplate.opsForValue().get(CHECK_CODE + email);
        if (!code.equals(checkCode)) {
            throw new BlogException(BlogExceptionEnum.USER_PHONE_CODE_ERROR);
        }

        // 检查数据库中是否存在该用户，如果存在就提示用户已存在
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(userWrapper);
        if (user != null) {
            throw new BlogException(BlogExceptionEnum.USER_EXIST_ERROR);
        }
        userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getEmail, email);
        user = userMapper.selectOne(userWrapper);
        if (user != null) {
            throw new BlogException(BlogExceptionEnum.USER_EXIST_ERROR);
        }

        User dbUser = new User();
        dbUser.setUsername(username);
        dbUser.setPassword(encryptionPassword(password, username));
        dbUser.setEmail(email);

        userMapper.insert(dbUser);
        return "注册成功";
    }

    @Override
    public void sendMsg(String phone) {
        // 根据手机号查询用户, 如果用户存在, 判断是否被冻结
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
        if (user != null && USER_FREEZE.equals(user.getUserStatus())) {
            throw new BlogException(BlogExceptionEnum.USER_FREEZE);
        }
        // 1. 随机生成6位数字
        // String code = RandomStringUtils.randomNumeric(6);
        // 2. 调用template对象, 发送短信
        // smsTemplate.sendSms(phone, code);
        // 3. 将验证码存入redis
        String code = "123456";
        redisTemplate.opsForValue().set(CHECK_CODE + phone, code, Duration.ofMinutes(5));
    }

    @Override
    public void sendEmailMsg(String email) {
        // 根据邮箱查询用户, 如果用户存在, 判断是否被冻结
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (user != null && USER_FREEZE.equals(user.getUserStatus())) {
            throw new BlogException(BlogExceptionEnum.USER_FREEZE);
        }

        // 生成验证码
        String code = RandomStringUtils.randomNumeric(CHECK_CODE_LENGTH);

        // 发送验证码
        sendEmail.sendEmail(email, code);

        // 将验证码存入缓存
        redisTemplate.opsForValue().set(CHECK_CODE + email, code, Duration.ofMinutes(5));
    }

    public String encryptionPassword(String password, String username) {
        return DigestUtils.md5DigestAsHex((password + password + username)
                .getBytes(StandardCharsets.UTF_8));
    }

    private String generateToken(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());
        return TokenUtil.getToken(map);
    }

    @Override
    public UserVo getSafeUser(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setGender(user.getGender());
        userVo.setAddress(user.getAddress());
        userVo.setPhone(user.getPhone());
        userVo.setEmail(user.getEmail());
        userVo.setRole(user.getRole());
        userVo.setAge(user.getAge());
        userVo.setUserStatus(user.getUserStatus());
        userVo.setUpdateTime(user.getUpdateTime());
        userVo.setCreateTime(user.getCreateTime());
        userVo.setIsDeleted(user.getIsDeleted());
        return userVo;
    }

    @Override
    public UserVo info(Long id) {
        User user = userMapper.selectById(id);
        return getSafeUser(user);
    }
}




