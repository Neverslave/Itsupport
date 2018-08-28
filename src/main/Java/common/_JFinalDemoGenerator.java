package common;


import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.kit.PathKit;

import javax.sql.DataSource;

/**
 * model生成器
 */
public class _JFinalDemoGenerator {
    public static DataSource getDataSource() {
        //PropKit.use("a_little_config.txt");
        DruidPlugin druidPlugin = new DruidPlugin("jdbc:mysql://47.100.23.51/ITsupport?useUnicode=true&characterEncoding=utf-8&useSSL=true", "zhu", "zhuwanqi19940509");
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
        // base model 所使用的包名
        String baseModelPackageName = "model.base";
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/model/base";

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        // 创建生成器
        Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        //generator.setMetaBuilder(new _MetaBuilder(getDataSource()));
        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(false);
        // 添加不需要生成的表名
        generator.addExcludedTable("");
        ;
        // 设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(true);
        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(true);
        // 设置是否生成字典文件
        generator.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        generator.setRemovedTableNamePrefixes("t_");
        // 生成
        generator.generate();

    }
}
