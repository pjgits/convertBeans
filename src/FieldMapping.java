package com.pj.blog.common.convert;

/**
 * Created by pengjie20304 on 2016/11/25.
 */
public class FieldMapping {
    private String model;

    private String pojo;

    private String dto;

    public FieldMapping(){

    }
    public FieldMapping(String model,String pojo,String dto){
        this.model = model;
        this.pojo = pojo;
        this.dto = dto;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPojo() {
        return pojo;
    }

    public void setPojo(String pojo) {
        this.pojo = pojo;
    }

    public String getDto() {
        return dto;
    }

    public void setDto(String dto) {
        this.dto = dto;
    }

    public FieldEntry model2Pojo(){
        return new FieldEntry(model,pojo);

    }

    public FieldEntry pojo2Model(){
        return new FieldEntry(pojo,model);
    }

    public FieldEntry dto2Pojo(){
        return new FieldEntry(dto,pojo);

    }

    public FieldEntry pojo2Dto(){
        return new FieldEntry(pojo,dto);
    }

    public FieldEntry model2Dto(){
        return new FieldEntry(model,dto);

    }

    public FieldEntry dto2Model(){
        return new FieldEntry(dto,model);
    }



    /**
     * 具体映射的字段名
     */
    public static class FieldEntry {
        private String ori;
        private String desc;


        public FieldEntry(String ori, String desc) {
            this.ori = ori;
            this.desc = desc;
        }

        public String getOri() {
            return ori;
        }

        public void setOri(String ori) {
            this.ori = ori;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
