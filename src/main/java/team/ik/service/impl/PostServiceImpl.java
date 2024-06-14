package team.ik.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import team.ik.common.HttpCodeEnum;
import team.ik.exception.BusinessException;
import team.ik.exception.ThrowUtils;
import team.ik.mapper.PostFavoriteMapper;
import team.ik.mapper.PostThumbMapper;
import team.ik.model.dto.post.PostAdminQueryRequest;
import team.ik.model.dto.post.PostQueryRequest;
import team.ik.model.entity.Post;
import team.ik.mapper.PostMapper;
import team.ik.model.entity.PostFavorite;
import team.ik.model.entity.PostThumb;
import team.ik.model.entity.User;
import team.ik.model.vo.UserVO;
import team.ik.model.vo.search.PostVO;
import team.ik.service.IPostService;
import org.springframework.stereotype.Service;
import team.ik.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 帖子表 服务层实现。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@Slf4j
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
    @Resource
    private UserService userService;

    @Resource
    private PostThumbMapper postThumbMapper;
    @Resource
    private PostFavoriteMapper postFavourMapper;


    @Override
    public void validPost(Post post, boolean add) {
        if (post == null) {
            throw new BusinessException(HttpCodeEnum.PARAMS_ERROR);
        }
        String title = post.getTitle();
        String content = post.getContent();
        String tags = post.getTags();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content), HttpCodeEnum.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(HttpCodeEnum.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(HttpCodeEnum.PARAMS_ERROR, "内容过长");
        }
    }

    @Override
    public PostVO getPostVO(Post post, HttpServletRequest request) {
        PostVO postVO = PostVO.objToVo(post);
        long postId = post.getId();
        // 1. 关联查询用户信息
        Long userId = post.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        postVO.setUser(userVO);
        // 2. 已登录，获取用户点赞、收藏状态
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            // 获取点赞
            QueryWrapper postThumbQueryWrapper = new QueryWrapper();
            postThumbQueryWrapper.in("post_id", postId);
            postThumbQueryWrapper.eq("user_id", loginUser.getId());
            PostThumb postThumb = postThumbMapper.selectOneByQuery(postThumbQueryWrapper);
            postVO.setHasThumb(postThumb != null);
            // 获取收藏
            QueryWrapper postFavourQueryWrapper = new QueryWrapper();
            postFavourQueryWrapper.in("post_id", postId);
            postFavourQueryWrapper.eq("user_id", loginUser.getId());
            PostFavorite postFavour = postFavourMapper.selectOneByQuery(postFavourQueryWrapper);
            postVO.setHasFavour(postFavour != null);
        }
        return postVO;
    }

    @Override
    public QueryWrapper getQueryWrapper(PostQueryRequest postQueryRequest) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (postQueryRequest == null) {
            return queryWrapper;
        }
        String searchText = postQueryRequest.getSearchText();
        Long id = postQueryRequest.getId();
        String title = postQueryRequest.getTitle();
        String content = postQueryRequest.getContent();
        List<String> tagList = postQueryRequest.getTags();
        Long userId = postQueryRequest.getUserId();
        Long notId = postQueryRequest.getNotId();
        // 拼接查询条件
        if (StringUtils.isNotBlank(searchText)) {
            //queryWrapper.like("title", searchText).or().like("content", searchText);
            queryWrapper.like("title", searchText);
        }
        queryWrapper.like("title", title, StringUtils.isNotBlank(title));
        queryWrapper.like("content", content, StringUtils.isNotBlank(content));
        if (CollectionUtils.isNotEmpty(tagList)) {
            for (String tag : tagList) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.ne("id", notId, ObjectUtils.isNotEmpty(notId));
        queryWrapper.eq("id", id, ObjectUtils.isNotEmpty(id));
        queryWrapper.eq("userId", userId, ObjectUtils.isNotEmpty(userId));
        queryWrapper.eq("isDelete", false);
//        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
//                sortField);
        return queryWrapper;
    }

    @Override
    public Page<PostVO> getPostVOPage(Page<Post> postPage, HttpServletRequest request) {
        List<Post> postList = postPage.getRecords();
        Page<PostVO> postVOPage = new Page<>(postPage.getPageNumber(), postPage.getPageSize(), postPage.getTotalRow());
        if (CollectionUtils.isEmpty(postList)) {
            return postVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = postList.stream().map(Post::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 已登录，获取用户点赞、收藏状态
        Map<Long, Boolean> postIdHasThumbMap = new HashMap<>();
        Map<Long, Boolean> postIdHasFavourMap = new HashMap<>();
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            Set<Long> postIdSet = postList.stream().map(Post::getId).collect(Collectors.toSet());
            loginUser = userService.getLoginUser(request);
            // 获取点赞
            QueryWrapper postThumbQueryWrapper = new QueryWrapper();
            postThumbQueryWrapper.in("post_id", postIdSet);
            postThumbQueryWrapper.eq("user_id", loginUser.getId());
            List<PostThumb> postPostThumbList = postThumbMapper.selectListByQuery(postThumbQueryWrapper);
            postPostThumbList.forEach(postPostThumb -> postIdHasThumbMap.put(postPostThumb.getPostId(), true));
            // 获取收藏
            QueryWrapper postFavourQueryWrapper = new QueryWrapper();
            postFavourQueryWrapper.in("post_id", postIdSet);
            postFavourQueryWrapper.eq("user_id", loginUser.getId());
            List<PostFavorite> postFavourList = postFavourMapper.selectListByQuery(postFavourQueryWrapper);
            postFavourList.forEach(postFavour -> postIdHasFavourMap.put(postFavour.getPostId(), true));
        }
        // 填充信息
        List<PostVO> postVOList = postList.stream().map(post -> {
            PostVO postVO = PostVO.objToVo(post);
            Long userId = post.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            postVO.setUser(userService.getUserVO(user));
            postVO.setHasThumb(postIdHasThumbMap.getOrDefault(post.getId(), false));
            postVO.setHasFavour(postIdHasFavourMap.getOrDefault(post.getId(), false));
            return postVO;
        }).collect(Collectors.toList());
        postVOPage.setRecords(postVOList);
        return postVOPage;
    }

    @Override
    public Page<PostVO> listPostVOByPage(PostQueryRequest postQueryRequest, HttpServletRequest request) {
        long current = postQueryRequest.getPageNumber();
        long pageSize = postQueryRequest.getPageSize();
        Page<Post> postPage = this.page(new Page<>(current, pageSize),
                this.getQueryWrapper(postQueryRequest));
        return this.getPostVOPage(postPage, request);
    }

//    /**
//     *  从 ES 查询
//     * @param postQueryRequest 查询条件
//     * @return 分页结果
//     */
//    @Override
//    public Page<Post> searchFromEs(PostQueryRequest postQueryRequest) {
//        Long id = postQueryRequest.getId();
//        Long notId = postQueryRequest.getNotId();
//        String searchText = postQueryRequest.getSearchText();
//        String title = postQueryRequest.getTitle();
//        String content = postQueryRequest.getContent();
//        List<String> tagList = postQueryRequest.getTags();
//        List<String> orTagList = postQueryRequest.getOrTags();
//        Long userId = postQueryRequest.getUserId();
//        // es 起始页为 0
//        long current = postQueryRequest.getPageNumber() - 1;
//        long pageSize = postQueryRequest.getPageSize();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        // 过滤
//        boolQueryBuilder.filter(QueryBuilders.termQuery("isDelete", 0));
//        if (id != null) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("id", id));
//        }
//        if (notId != null) {
//            boolQueryBuilder.mustNot(QueryBuilders.termQuery("id", notId));
//        }
//        if (userId != null) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("userId", userId));
//        }
//        // 必须包含所有标签
//        if (CollectionUtils.isNotEmpty(tagList)) {
//            for (String tag : tagList) {
//                boolQueryBuilder.filter(QueryBuilders.termQuery("tags", tag));
//            }
//        }
//        // 包含任何一个标签即可
//        if (CollectionUtils.isNotEmpty(orTagList)) {
//            BoolQueryBuilder orTagBoolQueryBuilder = QueryBuilders.boolQuery();
//            for (String tag : orTagList) {
//                orTagBoolQueryBuilder.should(QueryBuilders.termQuery("tags", tag));
//            }
//            orTagBoolQueryBuilder.minimumShouldMatch(1);
//            boolQueryBuilder.filter(orTagBoolQueryBuilder);
//        }
//        // 按关键词检索
//        if (StringUtils.isNotBlank(searchText)) {
//            boolQueryBuilder.should(QueryBuilders.matchQuery("title", searchText));
//            boolQueryBuilder.should(QueryBuilders.matchQuery("content", searchText));
//            boolQueryBuilder.minimumShouldMatch(1);
//        }
//        // 按标题检索
//        if (StringUtils.isNotBlank(title)) {
//            boolQueryBuilder.should(QueryBuilders.matchQuery("title", title));
//            boolQueryBuilder.minimumShouldMatch(1);
//        }
//        // 按内容检索
//        if (StringUtils.isNotBlank(content)) {
//            boolQueryBuilder.should(QueryBuilders.matchQuery("content", content));
//            boolQueryBuilder.minimumShouldMatch(1);
//        }
//        // 分页
//        PageRequest pageRequest = PageRequest.of((int) current, (int) pageSize);
//        // 构造查询
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
//                .withPageable(pageRequest).build();
//        SearchHits<PostEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, PostEsDTO.class);
//        Page<Post> page = new Page<>();
//        page.setTotalRow(searchHits.getTotalHits());
//        List<Post> resourceList = new ArrayList<>();
//        // 查出结果后，从 db 获取最新动态数据（比如点赞数）
//        if (searchHits.hasSearchHits()) {
//            List<SearchHit<PostEsDTO>> searchHitList = searchHits.getSearchHits();
//            List<Long> postIdList = searchHitList.stream().map(searchHit -> searchHit.getContent().getId())
//                    .collect(Collectors.toList());
//            // 从数据库中取出更完整的数据
//            List<Post> postList = this.listByIds(postIdList);
//            if (postList != null) {
//                Map<Long, List<Post>> idPostMap = postList.stream().collect(Collectors.groupingBy(Post::getId));
//                postIdList.forEach(postId -> {
//                    if (idPostMap.containsKey(postId)) {
//                        resourceList.add(idPostMap.get(postId).get(0));
//                    } else {
//                        // 从 es 清空 db 已物理删除的数据
//                        String delete = elasticsearchRestTemplate.delete(String.valueOf(postId), PostEsDTO.class);
//                        log.info("delete post {}", delete);
//                    }
//                });
//            }
//        }
//        page.setRecords(resourceList);
//        return page;
//    }

    @Override
    public Page<Post> listPostByPage(Page<Post> objectPage, QueryWrapper queryWrapper) {
        return this.page(objectPage, queryWrapper);
    }

    @Override
    public QueryWrapper getAdminQueryWrapper(PostAdminQueryRequest postQueryRequest) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (postQueryRequest == null) {
            return queryWrapper;
        }
        String title = postQueryRequest.getTitle();
        String content = postQueryRequest.getContent();
        queryWrapper.like("title", title, StringUtils.isNotBlank(title));
        queryWrapper.like("content", content, StringUtils.isNotBlank(content));
        return queryWrapper;
    }

}
