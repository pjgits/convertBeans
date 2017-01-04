package com.pj.blog.common.convert;

import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pengjie20304 on 2016/11/25.
 */
public class ConvertBeanConfig {
    protected final static Character SEPARATOR = (char)31;
    
    private static Map<String,List<FieldMapping>> cache = new HashMap<String, List<FieldMapping>>();
    private static Map<String,String> convertMap = new HashMap<String, String>();

    private final static String MODEL2POJO = "model2Pojo";
    private final static String POJO2MODEL = "pojo2Model";
    private final static String MODEL2DTO = "model2Dto";
    private final static String DTO2MODEL = "dto2Model";
    private final static String POJO2DTO = "pojo2Dto";
    private final static String DTO2POJO = "dto2Pojo";

    /**
     * 每对bean需要初始化一次。
     * @param model
     * @param pojo
     * @param dto
     * @param fieldMappingList
     */
    public static void init(Class model, Class pojo, Class dto, List<FieldMapping> fieldMappingList){
        if(fieldMappingList==null){
            //// TODO: 2016/11/25 should throw exception 
            return ;
        }
        //// TODO: 2016/11/25 judge exist?
        String key1 = String.format("%s%c%s",model.getName(),SEPARATOR,pojo.getName());
        cache.put(key1,fieldMappingList);
        convertMap.put(key1,MODEL2POJO);

        String key2 = String.format("%s%c%s",pojo.getName(),SEPARATOR,model.getName());
        cache.put(key2,fieldMappingList);
        convertMap.put(key2,POJO2MODEL);

        String key3 = String.format("%s%c%s",model.getName(),SEPARATOR,dto.getName());
        cache.put(key3,fieldMappingList);
        convertMap.put(key3,MODEL2DTO);

        String key4 = String.format("%s%c%s",dto.getName(),SEPARATOR,model.getName());
        cache.put(key4,fieldMappingList);
        convertMap.put(key4,DTO2MODEL);

        String key5 = String.format("%s%c%s",pojo.getName(),SEPARATOR,dto.getName());
        cache.put(key5,fieldMappingList);
        convertMap.put(key5,POJO2DTO);

        String key6 = String.format("%s%c%s",dto.getName(),SEPARATOR,pojo.getName());
        cache.put(key6,fieldMappingList);
        convertMap.put(key6,DTO2POJO);
    }


    public static List<FieldMapping.FieldEntry> getFieldEntry(Class ori,Class dest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String key = String.format("%s%c%s",ori.getName(),SEPARATOR,dest.getName());
        String methodName = convertMap.get(key);
        Assert.notNull(methodName);

        List<FieldMapping> fieldMappingList = cache.get(key);
        List<FieldMapping.FieldEntry> resList = new ArrayList<FieldMapping.FieldEntry>(fieldMappingList.size());
        Class clazz = FieldMapping.class;
        Method method = clazz.getDeclaredMethod(methodName);

        for(FieldMapping mapping:fieldMappingList){
            resList.add((FieldMapping.FieldEntry) method.invoke(mapping));
        }
        return resList;
    }

    public static void main(String[] args) {
        System.out.println("111"+(char)31+"333");
    }

}
