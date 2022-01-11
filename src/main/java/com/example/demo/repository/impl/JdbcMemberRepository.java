package com.example.demo.repository.impl;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO MEMBER(NAME) VALUES(?)";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, member.getName());
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();

            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("Failed to generate id.");
            }

            return member;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstm, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM MEMBER WHERE ID = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();

            if (rs.next()) {
                Member member = mapping(rs);
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstm, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM MEMBER";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            List<Member> memberList = new ArrayList<>();
            while (rs.next()) {
                Member member = mapping(rs);
                memberList.add(member);
            }

            return memberList;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstm, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "SELECT * FROM MEMBER WHERE NAME = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, name);
            rs = pstm.executeQuery();

            if (rs.next()) {
                Member member = mapping(rs);
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstm, rs);
        }
    }

    @Override
    public void delete(Member member) {
        deleteById(member.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM MEMBER WHERE ID = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setLong(1, id);
            int result = pstm.executeUpdate();

            if (result == 0) {
                throw new SQLException("Failed to delete a row by id.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstm, rs);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM MEMBER";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstm = conn.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstm, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (conn != null) {
            close(conn);
        }
    }

    private void close(Connection conn) {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

    private Member mapping(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getLong("ID"));
        member.setName(rs.getString("NAME"));
        return member;
    }
}
