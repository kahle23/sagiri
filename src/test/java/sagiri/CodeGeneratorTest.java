/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package sagiri;

import kunlun.db.jdbc.support.JdbcTableLoader;
import kunlun.db.jdbc.support.function.MysqlTableCommentConsumer;
import kunlun.generator.render.support.java.JavaCodeGenConfig;
import kunlun.generator.render.support.java.JavaCodeGenerator;
import kunlun.io.file.support.JarFileLoader;
import kunlun.renderer.support.VelocityTextRenderer;
import org.junit.Test;

import static java.util.Arrays.asList;

/**
 * The java code generator Test.
 * @author Sagiri
 */
public class CodeGeneratorTest {

    @Test
    public void generate() {
        // Jdbc table loader config.
        JdbcTableLoader.Config loaderConfig = new JdbcTableLoader.Config();
        loaderConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        loaderConfig.setUrl("jdbc:mysql://127.0.0.1:3306/sagiri?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT&nullCatalogMeansCurrent=true");
        loaderConfig.setUsername("root");
        loaderConfig.setPassword("root");
        loaderConfig.setCatalog("sagiri");
        //loaderConfig.getExcludedTables().add("t_test");
        loaderConfig.getReservedTables().addAll(asList("t_test", "t_test1"));
        loaderConfig.getPostConsumers().add(new MysqlTableCommentConsumer());
        // Java code generator config.
        JavaCodeGenConfig genConfig = new JavaCodeGenConfig();
        genConfig.setTableLoader(new JdbcTableLoader());
        genConfig.setTableLoaderConfig(loaderConfig);
        genConfig.getRemovedTableNamePrefixes().add("t_");
        genConfig.setFileLoader(new JarFileLoader());
        genConfig.setRenderer(new VelocityTextRenderer());
        genConfig.setBaseTemplatePath("templates/generator/spring-boot-mybatis-plus-standard");
        genConfig.setXmlBaseOutputPath("src\\main\\resources\\mapper\\test");
        genConfig.setBasePackageName("sagiri.test");
        genConfig.getCustomAttributes().put("author", "Sagiri");
        genConfig.getCustomAttributes().put("useLombok", true);
        new JavaCodeGenerator().generate(genConfig);
    }

}
