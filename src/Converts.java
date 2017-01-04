package com.pj.blog.common.convert;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pengjie20304 on 2016/11/25.
 */
public class Converts {
    private final static String GET_METHOD_HEAD = "get";
    private final static String SET_METHOD_HEAD = "set";
    private final static String IS_METHOD_HEAD = "is";

    private final static Map<String,Class> METHOD_TYPE = new HashMap<String, Class>(){{
        put("int",Integer.class);
        put("long",Long.class);
        put("double",Double.class);
        put("float",Float.class);
        put("char", Character.class);
        put("short",Short.class);
        put("byte",Byte.class);
        put("boolean",Boolean.class);
        put("java.lang.Integer", int.class);
        put("java.lang.Long",long.class);
        put("java.lang.Double",double.class);
        put("java.lang.Float",float.class);
        put("java.lang.Character",char.class);
        put("java.lang.Short",short.class);
        put("java.lang.Byte",byte.class);
        put("java.lang.Boolean",boolean.class);
    }};
    public static void  convert(Object ori,Object dest){
        Assert.notNull(ori);
        Assert.notNull(dest);
        Class oriClazz = ori.getClass();
        Class destClazz = dest.getClass();

        try {
            List<FieldMapping.FieldEntry> fieldEntryList = ConvertBeanConfig.getFieldEntry(oriClazz,destClazz);
            Method getMethod = null;
            Method setMethod = null;
            Object data = null;
            String oriField = null;
            String destField = null;
            String getMethodHead = null;
            for(FieldMapping.FieldEntry entry:fieldEntryList){
                oriField = entry.getOri();
                destField = entry.getDesc();

                if(StringUtils.isEmpty(oriField) || StringUtils.isEmpty(destField)){
                    continue;
                }
                Field field = oriClazz.getDeclaredField(oriField);
                getMethodHead = field.getType().equals(boolean.class)?IS_METHOD_HEAD:GET_METHOD_HEAD;
                getMethod = oriClazz.getDeclaredMethod(getMethodName(getMethodHead,oriField));
                data = getMethod.invoke(ori);
                if(null == data){
                    continue;
                }
                try {
                    setMethod = destClazz.getDeclaredMethod(getMethodName(SET_METHOD_HEAD,destField),getMethod.getReturnType());
                }catch (NoSuchMethodException e){
                    Class type = METHOD_TYPE.get(getMethod.getReturnType().getName());
                    if(type==null){
                        throw e;
                    }
                    setMethod = destClazz.getDeclaredMethod(getMethodName(SET_METHOD_HEAD,destField),type);
                }
                setMethod.invoke(dest, new Object[] { data });// 调用 set
            }

        } catch (Exception e) {
            //// TODO: 2016/11/25 deal  exception
            e.printStackTrace();
            throw new RuntimeException("meet some exception!!!");
        }

    }

    private static String getMethodName(String methodHead,String field){
        field = field.trim();
        StringBuilder sb = new StringBuilder(methodHead);
        sb.append(String.valueOf(field.charAt(0)).toUpperCase())
                .append(field.substring(1));
        return sb.toString();
    }
    public static void main(String[] args) {
       /* String s = "name";
        StringBuilder sb = new StringBuilder("get");
        sb.append(String.valueOf(s.charAt(0)).toUpperCase())
                .append(s.substring(1));
        System.out.println(sb.toString());*/
        try {
            System.out.println(int.class);;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
