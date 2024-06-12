package org.example.mybatissample.handler.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.example.mybatissample.model.enums.AccountType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(AccountType.class)
public class AccountTypeHandler implements TypeHandler<AccountType> {

    @Override
    public void setParameter(PreparedStatement ps, int i, AccountType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public AccountType getResult(ResultSet rs, String columnName) throws SQLException {
        String accountTypeCode = rs.getString(columnName);
        return getAccountType(accountTypeCode);
    }

    @Override
    public AccountType getResult(ResultSet rs, int columnIndex) throws SQLException {
        String accountTypeCode = rs.getString(columnIndex);
        return getAccountType(accountTypeCode);
    }

    @Override
    public AccountType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String accountTypeCode = cs.getString(columnIndex);
        return getAccountType(accountTypeCode);
    }

    private AccountType getAccountType(String accountTypeCode) {
        if (accountTypeCode == null) {
            return null;
        }

        switch (accountTypeCode) {
            case "USER":
                return AccountType.USER;
            case "MANAGER":
                return AccountType.MANAGER;
            default:
                return null;
        }
    }
}

