package com.hiscene.retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by junhu on 2019-10-23
 * aop 拦截方法的存储封装类
 */
public class ServiceMethod {
    private final Builder builder;

    private ServiceMethod(Builder builder) {
        this.builder = builder;
    }

    public OKHttpCall toCall(Object[] args) {
        //将网络请求的所有参数args，拼装
        RequestBuilder requestBuilder = new RequestBuilder(builder.httpMethod,builder.retrofit.getBaseUrl(),builder.pathUrl,builder.hasBody);
        ParameterHandler[] handlers = this.builder.parameterHandler;

        int argsNum = args!=null?args.length:0;
        if (argsNum!=handlers.length){
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < argsNum; i++) {
            handlers[i].apply(requestBuilder,args[i].toString());
        }
//        Call call =builder.retrofit.getCallFactory().newCall(requestBuilder.build());
        OKHttpCall okHttpCall = new OKHttpCall(this,args);
        return okHttpCall;
    }

    static final class Builder {
        private final Retrofit retrofit;
        private final Method method;

        final Annotation[][] paramsAnnotationArray;
        final Annotation[] methodAnnotation;

        private String httpMethod;
        private boolean hasBody;
        private String pathUrl;

        private ParameterHandler[] parameterHandler;

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            this.methodAnnotation = method.getAnnotations();
            this.paramsAnnotationArray = method.getParameterAnnotations();
        }

        public ServiceMethod build() {
            //遍历方法的每个注解
            for (Annotation annotation : methodAnnotation) {
                parseMethodAnnotation(annotation);
            }

            int parameterCount = paramsAnnotationArray.length;
            parameterHandler = new ParameterHandler[parameterCount];
            for (int i = 0; i < parameterCount; i++) {
                //获取每个参数的所有注解
                Annotation[] parameterAnnotations = paramsAnnotationArray[i];
                //如果开发者弄的类方法，没有任何注解，则可能为null
                if (parameterAnnotations == null) {
                    throw new IllegalArgumentException("Retofit annotation not fount");
                }
                parameterHandler[i] = parseParameter(parameterAnnotations);
            }
            return new ServiceMethod(this);
        }

        private ParameterHandler parseParameter(Annotation[] parameterAnnotations) {
            //解析参数的所有注解
            ParameterHandler result = null;
            for (Annotation annotation : parameterAnnotations) {
                ParameterHandler parameterHandler = parseParameterAnnotation(annotation);
                if (parameterHandler==null){
                    continue;
                }
                result = parameterHandler;
            }
            return result;
        }

        private ParameterHandler parseParameterAnnotation(Annotation annotation) {
            if (annotation instanceof Query){
                Query query = (Query) annotation;
                String name = query.value();
                return new ParameterHandler.Query(name);

            }else if (annotation instanceof Field){
                Field query = (Field) annotation;
                String name = query.value();
                return new ParameterHandler.Field(name);
            }
            return null;
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            }
        }

        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            this.httpMethod = httpMethod;
            this.hasBody = hasBody;
            this.pathUrl = value;
        }
    }
}
