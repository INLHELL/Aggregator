package com.genesys.raa.agg.definition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class SqlMetaData implements UserType, Serializable {
    private static final long serialVersionUID = 1L;
    private static final int[] SQL_TYPES = new int[] {  Types.CLOB };
    private int sqlType = Types.CLOB; // before any guessing
    private boolean mutable;

    private Class<?> classType;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private List<ColumnMetaData> columns;
    private List<ArgumentMetaData> arguments;


    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        Object copy = null;
        if (value != null) {

            try {
                return MAPPER.readValue(MAPPER.writeValueAsString(value), value.getClass());
            } catch (IOException e) {
                throw new HibernateException("unable to deep copy object", e);
            }
        }
        return copy;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new HibernateException("unable to disassemble object", e);
        }
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equal(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public boolean isMutable() {
        return mutable;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException,
            SQLException {
        Object obj = null;
        if (!rs.wasNull()) {
            if (this.sqlType == Types.CLOB || this.sqlType == Types.BLOB) {
                byte[] bytes = rs.getBytes(names[0]);
                if (bytes != null) {
                    try {
                        obj = MAPPER.readValue(bytes, this.classType);
                    } catch (IOException e) {
                        throw new HibernateException("unable to read object from result set", e);
                    }
                }
            } else {
                try {
                    String content = rs.getString(names[0]);
                    if (content != null) {
                        obj = MAPPER.readValue(content, this.classType);
                    }
                } catch (IOException e) {
                    throw new HibernateException("unable to read object from result set", e);
                }
            }
        }
        return obj;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException,
            SQLException {
        if (value == null) {
            st.setNull(index, this.sqlType);
        } else {

            if (this.sqlType == Types.CLOB || this.sqlType == Types.BLOB) {
                try {
                    st.setBytes(index, MAPPER.writeValueAsBytes(value));
                } catch (JsonProcessingException e) {
                    throw new HibernateException("unable to set object to result set", e);
                }
            } else {
                try {
                    st.setString(index, MAPPER.writeValueAsString(value));
                } catch (JsonProcessingException e) {
                    throw new HibernateException("unable to set object to result set", e);
                }
            }
        }
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return this.deepCopy(original);
    }

    @Override
    public Class<?> returnedClass() {
        return this.classType;
    }

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }
}
