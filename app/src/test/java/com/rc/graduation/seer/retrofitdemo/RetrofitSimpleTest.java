package com.rc.graduation.seer.retrofitdemo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** Test Retrofit
 * Created by rosen on 2016/10/18 0018.
 */
public class RetrofitSimpleTest {

    RetrofitSimple simple;

    @Before
    public void setUp() throws Exception {
        simple = new RetrofitSimple();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetUserRepos() throws Exception {
        simple.getUserRepos();
    }

    @Test
    public void testGetUserReposWithSort() throws Exception {
        simple.getUserReposWithSort();
    }

    @Test
    public void testGetUserReposWithMap() throws Exception {
        simple.getUserReposWithMap();
    }
}