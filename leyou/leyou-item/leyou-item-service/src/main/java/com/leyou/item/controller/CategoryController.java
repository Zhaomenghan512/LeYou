package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点的ID查询子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid) {
        if (pid == null || pid <0) {
            //响应400 - 参数不合法
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
        if (CollectionUtils.isEmpty(categories)) {
            ///响应404 - 资源服务器未找到
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.notFound().build();
        }
        //响应200 - 查询成功
        return ResponseEntity.ok(categories);
    }

    @PostMapping("add")
    public ResponseEntity<Void> addCategory(Category node) {
        System.out.println(node);
        this.categoryService.addCategory(node);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("delete")
    public ResponseEntity<Void> deleteCategory(@RequestParam("id") Integer id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("edit")
    public ResponseEntity<Void> editCategory(Category category) {
        this.categoryService.editCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
