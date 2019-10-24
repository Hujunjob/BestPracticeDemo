package com.hiscene.retrofit;

/**
 * Created by junhu on 2019-10-23
 *
 * 抽象类的自我实现类
 */
public abstract class ParameterHandler {
    abstract void apply(RequestBuilder builder,String value);

    static final class Query extends ParameterHandler{
        //参数名
        private String name;

        public Query(String name) {
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            if (value==null){
                return;
            }
            builder.addQueryParam(name,value);
        }
    }


    static final class Field extends ParameterHandler{
        //参数名
        private String name;

        public Field(String name) {
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            if (value==null){
                return;
            }
            builder.addFieldParam(name,value);
        }
    }
}
