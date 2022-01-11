package com.example.demo.repository;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"com.example.demo.repository.impl"})
class MemberRepositoryTest {
}