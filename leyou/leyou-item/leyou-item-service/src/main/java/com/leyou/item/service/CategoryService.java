package com.leyou.item.service;

import com.alibaba.fastjson.JSON;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }
    @Transactional
    public void addCategory(Category category) {
        this.categoryMapper.insertSelective(category);
    }

    @Transactional
    public void deleteCategory(Integer id) {
        this.categoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void editCategory(Category category) {
        this.categoryMapper.updateByPrimaryKeySelective(category);
    }

    public List<String> queryNamesByIds(List<Long> ids) {
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
        return categories.stream().map(category ->
            category.getName()).collect(Collectors.toList());
    }
}
