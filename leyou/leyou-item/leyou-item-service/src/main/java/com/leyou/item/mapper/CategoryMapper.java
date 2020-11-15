package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {
    @Update("update tb_category set name = #{name} where id = #{id}")
    void updateNameById(Long id, String name);
}
