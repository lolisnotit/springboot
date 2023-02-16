package com.example.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.Set;
/**
 * Spring Bootで使うユーザ情報の取得を行うクラス。
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    /**
     * ユーザ情報を取得する。
     * もし引数のユーザ情報が存在しなかったら、UsernameNotFoundExceptionを投げる。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByName(username);
        // もしユーザが見つからなかった場合、例外を投げる
        if (user == null) {
            throw new UsernameNotFoundException("User [" + username + "] not found.");
        }
        return createUser(user);
    }


    /**
     * DBから取得したユーザ情報をSpring Bootのユーザ情報に変更する。
     * @param user DBから取得したユーザ情報
     * @return Spring Bootのユーザ情報
     */
    private UserDetails createUser(com.example.demo.entity.User user) {
        Set<GrantedAuthority> auth = new HashSet<>();
        // ロールにはROLE_というプレフィックスを付ける
        auth.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        User userDetails = new User(user.getName(), user.getPassword(), auth);
        return userDetails;
    }

}