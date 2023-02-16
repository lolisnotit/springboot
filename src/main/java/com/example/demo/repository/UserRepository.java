package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {/**
 * 名前でユーザを検索。
 * 命名規則に従ってインタフェースでメソッドを定義すると、
 * JPAが実装を行ってくれる。
 * @param name 検索するユーザ名
 * @return ユーザ名に対応するユーザ
 */
User findByName(String username);

}