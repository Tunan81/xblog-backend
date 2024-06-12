package team.ik.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import team.ik.mapper.PostCategoryMapper;
import team.ik.model.entity.PostCategory;
import team.ik.service.IPostCategoryService;

@Service
public class PostCategoryServiceImpl extends ServiceImpl<PostCategoryMapper, PostCategory> implements IPostCategoryService {
}
