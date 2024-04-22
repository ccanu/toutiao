package com.heima.article.test;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.model.article.pojos.ApArticleContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ArticleFreemarkerTest.class)
@RunWith(SpringRunner.class)
public class ArticleFreemarkerTest {

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;
    @Autowired
    private Configuration configuration;
    @Test
    public void createStaticUrlTest() throws Exception{
        //文章の内容得る
        ApArticleContent apArticleContent = apArticleContentMapper.selectOne
                (Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, "1410052897231708162L"));
        if (apArticleContent != null && StringUtils.isNotBlank(apArticleContent.getContent())){
            //文章の内容をfreemarkerに通じてhtmlタイプとしてえる


            //htmlファイルをミニオに送る

            //ap_article表を改めてurl字段保存します
        }


    }
}
