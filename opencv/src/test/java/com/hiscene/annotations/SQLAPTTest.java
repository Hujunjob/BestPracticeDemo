package com.hiscene.annotations;

import com.hiscene.opencv.Member;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hujun on 2019/4/2.
 */

public class SQLAPTTest {

    @Test
    public void createTableSql() {
        try {
            String sql = SQLAPT.createTableSql(Member.class.getName());
            System.out.println("sql:"+sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}