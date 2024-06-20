package team.ik.controller;

import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import team.ik.common.Result;
import team.ik.model.entity.PostCategory;
import team.ik.service.IPostCategoryService;

import java.io.Serializable;
import java.util.List;

/**
 * @author tunan
 */
@RestController
@RequestMapping("/post/category")
public class PostCategoryController {
    
    @Resource
    private IPostCategoryService postCategoryService;

    @PostMapping("save")
    public Result<Boolean> save(@RequestBody PostCategory postCategory) {
        return Result.success(postCategoryService.save(postCategory));
    }

    /**
     * 根据主键删除文章种类表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public Result<Boolean> remove(@PathVariable Serializable id) {
        return Result.success(postCategoryService.removeById(id));
    }


    /**
     * 根据主键更新文章种类表。
     *
     * @param category 文章种类表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public Result<Boolean> update(@RequestBody PostCategory category) {
        return Result.success(postCategoryService.updateById(category));
    }

    /**
     * 查询所有文章种类表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public Result<List<PostCategory>> list() {
        return Result.success(200,"查询成功",postCategoryService.list());
    }

    /**
     * 根据文章种类表主键获取详细信息。
     *
     * @param id 文章种类表主键
     * @return 文章种类表详情
     */
    @GetMapping("getInfo/{id}")
    public Result<PostCategory> getInfo(@PathVariable Serializable id) {
        return Result.success(postCategoryService.getById(id));
    }

    /**
     * 分页查询文章种类表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Result<Page<PostCategory>> page(Page<PostCategory> page) {
        return Result.success(postCategoryService.page(page));
    }
    
}
